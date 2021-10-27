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
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private char degreeSign = 176;

    public ForecastBoxController(AppManager appManager, ViewFactory viewFactory) {
        super(appManager, viewFactory);
    }

    public void prepareForecastBox(int i, List<WeatherCityModel> list){
        Instant instant = list.get(i).getTimestamp().toInstant();
        String secondsOffset = list.get(i).getCityData().get("timezone").toString();
        String hoursOffset = appManager.prepareTimeOfTimeZone(secondsOffset);
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.of(hoursOffset));
        dateLabel.setText(zonedDateTime.toString().substring(0,10) + " " + zonedDateTime.getHour() + ":00");
        Double temperature = list.get(i).getMain().get("temp");
        temperatureLabel.setText(temperature.toString() + " " + degreeSign + "C");
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
    }

    public ImageView getIconBox() {
        return iconBox;
    }

    public Label getTemperatureLabel() {
        return temperatureLabel;
    }

    public Label getPressureLabel() {
        return pressureLabel;
    }

    public Label getHumidityLabel() {
        return humidityLabel;
    }

    public Label getWindLabel() {
        return windLabel;
    }

    public Label getDateLabel() {
        return dateLabel;
    }
}
