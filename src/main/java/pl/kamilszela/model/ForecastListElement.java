package pl.kamilszela.model;

import java.util.List;
import java.util.Map;

public class ForecastListElement {
    private long dt;
    private Map<String, Double> main;
    private List<Map<String, String>> weather;
    private Map<String, Integer> clouds;
    private Map<String, Double> wind;
    private int visibility;
    private double pop;
    private Map<String, String> sys;
    private String dt_txt;

    public long getDt() {
        return dt;
    }

    public Map<String, Double> getMain() {
        return main;
    }

    public List<Map<String, String>> getWeather() {
        return weather;
    }

    public Map<String, Integer> getClouds() {
        return clouds;
    }

    public Map<String, Double> getWind() {
        return wind;
    }

    public int getVisibility() {
        return visibility;
    }

    public double getPop() {
        return pop;
    }

    public Map<String, String> getSys() {
        return sys;
    }

    public String getDt_txt() {
        return dt_txt;
    }
}
