package pl.kamilszela.model;

import java.util.List;
import java.util.Map;

public class FullWeatherCityModel {

    private String cod;
    private int message;
    private int cnt;
    private List<ForecastListElement> list;
    private Map<String, Object> city;

    public String getCod() {
        return cod;
    }

    public int getMessage() {
        return message;
    }

    public int getCnt() {
        return cnt;
    }

    public List<ForecastListElement> getList() {
        return list;
    }

    public Map<String, Object> getCity() {
        return city;
    }
}
