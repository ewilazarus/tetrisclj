(ns bcsat-clj.coordinates
  (:require [bcsat-clj.board :as b]
            [bcsat-clj.piece :as p]
            [bcsat-clj.common :as c]))
            

(defn valid-coordinate? 
  "Checks if a coordinate (x, y) is within the boundaries of a board"
  [coordinate]
  (let [[cx cy] coordinate]
    (and (>= cx 0) (< cx b/size)
         (>= cy 0) (< cy b/size))))

(defn coordinates->board! 
  "Sets the values '*' for all (x, y) pairs in coordinates of a given board"
  [board coordinates]
  (let [positions (set (b/positions board))]
    (loop [bd board, cd coordinates]
      (let [target (first cd)]
        (cond (nil? target) bd
              (not (valid-coordinate? target)) nil
              (not (contains? positions target))
                (recur (apply b/value! bd target) (rest cd)))))))

(defn get-boundary 
  "Returns the boundary of coordinates in a given axis. (axis should be 0 for
  x and 1 for y; f should be min or max, depending if you want lower boundary 
  or upper boundary, repectively)"
  [coordinates axis f]
  (-> (map #(nth % axis) coordinates)
      (#(apply f %))))

(defn coordinates->piece
  "Transforms coordinates into a piece representation"
  [coordinates]
  (let [min-x (get-boundary coordinates 0 min)
        min-y (get-boundary coordinates 1 min)
        delta-x (inc (- (get-boundary coordinates 0 max) min-x))
        delta-y (inc (- (get-boundary coordinates 1 max) min-y))
        canvas (->> " "
                    (repeat delta-y)
                    (vec)
                    (repeat delta-x)
                    (vec))]
    (loop [cv canvas, n 0]
      (if (= n (count coordinates))
        cv
        (let [target (nth coordinates n)
              target-x (- (nth target 0) min-x)
              target-y (- (nth target 1) min-y)]
          (recur (assoc-in cv [target-x target-y] "*")
                 (inc n)))))))

(defn piece->coordinates
  "Transforms a piece in a list of coordinates, based on the (x, y) pair" 
  [piece x y]
  (let [wrange (take (p/width piece) (range))
        hrange (take (p/height piece) (range))]
    (for [i wrange
          j hrange
          :let [k (+ i x)
                l (+ j y)]
          :when (not (c/empty?? piece j i))]
      [l k])))
