package pl.kamilszela.controller;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pl.kamilszela.AppManager;
import pl.kamilszela.view.ViewFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OneDayForecastBoxControllerTest extends ApplicationTest {

    public static final int NUMBER_OF_MINIMAL_RECORDS_FOR_ONE_DAY_FORECAST = 8;
    AppManager manager;
    ViewFactory factory;
    OneDayForecastBoxController controller;

    @Start
    public void start(Stage stage) throws Exception {
        manager = new AppManager();
        factory = new ViewFactory(manager);
        controller = new OneDayForecastBoxController(manager, factory);
        manager.setParametersInWeatherCityModel(loadSampleJsonData(), CityType.CURRENT_CITY);
        manager.setParametersInWeatherCityModel(loadSampleJsonData(), CityType.DESTINATION_CITY);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/oneDayForecastBox.fxml"));
        loader.setController(controller);
        Parent mainNode = loader.load();

        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    private String loadSampleJsonData(){
        InputStream stream = OneDayForecastBoxControllerTest.class.getResourceAsStream("/jsonWeatherForecastExample" +
                ".txt");
        String result = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining());
        return result;
    }

    @Test
    void shouldPopulateVboxesWithForecastBoxes(){
        //given
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //when
                controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(), NUMBER_OF_MINIMAL_RECORDS_FOR_ONE_DAY_FORECAST);
                WaitForAsyncUtils.waitForFxEvents();
                //then
                assertThat(controller.getColumnLeft().getChildren().size(), equalTo(4));
                assertThat(controller.getColumnRight().getChildren().size(), equalTo(4));
            }
        });
    }
    @Test
    void shouldThrowIllegalArgumentExceptionWhenPositionOfElementRecordIsBelowEight(){
        //given
        //when
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //then
                assertThrows(IllegalArgumentException.class,
                        () -> controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(),
                                NUMBER_OF_MINIMAL_RECORDS_FOR_ONE_DAY_FORECAST - 1));
            }
        });
    }
    @Test
    void forecastBoxInsideVboxShouldHaveNullOnClickEvent(){
        //given
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //when
                controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(), NUMBER_OF_MINIMAL_RECORDS_FOR_ONE_DAY_FORECAST);
                WaitForAsyncUtils.waitForFxEvents();
                //then
                assertThat(controller.getColumnRight().getChildren().get(0).getOnMouseClicked(), nullValue());
            }
        });
    }
    @Test
    void shouldPrepareCityDataLabel(){
        //given
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //when
                controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(), NUMBER_OF_MINIMAL_RECORDS_FOR_ONE_DAY_FORECAST);
                WaitForAsyncUtils.waitForFxEvents();
                String townName = manager.getCurrentCityWeatherModelList().get(3).getCityData().get("name").toString();
                String countryCode =
                        manager.getCurrentCityWeatherModelList().get(3).getCityData().get("country").toString();
                String date = manager.getCurrentCityWeatherModelList().get(3).getDt_txt().substring(0,10);
                String expectedLabelContent = townName + ", " + countryCode + "; " + date + ", strefa czasowa: +02:00" +
                        " GMT";
                //then
                assertThat(controller.getCityDataLabel().getText(), equalTo(expectedLabelContent));
            }
        });
    }
}