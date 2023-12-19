(ns day-one
  (:require [clojure.string :as str]))

(defn split-file-into-groups [file-content]
  (->> file-content
     (str/split-lines)
     (partition-by #(= % ""))
     (remove #(= % [""]))
     ))

(defn sum-group [arr-of-strings]
  (->> arr-of-strings
     (map read-string)
     (reduce +)
     ))

(let [file-content  (slurp "data/day-one.txt")
      groups        (split-file-into-groups file-content)
      sums          (map sum-group groups)
      max           (reduce max sums)
      top-three     (->> sums (sort) (reverse) (take 3))
      sum-top-three (reduce + top-three)
      ]
  )
