(ns mobile.state.events
  (:require
    [re-frame.core :refer [reg-event-fx dispatch]]
    [mobile.interop.expo :as expo]))

(reg-event-fx
  :initialize-db
  (fn [_ _]
    {:db {:image-uri nil}}))

(reg-event-fx
  :launch-camera
  (fn [_ _]
    (expo/launch-camera (fn [{:keys [uri]}]
                          (dispatch [:handle-image-capture uri])))
    {}))

(reg-event-fx
  :handle-image-capture
  (fn [{db :db} [_ uri]]
    {:db (assoc db :image-uri uri)}))
