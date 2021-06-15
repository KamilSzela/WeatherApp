package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.services.CurrentTownJsonDownloadService;
import pl.kamilszela.controller.services.DestinationTownJsonDownloadService;
import pl.kamilszela.controller.services.JsonDownloadService;

public class MainWindowController {

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
        currentTownJsonDownloadService.setCityName(sourceTown.getText());
        currentTownJsonDownloadService.restart();
        currentTownJsonDownloadService.setAppManager(appManager);
        currentTownJsonDownloadService.setOnSucceeded(e -> {
            JsonDownloadResult result = (JsonDownloadResult) currentTownJsonDownloadService.getValue();
            currentTownJsonDownloadService.setCurrentTownForcastInAppManager();
            System.out.println(appManager.getCurrentTownForcastJson());
        });

    }
    public void downloadDestinationTownForcast(){
        destinationTownJsonDownloadService.setCityName(destinationTown.getText());
        destinationTownJsonDownloadService.restart();
        destinationTownJsonDownloadService.setAppManager(appManager);
        destinationTownJsonDownloadService.setOnSucceeded(e -> {
            JsonDownloadResult result = (JsonDownloadResult) destinationTownJsonDownloadService.getValue();
            destinationTownJsonDownloadService.setDestinationTownForcastInAppManager();
            System.out.println(appManager.getDestinationTownForcastJson());
        });
    }
}
