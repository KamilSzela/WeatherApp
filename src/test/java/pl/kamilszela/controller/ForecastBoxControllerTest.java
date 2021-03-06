package pl.kamilszela.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.OneDayWeatherCityModel;
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
    private List<OneDayWeatherCityModel> prepareExampleListForSetUpInForecastBox(){

        Map<String, Double> main = new HashMap<>();
        main.put("temp", 20.06);
        main.put("pressure", 1014.0);
        main.put("humidity", 50.0);
        Map<String, String> weather = new HashMap<>();
        weather.put("icon", "01n");
        Map<String, Double> wind = new HashMap<>();
        wind.put("speed", 3.63);
        String dt_txt = "2021-06-01 21:00:00";
        Map<String,Object> cityData = new HashMap<>();
        cityData.put("name", "London");
        cityData.put("country", "GB");
        cityData.put("timezone", 3600);
        Long timestampLong = Long.valueOf("1622581200000");
        Timestamp timestamp = new Timestamp(timestampLong);
        List<OneDayWeatherCityModel> list = FXCollections.observableArrayList();
        OneDayWeatherCityModel model = new OneDayWeatherCityModel(main, weather, wind, dt_txt, cityData, timestamp);
        list.add(model);
        return list;
    }
}