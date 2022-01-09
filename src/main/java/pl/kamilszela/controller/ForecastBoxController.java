package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.OneDayWeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
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
    private static final char DEGREE_SIGN = 176;

    public ForecastBoxController(AppManager appManager, ViewFactory viewFactory) {
        super(appManager, viewFactory);
    }

    public void prepareForecastBox(int forecastElementNumber, List<OneDayWeatherCityModel> list) {
        Instant instant = list.get(forecastElementNumber).getTimestamp().toInstant();
        String secondsOffset = list.get(forecastElementNumber).getCityData().get("timezone").toString();
        Double secondsOffsetDouble = Double.parseDouble(secondsOffset);
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(secondsOffsetDouble.intValue());
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(instant, ZoneId.ofOffset("GMT", offset));
        dateLabel.setText(zonedDateTime.toString().substring(0, 10) + " " + zonedDateTime.getHour() + ":00");
        Double temperature = list.get(forecastElementNumber).getMain().get("temp");
        temperatureLabel.setText(temperature.toString() + " " + DEGREE_SIGN + "C");
        Double pressure = list.get(forecastElementNumber).getMain().get("pressure");
        pressureLabel.setText(pressure + " hPa");
        Double humidity = list.get(forecastElementNumber).getMain().get("humidity");
        humidityLabel.setText(humidity.toString() + "%");
        Double windSpeed = list.get(forecastElementNumber).getWind().get("speed");
        windLabel.setText(windSpeed.toString() + " m/s");
        String iconName = list.get(forecastElementNumber).getWeather().get("icon");
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
