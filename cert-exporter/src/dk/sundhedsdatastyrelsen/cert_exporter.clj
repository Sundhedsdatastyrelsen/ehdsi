(ns dk.sundhedsdatastyrelsen.cert-exporter
  (:require
   [aero.core :as aero]
   [clojure.java.io :as io]
   [clojure.tools.logging :as log]
   [ring.adapter.jetty :as jetty])
  (:import
   [io.prometheus.metrics.core.metrics Gauge Gauge$DataPoint]
   [io.prometheus.metrics.expositionformats ExpositionFormats]
   [io.prometheus.metrics.model.registry PrometheusRegistry]
   [java.io ByteArrayOutputStream]
   [java.security KeyStore]
   [java.security.cert X509Certificate]
   [java.time Instant]
   [java.util.concurrent Executors TimeUnit]))

(set! *warn-on-reflection* true)

;;; ---------------------------------------------------------------------------
;;; Config

(defn load-config [path]
  (aero/read-config (io/file path)))

;;; ---------------------------------------------------------------------------
;;; Keystore parsing

(defn load-keystore ^KeyStore [{:keys [path type password]}]
  (let [ks (KeyStore/getInstance type)]
    (with-open [stream (io/input-stream path)]
      (.load ks stream (char-array password)))
    ks))

(defn leaf-cert
  "Returns the X509Certificate for the given alias, or nil if none."
  [^KeyStore ks alias]
  (cond
    (.isCertificateEntry ks alias)
    (.getCertificate ks alias)

    (.isKeyEntry ks alias)
    (when-let [chain (.getCertificateChain ks alias)]
      (first chain))

    :else nil))

(defn cert-info [^X509Certificate cert]
  {:subject (-> cert .getSubjectX500Principal .getName)
   :issuer (-> cert .getIssuerX500Principal .getName)
   :not-before (-> cert .getNotBefore .toInstant)
   :not-after (-> cert .getNotAfter .toInstant)})

(defn keystore-certs
  "Returns a seq of maps with certificate metadata for every alias in the keystore."
  [{:keys [label] :as ks-config}]
  (let [ks (load-keystore ks-config)]
    (for [alias (enumeration-seq (.aliases ks))
          :let  [cert (leaf-cert ks alias)]
          :when (instance? X509Certificate cert)]
      (assoc (cert-info cert)
             :label label
             :alias alias))))

;;; ---------------------------------------------------------------------------
;;; Prometheus metrics

(def labels [:label :alias :subject :issuer])

(defonce ^Gauge cert-not-after
  (-> (Gauge/builder)
      (.name "cert_not_after_timestamp_seconds")
      (.help "Unix timestamp of certificate expiry (not-after)")
      (.labelNames (into-array String (map name labels)))
      (.register PrometheusRegistry/defaultRegistry)))

(defonce ^Gauge cert-not-before
  (-> (Gauge/builder)
      (.name "cert_not_before_timestamp_seconds")
      (.help "Unix timestamp of certificate validity start (not-before)")
      (.labelNames (into-array String (map name labels)))
      (.register PrometheusRegistry/defaultRegistry)))

(comment
  ;; unregister gauges
  (do (-> PrometheusRegistry/defaultRegistry (.unregister cert-not-after))
      (-> PrometheusRegistry/defaultRegistry (.unregister cert-not-before)))
  )

(defn set-gauge! [^Gauge gauge labels data ^double val]
  (let [label-values (into-array String (map #(get data %) labels))
        dp ^Gauge$DataPoint (.labelValues gauge label-values)]
    (.set dp val)))

(defn set-cert-gauges! [{:keys [^Instant not-after ^Instant not-before] :as cert-data}]
  (set-gauge! cert-not-after labels cert-data (.getEpochSecond not-after))
  (set-gauge! cert-not-before labels cert-data (.getEpochSecond not-before)))

(defn update-metrics! [keystores]
  (doseq [ks-config keystores]
    (try
      (doseq [cert (keystore-certs ks-config)]
        (set-cert-gauges! cert))
      (catch Exception e
        (log/warn "failed to read keystore" (:label ks-config) ":" (.getMessage e))))))

(comment
  (update-metrics! [{:path "../NCP/keystore/dev-tls.jks"
                     :type "JKS"
                     :password "password"
                     :label "NCP-dev-tls"}])
  )

;;; ---------------------------------------------------------------------------
;;; HTTP handler

(defn metrics-response []
  (let [baos    (ByteArrayOutputStream.)
        writer  (-> (ExpositionFormats/init) .getPrometheusTextFormatWriter)
        samples (.scrape PrometheusRegistry/defaultRegistry)]
    (.write writer baos samples)
    {:status  200
     :headers {"Content-Type" (.getContentType writer)}
     :body    (.toString baos "UTF-8")}))

(defn handler [request]
  (if (and
       (= (:request-method request) :get)
       (= (:uri request) "/metrics"))
    (metrics-response)
    {:status 404 :headers {} :body "Not found"}))

(comment
  (-> (handler {:method :GET :uri "/metrics"})
      :body
      println)
  )

;;; ---------------------------------------------------------------------------
;;; Scrape loop

(defn start-scrape-loop! [keystores interval-seconds]
  (let [executor (Executors/newSingleThreadScheduledExecutor)]
    (.scheduleAtFixedRate executor
                          #(do (log/debug "Updating certificate metrics")
                               (update-metrics! keystores))
                          interval-seconds
                          interval-seconds
                          TimeUnit/SECONDS)
    executor))

;;; ---------------------------------------------------------------------------
;;; Entry point

(defn -main [& args]
  (let [config-path (or (first args) "config.edn")
        config      (load-config config-path)
        keystores   (:keystores config)
        port        (:port config)
        interval    (:scrape-interval-seconds config)]
    (log/info "Starting cert-exporter on port" port
              "with" (count keystores) "keystore(s),"
              "scrape interval" interval "s")
    (update-metrics! keystores)
    (start-scrape-loop! keystores interval)
    (jetty/run-jetty handler {:port port :join? true})))

(comment
  (-main)
  (load-config "config.edn")
  )
