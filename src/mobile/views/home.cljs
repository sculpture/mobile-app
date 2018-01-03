(ns mobile.views.home
  (:require
    [re-frame.core :refer [dispatch subscribe]]
    [mobile.interop.react-native :as rn]))

(defn images-view [image-uris]
  [rn/view {:style {:flex 1
                    :flex-wrap "wrap"
                    :flex-direction "row"}}
   (for [image-uri image-uris]
     ^{:key image-uri}
     [rn/image {:source {:uri image-uri}
                :style {:width "25%"
                        :height 100}}])])

(defn take-image-button-view []
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
    "take photo"]])

(defn home-view []
  [rn/view
   {:style {:flex 1
            :margin-top 24
            :flex-direction "column"
            :align-items "center"}}
   [images-view (reverse @(subscribe [:image-uris]))]
   [take-image-button-view]])
