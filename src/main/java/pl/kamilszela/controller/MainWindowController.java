package pl.kamilszela.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.services.CurrentTownJsonDownloadService;
import pl.kamilszela.controller.services.DestinationTownJsonDownloadService;
import pl.kamilszela.controller.services.JsonDownloadService;
import pl.kamilszela.view.ColorTheme;
import pl.kamilszela.view.ViewFactory;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    public static final String ERROR_TEXT_MALFORMED_URL = "Znaleziono błąd w adresie URL";
    public static final String ERROR_TEXT_GENERIC_TEXT = "Wystąpił niespodziewany błąd";
    public static final String INITIAL_SOURCE_CITY_NAME = "Warszawa";
    public static final String INITIAL_DESTINATION_CITY_NAME = "Madryt";
    public static final String ERROR_TEXT_INCOMPATIBLE_ENCODING = "Program nie wspiera podanego kodowania znaków.";
    public static final String ERROR_TEXT_EMPTY_TOWN_TEXT_FIELD = "Proszę wpisać obie nazwy miast w odpowiednie pola";

    private JsonDownloadService currentTownJsonDownloadService;
    private JsonDownloadService destinationTownJsonDownloadService;
   // private JsonDownloadService service;

    @FXML
    private VBox sourceTownForecastField;

    @FXML
    private VBox destinationForecastField;

    @FXML
    private TextField sourceTown;

    @FXML
    private TextField destinationTown;

    @FXML
    private Label errorLabel;

    public MainWindowController(AppManager appManager, ViewFactory viewFactory,
                                CurrentTownJsonDownloadService currentTownService,
                                DestinationTownJsonDownloadService destinationTownService){
        super(appManager, viewFactory);
        this.currentTownJsonDownloadService = currentTownService;
        this.destinationTownJsonDownloadService = destinationTownService;
    }

    @FXML
    public void generateForecastAction() {
        if(!sourceTown.getText().equals("") && !destinationTown.getText().equals("")){
            clearForecastFields();
            appManager.clearJsonForecast();
            try{
                downloadCurrentTownForecast();
                downloadDestinationTownForecast();
            } catch (UnsupportedEncodingException e) {
                errorLabel.setText(ERROR_TEXT_INCOMPATIBLE_ENCODING);
            }
        } else {
            errorLabel.setText(ERROR_TEXT_EMPTY_TOWN_TEXT_FIELD);
        }
    }

    public void downloadCurrentTownForecast() throws UnsupportedEncodingException {
        String cityName = sourceTown.getText();
        String cityNameForDownload = new String(cityName.getBytes(StandardCharsets.UTF_8));
        downloadForecast(currentTownJsonDownloadService, cityNameForDownload, CityType.CURRENT_CITY);
    }
    public void downloadDestinationTownForecast() throws UnsupportedEncodingException {
        String cityName = destinationTown.getText();
        String cityNameForDownload = new String(cityName.getBytes(StandardCharsets.UTF_8));
        downloadForecast(destinationTownJsonDownloadService, cityNameForDownload, CityType.DESTINATION_CITY);
    }
    public void downloadForecast(JsonDownloadService service, String cityName, CityType typeOfCityType){
        service.setCityName(cityName);
        service.setAppManager(appManager);
        service.restart();

        service.setOnSucceeded(e -> {
            APICallResult result = service.getValue();
            switch (result.getResult()){
                case SUCCESS:
                    appManager.setParametersInWeatherCityModel(result.getDownloadedJSON(), typeOfCityType);
                    this.errorLabel.setText("");
                    viewFactory.prepareForecastPanel(destinationForecastField, sourceTownForecastField);
                    break;
                case FAILED_BY_MALFORMED_URL:
                    this.errorLabel.setText(ERROR_TEXT_MALFORMED_URL);
                    break;
                case FAILED_BY_UNEXPECTED_ERROR:
                    this.errorLabel.setText(ERROR_TEXT_GENERIC_TEXT);
                    break;
            }
        });

    }
    @FXML
    void setDarkTheme() {
        viewFactory.setColorTheme(ColorTheme.DARK);
        viewFactory.updateStyle(getScene());
    }

    @FXML
    void setLightTheme() {
        viewFactory.setColorTheme(ColorTheme.LIGHT);
        viewFactory.updateStyle(getScene());
    }
    @FXML
    void closeApp() {
        Platform.exit();
        System.exit(0);
    }

    @FXML
    void showAboutWindow() {
        viewFactory.showAboutWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.sourceTown.setText(INITIAL_SOURCE_CITY_NAME);
        this.destinationTown.setText(INITIAL_DESTINATION_CITY_NAME);
        setUpForecastFields();
    }

    private void setUpForecastFields() {
        sourceTown.setOnMouseClicked(e ->{
            clearForecastFields();
        });
        destinationTown.setOnMouseClicked(e -> {
            clearForecastFields();
        });
    }

    private void clearForecastFields() {
        destinationForecastField.getChildren().clear();
        sourceTownForecastField.getChildren().clear();
    }
    private Scene getScene(){
        return this.errorLabel.getScene();
    }

    public TextField getSourceTown() {
        return sourceTown;
    }

    public TextField getDestinationTown() {
        return destinationTown;
    }

    public Label getErrorLabel() {
        return errorLabel;
    }

    public VBox getSourceTownForecastField() {
        return sourceTownForecastField;
    }

    public VBox getDestinationForecastField() {
        return destinationForecastField;
    }
}
