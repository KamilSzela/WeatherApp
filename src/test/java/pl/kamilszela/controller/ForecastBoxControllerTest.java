package pl.kamilszela.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.WeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.sql.Timestamp;
import java.util.*;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class ForecastBoxControllerTest extends ApplicationTest {

    AppManager manager;
    ViewFactory factory;
    ForecastBoxController controller;
    private char degreeSign = 176;

    @Start
    public void start(Stage stage) throws Exception{
        manager = new AppManager();
        factory = new ViewFactory(manager);
        controller = new ForecastBoxController(manager, factory);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/forecastBox.fxml"));
        loader.setController(controller);
        Parent mainNode = loader.load();
       // controller.prepareForecastBox(0, prepareExampleListForSetUpInForecastBox());

        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
    @Test
    void dataFromListShouldBeDisplayedAsLabels(){
        //given
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //when
                controller.prepareForecastBox(0, prepareExampleListForSetUpInForecastBox());
                //then
                String iconUrl = controller.getIconBox().getImage().getUrl();
                String expectedIconUrl = controller.getClass().getResource("/icons/01n.png").toString();
                String expectedTemplabel = "20.06 " + degreeSign + "C";
                assertAll(
                        () -> assertThat(iconUrl, equalTo(expectedIconUrl)),
                        () -> assertThat(controller.getTemperatureLabel().getText(), equalTo("20.06 " + degreeSign +
                                "C")),
                        () -> assertThat(controller.getDateLabel().getText(), equalTo("2021-06-01 22:00")),
                        () -> assertThat(controller.getHumidityLabel().getText(), equalTo("50.0%")),
                        () -> assertThat(controller.getPressureLabel().getText(), equalTo("1014.0 hPa")),
                        () -> assertThat(controller.getWindLabel().getText(), equalTo("3.63 m/s"))
                );
            }
        });
    }
    private List<WeatherCityModel> prepareExampleListForSetUpInForecastBox(){
        WeatherCityModel model = new WeatherCityModel();
        Map<String, Double> main = new HashMap<>();
        main.put("temp", 20.06);
        main.put("pressure", 1014.0);
        main.put("humidity", 50.0);
        model.setMain(main);
        Map<String, String> weather = new HashMap<>();
        weather.put("icon", "01n");
        model.setWeather(weather);
        Map<String, Double> wind = new HashMap<>();
        wind.put("speed", 3.63);
        model.setWind(wind);
        String dt_txt = "2021-06-01 21:00:00";
        model.setDt_txt(dt_txt);
        Map<String,Object> cityData = new HashMap<>();
        cityData.put("name", "London");
        cityData.put("country", "GB");
        cityData.put("timezone", 3600);
        model.setCityData(cityData);
        Long timestampLong = Long.valueOf("1622581200000");
        Timestamp timestamp = new Timestamp(timestampLong);
        model.setTimestamp(timestamp);
        List<WeatherCityModel> list = FXCollections.observableArrayList();
        list.add(model);
        return list;
    }
}