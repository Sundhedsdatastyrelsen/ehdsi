(ns script
  (:require
   [babashka.fs :as fs]
   [clojure.edn :as edn]
   [etaoin.api :as e]
   [etaoin.keys :as k]
   [taoensso.timbre :as log]))

;; default log level for bb is debug, change it to info
(alter-var-root #'log/*config* assoc :min-level :info)
;; all log messages to stderr
(alter-var-root #'log/*config* update-in [:appenders :println :fn]
                (fn [f] (fn [x] (binding [*out* *err*] (f x)))))



(defn highlight-el [d el]
  (e/js-execute d "var old = arguments[0].style;
 arguments[0].style.border='3px solid red';
setTimeout(() => arguments[0].style = old, 1000);"
                (e/el->ref el)))

(defn highlight [d q & qs]
  (highlight-el d (apply e/query d q qs)))


(def credentials
  (try
    (-> (fs/parent *file*)
        (fs/path "credentials.edn")
        str
        slurp
        edn/read-string)
    (catch Exception e
      (throw (Exception. (str
                          "Missing credentials file. "
                          "Make sure credentials.edn contain {:username \"...\" :password \"...}.")
                         e)))))


(comment
  (def d (e/firefox))
  (e/quit d)
  (def username (:username credentials))
  (def password (:password credentials))
  )

(def fmk-url "https://test2.fmk.netic.dk/fmk/")

(defn fmk-login [d username password]
  (do
    ;; Make sure that there is only one open window
    (while (< 1 (count (e/get-window-handles d)))
      (e/switch-window-next d)
      (e/close-window d))
    (e/switch-window-next d)

    (log/info "Opening FMK at" fmk-url)
    (e/go d fmk-url)

    ;; Click "Gaa til NemLog-in button which launches new window"
    (e/click-visible d ".//button/span[contains(., 'Gå til NemLog-in')]/..")
    (e/switch-window-next d)
    (e/wait d 4)

    ;; Click "Test login"
    (e/click-visible d :LoginMenuItem_3)
    (log/info "Logging in to MitID with test user")

    (e/wait-visible d :MitIDSimulator_InputUsername)
    (e/fill d :MitIDSimulator_InputUsername username)
    (e/fill d :MitIDSimulator_InputPassword password)
    (e/submit d :MitIDSimulator_InputPassword)

    (e/wait 1)

    ;; Click "Sundhedsdatastyrelsen" when presented with a choice
    (e/click-visible d "//div[text()='Sundhedsdatastyrelsen']")

    ;; Go back to main window
    (e/switch-window-next d)

    ;; Choose role from dropdown
    (e/click-visible d "//button[@data-cy='boot-role-dropdown']")
    (e/click-visible d "//button[@data-cy='boot-role-dropdown']/../ul/li[contains(string(), 'læge')]")
    (e/click-visible d "//button[@data-cy='boot-choose-org-continue-button']")

    ;; Choose workplace
    (e/fill d "//input[@data-cy='boot-organisation-search-field']" "rigshospitalet")
    (e/click-visible d ["//input[@data-cy='boot-organisation-search-field']"
                        "../../ul/li[2]"])
    (e/click-visible d "//button[@data-cy='boot-choose-org-finish-button']")
    (e/wait 2))
  )

(def prescription-details
  {:patient-cpr "0201909309"})

(comment
  (def details prescription-details)
  )

(defn create-prescription [d details]
  (do
    ;; Open patient view
    (e/fill d {:tag :input} (:patient-cpr details))
    (e/click d ".//button[@data-cy='boot-main-cpr-search-button']")

    ;; Start create prescription flow
    (e/click-visible d ".//li[@aria-controls='DrugMedicationsTabTS']")
    (e/wait 3)
    (e/click-visible d ".//button[@data-cy='fmk-create-drug-medication-button']")

    (e/fill d ".//input[@data-cy='fmk-drug-search-field']" "pinex")
    (e/wait 1)
    (e/click-visible d [".//input[@data-cy='fmk-drug-search-field']" "../../ul/li[contains(text(), 'filmovertrukne tabletter')]"])

    (let [xp ".//button[@data-cy='fmk-indication-dropdown']"]
      (e/click-visible d xp)
      (e/click-visible d [xp "../ul/li[2]"]))

    (e/click d ".//button[@data-cy='boot-stepper-forward-button']")
    (e/wait 1)
    (e/click d ".//button[@data-cy='boot-stepper-forward-button']")

    (e/click-visible d [".//fieldset[@data-cy='fmk-dosage-proposal-radio-group']" "./div/input"])
    (e/click d ".//button[@data-cy='boot-stepper-forward-button']")
    (e/wait 1)
    (e/click d ".//button[@data-cy='boot-stepper-forward-button']")
    (e/click-visible d ".//input[@data-cy='fmk-prescription-switch']")

    (e/click-visible d ".//button[@data-cy='fmk-package-dropdown']")

    (e/click-visible d [".//button[@data-cy='fmk-package-dropdown']"
                        "../ul/ul/li[@data-has-image]"])

    (e/fill d ".//input[@type='tel']" "87654321" k/tab)

    (e/click d ".//button[@data-cy='fmk-create-drug-medication-form-button']")
    (e/wait 1)
    (when (e/visible? d ".//section[@aria-label='Dobbeltregistrering footer']")
      (e/click d ".//span[text()='Fortsæt']/.."))
    (e/wait 2)))

(comment
  (do
    (fmk-login d username password)
    (create-prescription d prescription-details))
  (e/refresh d)
  )

(defn main []
  (e/with-firefox d
    (fmk-login d (:username credentials) (:password credentials))
    (create-prescription d prescription-details)))

(when (= *file* (System/getProperty "babashka.file"))
  (main))
