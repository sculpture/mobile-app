(ns mobile.views.home
  (:require
    [re-frame.core :refer [dispatch]]
    [mobile.interop.react-native :as rn]))

(defn home-view []
  [rn/view
   {:style {:flex-direction "column"
            :margin 40
            :align-items "center"}}
   [rn/touchable-highlight
    {:style {:background-color "#999"
             :padding 10
             :border-radius 5}
     :on-press (fn []
                 (dispatch [:launch-camera]))}
    [rn/text
     {:style {:color "white"
              :text-align "center"
              :font-weight "bold"}}
     "take photo"]]])