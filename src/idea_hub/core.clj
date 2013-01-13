(ns idea-hub.core
	(:use [seesaw.core])
	(:gen-class))
(use 'seesaw.core)
(use 'seesaw.color)
(import (javax.swing.border LineBorder))

(javax.swing.UIManager/setLookAndFeel "org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel")

(defn make-item []
	(text 	
		:multi-line? true
		:wrap-lines? true
		:rows 10
		:margin 7
		:columns 15))

(defn make-column []
	(vertical-panel :items [(make-item)]))

(defn make-content [] 
	(flow-panel :items [(make-column)]))

(defn make-frame []
	(frame
		:title "Idea Hub"
		:size [400 :by 300]
		:on-close :exit
		:content (make-content)))

(defn -main [& args]
		(invoke-soon(-> (make-frame) pack! show!)))
	
	