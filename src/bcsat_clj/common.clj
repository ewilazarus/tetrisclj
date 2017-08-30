(ns bcsat-clj.common)


(defn value
  "Gets the value of an element (e.g. board or piece) in the 
  coordinates (x, y)"
  [element x y]
  (get-in element [x y]))

(defn empty??
  "Checks if an elements (e.g. board or piece) is empty in the
  coordinates (x, y)"
  [element x y]
  (= " " (value element x y)))

(defn clearscreen
  "Clears the screen. (Doesn't work on Windows)"
  []
  (do (print (str (char 27) "[2J"))
      (print (str (char 27) "[;H"))))
