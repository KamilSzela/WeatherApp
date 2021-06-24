package pl.kamilszela;

import com.google.gson.reflect.TypeToken;
import pl.kamilszela.model.WeatherCityModel;
import com.google.gson.*;

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
        Iterator iterator = weatherForcastList.iterator();

        while(iterator.hasNext()){

        }
    }
}
