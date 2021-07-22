package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.WeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OneDayForecastBoxController extends BaseController implements Initializable {

    public OneDayForecastBoxController(AppManager appManager, ViewFactory viewFactory) {
        super(appManager, viewFactory);
    }

    @FXML
    private VBox columnLeft;
    @FXML
    private VBox columnRight;
    @FXML
    private Label cityDataLabel;
    @FXML
    void closeForecastForOneDay() {
        Stage activeStage = (Stage) this.columnLeft.getScene().getWindow();
        activeStage.close();
    }

    public void fillColumnsWithForecastData(List<WeatherCityModel> list, int k, int counter){

        String cityName = list.get(k).getCityData().get("name").toString();
        String countryCode = list.get(k).getCityData().get("country").toString();
        String dateForDisplay = list.get(k).getDt_txt().substring(0,10);
        cityDataLabel.setText(cityName + ", " + countryCode + "; " + dateForDisplay);
        if(counter > 4){
            viewFactory.showForecastInVBox(k, list, columnRight, false);
        } else {
            viewFactory.showForecastInVBox(k, list, columnLeft, false);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
