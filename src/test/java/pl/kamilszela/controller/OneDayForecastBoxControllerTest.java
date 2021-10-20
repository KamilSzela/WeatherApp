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

import static org.junit.jupiter.api.Assertions.*;

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
        manager.setCurrentTownForcastJson(result);
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
                System.out.println(controller.getColumnLeft().getChildren().size());
                sleep(5000);
            }
        });
    }

}