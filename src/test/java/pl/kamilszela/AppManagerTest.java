package pl.kamilszela;

import org.junit.jupiter.api.Test;

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
        appManager.setCurrentTownForcastJson(loadSampleJsonData());
        appManager.setDestinationTownForcastJson(loadSampleJsonData());
        //when
        appManager.setParametersInWeatherCityModel();
        appManager.setParametersInWeatherCityModel();
        //then
        assertThat(appManager.getCurrentCityWeatherModelList().size(), equalTo(40));
        assertThat(appManager.getDestinationCityWeatherModelList().size(), equalTo(40));
    }

    @Test
    void afterCallingClearJsonDataInAppManagerJsonStringsShouldBeNull(){
        //given
        AppManager appManager = new AppManager();
        appManager.setCurrentTownForcastJson(loadSampleJsonData());
        appManager.setDestinationTownForcastJson(loadSampleJsonData());
        //when
        appManager.clearJsonForecast();
        //then
        assertNull(appManager.getCurrentTownForcastJson());
        assertNull(appManager.getDestinationTownForcastJson());
    }
    @Test
    void passingPositiveValueOfSecondsShouldCreateTimeZoneWithPlusSign(){
        //given
        AppManager appManager = new AppManager();
        //when
        String timeZone = appManager.prepareTimeOfTimeZone("7200");
        //then
        assertThat(timeZone, equalTo("GMT+2"));
    }
    @Test
    void passingNegativeValueOfSecondsShouldCreateTimeZoneWithMinusSign(){
        //given
        AppManager appManager = new AppManager();
        //when
        String timeZone = appManager.prepareTimeOfTimeZone("-7200");
        //then
        assertThat(timeZone, equalTo("GMT-2"));
    }
    private String loadSampleJsonData(){
        InputStream stream = AppManager.class.getResourceAsStream("/jsonWeatherForecastExample" +
                ".txt");
        String result = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining());
        return result;
    }
}