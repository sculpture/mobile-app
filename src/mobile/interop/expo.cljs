(ns mobile.interop.expo
  (:require
    [reagent.core :as r]))

(def expo (js/require "expo"))

(def image-picker (.-ImagePicker expo))
(def keep-awake  (r/adapt-react-class (.-KeepAwake expo)))

(defn launch-camera [callback]
  (.then
    ((aget image-picker "launchCameraAsync") #js {:allowsEditing true})
    (fn [response]
      (callback (js->clj response :keywordize-keys true)))))
