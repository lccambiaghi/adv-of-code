(ns day-four
  (:require [clojure.string :as str]
            [clojure.set]))

(defn str-range->set [str-range]
  (let [rng (->> (str/split str-range #"-")
                 (map #(Integer/parseInt %)))
        rng (range (first rng) (inc (second rng)))]
    (set rng)))

(defn is-line-redundant? [line]
  (let [[str-range1 str-range2] (str/split line #",")
        set1                    (str-range->set str-range1)
        set2                    (str-range->set str-range2)
        diff1                   (clojure.set/difference set1 set2)
        diff2                   (clojure.set/difference set2 set1)
        redundant?              (or (= 0 (count diff1)) (= 0 (count diff2)))
        ]
    (if redundant? 1 0)
    ))

(defn is-line-overlapping? [line]
  (let [[str-range1 str-range2] (str/split line #",")
        set1                    (str-range->set str-range1)
        set2                    (str-range->set str-range2)
        inters                  (clojure.set/intersection set1 set2)
        overlap?                (> 0 (count inters))
        ]
    (if overlap? 1 0)))

(let [file-content       (slurp "data/day-four.txt")
      lines              (->> file-content (str/split-lines))
      lines-redundant?   (->> lines (map is-line-redundant?))
      lines-overlapping? (->> lines (map is-line-overlapping?))
      ]
  ;; (reduce + lines-redundant?)
  (reduce + lines-overlapping?)
  )
