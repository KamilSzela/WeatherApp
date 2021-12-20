package pl.kamilszela.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.*;
import pl.kamilszela.model.OneDayWeatherCityModel;

import java.io.IOException;
import java.util.List;

public class ViewFactory {

    public static final String MAIN_WINDOW_NAME = "Pogoda na lato";
    public static final String MAIN_WINDOW_FXML_FILE_NAME = "/fxml/mainWindow.fxml";
    public static final String FORECAST_BOX_FXML = "/fxml/forecastBox.fxml";
    public static final String ONE_DAY_FORECAST_BOX_FXML = "/fxml/oneDayForecastBox.fxml";
    public static final String ONE_DAY_STAGE_TITLE = "Pogoda na wybrany dzień";
    public static final String ABOUT_WINDOW_FXML = "/fxml/aboutWindow.fxml";
    public static final String ABOUT_WINDOW_TITLE = "Opis aplikacji";
    private AppManager appManager;

    private ColorTheme colorTheme = ColorTheme.LIGHT;

    public ViewFactory(AppManager appManager) {
        this.appManager = appManager;
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(appManager, this);
        String fileName = MAIN_WINDOW_FXML_FILE_NAME;

        Parent parent = loadFXMLFile(controller, fileName);

        showStage(MAIN_WINDOW_NAME, parent, false, false);
    }

    public void prepareForecastPanel(Pane destinationForecastField, Pane sourceTownForecastField) {
        int counter = 0;
        int size = appManager.currentCityWeatherModelList.size();
        if(appManager.currentCityWeatherModelList.size() > 0 && appManager.destinationCityWeatherModelList.size() > 0){
            for(int i = 0; i < size; i++){
                if(counter == 8){
                    showForecastInVBox(i,  appManager.destinationCityWeatherModelList, destinationForecastField, true);
                    showForecastInVBox(i,  appManager.currentCityWeatherModelList, sourceTownForecastField, true);
                    counter = 0;
                }
                counter++;
                if(i == 38){
                    counter++;
                }
            }
        }
    }

    public void showForecastInVBox(int i, List<OneDayWeatherCityModel> list, Pane forecastField,
                                   boolean clickForOneDayForecast) {
        ForecastBoxController controller = new ForecastBoxController(this.appManager, this);
        String fileName = FORECAST_BOX_FXML;
        Parent parent = loadFXMLFile(controller, fileName);
        controller.prepareForecastBox(i,list);
        if(clickForOneDayForecast){
            parent.setOnMouseClicked(e->{
                showForecastForOneDay(list, i);
            });
        }
        forecastField.getChildren().add(parent);
    }

    private void showForecastForOneDay(List<OneDayWeatherCityModel> list, int i) {
        OneDayForecastBoxController controller = new OneDayForecastBoxController(this.appManager, this);
        String fileName = ONE_DAY_FORECAST_BOX_FXML;
        Parent parent = loadFXMLFile(controller, fileName);
        controller.prepareForecastDataForOneDay(list, i);
        showStage(ONE_DAY_STAGE_TITLE, parent, false, true);
    }

    private void showStage(String title, Parent parent, boolean resiable, boolean onTop) {
        Scene scene = new Scene(parent);
        updateStyle(scene);
        Stage stage = new Stage();
        stage.setTitle(title);
        stage.setResizable(resiable);
        stage.setAlwaysOnTop(onTop);
        stage.setScene(scene);
        stage.show();
    }

    private Parent loadFXMLFile(BaseController controller, String fileName) {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource(fileName));
        loader.setController(controller);
        Parent parent = null;
        try{
            parent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        return parent;
    }

    public void updateStyle(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add(getClass().getResource(ColorTheme.getStylesheetPath(colorTheme)).toExternalForm());
    }

    public ColorTheme getColorTheme() {
        return colorTheme;
    }

    public void setColorTheme(ColorTheme colorTheme) {
        this.colorTheme = colorTheme;
    }

    public void showAboutWindow() {
        AboutWindowController controller = new AboutWindowController(this.appManager, this);
        String fileName = ABOUT_WINDOW_FXML;
        Parent parent = loadFXMLFile(controller, fileName);
        showStage(ABOUT_WINDOW_TITLE, parent, false, true);
    }
}
