package pl.kamilszela.model;

import java.sql.Timestamp;
import java.util.Map;

public class OneDayWeatherCityModel {
    private Map<String, Double> main = null;
    private Map<String, String> weather = null;
    private Map<String, Double> wind = null;
    private String dt_txt = null;
    private Map<String, Object> cityData = null;
    private Timestamp timestamp = null;

    public OneDayWeatherCityModel(Map<String, Double> main, Map<String, String> weather, Map<String, Double> wind, String dt_txt, Map<String, Object> cityData, Timestamp timestamp) {
        this.main = main;
        this.weather = weather;
        this.wind = wind;
        this.dt_txt = dt_txt;
        this.cityData = cityData;
        this.timestamp = timestamp;
    }

    public Map<String, Double> getMain() {
        return main;
    }

    public void setMain(Map<String, Double> main) {
        this.main = main;
    }

    public Map<String, String> getWeather() {
        return weather;
    }

    public void setWeather(Map<String, String> weather) {
        this.weather = weather;
    }

    public Map<String, Double> getWind() {
        return wind;
    }

    public void setWind(Map<String, Double> wind) {
        this.wind = wind;
    }

    public String getDt_txt() {
        return dt_txt;
    }

    public void setDt_txt(String dt_txt) {
        this.dt_txt = dt_txt;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getCityData() {
        return cityData;
    }

    public void setCityData(Map<String, Object> cityData) {
        this.cityData = cityData;
    }
}
