package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.WeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ForecastBoxController extends BaseController implements Initializable {

    @FXML
    private ImageView iconBox;
    @FXML
    private Label temperatureLabel;
    @FXML
    private Label pressureLabel;
    @FXML
    private Label humidityLabel;
    @FXML
    private Label windLabel;
    @FXML
    private Label dateLabel;
    @FXML
    private AnchorPane forecastBox;
    @FXML
    private HBox forecastBoxInnerBox;

    public ForecastBoxController(AppManager appManager, ViewFactory viewFactory) {
        super(appManager, viewFactory);
    }

    public void prepareForecastBox(int i, List<WeatherCityModel> list){
        String dateTime = list.get(i).getDt_txt();
        dateLabel.setText(dateTime);
        Double temperature = list.get(i).getMain().get("temp");
        temperatureLabel.setText(temperature.toString() + " deg C");
        Double pressure = list.get(i).getMain().get("pressure");
        pressureLabel.setText(pressure + " hPa");
        Double humidity = list.get(i).getMain().get("humidity");
        humidityLabel.setText(humidity.toString() + "%");
        Double windSpeed = list.get(i).getWind().get("speed");
        windLabel.setText(windSpeed.toString() + " m/s");
        String iconName = list.get(i).getWeather().get("icon");
        String path = "/icons/" + iconName + ".png";
        Image icon = new Image(String.valueOf(this.getClass().getResource(path)));
        iconBox.setImage(icon);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        forecastBox.setOnMouseEntered(e->{
//            forecastBoxInnerBox.setStyle("-fx-background-color: silver");
//            forecastBox.setStyle("-fx-cursor: hand;");
//        });
//        forecastBox.setOnMouseExited(e->{
//            forecastBoxInnerBox.setStyle("-fx-background-color: white;");
//        });
    }
}
