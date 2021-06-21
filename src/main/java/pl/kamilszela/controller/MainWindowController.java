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

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    public AppManager appManager;
    private CurrentTownJsonDownloadService currentTownJsonDownloadService =
            new CurrentTownJsonDownloadService(appManager);
    private DestinationTownJsonDownloadService destinationTownJsonDownloadService =
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

    public MainWindowController(AppManager appManager){
        this.appManager = appManager;
    }

    @FXML
    public void generateForcastAction() {
        if(sourceTown.getText() != "" && destinationTown.getText() != ""){
            downloadCurrentTownForcast();
            downloadDestinationTownForcast();
        }
    }

    public void downloadCurrentTownForcast(){
        downloadForecast((JsonDownloadService) currentTownJsonDownloadService, sourceTown.getText());
        System.out.println(appManager.getCurrentTownForcastJson());
    }
    public void downloadDestinationTownForcast(){
        downloadForecast((JsonDownloadService) destinationTownJsonDownloadService, destinationTown.getText());
        System.out.println(appManager.getDestinationTownForcastJson());
    }
    public void downloadForecast(JsonDownloadService service, String cityName){
        service.setCityName(cityName);
        service.setAppManager(appManager);
        service.restart();

        service.setOnSucceeded(e -> {
            service.setForecastInAppManager();
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentTownJsonDownloadService.start();
        destinationTownJsonDownloadService.start();
    }
}
