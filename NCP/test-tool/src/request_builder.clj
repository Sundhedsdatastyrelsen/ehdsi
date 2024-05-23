(ns request-builder
  (:require
   [clojure.java.io :as io]
   [saml20-clj.coerce :as saml-coerce]
   [saml20-clj.crypto :as saml-crypto]
   [selmer.parser :as selmer]))

(set! *warn-on-reflection* true)

(defn element-to-string
  "Serialize an org.w3c.dom.Element to string"
  [element]
  (let [factory (javax.xml.transform.TransformerFactory/newInstance)
        transformer (doto (.newTransformer factory)
                      (.setOutputProperty
                       javax.xml.transform.OutputKeys/OMIT_XML_DECLARATION
                       "yes"))
        source (javax.xml.transform.dom.DOMSource. element)
        w (java.io.StringWriter.)]
    (.transform transformer source (javax.xml.transform.stream.StreamResult. w))
    (str w)))

(defn sign
  "with thanks to [[saml-crypto/sign]]"
  ^org.w3c.dom.Element
  [object credential cert & {:keys [signature-algorithm
                                    canonicalization-algorithm]
                             :or   {signature-algorithm        [:rsa :sha256]
                                    canonicalization-algorithm :excl-omit-comments}}]
  (when-let [object (saml-coerce/->SAMLObject object)]
    (when-let [^org.opensaml.security.credential.Credential credential
               (try
                 (saml-coerce/->Credential credential)
                 (catch Throwable _
                   (saml-coerce/->Credential (saml-coerce/->PrivateKey credential))))]
      (let [signature (doto (.buildObject (org.opensaml.xmlsec.signature.impl.SignatureBuilder.))
                        (.setSigningCredential credential)
                        (.setSignatureAlgorithm (or (get-in saml-crypto/signature-algorithms signature-algorithm)
                                                    (throw (ex-info "No matching signature algorithm"
                                                                    {:algorithm signature-algorithm}))))
                        (.setCanonicalizationAlgorithm (or (get saml-crypto/canonicalization-algorithms canonicalization-algorithm)
                                                           (throw (ex-info "No matching canonicalization algorithm"
                                                                           {:algorithm canonicalization-algorithm})))))
            key-info-gen (doto (new org.opensaml.xmlsec.keyinfo.impl.X509KeyInfoGeneratorFactory)
                           (.setEmitEntityCertificate true))]
        (when-let [key-info (.generate (.newInstance key-info-gen) cert)] ; No need to test X509 coercion first
          (.setKeyInfo signature key-info))
        (.setSignature object signature)
        (let [element (saml-coerce/->Element object)]
          (org.opensaml.xmlsec.signature.support.Signer/signObject signature)
          element)))))

(defn- load-xml [file context-map]
  (let [f (io/file file)]
    (when-not (and f (.exists f))
      (throw (ex-info (str "File does not exist: " file) {:file file})))
    (-> (io/file file)
        .toURL
        (selmer/render-file context-map)
        saml-coerce/->Element)))

(defn build [args]
  (let [{:keys [hcp-template trc-template request-template
                cert private-key] :as args} (into {} (map (juxt key (comp str val))) args)
        cert (try (saml-coerce/->Credential (slurp cert))
                  (catch Throwable _ (throw (ex-info (str "Invalid/missing certificate: " cert)
                                                     (or args {})))))
        private-key (try (saml-coerce/->PrivateKey (slurp private-key))
                         (catch Exception _ (throw (ex-info (str "Invalid/missing private key: " private-key)
                                                            (or args {})))))
        now (java.time.Instant/now)
        sign-assertion (fn [assertion-element]
                         (-> assertion-element (sign private-key cert) element-to-string))
        hcp-assertion-id (str "id" (random-uuid))
        common-context {:issue-instant now
                        :authn-instant now
                        :not-before (-> now (.minusSeconds 60))
                        :not-on-or-after (-> now (.plusSeconds (* 60 60)))}

        hcp-assertion (load-xml hcp-template
                                (assoc common-context
                                       :assertion-id hcp-assertion-id))
        trc-assertion (when trc-template
                        (load-xml trc-template
                                  (assoc common-context
                                         :assertion-id (str "id" (random-uuid))
                                         :hcp-assertion-id hcp-assertion-id))) ]

    (-> (io/file request-template)
        .toURL
        (selmer/render-file {:message-id (str "uuid:" (random-uuid))
                             :hcp-assertion (sign-assertion hcp-assertion)
                             :trc-assertion (when trc-assertion
                                              (sign-assertion trc-assertion))})
        print)))

(comment
  (.toURL (io/file "hcp1.xml"))

  (build {:cert "../test/client-dk.crt" :private-key "../test/client-dk.key"
          :request-template "request1.xml" :hcp-template "hcp1.xml"
          :trc-template "trc1.xml"})

  )
