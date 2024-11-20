(ns lms
  "Small tool for loading code systems and transcodings directly into
  the local terminology database.

  Useful for test transcodings because we can avoid a dependency on the Central
  Terminology Server and EU.

  Usage (from console):

    clojure -X lms/download-newest-lms-files
      Download most recent LMS files from ftp.medicinpriser.dk

    clojure -X lms/load-dummy-lms22-transcoding
      Load LMS22 code system into the local terminology database, and add
      a dummy transcoding"
  {:clj-kondo/config '{:lint-as {miner.ftp/with-ftp clojure.core/with-open}}}
  (:require
   [clojure.java.io :as io]
   [clojure.string :as str]
   [miner.ftp :as ftp]
   [next.jdbc :as jdbc]
   [next.jdbc.result-set :as result-set]))

(defn env
  ([v]
   (env v nil))
  ([v default]
   (or (System/getenv v)
       default
       (throw (IllegalArgumentException.
               (format "Environment variable %s is required but not set" v))))))

(def db
  {:dbtype "mysql"
   :dbname "ehealth_ltrdb"
   :host (env "DB_HOST" "mysql")
   :user (env "DB_USERNAME")
   :password (env "DB_PASSWORD")
   :port (env "DB_PORT" "3306")})

;;;; SQL functions

(defn test-db-conn [conn]
  (jdbc/execute! conn ["SELECT oid, name FROM code_system"]))

(comment
  (test-db-conn db)
  )

(defn insert-code-system [code-system conn]
  (let [q "INSERT INTO code_system (oid, name) VALUES (?, ?)"
        res (jdbc/execute-one! conn
                               [q (:oid code-system) (:name code-system)]
                               {:return-keys true
                                :builder-fn result-set/as-unqualified-maps})]
    ;; mysql returns the key as "GENERATED_KEY" while mariadb calls it "inserted_id"
    (assoc code-system :code_system/id (:GENERATED_KEY res))))

(comment
  (jdbc/with-transaction [conn db {:rollback-only true}]
    (insert-code-system {:oid "TEST" :name "TEST"} conn))
  )

(defn upsert-code-system [code-system conn]
  (let [q "SELECT id FROM code_system WHERE oid = ?"
        e (jdbc/execute-one! conn [q (-> code-system :oid)])]
    (if e
      (merge code-system e)
      (insert-code-system code-system conn))))

(defn insert-code-system-version [code-system conn]
  (let [q (str "INSERT INTO code_system_version"
               " (full_name, local_name, code_system_id, status, status_date)"
               " VALUES (?, ?, ?, 'Current', NOW())")
        res (jdbc/execute-one! conn
                               [q
                                (:version code-system)
                                (:version code-system)
                                (:code_system/id code-system)]
                               {:return-keys true
                                :builder-fn result-set/as-unqualified-maps})]
    ;; mysql returns the key as "GENERATED_KEY" while mariadb calls it "inserted_id"
    (assoc code-system :code_system_version/id (:GENERATED_KEY res))))

(defn upsert-code-system-version [code-system conn]
  (let [q (str "SELECT id FROM code_system_version"
               " WHERE code_system_id = ?"
               " AND full_name = ?")
        e (jdbc/execute-one! conn [q (:code_system/id code-system) (:version code-system)])]
    (if e
      (merge code-system e)
      (insert-code-system-version code-system conn))))

(defn invalidate-old-code-system-versions [code-system conn]
  (let [q (str "UPDATE code_system_version"
               "  SET status='Old'"
               "  WHERE code_system_id = ?"
               "    AND id != ?")
        _ (assert (and (:code_system/id code-system) (:code_system_version/id code-system)))
        e (jdbc/execute-one! conn [q
                                   (:code_system/id code-system)
                                   (:code_system_version/id code-system)])]
    code-system))

(defn insert-concepts [code-system conn]
  ;; remove existing concepts for code-system
  (assert (:code_system_version/id code-system))
  (let [q1 (str "DELETE FROM transcoding_association"
                " WHERE source_concept_id IN ("
                "  SELECT id FROM code_system_concept"
                "    WHERE code_system_version_id = ?"
                " )")
        q2 (str "DELETE FROM code_system_concept"
                " WHERE code_system_version_id = ?")]
    (jdbc/execute-one! conn [q1 (:code_system_version/id code-system)])
    (jdbc/execute-one! conn [q2 (:code_system_version/id code-system)]))

  ;; insert concepts
  (assert (seq (:concepts code-system)))
  (let [q (str "INSERT INTO code_system_concept"
               " (code_system_version_id, code, definition, status, status_date)"
               " VALUES (?, ?, ?, 'Current', NOW())")
        code-system-version-id (:code_system_version/id code-system)
        param-groups (map (fn [{:keys [code description]}]
                            [code-system-version-id code description])
                          (:concepts code-system))]
    (jdbc/execute-batch! conn q param-groups {:batch-size 100}))

  code-system)

(defn insert-transcodings [code-system conn]

  ;; Per transcoding (source -> target):
  ;; -----------------------------------
  ;;
  ;; find source_concept_id sci,
  ;; delete existing mapping for sci,
  ;; find target_concept_id tci
  ;; insert row
  (let [q1 (str "SELECT csc.id FROM code_system_concept csc"
                " WHERE code = ?"
                " AND code_system_version_id = ?")
        q2 (str "SELECT csc.id FROM code_system_concept csc"
                " JOIN code_system_version csv ON csv.id = csc.code_system_version_id"
                " JOIN code_system cs ON cs.id = csv.code_system_id"
                " WHERE csc.code = ? "
                "   AND csv.full_name = ?"
                "   AND cs.name = ?")
        insert (str "INSERT INTO transcoding_association"
                    "  (source_concept_id, target_concept_id,"
                    "   transcoding_association_id, status, status_date)"
                    " VALUES (?, ?, 1, 'Valid', NOW())")
        delete (str "DELETE FROM transcoding_association"
                    " WHERE source_concept_id = ?")
        csv (:code_system_version/id code-system)
        transcoding (:transcoding code-system)
        stats (atom {})]
    (doseq [[source-code target] transcoding
            :let [;; Note: mariadb returns the value with the alias'ed table name
                  ;; csc while mysql uses the full table name code_system_concept
                  {source-id :code_system_concept/id} (jdbc/execute-one! conn [q1 source-code csv])
                  delete-res (jdbc/execute-one! conn [delete source-id])
                  _ (swap! stats update :delete-transcoding-count (fnil + 0)
                           (:next.jdbc/update-count delete-res))
                  {target-id :code_system_concept/id} (jdbc/execute-one!
                                                       conn
                                                       [q2
                                                        (:code target)
                                                        (:code-system-version target)
                                                        (:code-system target)])
                  insert-res (jdbc/execute-one! conn [insert source-id target-id])
                  _ (swap! stats update :insert-transcoding-count (fnil + 0)
                           (:next.jdbc/update-count insert-res))]])
    (prn @stats)
    code-system))

(defn map-everything-to-same [code-system target]
  (let [concepts (:concepts code-system)
        _ (assert (seq concepts))
        transcoding (into {} (map (fn [{:keys [code]}] [code target])) concepts)]
    (assoc code-system :transcoding transcoding)))


;;; LMS file parsing

(def lms15-url (io/resource "lms/lms15.txt"))
(def lms22-url (io/resource "lms/lms22.txt"))
(def system-url (io/resource "lms/system.txt"))

(defn lms15-parse-line [line]
  {:unit-type (case (-> line (subs 0 1))
                "3" :strength
                "4" :package
                "1" :time)
   :code (-> line (subs 1 4) str/trim)
   :short-desc (-> line (subs 4 14) str/trim)
   :desc (-> line (subs 14 64) str/trim)})


(defn lms22-parse-line [line]
  {:code (-> line (subs 0 7) str/trim)
   :description (-> line (subs 7 107) str/trim)
   :is-active (case (-> line (subs 107 108))
                "A" true
                "I" false)})

(defn with-reader-fn [url f]
  (with-open [rdr (io/reader url :encoding "cp850")]
    (f rdr)))

(defn lms22-concepts []
  (with-reader-fn lms22-url
    (fn [rdr]
      (into []
            (comp (map lms22-parse-line)
                  (filter :is-active)
                  (map #(select-keys % [:code :description])))
            (line-seq rdr)))))

(comment
  (lms22-concepts)
  )

(defn lms15-packages []
  (with-reader-fn lms15-url
    (fn [rdr]
      (into []
            (comp (map lms15-parse-line)
                  (filter (comp #{:package} :unit-type))
                  (map (fn [m] {:lms15/code (:code m)
                                :lms15/display-name (:desc m)})))
            (line-seq rdr)))))

(comment
  (with-reader-fn lms15-url
    (fn [rdr]
      (into []
            (comp (map lms15-parse-line)
                  (filter (comp #{:package} :unit-type))
                  (map (fn [m] [(:code m) (:desc m)])))
            (line-seq rdr))))

  (with-reader-fn lms22-url
    (fn [rdr]
      (into []
            (map lms22-parse-line)
            (line-seq rdr))))
  )

(defn get-lms-version []
  (with-reader-fn system-url
    (fn [rdr] (-> rdr
                  line-seq
                  first
                  (subs 47 55)))))

;;; Downloading LMS files:

(def ftp-credentials (delay {:username (env "MEDICINPRISER_FTP_USERNAME")
                             :password (env "MEDICINPRISER_FTP_PASSWORD")}))

(defn download-newest-lms-files [& _]
  (ftp/with-ftp [client "ftp://ftp.medicinpriser.dk"
                 :username (:username @ftp-credentials)
                 :password (:password @ftp-credentials)
                 :file-type :binary]
    (ftp/client-cd client "LMS/NYESTE")
    (doseq [f ["system.txt" "lms15.txt" "lms22.txt"]
            :let [target (str "resources/lms/" f)]]
      (ftp/client-get client f (str "resources/lms/" f))
      (println "Downloaded" target))
    (println "Retrieved LMS files from date:" (get-lms-version))))

(comment
  (time (download-newest-lms-files))
  )

;;; Tying it all together

(def lms22
  {:oid "2.16.17.710.802.1000.990.1.20.22"
   :name "LMS22"
   :version (get-lms-version)})

(def edqm-ear-tampon
  {:code "10714000" :code-system "EDQM" :code-system-version "2024-01-09"})

(comment
  (jdbc/with-transaction [conn db {:rollback-only true}]
    (-> lms22
        (assoc :concepts (lms22-concepts))
        (map-everything-to-same edqm-ear-tampon)
        (upsert-code-system conn)
        (upsert-code-system-version conn)
        (invalidate-old-code-system-versions conn)
        (insert-concepts conn)
        (insert-transcodings conn)))

  )

(defn load-dummy-lms22-transcoding [& _args]
  (jdbc/with-transaction [conn db]
    (-> lms22
        (assoc :concepts (lms22-concepts))
        (map-everything-to-same edqm-ear-tampon)
        (upsert-code-system conn)
        (upsert-code-system-version conn)
        (invalidate-old-code-system-versions conn)
        (insert-concepts conn)
        (insert-transcodings conn)))
  (println "Done."))

(comment
  (time (load-dummy-lms22-transcoding))
  )
