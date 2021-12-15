package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.OneDayWeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.time.ZoneOffset;
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

    private void fillColumnsWithForecastData(List<OneDayWeatherCityModel> list, int k, int counter){

        String cityName = list.get(k).getCityData().get("name").toString();
        String countryCode = list.get(k).getCityData().get("country").toString();
        String dateForDisplay = list.get(k).getDt_txt().substring(0,10);
        String timeZoneSeconds = list.get(k).getCityData().get("timezone").toString();
        Double seconds = Double.parseDouble(timeZoneSeconds);
        ZoneOffset offset = ZoneOffset.ofTotalSeconds(seconds.intValue());
        String timeZone = offset.toString();
        String fullLabelString =
                cityName + ", " + countryCode + "; " + dateForDisplay + ", strefa czasowa: " + timeZone + " GMT";
        String fullLabelWithCharset = "";
        try {
            fullLabelWithCharset = new String(fullLabelString.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            cityDataLabel.setText("System nie wspiera podanych znaków. Spróbuj użyć standardowego alfabetu " +
                    "łacińskiego");
        }
        cityDataLabel.setText(fullLabelWithCharset);
        cityDataLabel.setTooltip(new Tooltip(fullLabelWithCharset));
        if(counter > 4){
            viewFactory.showForecastInVBox(k, list, columnRight, false);
        } else {
            viewFactory.showForecastInVBox(k, list, columnLeft, false);
        }
    }

    public void prepareForecastDataForOneDay(List<OneDayWeatherCityModel> list, int i){
        String dateTime = list.get(i).getDt_txt();
        String date = dateTime.substring(0,10);
        int counter = 0;
        for(int k = 0; k < list.size(); k++){
            String dateOFSingleRecord = list.get(k).getDt_txt();
            String dateForComparison = dateOFSingleRecord.substring(0,10);
            if(date.equals(dateForComparison)){
                counter++;
                fillColumnsWithForecastData(list, k, counter);
            }
        }
    }

    public VBox getColumnLeft() {
        return columnLeft;
    }

    public VBox getColumnRight() {
        return columnRight;
    }

    public Label getCityDataLabel() {
        return cityDataLabel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
