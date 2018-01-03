(ns mobile.interop.expo)

(def image-picker (.-ImagePicker (js/require "expo")))

(defn launch-camera [callback]
  (.then
    ((aget image-picker "launchCameraAsync") #js {:allowsEditing true})
    (fn [response]
      (callback (js->clj response :keywordize-keys true)))))

