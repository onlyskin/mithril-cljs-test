(ns mithril-cljs-test.core
  (:require cljsjs.mithril))

(def state (atom {:text "atom-text"}))

(def body (.-body js/document))

(def para-button (js-obj
                   "view"
                   #(js/m "p" (js-obj) (.-text (.-attrs %)))
                   ))

(def page (js-obj
            "view"
            #(array
               (js/m "p" (js-obj) "Test Paragraph")
               (js/m para-button (js-obj "text" (@state :text)))
               (js/m
                 "p"
                 (js-obj
                   "onclick" (fn []
                               (reset! state {:text "new-text"})
                               ))
                 "Click Me"))))

(.mount js/m body page)

