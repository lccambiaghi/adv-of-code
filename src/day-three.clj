(ns day-three
  (:require [clojure.string :as str]
            [clojure.set]))

(defn letter->number [letter]
  (let [n0 (int letter)
        n  (cond
             ;; 65<x<90 is capital letter
             ;; 65 - 27 = 38
             (< n0 91)  (- n0 38)
             ;; 97<x< 122 is lowercase letter
             ;; 97 - 1 = 96
             (< n0 123) (- n0 96)
             :else      (throw (AssertionError. "Impossible"))
             )]
    n
    ))

(defn calculate-line-priority [line]
  (let [half         (/ (count line) 2)
        first-comp   (->> line (take half) set)
        second-comp  (->> line (take-last half) (set))
        intersection (clojure.set/intersection first-comp second-comp)
        ]
    (letter->number (first intersection))
    ))


(let [file-content   (slurp "data/day-three.txt")
      lines          (->> file-content (str/split-lines))
      lines-priority (->> lines (map calculate-line-priority))
      ]
  (reduce + lines-priority)
  )
;; 7826
