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

    AppManager manager;
    ViewFactory factory;
    OneDayForecastBoxController controller;

    @Start
    public void start(Stage stage) throws Exception{
        manager = new AppManager();
        factory = new ViewFactory(manager);
        controller = new OneDayForecastBoxController(manager, factory);
        setSampleJsonInAppManager(manager);
        manager.setParametersInWeatherCityModel();
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/oneDayForeCastBox.fxml"));
        loader.setController(controller);
        Parent mainNode = loader.load();

        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }

    private void setSampleJsonInAppManager(AppManager manager) throws IOException {
        InputStream stream = OneDayForecastBoxControllerTest.class.getResourceAsStream("/jsonWeatherForecastExample" +
                ".txt");
        String result = new BufferedReader(new InputStreamReader(stream)).lines().collect(Collectors.joining());
        manager.setCurrentTownForecastJson(result);
        manager.setDestinationTownForecastJson(result);
    }

    @Test
    void shouldPopulateVboxesWithForecastBoxes(){
        //given
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //when
                controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(), 8);
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
                        () -> controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(), 7));
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
                controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(), 8);
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
                controller.prepareForecastDataForOneDay(manager.getCurrentCityWeatherModelList(), 8);
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