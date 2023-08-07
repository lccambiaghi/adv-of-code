(ns day-13
  (:require [clojure.string :as str]))

(defn compare-lines [lists]
  (let [[left right] lists]
    (reduce #(and %1 %2) [true] (compare-lists left right))))

(defn compare-element [left right]
  (<= left right))

(defn compare-lists [left right]
  (let [left  (if (coll? left) left (list left))
        right (if (coll? right) right (list left))]
    (cond
      ;; right runs out of elements
      (and (> (count left) (count right))) false
      ;; left runs out of elements
      (and (< (count left) (count right))) true
      ;; both lists, comparable
      :else                                (mapv compare-element left right)
      )))

(let [file-content    (slurp "../data/day-13.txt")
      file-content    test-case
      lines           (->> file-content
                           (str/split-lines)
                           (remove #(= % ""))
                           (map clojure.edn/read-string))
      pairs           (->> lines (partition 2))
      pairs-in-order? (map compare-lines pairs)
      ]
  pairs-in-order?
  ;; (->> (zipmap (range (count pairs)) pairs-in-order?)
  ;;      (filter #(true? (second %)))
  ;;      (keys)
  ;;      (map inc)
  ;;      (reduce +)
  ;;      )
  )


(def test-case
  "[1,1,3,1,1]
[1,1,5,1,1]

[[1],[2,3,4]]
[[1],4]

[9]
[[8,7,6]]

[[4,4],4,4]
[[4,4],4,4,4]

[7,7,7,7]
[7,7,7]

[]
[3]

[[[]]]
[[]]

[1,[2,[3,[4,[5,6,7]]]],8,9]
[1,[2,[3,[4,[5,6,0]]]],8,9]")
