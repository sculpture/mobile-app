(ns mobile.views.home
  (:require
    [reagent.core :as r]
    [re-frame.core :refer [dispatch dispatch-sync subscribe]]
    [mobile.interop.react-native :as rn]
    [mobile.interop.expo :refer [keep-awake]]))

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
  [rn/button
   {:on-press (fn []
                (dispatch [:launch-camera]))
    :title "Take Photo"}])

(defn data-title-input-view [{:keys [label key return-key-type next-key]}]
  [rn/view
   {:style
    {:flex 1
     :flex-direction :column}}
   [rn/text {} label]
   [rn/text-input
    {:ref (fn [node]
            (when (and node @(subscribe [:focused? key]))
              (.focus node)))
     :value @(subscribe [:attribute key])
     :style {:height 40}
     :focus @(subscribe [:focused? key])
     :return-key-type return-key-type
     :on-submit-editing (fn []
                          (dispatch [:focus-attribute next-key]))
     :on-change-text (fn [text]
                       (dispatch-sync [:set-attribute key text])
                       (r/flush))}]])

(defn data-entry-view []
  [rn/keyboard-avoiding-view
   {:style {:flex 1
            :flex-direction "column"}}
   [data-title-input-view
    {:label "Title"
     :key :title
     :return-key-type "next"
     :next-key :artist}]
   [data-title-input-view
    {:label "Artist"
     :key :artist
     :return-key-type "next"
     :next-key :year}]
   [data-title-input-view
    {:label "Year"
     :key :year
     :return-key-type "done"
     :next-key nil}]])

(defn home-view []
  [rn/view
   {:style {:flex 1
            :margin-top 24
            :flex-direction "column"
            :align-items "center"}}
   [keep-awake]
   [images-view (reverse @(subscribe [:image-uris]))]
   [take-image-button-view]
   [rn/keyboard-avoiding-view
    {:behavior "padding"
     :style {:flex 1
             :flex-direction "column"
             :padding 20
             :width "100%"}}
    [data-entry-view]
    [rn/button
     {:title "Submit"
      :on-press (fn []
                  (dispatch [:submit]))}]]])
