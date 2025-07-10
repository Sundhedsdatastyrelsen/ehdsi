#!/usr/bin/env bb

;;; Fetch WSDL and XSD dependencies.
;;; Run with Babashka: https://github.com/babashka/babashka#installation

(ns fetch-wsdl
  (:require
   [babashka.fs :as fs]
   [babashka.http-client :as http]
   [clojure.string :as str]
   [clojure.java.io :as io])
  (:import
   (java.net URI)))

(defn extract-filename [url]
  (if (str/includes? url "?xsd=")
    ;; Extract filename from ?xsd= parameter
    (second (re-matches #".*\?xsd=([^&]*).*" url))
    ;; Extract filename from URL path, removing query parameters
    (first (str/split (last (str/split url #"/")) #"\?"))))

(defn non-empty? [file-path]
  (and (fs/exists? file-path)
       (pos? (fs/size file-path))))

(defn download-file [url file-path]
  (println (str "Fetching " url " -> " file-path))
  (io/copy
   (:body (http/get url {:as :stream}))
   (fs/file file-path)))

(defn extract-schema-locations [file-path]
  (->> (re-seq #"schemaLocation=\"([^\"]*)\"" (slurp file-path))
       (map second)))

(defn uri [p] (if (instance? URI p) p (URI. p)))

(defn fetch-files [urls base-url]
  (let [fetch-file
        (fn [url]
          (let [file-path (str "xsd/" (extract-filename url))]
            (if (non-empty? file-path)
              (println "File exists, skipping:" file-path)
              (let [resolved-url (.resolve (uri base-url) url)]
                (download-file resolved-url file-path)
                (fetch-files (extract-schema-locations file-path) resolved-url)))))]
    (doall (pmap fetch-file urls))))

(defn fix-schema-locations [file substituent-prefix]
  (let [new-content
        (-> (slurp file)
            ;; Fix schemaLocation in xsds
            (str/replace #"schemaLocation=\"[^\"]*/([^\"]+)\""
                         (str "schemaLocation=\"" substituent-prefix "$1\""))
            ;; Handle ?xsd= parameters in schemaLocation
            (str/replace #"schemaLocation=\"[^\"]*\?xsd=([^\"]+)\""
                         (str "schemaLocation=\"" substituent-prefix "$1\"")))]
    (spit file new-content)))

(defn fetch-wsdl [wsdl-url wsdl-local-path]
  (println "Processing WSDL...")

  (or (non-empty? wsdl-local-path)
      (download-file wsdl-url wsdl-local-path))

  ;; Create xsd directory if it doesn't exist
  (fs/create-dirs "xsd")

  ;; Fetch schema files
  (fetch-files (extract-schema-locations wsdl-local-path) wsdl-url)

  ;; Fix schema locations
  (fix-schema-locations wsdl-local-path "./xsd/")

  ;; Fix schema locations in XSD files
  (->> (fs/list-dir "xsd")
       (filter fs/regular-file?)
       (map fs/file)
       (run! #(fix-schema-locations % "./"))))

(def minlog-wsdl-url
  "http://test1.ekstern-test.nspop.dk:8080/minlog2-registration/20250312/RegisterService?wsdl")

(when (= *file* (System/getProperty "babashka.file"))
  (fetch-wsdl minlog-wsdl-url "minlog.wsdl"))
