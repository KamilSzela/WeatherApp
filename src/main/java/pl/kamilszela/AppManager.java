package pl.kamilszela;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import pl.kamilszela.model.WeatherCityModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AppManager {

    private String currentTownForecastJson;
    private String destinationTownForecastJson;
    public List <WeatherCityModel> currentCityWeatherModelList = FXCollections.observableArrayList();
    public List <WeatherCityModel> destinationCityWeatherModelList = FXCollections.observableArrayList();


    public String getCurrentTownForcastJson() {
        return currentTownForecastJson;
    }

    public void setCurrentTownForcastJson(String currentTownForcastJson) {
        this.currentTownForecastJson = currentTownForcastJson;
    }

    public String getDestinationTownForecastJson() {
        return destinationTownForecastJson;
    }

    public void setDestinationTownForecastJson(String destinationTownForecastJson) {
        this.destinationTownForecastJson = destinationTownForecastJson;
    }

    public void setParametersInWeatherCityModel(){
        if(currentCityWeatherModelList.size() > 0 && destinationCityWeatherModelList.size() > 0){
            clearListsOfWeatherForecast();
        }

        if(currentTownForecastJson != null && destinationTownForecastJson != null){
            if(currentTownForecastJson.equals("") || destinationTownForecastJson.equals("")){
                throw new IllegalArgumentException("Json data empty");
            } else {
                setUpListWithPredictionModel(currentCityWeatherModelList, currentTownForecastJson);
                setUpListWithPredictionModel(destinationCityWeatherModelList, destinationTownForecastJson);
            }
        }

    }
    private void setUpListWithPredictionModel(List<WeatherCityModel> list, String forecastJson){
        Gson gson = new Gson();
        Map<String, Object> forecastConvertedToMap = gson.fromJson(forecastJson, new TypeToken<Map<String,
                Object>>(){}.getType());
        ArrayList<Object> weatherForecastList = (ArrayList<Object>) forecastConvertedToMap.get("list");
        Map<String, Object> cityData = (Map<String, Object>) forecastConvertedToMap.get("city");

        for(int i = 0; i < weatherForecastList.size(); i++){
            Map prediction = (Map) weatherForecastList.get(i);
            WeatherCityModel model = new WeatherCityModel();
            model.setMain((Map<String, Double>) prediction.get("main"));
            ArrayList weatherArrayList = (ArrayList) prediction.get("weather");
            model.setWeather((Map<String, String>) weatherArrayList.get(0));
            model.setWind((Map<String, Double>) prediction.get("wind"));
            model.setDt_txt((String) prediction.get("dt_txt"));
            String timestampString = (prediction.get("dt").toString());
            Double timestamp = Double.valueOf(timestampString) * 1000;
            Timestamp dateTimeStamp = new Timestamp(timestamp.longValue());
            model.setTimestamp(dateTimeStamp);
            model.setCityData(cityData);
            list.add(model);
        }
        forecastJson = null;
    }
    public void clearJsonForecast(){
        currentTownForecastJson = null;
        destinationTownForecastJson = null;
    }

    private void clearListsOfWeatherForecast(){
        currentCityWeatherModelList.clear();
        destinationCityWeatherModelList.clear();
    }

    public String prepareTimeOfTimeZone(String secondsOffsetString){
        Double secondsDouble = Double.valueOf(secondsOffsetString);
        int secondsInt = secondsDouble.intValue();

        int hours = secondsInt / 3600;
        String zoneId = "";

        if(hours > 0){
            zoneId = "GMT+" + hours;
        } else {
            zoneId = "GMT" + hours;
        }

        return zoneId;
    }

    public List<WeatherCityModel> getCurrentCityWeatherModelList() {
        return currentCityWeatherModelList;
    }

    public List<WeatherCityModel> getDestinationCityWeatherModelList() {
        return destinationCityWeatherModelList;
    }

}
