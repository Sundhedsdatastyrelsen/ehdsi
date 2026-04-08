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
   [java.util.concurrent Executors ExecutorService TimeUnit]
   [org.eclipse.jetty.server Server]))

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
  [{:keys [description path] :as ks-config}]
  (let [ks (load-keystore ks-config)]
    (for [alias (enumeration-seq (.aliases ks))
          :let  [cert (leaf-cert ks alias)]
          :when (instance? X509Certificate cert)]
      (assoc (cert-info cert)
             :description description
             :path path
             :alias alias))))

;;; ---------------------------------------------------------------------------
;;; Prometheus metrics

(defonce registry (atom {}))

(defn unregister-gauge!
  "Remove a gauge from the default prometheus registry."
  [g]
  (when-some [^Gauge gauge (@registry (:name g))]
    (-> PrometheusRegistry/defaultRegistry (.unregister gauge))))

(defn register-gauge!
  "Register a Gauge with the default Prometheus registry. Returns the Gauge object."
  [g]
  ;; Perform clean-up first to make registration idempotent
  (unregister-gauge! g)
  (let [raw (-> (Gauge/builder)
                (.name (:name g))
                (.help (:help g))
                (.labelNames (into-array String (map name (:labels g))))
                (.register PrometheusRegistry/defaultRegistry))]
    (swap! registry assoc (:name g) raw)
    (assoc g :raw raw)))

(defn prefix
  "Add a prefix to the metric name of the gauge"
  [g prefix]
  (update g :name #(str prefix %)))

(def not-after-gauge
  {:name "not_after_timestamp_seconds"
   :help "Unix timestamp of certificate expiry (not-after)"
   :labels [:description :path :alias :subject :issuer]
   :value-fn (fn [cert-data] (.getEpochSecond ^Instant (:not-after cert-data)))})

(def not-before-gauge
  {:name "not_before_timestamp_seconds"
   :help "Unix timestamp of certificate validity start (not-before)"
   :labels [:description :path :alias :subject :issuer]
   :value-fn (fn [cert-data] (.getEpochSecond ^Instant (:not-before cert-data)))})

(defn set-gauge!
  "Update a gauge with a data point"
  [gauge data]
  (let [label-values (into-array String (map #(get data %) (:labels gauge)))
        ^double value ((:value-fn gauge) data)]
    (-> gauge
        ^Gauge (:raw)
        ^Gauge$DataPoint (.labelValues label-values)
        (.set value))))

(defn update-metrics! [keystores gauges]
  (doseq [ks-config keystores
          data (try
                 (keystore-certs ks-config)
                 (catch Exception e
                   (log/error "failed to read keystore" (:path ks-config) ":" (.getMessage e))))
          :when data
          gauge gauges]
    (set-gauge! gauge data)))

(comment
  (def *gauges (mapv register-gauge! [not-after-gauge not-before-gauge]))

  (update-metrics! [{:path "../NCP/keystore/dev-tls.jks"
                     :type "JKS"
                     :password "password"
                     :description "NCP-dev-tls"}]
                   *gauges)
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

(defn start-scrape-loop! [keystores gauges interval-seconds]
  (let [executor (Executors/newSingleThreadScheduledExecutor)]
    (.scheduleAtFixedRate executor
                          #(do (log/debug "Updating certificate metrics")
                               (update-metrics! keystores gauges))
                          interval-seconds ; initialDelay
                          interval-seconds ; period
                          TimeUnit/SECONDS)
    executor))

;;; ---------------------------------------------------------------------------
;;; Entry point

(defn start! [config]
  (let [keystores (:keystores config)
        gauges (->> [not-after-gauge not-before-gauge]
                    (mapv #(prefix % (:prefix config)))
                    (mapv register-gauge!))
        port (:port config)
        interval (:scrape-interval-seconds config)]
    (log/info "Starting cert-exporter on port" port
              "with" (count keystores) "keystore(s),"
              "scrape interval" interval "s")
    (update-metrics! keystores gauges)
    {:executor (start-scrape-loop! keystores gauges interval)
     :jetty (jetty/run-jetty handler {:port port :join? false})}))

(defn stop! [{:keys [^ExecutorService executor ^Server jetty]}]
  (.shutdown executor)
  (.stop jetty))


(defn -main [& args]
  (let [config-path (or (first args) "config.edn")
        config (load-config config-path)]
    (start! config)))

(comment
  (def *server (start! (assoc (load-config "config.edn") :port 9091)))
  (stop! *server)
  )
