(ns mobile.state.fx.fetch)

(defn fetch [{:keys [uri method form-data params] :as opts}]
  (cond
    form-data
    (let [body (js/FormData.)]
      (doseq [[k v] form-data]
        (.append body (name k) v))
      (-> (js/fetch uri
            (clj->js {:method (name method)
                      :headers {"user-id" "013ec717-531b-4b30-bacf-8a07f33b0d43"}
                      :body body}))
          (.then (fn [response]
                   (js/console.log "response" uri response)))
          (.catch (fn [err]
                    (println "error" uri (js->clj err))))))

    params
    (-> (js/fetch uri
          (clj->js {:method (name method)
                    :headers {"Accept" "application/edn"
                              "Content-Type" "application/edn"
                              "user-id" "013ec717-531b-4b30-bacf-8a07f33b0d43"}
                    :body (pr-str params)}))
        (.then (fn [response]
                 (js/console.log "response" uri response)))
        (.catch (fn [err]
                  (println "err" uri err))))))
