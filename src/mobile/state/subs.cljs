(ns mobile.state.subs
  (:require
    [re-frame.core :refer [reg-sub]]))

(reg-sub
  :image-uris
  (fn [db _]
    (db :image-uris)))

(reg-sub
  :attribute
  (fn [db [_ key]]
    (get-in db [:attributes key])))

(reg-sub
  :focused?
  (fn [db [_ key]]
    (= key (db :focused-attribute))))
