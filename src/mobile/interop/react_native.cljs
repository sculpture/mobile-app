(ns mobile.interop.react-native
  (:require
    [reagent.core :as r]))

(def ReactNative (js/require "react-native"))

(def button (r/adapt-react-class (.-Button ReactNative)))
(def image (r/adapt-react-class (.-Image ReactNative)))
(def keyboard-avoiding-view (r/adapt-react-class (.-KeyboardAvoidingView ReactNative)))
(def text (r/adapt-react-class (.-Text ReactNative)))
(def text-input (r/adapt-react-class (.-TextInput ReactNative)))
(def touchable-highlight (r/adapt-react-class (.-TouchableHighlight ReactNative)))
(def view (r/adapt-react-class (.-View ReactNative)))
