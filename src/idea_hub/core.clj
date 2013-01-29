(ns idea-hub.core
	(:use [seesaw.core])
	(:gen-class))
(use 'seesaw.core)
(use 'seesaw.border)
(import (javax.swing.border LineBorder))

(javax.swing.UIManager/setLookAndFeel "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel")

(defn make-item []
	(text 	
		:multi-line? true
		:wrap-lines? true
		:rows 10
		:margin [5 5 5 5]
		:border (line-border)
		:columns 15))

(defn make-column []
	(let [col (vertical-panel :class :todos)
		  btn (button :class :add-item
                      :text "+"
                      :listen [:action (fn [e] 
                              	(config! col :items (conj (config col :items) (make-item))))])]

		(config! col :items (conj (config col :items) btn))))

(defn make-content [] 
	(flow-panel :items [(button :id :add-column :text "Add column") (make-column)]))

(defn make-frame []
	(frame
		:title "Idea Hub"
		:on-close :exit
		:content (make-content)))

(defn -main [& args]
		(invoke-soon(-> (make-frame) pack! show!)))
	
	