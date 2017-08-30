(ns bcsat-clj.movement
  (:require [bcsat-clj.coordinates :as c]
            [bcsat-clj.piece :as p]))


(defn move-left
  "Moves the coordinates one unit to the left"
  [coordinates]
  (for [c coordinates]
    [(nth c 0) (dec (nth c 1))]))

(defn move-right
  "Moves the coordinates one unit to the right"
  [coordinates]
  (for [c coordinates]
    [(nth c 0) (inc (nth c 1))]))

(defn move-down 
  "Moves the coordinates one unit down"
  [coordinates]
  (for [c coordinates]
    [(inc (nth c 0)) (nth c 1)]))

(defn move-rotate
  "Rotates the coordinates based on the provided function f"
  [coordinates f]
  (-> (c/coordinates->piece coordinates)
      (f)
      (#(map vec %))
      (vec)
      (c/piece->coordinates (c/get-boundary coordinates 1 min)
                            (c/get-boundary coordinates 0 min))))

(defn move-rotate-clw
  "Rotates the coordinates clockwise"
  [coordinates]
  (move-rotate coordinates p/rotate-clw))

(defn move-rotate-cclw 
  "Rotates the coordinates counter-clockwise"
  [coordinates]
  (move-rotate coordinates p/rotate-cclw))

(defn LEFT 
  "Moves left"
  [coordinates]
  (-> (move-left coordinates)
      (move-down)))

(defn RIGHT
  "Moves right"
  [coordinates]
  (-> (move-right coordinates)
      (move-down)))

(defn CLOCKWISE
  "Moves clockwise"
  [coordinates]
  (-> (move-rotate-clw coordinates)
      (move-down)))

(defn COUNTER-CLOCKWISE 
  "Moves counter-clockwise"
  [coordinates]
  (-> (move-rotate-cclw coordinates)
      (move-down)))
