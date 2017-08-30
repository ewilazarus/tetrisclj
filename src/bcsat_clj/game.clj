(ns bcsat-clj.game
  (:require [bcsat-clj.board :as b]
            [bcsat-clj.piece :as p]
            [bcsat-clj.coordinates :as c]
            [bcsat-clj.movement :as m]))

(defrecord Game [board coordinates])


(defn new-coordinates 
  "Creates a new piece at a random x-coordinate and returns its coordinates"
  []
  (let [piece (p/random)
        x (-> (- b/size (p/width piece))
              (inc)
              (rand-int))
        y 0]
    (vec (c/piece->coordinates piece x y))))

(defn new-game 
  "Creates a new game"
  ([board coordinates] (Game. board coordinates))
  ([board] (new-game board (new-coordinates)))
  ([] (new-game (b/init))))
        
(defn move!
  "Moves the piece coordinates according to a direction function"
  [game direction]
  (c/coordinates->board! (:board game)
                         (direction (:coordinates game))))
(defn perpetuate!
  "Returns the board with the applied current coordinates"
  [game]
  (move! game identity))

(defn handle-action
  "Dispatches movements based on the input received"
  [game input]
  (let [test-input (fn [direction] (if (move! game direction)
                                     (direction (:coordinates game))))]
    (cond (= input :a) (test-input m/LEFT)
          (= input :d) (test-input m/RIGHT)
          (= input :w) (test-input m/COUNTER-CLOCKWISE)
          (= input :s) (test-input m/CLOCKWISE))))

(defn movements-available? 
  "Checks if there are movements available"
  [game]
  (let [movements (map #(handle-action game %) [:a :d :w :s])]
    (some #(not (nil? %)) movements)))

(defn display 
  "Prints the game to the screen"
  [game]
  (do (println "BASECASE TETRIS:\n")
      (b/display (c/coordinates->board! (:board game) (:coordinates game)))
      (println "\nActions: [a] Move piece left")
      (println "         [d] Move piece right")
      (println "         [w] Rotate piece counter-clockwise")
      (println "         [s] Rotate piece clockwise")))
