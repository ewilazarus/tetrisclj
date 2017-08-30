(ns bcsat-clj.core
  (:use [bcsat-clj.game :as g]
        [bcsat-clj.common :as c])
  (:gen-class))


(defn handle-input 
  "Reads from std-in and returns a keyword corresponding to one of the valid
  options ('a' 'd' 'w' 's')"
  []
  (let [input (keyword (read))]
    (if (contains? #{:a :d :w :s} input)
      input)))

(defn game-over 
  "Prints game over to the screen"
  []
  (do (println "\n  **********************")
      (println "  ****** GAME OVER *****")
      (println "  **********************\n")))

(defn -main 
  "Application entry point"
  []
  (loop [game (g/new-game)
         attempt false]
    (do (c/clearscreen) 
        (if (and attempt (not (g/movements-available? game)))
          (game-over)
          (do (g/display game)
              (let [input (handle-input)]
                (if input
                  (let [coordinates (g/handle-action game input)]
                    (if coordinates
                      (recur (g/new-game (:board game) coordinates) false)
                      (if (g/movements-available? game)
                        (recur game false)
                        (recur (g/new-game (g/perpetuate! game)) true))))
                  (recur game false))))))))
