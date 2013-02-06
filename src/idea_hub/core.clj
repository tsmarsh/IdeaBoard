(ns idea-hub.core
	(:use [seesaw.core])
	(:gen-class))
(use 'seesaw.core)
(use 'seesaw.border)
(import (javax.swing.border LineBorder))

(javax.swing.UIManager/setLookAndFeel "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel")

(def model (ref {:lists [(ref {:name "Dev Ready" :items [(ref {:text "Story one"}) (ref {:text "Story two"}) (ref {:text "Story three"}) ]})  
                         (ref {:name "In development" :items [(ref {:text "Story four"}) (ref {:text "Story five"})]})
                         (ref {:name "Done" :items [(ref {:text "A completed story"})]})]}))

(defn make-item [item]
	(flow-panel :items [
				(text :text (:text @item)	
          :multi-line? true
						:wrap-lines? true
						:rows 10
						:margin [20 20 20 20]
						:border (line-border)
						:columns 15)]))

(defn make-column [column]
	(let [col (vertical-panel 	:class :col)
		  btn (button :class :add-item
                      :text "+"
                      :listen [:action (fn [e] 
                                         (let [todos (first (select col [:.todos]))]
                                           (config! todos :items (concat (config todos :items) [(make-item (ref {:text ""}))]))))])
    items (map #(make-item %) (:items @column))]
                      

   (config! col :items (conj (config col :items) (vertical-panel :class :todos :items items)))
   (scrollable col :column-header (flow-panel :items [(text	:text (:name @column)) btn]))))

(defn make-content [model] 
	(let [col-panel (horizontal-panel :items (map #(make-column %) (:lists @model)))
		  btn (button 	:id :add-column 
						:text "Add column" 
						:listen [:action (fn [e]
											(config! col-panel :items (concat (config col-panel :items) [(make-column (ref {:name "New" :items []}))])))])]
   
   (border-panel 	:north (horizontal-panel :items [(label :text "Board"
                                                          :font  (seesaw.font/font	:name "ARIAL" 
                                                                                    :style :bold
                                                                                    :size 18))]) 
                  :east (vertical-panel :items [btn])
                  :center col-panel)))

(defn make-frame [model]
	(frame
		:title "Idea Hub"
		:on-close :exit
		:content (make-content model)
		:height 300
		:width 400))

(defn -main [& args]
		(invoke-soon(-> (make-frame model) show!)))
	
	