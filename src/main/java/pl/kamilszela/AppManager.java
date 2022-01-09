package pl.kamilszela;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import pl.kamilszela.controller.CityType;
import pl.kamilszela.model.ForecastListElement;
import pl.kamilszela.model.FullWeatherCityModel;
import pl.kamilszela.model.OneDayWeatherCityModel;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class AppManager {

    private static final Gson GSON = new Gson();

    public List<OneDayWeatherCityModel> currentCityWeatherModelList = FXCollections.observableArrayList();
    public List<OneDayWeatherCityModel> destinationCityWeatherModelList = FXCollections.observableArrayList();


    public void setParametersInWeatherCityModel(String downloadedJson, CityType typeOfCity) {

        if (currentCityWeatherModelList.size() > 0 && destinationCityWeatherModelList.size() > 0) {
            clearListsOfWeatherForecast();
        }

        if (downloadedJson != null) {
            if (downloadedJson.equals("")) {
                throw new IllegalArgumentException("JSON data empty");
            } else {
                switch (typeOfCity) {
                    case CURRENT_CITY:
                        setUpListWithPredictionModel(currentCityWeatherModelList, downloadedJson);
                        break;
                    case DESTINATION_CITY:
                        setUpListWithPredictionModel(destinationCityWeatherModelList, downloadedJson);
                        break;
                }
            }
        }

    }

    private void setUpListWithPredictionModel(List<OneDayWeatherCityModel> list, String forecastJson) {
        FullWeatherCityModel fullCityForecast = GSON.fromJson(forecastJson, new TypeToken<FullWeatherCityModel>() {
        }.getType());
        List<ForecastListElement> forecastListFor5Days = fullCityForecast.getList();
        Map<String, Object> cityData = fullCityForecast.getCity();

        for (ForecastListElement element : forecastListFor5Days) {
            long timeStampDouble = element.getDt() * 1000;
            Timestamp dateTimeStamp = new Timestamp(timeStampDouble);
            OneDayWeatherCityModel model = new OneDayWeatherCityModel(element.getMain(),
                    element.getWeather().get(0),
                    element.getWind(),
                    element.getDt_txt(),
                    cityData,
                    dateTimeStamp);
            list.add(model);
        }
    }

    public void clearListsOfWeatherForecast() {
        currentCityWeatherModelList.clear();
        destinationCityWeatherModelList.clear();
    }

    public List<OneDayWeatherCityModel> getCurrentCityWeatherModelList() {
        return currentCityWeatherModelList;
    }

    public List<OneDayWeatherCityModel> getDestinationCityWeatherModelList() {
        return destinationCityWeatherModelList;
    }

}
