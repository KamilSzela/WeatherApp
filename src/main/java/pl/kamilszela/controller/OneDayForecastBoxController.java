package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.model.OneDayWeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.io.UnsupportedEncodingException;
import java.time.ZoneOffset;
import java.util.List;

public class OneDayForecastBoxController extends BaseController {

    public static final int NUMBER_OF_RECORDS_FOR_ONE_DAY = 8;
    public static final int MAX_NUMBER_OF_FORECAST_BOXES_IN_ONE_COLUMN = 4;
    public static final int NUMBER_OF_LAST_RECORD_IN_FORECAST_LIST = 39;

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

    private void fillColumnsWithForecastData(List<OneDayWeatherCityModel> listOfForecastRecords, int numberOfSingleRecord, int counter){

        String cityName = listOfForecastRecords.get(numberOfSingleRecord).getCityData().get("name").toString();
        String countryCode = listOfForecastRecords.get(numberOfSingleRecord).getCityData().get("country").toString();
        String dateForDisplay = listOfForecastRecords.get(numberOfSingleRecord).getDt_txt().substring(0,10);
        String timeZoneSeconds = listOfForecastRecords.get(numberOfSingleRecord).getCityData().get("timezone").toString();
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
        if(counter > MAX_NUMBER_OF_FORECAST_BOXES_IN_ONE_COLUMN){
            viewFactory.showForecastInVBox(numberOfSingleRecord, listOfForecastRecords, columnRight, false);
        } else {
            viewFactory.showForecastInVBox(numberOfSingleRecord, listOfForecastRecords, columnLeft, false);
        }
    }

    public void prepareForecastDataForOneDay(List<OneDayWeatherCityModel> listOfForecastRecords, int numberPositionOfElementRecord){

        if (numberPositionOfElementRecord == NUMBER_OF_LAST_RECORD_IN_FORECAST_LIST) {
            numberPositionOfElementRecord++;
        }
        int positionNumberOfFirstRecord = numberPositionOfElementRecord - NUMBER_OF_RECORDS_FOR_ONE_DAY;
        if (positionNumberOfFirstRecord < 0) {
            throw new IllegalArgumentException("Index of first record of element cannot be negative.");
        }
        int counter = 0;
        for(int k = positionNumberOfFirstRecord; k < numberPositionOfElementRecord; k++){
            counter++;
            fillColumnsWithForecastData(listOfForecastRecords, k, counter);
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

}
