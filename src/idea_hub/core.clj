(ns idea-hub.core
	(:use [seesaw.core])
	(:gen-class))
(use 'seesaw.core)
(use 'seesaw.border)
(import (javax.swing.border LineBorder))

(javax.swing.UIManager/setLookAndFeel "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel")

(defn make-item []
	(flow-panel :items [
				(text 	:multi-line? true
						:wrap-lines? true
						:rows 10
						:margin [20 20 20 20]
						:border (line-border)
						:columns 15)]))

(defn make-column [name]
	(let [col (vertical-panel 	:class :col)
		  btn (button :class :add-item
                      :text "+"
                      :listen [:action (fn [e] 
                      						(let [todos (first (select col [:.todos]))]
                      							(config! todos :items (concat (config todos :items) [(make-item)]))))])]

   (config! col :items (conj (config col :items) (vertical-panel :class :todos)))
   (scrollable col :column-header (flow-panel :items [(text	:text name) btn]))))

(defn make-content [] 
	(let [col-panel (horizontal-panel)
		  btn (button 	:id :add-column 
						:text "Add column" 
						:listen [:action (fn [e]
											(config! col-panel :items (concat (config col-panel :items) [(make-column "New…")])))])]
   
   (border-panel 	:north (horizontal-panel :items [(label :text "Board"
                                                          :font  (seesaw.font/font	:name "ARIAL" 
                                                                                    :style :bold
                                                                                    :size 18))]) 
                  :east (vertical-panel :items [btn])
                  :center col-panel)))

(defn make-frame []
	(frame
		:title "Idea Hub"
		:on-close :exit
		:content (make-content)
		:height 300
		:width 400))

(defn -main [& args]
		(invoke-soon(-> (make-frame) show!)))
	
	