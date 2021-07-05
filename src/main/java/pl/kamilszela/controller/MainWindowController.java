package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.services.CurrentTownJsonDownloadService;
import pl.kamilszela.controller.services.DestinationTownJsonDownloadService;
import pl.kamilszela.controller.services.JsonDownloadService;
import pl.kamilszela.model.WeatherCityModel;
import pl.kamilszela.view.ViewFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    private JsonDownloadService currentTownJsonDownloadService =
            new CurrentTownJsonDownloadService(appManager);
    private JsonDownloadService destinationTownJsonDownloadService =
            new DestinationTownJsonDownloadService(appManager);

    @FXML
    private VBox sourceTownForcastField;

    @FXML
    private VBox destinationForcastField;

    @FXML
    private TextField sourceTown;

    @FXML
    private TextField destinationTown;

    @FXML
    private Label errorLabel;

    public MainWindowController(AppManager appManager, ViewFactory viewFactory){
        super(appManager, viewFactory);
    }

    @FXML
    public void generateForcastAction() {
        if(sourceTown.getText() != "" && destinationTown.getText() != ""){
            clearForecastFields();
            appManager.clearJsonForecast();
            downloadCurrentTownForcast();
            downloadDestinationTownForcast();
        }
    }

    public void downloadCurrentTownForcast(){
        downloadForecast(currentTownJsonDownloadService, sourceTown.getText());
    }
    public void downloadDestinationTownForcast(){
        downloadForecast(destinationTownJsonDownloadService, destinationTown.getText());
    }
    public void downloadForecast(JsonDownloadService service, String cityName){
        service.setCityName(cityName);
        service.setAppManager(appManager);
        service.restart();

        service.setOnSucceeded(e -> {
            JsonDownloadResult result = (JsonDownloadResult) service.getValue();
            switch (result){
                case SUCCESS:
                    service.setForecastInAppManager();
                    appManager.setParametersInWeatherCityModel();
                    viewFactory.prepareForecastPanel(destinationForcastField, sourceTownForcastField);
                    return;
                case FAILED_BY_MALFORMED_URL:
                    this.errorLabel.setText("Znaleziono błąd w adresie URL");
                    return;
                case FAILED_BY_UNEXPECTED_ERROR:
                    this.errorLabel.setText("Wystąpił niespodziewany błąd");
                    return;
            }
        });

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sourceTown.setText("London");
        this.destinationTown.setText("Paris");
        setUpPrecastFields();
    }

    private void setUpPrecastFields() {
        sourceTown.setOnMouseClicked(e ->{
            clearForecastFields();
        });
        destinationTown.setOnMouseClicked(e -> {
            clearForecastFields();
        });
    }

    private void clearForecastFields() {
        destinationForcastField.getChildren().clear();
        sourceTownForcastField.getChildren().clear();
    }
}
