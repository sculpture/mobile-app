(ns mobile.state.events
  (:require
    [cljs-uuid-utils.core :as uuid]
    [re-frame.core :refer [reg-event-fx dispatch reg-fx]]
    [mobile.state.fx.fetch :refer [fetch]]
    [mobile.interop.expo :as expo]))

(reg-fx :fetch fetch)

(reg-event-fx
  :initialize-db
  (fn [_ _]
    {:db {:image-uris []
          :attributes {:title nil
                       :artist nil
                       :year nil}
          :focused-attribute nil}}))

(reg-event-fx
  :launch-camera
  (fn [_ _]
    (expo/launch-camera (fn [{:keys [uri]}]
                          (dispatch [:add-image uri])))
    {}))

(reg-event-fx
  :add-image
  (fn [{db :db} [_ uri]]
    {:db (update-in db [:image-uris] conj uri)}))

(reg-event-fx
  :set-attribute
  (fn [{db :db} [_ key value]]
    {:db (assoc-in db [:attributes key] value)}))

(reg-event-fx
  :focus-attribute
  (fn [{db :db} [_ key]]
    {:db (assoc db :focused-attribute key)}))

(defn slugify [text]
  (if text
    (-> text
      clojure.string/trim
      clojure.string/lower-case
      (clojure.string/replace #"[=+|,/?%#&\.\!:$'@()-]*" "")
      (clojure.string/replace #" +" "-"))
    ""))

(reg-event-fx
  :submit
  (fn [{db :db} _]
    (let [artist {:id (uuid/make-random-uuid)
                  :name (get-in db [:attributes :artist])
                  :slug (slugify (get-in db [:attributes :artist]))}
          sculpture {:id (uuid/make-random-uuid)
                     :title (get-in db [:attributes :title])
                     :year (get-in db [:attributes :year])
                     :artist-ids [(artist :id)]
                     :slug (slugify (get-in db [:attributes :title]))}
          photos (for [image-uri (db :image-uris)]
                   {:id (uuid/make-random-uuid)
                    :file image-uri
                    :sculpture-id (sculpture :id)})]
      {:dispatch-n (concat
                     [[:-post-artist artist]]
                     (for [photo photos]
                       [:-upload-photo photo])
                     [[:-post-sculpture sculpture]])})))

(def api-uri "http://192.168.0.13:7862/api")

(reg-event-fx
  :-upload-photo
  (fn [_ [_ {:keys [id file sculpture-id]}]]
    {:fetch {:uri (str api-uri "/photos")
             :method :post
             :form-data {:id (str id)
                         :sculpture-id (str sculpture-id)
                         :file #js {:uri file
                                    :type "image/jpg"
                                    :name (str id ".jpg")}}}}))

(reg-event-fx
  :-post-sculpture
  (fn [_ [_ {:keys [id title year artist-ids slug]}]]
    {:fetch {:uri (str api-uri "/sculptures")
             :method :post
             :params {:id id
                      :title title
                      :year year
                      :artist-ids artist-ids
                      :slug slug}}}))

(reg-event-fx
  :-post-artist
  (fn [_ [_ {:keys [id name slug]}]]
    {:fetch {:uri (str api-uri "/artists")
             :method :post
             :params {:id id
                      :name name
                      :slug slug}}}))
