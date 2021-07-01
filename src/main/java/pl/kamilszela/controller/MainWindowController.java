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
            clearForecastFields();
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
        int counter = 0;
        int size = appManager.currentCityWeatherModelList.size();
        if(appManager.currentCityWeatherModelList.size() > 0 && appManager.destinationCityWeatherModelList.size() > 0){
            for(int i = 0; i < size; i++){
                if(counter == 8){
                    showPrecastInVBox(i,  appManager.destinationCityWeatherModelList, destinationForcastField);
                    showPrecastInVBox(i,  appManager.currentCityWeatherModelList, sourceTownForcastField);
                    counter = 0;
                }
                counter++;
                if(i == 38){
                    counter++;
                }
            }
        }
    }
    public void showPrecastInVBox(int i, List< WeatherCityModel> list, Pane forecastField){
        String dateTime = list.get(i).getDt_txt();
        Label labelTimeDestination = new Label(dateTime);
        Double tempDestination = list.get(i).getMain().get("temp");
        Label tempLabeldest = new Label(tempDestination.toString());
        VBox innerBox = new VBox();
        innerBox.getChildren().addAll(labelTimeDestination,tempLabeldest);
        forecastField.getChildren().add(innerBox);

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
