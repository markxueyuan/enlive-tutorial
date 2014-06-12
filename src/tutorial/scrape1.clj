(ns tutorial.scrape1
  (:require [net.cgrand.enlive-html :as html]))

(def ^:dynamic *base-url* "https://news.ycombinator.com/")

(defn fetch-url [url]
  (html/html-resource (java.net.URL. url)))

(defn hn-headlines []
  (map html/text (html/select (fetch-url *base-url*) [:td.title :a])))

(defn hn-points []
  (map html/text (html/select (fetch-url *base-url*) [:td.subtext html/first-child])))

(defn print-headlines-and-points []
  (doseq [line (map #(str %1 " (" %2 ")") (hn-headlines) (hn-points))]
    (println line)))



(defn headlines [url]
  (map html/text (html/select (fetch-url url) [:#main :> :div.searchListOne :> :ul :> :li :> :div :> :h3 :> :a])))

(headlines "http://search.tianya.cn/bbs?q=%E4%B8%96%E7%95%8C%E6%9D%AF")

(defn points [url]
  (map html/text (html/select (fetch-url url) [:#main :> :div.searchListOne :> :ul :> :li :> :p :> [:span (html/nth-of-type 1)]])))

(points "http://search.tianya.cn/bbs?q=%E4%B8%96%E7%95%8C%E6%9D%AF")

(defn head-and-date [url]
  (html/select (fetch-url url) #{[:#main :> :div.searchListOne :> :ul :> :li :> :div :> :h3 :> :a]
                                                [:#main :> :div.searchListOne :> :ul :> :li :> :p :> [:span (html/nth-of-type 1)]]}))

(head-and-date "http://search.tianya.cn/bbs?q=%E4%B8%96%E7%95%8C%E6%9D%AF")
