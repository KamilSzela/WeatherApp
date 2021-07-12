package pl.kamilszela.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.BaseController;
import pl.kamilszela.controller.MainWindowController;
import pl.kamilszela.controller.ForecastBoxController;
import pl.kamilszela.controller.OneDayForecastBoxController;
import pl.kamilszela.model.WeatherCityModel;

import java.io.IOException;
import java.util.List;

public class ViewFactory {

    public AppManager appManager;

    public ViewFactory(AppManager appManager) {
        this.appManager = appManager;
    }

    public void showMainWindow(){
        BaseController controller = new MainWindowController(appManager, this);
        String fileName = "/fxml/mainWindow.fxml";

        Parent parent = loadFXMLFile(controller, fileName);

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Pogoda na lato");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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

    public void showForecastInVBox(int i, List<WeatherCityModel> list, Pane forecastField,
                                    boolean clickForOneDayForecast) {
        ForecastBoxController controller = new ForecastBoxController(this.appManager, this);
        String fileName = "/fxml/forecastBox.fxml";
        Parent parent = loadFXMLFile(controller, fileName);
        controller.prepareForecastBox(i,list);
        if(clickForOneDayForecast){
            parent.setOnMouseClicked(e->{
                showForecastForOneDay(list, i);
            });
        }
        forecastField.getChildren().add(parent);
    }

    private void showForecastForOneDay(List<WeatherCityModel> list, int i){
        OneDayForecastBoxController controller = new OneDayForecastBoxController(this.appManager, this);
        String fileName = "/fxml/oneDayForecastBox.fxml";
        Parent parent = loadFXMLFile(controller, fileName);

        String dateTime = list.get(i).getDt_txt();
        String date = dateTime.substring(0,10);
        int counter = 0;
        for(int k = 0; k < list.size(); k++){
            String dateOFSingleRecord = list.get(k).getDt_txt();
            String dateForComparision = dateOFSingleRecord.substring(0,10);
            if(date.equals(dateForComparision)){
                counter++;
                controller.fillColumnsWithForecastData(list, k, counter);
            }
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Prognoza na jeden dzień");
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.show();
    }

    private Parent loadFXMLFile(BaseController controller, String fileName){
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
}
