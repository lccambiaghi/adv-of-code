(ns day-six)

(defn no-duplicates? [map]
  (let [[items index] map
        uniques       (set items)]
    (= (count uniques) (count items))
    ))

(let [file-content  (slurp "data/day-six.txt")
      ;; first task
      groups        (partition 4 1 file-content)
      indices       (->> (range (count groups)) (map #(+ % 4)))
      ;; second task
      groups        (partition 14 1 file-content)
      indices       (->> (range (count groups)) (map #(+ % 14)))
      groups->index (zipmap groups indices)
      ]
  (->> groups->index
       (filter no-duplicates?)
       (vals)
       (reduce min)))

