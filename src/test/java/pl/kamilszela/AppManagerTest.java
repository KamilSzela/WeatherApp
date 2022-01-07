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
    void passedJsonDataShouldBeConvertedToTheList(){
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
    void afterCallingClearJsonDataInAppManagerJsonStringsShouldBeNull(){
        //given
        AppManager appManager = new AppManager();
        appManager.setCurrentTownForecastJson(loadSampleJsonData());
        appManager.setDestinationTownForecastJson(loadSampleJsonData());
        //when
        appManager.clearJsonForecast();
        //then
        assertNull(appManager.getCurrentTownForecastJson());
        assertNull(appManager.getDestinationTownForecastJson());
    }
    @Test
    void shouldThrowIllegalArgumentExceptionWhenPassingEmptyJsonString(){
        //given
        AppManager appManager = new AppManager();
        //when
        appManager.setDestinationTownForecastJson("");
        appManager.setCurrentTownForecastJson("");
        //then
        assertThrows(IllegalArgumentException.class, () -> appManager.setParametersInWeatherCityModel("", CityType.DESTINATION_CITY));
    }
    private String loadSampleJsonData(){
        InputStream stream = AppManager.class.getResourceAsStream("/jsonWeatherForecastExample" +
                ".txt");
        String result = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining());
        return result;
    }
}