(ns mobile.interop.expo)

(def image-picker (.-ImagePicker (js/require "expo")))

(defn launch-camera []
  (.then ((aget image-picker "launchCameraAsync") #js {:allowsEditing true}) (fn [])))

