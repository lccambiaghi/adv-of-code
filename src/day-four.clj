(ns day-four
  (:require [clojure.string :as str]))

(defn is-line-redundant? [line]
  (let [[str-range1 str-range2] (str/split line #",")
        range1                  (->> (str/split str-range1 #"-") ;; (map range)
                                     )
        ]
    range1
    )
  )

(let [file-content     (slurp "data/day-four.txt")
      lines            (->> file-content (str/split-lines))
      lines-redundant? (->> lines (map is-line-redundant?))
      ]
  (take 3 lines-redundant?)
  ;; (reduce + lines-priority)
  )
