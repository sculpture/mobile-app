(ns mobile.state.subs
  (:require
    [re-frame.core :refer [reg-sub]]))

(reg-sub
  :image-uris
  (fn [db _]
    (db :image-uris)))
