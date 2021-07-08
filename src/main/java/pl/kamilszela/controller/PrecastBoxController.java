package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.WeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PrecastBoxController extends BaseController implements Initializable {

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

    public PrecastBoxController(AppManager appManager, ViewFactory viewFactory) {
        super(appManager, viewFactory);
    }

    public void preparePrecastBox(int i, List<WeatherCityModel> list){
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
        //String path = "/icons/50d.png";
        Image icon = new Image(String.valueOf(this.getClass().getResource(path)));
        iconBox.setImage(icon);
    }
    public void showForecastForOneDay(List<WeatherCityModel> list, int i ){
        String dateTime = list.get(i).getDt_txt();
        String date = dateTime.substring(0,10);
        for(int k = 0; k < list.size(); k++){
            String dateOFSingleRecord = list.get(k).getDt_txt();
            String dateForComparision = dateOFSingleRecord.substring(0,10);
            if(date.equals(dateForComparision)){
                Double temperature = list.get(k).getMain().get("temp");
                Double pressure = list.get(k).getMain().get("pressure");
                Double humidity = list.get(k).getMain().get("humidity");
                Double windSpeed = list.get(k).getWind().get("speed");
                String dateForDisplay = list.get(k).getDt_txt();
                System.out.println(dateForDisplay + " " + temperature + "C " + pressure + " hPa " + humidity + " %" + windSpeed );
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        forecastBox.setOnMouseEntered(e->{
            System.out.println("mouse entered");
            forecastBox.setStyle("-fx-background-color: silver");
        });
        forecastBox.setOnMouseExited(e->{
            System.out.println("mouse exited");
            forecastBox.setStyle("-fx-background-color: white;");
        });
    }
}
