(ns mithril-cljs-test.core
  (:require cljsjs.mithril cljsjs.d3))

(def state (atom {:text "atom-text"}))

(def body (.-body js/document))

(def para-button #js {"view"
                      #(js/m "p" (js-obj) (.-text (.-attrs %)))})

(def config (atom {:data #js ["a" "c" "d"]}))

(defn update-d3 []
  (let [p (.. js/d3
              (select "#d3-container")
              (selectAll "p")
              (data (@config :data) (fn [d] d)))]

      (.. p
        (attr "class" "update")
        (text (fn [d] d)))

      (.. p
          (enter)
          (append "p")
          (attr "class" "enter")
          (text (fn [d] d)))
      
      (.. p
          (exit)
          (remove))))

(def page #js {"oncreate"
               #(update-d3)
               "onupdate"
               #(update-d3)
               "view"
               #(array
                  (js/m "" #js {"id" "d3-container"})
                  (js/m "p" #js {} "Test Paragraph")
                  (js/m para-button #js {"text" (@state :text)})
                  (js/m
                    "p"
                    #js {"onclick" (fn []
                                     (reset! state {:text "new-text"})
                                     (reset! config {:data #js ["d" "e" "f"]})
                                     )}
                    "Click Me"))})

(.mount js/m body page)

