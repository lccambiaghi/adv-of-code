(ns day-five
  (:require [clojure.string :as str]))

(defn all-spaces? [elm]
  (= '(\space \space \space) elm))

(defn parse-state [state-lines]
  (let [cols-by-line (->> state-lines
                          (map #(partition 3 4 %)))
        state        {}
        ;; for each column, gather elements
        state        (for [i (range (count cols-by-line))]
                       (assoc state (inc i) (->> cols-by-line
                                                 (map #(nth % i))
                                                 (remove all-spaces?)
                                                 (reverse)
                                                 (vec))))
        ;; from vector of maps to single map
        state        (apply merge state)
        ]
    state))

(defn apply-moves [state moves-lines reverse?]
  (reduce
   (fn [state move-line]
     (let [[_ n _ from _ to] (str/split move-line #" ")
           [n from to]       (map #(Integer/parseInt %) [n from to])
           to-move           (->> (state from) (take-last n))
           to-move           (if reverse? (reverse to-move) to-move)
           state-from        (->> (state from) (drop-last n) (vec))
           state-to          (apply conj (state to) to-move)]
       (-> state
           (assoc to state-to)
           (assoc from state-from))))
   state
   moves-lines
   ))

(defn get-top-of-stacks [state]
  (->>  state
        (into (sorted-map-by <))
        (vals)
        (map last)
        (map second)
        (apply str)))

(let [file-content                (slurp "data/day-five.txt")
      lines                       (->> file-content (str/split-lines))
      [state-lines _ moves-lines] (->> lines (partition-by #(= % "")))
      state                       (parse-state state-lines)
      final-state                 (apply-moves state moves-lines true)
      ;; task two
      final-state                 (apply-moves state moves-lines false)
      ]
  (get-top-of-stacks final-state)
  )
