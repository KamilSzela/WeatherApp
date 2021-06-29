package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.services.CurrentTownJsonDownloadService;
import pl.kamilszela.controller.services.DestinationTownJsonDownloadService;
import pl.kamilszela.controller.services.JsonDownloadService;
import pl.kamilszela.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public AppManager appManager;
    public ViewFactory viewFactory;
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
        this.appManager = appManager;
        this.viewFactory = viewFactory;
    }

    @FXML
    public void generateForcastAction() {
        if(sourceTown.getText() != "" && destinationTown.getText() != ""){
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
                    prepareForecastPanel();
                    //viewFactory.prepareForecastPanel();
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
    public void prepareForecastPanel(){
        if(appManager.currentCityWeatherModelList.size() > 0 && appManager.destinationCityWeatherModelList.size() > 0){
            System.out.println("current town" + appManager.currentCityWeatherModelList.size());
            System.out.println("destination town" + appManager.destinationCityWeatherModelList.size());
            String dateTime = appManager.destinationCityWeatherModelList.get(0).getDt_txt();
            Label labelTimeDestination = new Label(dateTime);
            destinationForcastField.getChildren().add(labelTimeDestination);
            Double tempDestination = appManager.destinationCityWeatherModelList.get(0).getMain().get("temp");
            Label tempLabeldest = new Label(tempDestination.toString());
            destinationForcastField.getChildren().add(tempLabeldest);

            String dateTimeCurr = appManager.currentCityWeatherModelList.get(0).getDt_txt();
            Label labelTimeCurr = new Label(dateTime);
            sourceTownForcastField.getChildren().add(labelTimeCurr);
            Double tempCurr = appManager.currentCityWeatherModelList.get(0).getMain().get("temp");
            Label tempLabelCurr = new Label(tempCurr.toString());
            sourceTownForcastField.getChildren().add(tempLabelCurr);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sourceTown.setText("London");
        this.destinationTown.setText("Paris");
    }
}
