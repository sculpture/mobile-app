(ns mobile.state.events
  (:require
    [re-frame.core :refer [reg-event-fx]]
    [mobile.interop.expo :as expo]))

(reg-event-fx
  :initialize-db
  (fn [_ _]
    {:db {}}))

(reg-event-fx
  :launch-camera
  (fn [_ _]
    (expo/launch-camera)
    {}))
