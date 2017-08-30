(ns bcsat-clj.piece)

; Pieces' blueprint
(def pieces [[["*" "*" "*" "*"]],

             [["*" " "]
              ["*" " "]
              ["*" "*"]],

             [[" " "*"]
              [" " "*"]
              ["*" "*"]],

             [[" " "*"]
              ["*" "*"]
              ["*" " "]],

             [["*" "*"]
              ["*" "*"]]])


(defn random 
  "Returns a random piece"
  []
  (rand-nth pieces))

(defn height
  "Returns the height of a given piece"
  [piece]
  (count piece))

(defn width 
  "Returns the width of a given piece"
  [piece]
  (count (get piece 0)))

(defn rotate 
  "Rotates a given piece given a horizontal and a vertical ranges"
  [piece hrange wrange]
  (let [position (fn [x y] (get-in piece [x y]))]
    (for [i wrange]
      (for [j hrange]
        (position j i)))))

(defn rotate-clw
  "Rotates a given piece clockwise"
  [piece]
  (let [hrange (reverse (take (height piece) (range)))
        wrange (take (width piece) (range))]
    (rotate piece hrange wrange)))

(defn rotate-cclw 
  "Rotates a given piece counter-clockwise"
  [piece]
  (let [hrange (take (height piece) (range))
        wrange (reverse (take (width piece) (range)))]
    (rotate piece hrange wrange)))
