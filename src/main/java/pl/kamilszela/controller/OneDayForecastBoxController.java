package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.BaseController;
import pl.kamilszela.model.WeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.util.List;

public class OneDayForecastBoxController extends BaseController {

    public OneDayForecastBoxController(AppManager appManager, ViewFactory viewFactory) {
        super(appManager, viewFactory);
    }

    @FXML
    private VBox columnLeft;

    @FXML
    private VBox columnRight;

    @FXML
    void closeForecastForOneDay() {

    }

    public void fillColumnsWithFoecastData(List<WeatherCityModel> list, int k){
        Double temperature = list.get(k).getMain().get("temp");
        Double pressure = list.get(k).getMain().get("pressure");
        Double humidity = list.get(k).getMain().get("humidity");
        Double windSpeed = list.get(k).getWind().get("speed");
        String dateForDisplay = list.get(k).getDt_txt();
        String text = dateForDisplay + " " + temperature + "C " + pressure + " hPa " + humidity + " %" + windSpeed;
        Label label = new Label(text);
        columnLeft.getChildren().add(label);
        System.out.println(dateForDisplay + " " + temperature + "C " + pressure + " hPa " + humidity + " %" + windSpeed );
    }
}
