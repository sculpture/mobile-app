(ns mobile.state.subs
  (:require
    [re-frame.core :refer [reg-sub]]))

(reg-sub
  :image-uri
  (fn [db _]
    (db :image-uri)))