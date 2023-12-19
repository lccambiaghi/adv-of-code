(ns day-two
  (:require [clojure.string :as str]))

(defn letter->number [letter]
  (let [conversion {\A 1
                    \B 2
                    \C 3
                    \X 1
                    \Y 2
                    \Z 3}]
    (get conversion letter)))

(defn me->number [opp me]
  (let [opp (letter->number opp)
        me  (letter->number me)]
    (if (and (= me 1) (= opp 3))
      (+ 3 me)
      me)))

(defn opp->number [opp me]
  (let [opp (letter->number opp)
        me  (letter->number me)]
    (if (and (= opp 1) (= me 3))
      (+ 3 opp)
      opp)))

(defn draw? [opp me]
  (= (opp->number opp me) (me->number opp me)))

(defn win? [opp me]
  (< (opp->number opp me) (me->number opp me)))

(defn loss? [opp me]
  (> (opp->number opp me) (me->number opp me)))

(defn calculate-match-score [opp me]
  (cond
    (win? opp me)  6
    (draw? opp me) 3
    (loss? opp me) 0
    :else          (throw (AssertionError. "Impossible"))
    ))

(defn outcome->me [opp outcome]
  ;; X lose, Y draw Z win
  (get
   {[\A \X] \Z
    [\A \Y] \X
    [\A \Z] \Y
    [\B \X] \X
    [\B \Y] \Y
    [\B \Z] \Z
    [\C \X] \Y
    [\C \Y] \Z
    [\C \Z] \X
    }
   [opp outcome]))

(defn calculate-line-score [line]
  (let [opp         (first line)
        me          (last line)
        ;; task 1
        match-score (calculate-match-score opp me)
        shape-score (letter->number me)
        ;; task 2
        outcome     (last line)
        me-mapped   (outcome->me opp outcome)
        match-score (calculate-match-score opp me-mapped)
        shape-score (letter->number me-mapped)
        ]
    (+ match-score shape-score)
    ;; me-mapped
    ))

(let [file-content (slurp "data/day-two.txt")
      lines        (->> file-content (str/split-lines))
      lines-score  (->> lines (map calculate-line-score))
      ]
  (reduce + lines-score)
  ;; (take 7 lines-score)
  )
;; 14060
