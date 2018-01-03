(ns mobile.views.home
  (:require
    [re-frame.core :refer [dispatch subscribe]]
    [mobile.interop.react-native :as rn]))

(defn home-view []
  [rn/view
   {:style {:flex-direction "column"
            :margin 40
            :align-items "center"}}
   (when-let [image-uri @(subscribe [:image-uri])]
     [rn/image {:source {:uri image-uri}
                :style {:width 200
                        :height 200}}])
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
