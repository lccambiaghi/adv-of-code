(ns day-seven
  (:require [clojure.string :as str]))

(defn init-state []
  {:current-dir [] :fs {} :all-visited-dirs (set [])})

(defn visit-new-dir [state command]
  (let [dir (-> command (str/split #" ") (last))]
    (cond
      (= dir "/")  ["/"]
      (= dir "..") (pop (state :current-dir))
      :else        (conj (state :current-dir) dir)
      )))

(defn is-dir? [line]
  (-> line (str/split #" ") (first) (= "dir")))

(defn str->filename-size [str]
  (-> str (str/split #" ") (reverse)))

(defn assign-files-to-dir [state command]
  (let [lines (->> command (str/split-lines) (rest))
        files (->> lines (remove is-dir?) (map str->filename-size) (map vec))
        ]
    (update-in
     (state :fs) (state :current-dir)
     (fn [_] (into {} files))))
  )

(defn walk-filesystem [state commands]
  (reduce
   (fn [state command]
     (let [cd-or-ls             (-> command (str/split #" ") (first))
           new-dir              (visit-new-dir state command)
           new-all-visited-dirs (conj (state :all-visited-dirs) new-dir)]
       (if (= "cd" cd-or-ls)
         (-> state
             ;; keep track of current dir to handle ".."
             (assoc :current-dir new-dir)
             ;; keep track of all visited dirs
             (assoc :all-visited-dirs new-all-visited-dirs))
         (assoc state :fs (assign-files-to-dir state command)))))
   state
   commands))

(defn flatten-nested-map [m]
  (if (coll? m)
    (mapcat flatten-nested-map m)
    [m]))

(defn get-folder-size [fs dir]
  (->> (get-in fs dir)
       (flatten-nested-map)
       (filter #(re-matches #"^[0-9]*$" %))
       (map #(Integer/parseInt %))
       (reduce +)))

(defn calculate-folders-size [{:keys [all-visited-dirs fs]}]
  (zipmap all-visited-dirs (map #(get-folder-size fs %) all-visited-dirs)))

(let [file-content    (slurp "data/day-seven.txt")
      commands        (-> file-content (str/split #"\$") (rest))
      commands        (->> commands (map str/trim))
      state           (init-state)
      final-state     (walk-filesystem state commands)
      ;; task one
      folders-sizes   (calculate-folders-size final-state)
      task-one-answer (->> folders-sizes
                           (map vals)
                           (filter #(> 100000 %) )
                           (reduce +))
      ;; task two
      used-space      (folders-sizes ["/"])
      needed-space    (->> (folders-sizes ["/"])
                           (- 70000000)
                           (- 30000000))]
  (->> (vals folders-sizes)
       (filter #(> % needed-space))
       (reduce min)
       ))

;; (reduce
;;  (fn [acc m]
;;    (conj acc (map vals m))
;;    )
;;  []
;;  {"crmsnch" {"lsprzlbf.prh" "195745", "tjslbpb" "271424", "vmc.cdf" "227054"}, "vmc.cdf" "294249"})

;; (tree-seq map? seq {"crmsnch" {"lsprzlbf.prh" "195745", "tjslbpb" "271424", "vmc.cdf" "227054"}, "vmc.cdf" "294249"})
