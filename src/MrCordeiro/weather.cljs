(ns ^:figwheel-hooks MrCordeiro.weather
  (:require
   [goog.dom :as gdom]
   [reagent.dom :as rdom]
   [reagent.core :as r]
   [ajax.core :as ajax]))

;; NOT FOR PRODUCTION
(def api-key "https://openweathermap.org's api secret")

;; Initial application state
;; In Reagent, we keep all of the data that we use to render the app inside an
;; atom, which is simply a container for data that might change.
(defonce
  app-state
  (r/atom {:title "WhichWeather"
           :city ""
           :temperatures {:today {:label "Today" :value nil}
                          :tommorrow {:label "Tomorrow" :value nil}}}))

(defn title []
  [:h1 (:title @app-state)])

(defn set-temperature [temperature]
  [:div {:class "temperature"}
   [:h2 (:label temperature)]
   [:div {:class "value"}
    (:value temperature)]])

(defn handle-response [resp]
  (let [today (get-in resp ["list" 0 "main" "temp"])
        tomorrow (get-in resp ["list" 8 "main" "temp"])]
    (swap! app-state
           update-in [:temperatures :today :value] (constantly today))
    (swap! app-state
           update-in [:temperatures :tomorrow :value] (constantly tomorrow))))

(defn get-forecast! []
  (let [city (:city @app-state)]
    (ajax/GET "http://api.openweathermap.org/data/2.5/forecast"
      {:params {"q" city
                "appid" api-key
                "units" "metric"}
       :handler handle-response})))

(defn city []
  [:div {:class "city"}
   [:h3 "Enter your city name"]
   [:input {:type "text"
            :placeholder "Postal Code"
            :value (:city @app-state)
            :on-change #(swap! app-state assoc :city (-> % .-target .-value))}]
   [:button {:on-click get-forecast!} "Go"]])

(defn app []
  [:div {:class "app"}
   [title]
   [:div {:class "temperatures"}
    (for [temperature (vals (:temperatures @app-state))]
      [set-temperature temperature])]
   [city]])

(defn mount-app-element []
  (rdom/render [app] (gdom/getElement "app")))
(mount-app-element)

;; specify reload hook with ^:after-load metadata
(defn ^:after-load on-reload []
  (mount-app-element))
