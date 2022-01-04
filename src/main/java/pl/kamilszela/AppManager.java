package pl.kamilszela;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import pl.kamilszela.controller.ForecastBoxController;
import pl.kamilszela.model.ForecastListElement;
import pl.kamilszela.model.FullWeatherCityModel;
import pl.kamilszela.model.OneDayWeatherCityModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class AppManager {

    private static final Gson GSON = new Gson();
    private String currentTownForecastJson;
    private String destinationTownForecastJson;
    public List<OneDayWeatherCityModel> currentCityWeatherModelList = FXCollections.observableArrayList();
    public List<OneDayWeatherCityModel> destinationCityWeatherModelList = FXCollections.observableArrayList();


    public String getCurrentTownForecastJson() {
        return currentTownForecastJson;
    }

    public void setCurrentTownForecastJson(String currentTownForcastJson) {
        this.currentTownForecastJson = currentTownForcastJson;
    }

    public String getDestinationTownForecastJson() {
        return destinationTownForecastJson;
    }

    public void setDestinationTownForecastJson(String destinationTownForecastJson) {
        this.destinationTownForecastJson = destinationTownForecastJson;
    }

    public void setParametersInWeatherCityModel() {
        if (currentCityWeatherModelList.size() > 0 && destinationCityWeatherModelList.size() > 0) {
            clearListsOfWeatherForecast();
        }

        if (currentTownForecastJson != null && destinationTownForecastJson != null) {
            if (currentTownForecastJson.equals("") || destinationTownForecastJson.equals("")) {
                throw new IllegalArgumentException("Json data empty");
            } else {
                setUpListWithPredictionModel(currentCityWeatherModelList, currentTownForecastJson);
                setUpListWithPredictionModel(destinationCityWeatherModelList, destinationTownForecastJson);
            }
        }

    }

    private void setUpListWithPredictionModel(List<OneDayWeatherCityModel> list, String forecastJson) {
        FullWeatherCityModel fullCityForecast = GSON.fromJson(forecastJson, new TypeToken<FullWeatherCityModel>() {
        }.getType());
        List<ForecastListElement> forecastListFor5Days = fullCityForecast.getList();
        Map<String, Object> cityData = fullCityForecast.getCity();

        for(ForecastListElement element: forecastListFor5Days){

            long timeStampDouble = element.getDt() * 1000;
            Timestamp dateTimeStamp = new Timestamp(timeStampDouble);
            OneDayWeatherCityModel model = new OneDayWeatherCityModel(element.getMain(),
                    element.getWeather().get(0),
                    element.getWind(),
                    element.getDt_txt(),
                    cityData,
                    dateTimeStamp);
//            model.setCityData(cityData);
//            model.setMain(element.getMain());
//            model.setWeather(element.getWeather().get(0));
//            model.setWind(element.getWind());
//            model.setDt_txt(element.getDt_txt());
//
//            model.setTimestamp(dateTimeStamp);
            list.add(model);
        }

    }

    public void clearJsonForecast() {
        currentTownForecastJson = null;
        destinationTownForecastJson = null;
    }

    private void clearListsOfWeatherForecast() {
        currentCityWeatherModelList.clear();
        destinationCityWeatherModelList.clear();
    }

//    public String prepareTimeOfTimeZone(String secondsOffsetString) {
//        Double secondsDouble = Double.valueOf(secondsOffsetString);
//        int secondsInt = secondsDouble.intValue();
//
//        int hours = secondsInt / 3600;
//        String zoneId = "";
//
//        if (hours > 0) {
//            zoneId = "GMT+" + hours;
//        } else {
//            zoneId = "GMT" + hours;
//        }
//
//        return zoneId;
//    }

    public List<OneDayWeatherCityModel> getCurrentCityWeatherModelList() {
        return currentCityWeatherModelList;
    }

    public List<OneDayWeatherCityModel> getDestinationCityWeatherModelList() {
        return destinationCityWeatherModelList;
    }

}
