package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.kamilszela.controller.services.JsonDownloadService;

public class MainWindowController {
    JsonDownloadService jsonDownloadService = new JsonDownloadService();

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

    @FXML
    void generateForcastAction() {
        if(sourceTown.getText() != ""){
            jsonDownloadService.setCityName(sourceTown.getText());
            jsonDownloadService.restart();
        }
    }
}
