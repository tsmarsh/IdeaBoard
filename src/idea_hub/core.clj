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
	(vertical-panel :id :ready :items [(button :id :add-item
                                  :text "+")]))

(defn make-content [] 
	(flow-panel :items [(make-column)]))

(defn make-frame []
	(frame
		:title "Idea Hub"
		:on-close :exit
		:content (make-content)))

(defn add-behaviour [root]
  (listen (select root [:#add-item]) :action (fn [e] (alert "Success!")))
  root)

(defn -main [& args]
		(invoke-soon(-> (make-frame) (add-behaviour ) pack! show!)))
	
	