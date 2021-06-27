package pl.kamilszela;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.JsonObject;
import pl.kamilszela.model.WeatherCityModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

public class AppManager {

    private String currentTownForcastJson;
    private String destinationTownForcastJson;
    public WeatherCityModel currentCityWeatherModel = new WeatherCityModel();

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
        Gson gson = new Gson();
        Map<String, Object> forecastConvertedToMap = gson.fromJson(currentTownForcastJson, new TypeToken<Map<String,
                Object>>(){}.getType());
        ArrayList<Object> weatherForcastList = (ArrayList<Object>) forecastConvertedToMap.get("list");
        Map<String, Object> cityData = (Map<String, Object>) forecastConvertedToMap.get("city");
        System.out.println(cityData.get("name"));
        //Iterator iterator = weatherForcastList.iterator();
        //Map mapOfprediction = (Map) weatherForcastList.get(0);
        //System.out.println(mapOfprediction);
        for(int i = 0; i < weatherForcastList.size(); i++){
            Map prediction = (Map) weatherForcastList.get(i);
            System.out.println(prediction);
        }

    }
}
