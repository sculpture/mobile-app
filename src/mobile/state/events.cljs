(ns mobile.state.events
  (:require
    [re-frame.core :refer [reg-event-fx dispatch]]
    [mobile.interop.expo :as expo]))

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
