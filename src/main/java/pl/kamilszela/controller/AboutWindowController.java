package pl.kamilszela.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.view.ViewFactory;

public class AboutWindowController extends BaseController {

    public AboutWindowController(AppManager appManager, ViewFactory viewFactory) {
        super(appManager, viewFactory);
    }

    @FXML
    private Button closeButton;

    @FXML
    void closeWindow() {
        Stage activeStage = (Stage) this.closeButton.getScene().getWindow();
        activeStage.close();
    }
}
