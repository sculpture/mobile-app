(ns mobile.core
  (:require
    [re-frame.core :refer [dispatch-sync]]
    [mobile.state.events]
    [mobile.state.subs]
    [mobile.views.home :refer [home-view]]))

(defn app-root []
  [home-view])

(defn init []
  (dispatch-sync [:initialize-db]))
