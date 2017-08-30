(ns bcsat-clj.board
  (:require [bcsat-clj.common :as c]))

(def size 20)


(defn init 
  "Initializes an empty board"
  []
  (->> " "
       (repeat size)
       (vec)
       (repeat size)
       (vec)))

(defn value! 
  "Sets the value '*' in the coordinates (x, y) of a given board"
  [board x y]
  (assoc-in board [x y] "*"))

(defn positions 
  "Returns a list of all the filled coordinates in the board"
  [board]
  (let [brange (take size (range))]
    (-> (for [i brange]
          (for [j brange
                :when (not (c/empty?? board i j))]
            [i j])) ;TODO verificar se é i j ou j i
        (#(apply concat %)))))

(defn display 
  "Prints the board to the screen"
  [board]
  (let [brange (take size (range))]
    (do (doseq [y brange, x brange]
          (cond (= x 0) (print (str "  *" (c/value board y x)))
                (= x (dec size)) (print (str (c/value board y x) "*\n"))
                :else (print (c/value board y x))))
        (println "  **********************"))))
