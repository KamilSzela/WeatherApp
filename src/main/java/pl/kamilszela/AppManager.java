package pl.kamilszela;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonObject;
import javafx.collections.FXCollections;
import pl.kamilszela.model.WeatherCityModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class AppManager {

    private String currentTownForcastJson;
    private String destinationTownForcastJson;
    public List <WeatherCityModel> currentCityWeatherModelList = FXCollections.observableArrayList();
    public List <WeatherCityModel> destinationCityWeatherModelList = FXCollections.observableArrayList();


    public String getCurrentTownForcastJson() {
        return currentTownForcastJson;
    }

    public void setCurrentTownForcastJson(String currentTownForcastJson) {
        this.currentTownForcastJson = currentTownForcastJson;
    }

    public String getDestinationTownForcastJson() {
        return destinationTownForcastJson;
    }

    public void setDestinationTownForcastJson(String destinationTownForcastJson) {
        this.destinationTownForcastJson = destinationTownForcastJson;
    }

    public void setParametersInWeatherCityModel(){
        if(currentCityWeatherModelList.size() > 0 && destinationCityWeatherModelList.size() > 0){
            clearListsOfWeatherForecast();
        }

        if(currentTownForcastJson != null && currentCityWeatherModelList.size() == 0){
            setUpListWithPredictionModel(currentCityWeatherModelList, currentTownForcastJson);
        } else if (destinationTownForcastJson != null && destinationCityWeatherModelList.size() == 0){
            setUpListWithPredictionModel(destinationCityWeatherModelList, destinationTownForcastJson);
        }

    }
    public void setUpListWithPredictionModel(List<WeatherCityModel> list, String forecastJson){
        Gson gson = new Gson();
        Map<String, Object> forecastConvertedToMap = gson.fromJson(forecastJson, new TypeToken<Map<String,
                Object>>(){}.getType());
        ArrayList<Object> weatherForcastList = (ArrayList<Object>) forecastConvertedToMap.get("list");
        Map<String, Object> cityData = (Map<String, Object>) forecastConvertedToMap.get("city");

        for(int i = 0; i < weatherForcastList.size(); i++){
            Map prediction = (Map) weatherForcastList.get(i);
            WeatherCityModel model = new WeatherCityModel();
            model.setMain((Map<String, Double>) prediction.get("main"));
            ArrayList weatherArrayList = (ArrayList) prediction.get("weather");
            model.setWeather((Map<String, String>) weatherArrayList.get(0));
            model.setWind((Map<String, Double>) prediction.get("wind"));
            model.setDt_txt((String) prediction.get("dt_txt"));
            model.setCityData(cityData);
            list.add(model);
        }
        forecastJson = null;
    }
    public void clearJsonForecast(){
        currentTownForcastJson = null;
        destinationTownForcastJson = null;
    }

    public void clearListsOfWeatherForecast(){
        currentCityWeatherModelList.clear();
        destinationCityWeatherModelList.clear();
    }
}
