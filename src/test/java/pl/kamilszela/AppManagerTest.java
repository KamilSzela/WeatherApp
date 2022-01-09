package pl.kamilszela;

import org.junit.jupiter.api.Test;
import pl.kamilszela.controller.CityType;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by  on .
 */
class AppManagerTest {

    @Test
    void passedJsonDataShouldBeConvertedToTheList() {
        //given
        AppManager appManager = new AppManager();
        //when
        appManager.setParametersInWeatherCityModel(loadSampleJsonData(), CityType.CURRENT_CITY);
        appManager.setParametersInWeatherCityModel(loadSampleJsonData(), CityType.DESTINATION_CITY);
        //then
        assertThat(appManager.getCurrentCityWeatherModelList().size(), equalTo(40));
        assertThat(appManager.getDestinationCityWeatherModelList().size(), equalTo(40));
        assertThat(appManager.getCurrentCityWeatherModelList().get(0).getCityData().get("name").toString(), equalTo(
                "Madrid"));
        assertThat(appManager.getDestinationCityWeatherModelList().get(0).getCityData().get("name").toString(),
                equalTo(
                "Madrid"));
    }

    @Test
    void shouldThrowIllegalArgumentExceptionWhenPassingEmptyJsonString() {
        //given
        AppManager appManager = new AppManager();
        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> appManager.setParametersInWeatherCityModel("", CityType.DESTINATION_CITY));
    }

    @Test
    void callingClearForecastModelListsShouldLeaveThemEmpty() {
        //given
        AppManager manager = new AppManager();
        //when
        manager.setParametersInWeatherCityModel(loadSampleJsonData(), CityType.CURRENT_CITY);
        manager.setParametersInWeatherCityModel(loadSampleJsonData(), CityType.DESTINATION_CITY);
        manager.clearListsOfWeatherForecast();
        //then
        assertThat(manager.getCurrentCityWeatherModelList().size(), equalTo(0));
        assertThat(manager.getDestinationCityWeatherModelList().size(), equalTo(0));

    }
    private String loadSampleJsonData() {
        InputStream stream = AppManager.class.getResourceAsStream("/jsonWeatherForecastExample" +
                ".txt");
        String result = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining());
        return result;
    }
}