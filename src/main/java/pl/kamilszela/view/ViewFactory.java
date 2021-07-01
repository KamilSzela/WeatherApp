package pl.kamilszela.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.MainWindowController;
import pl.kamilszela.model.WeatherCityModel;

import java.io.IOException;
import java.util.List;

public class ViewFactory {

    public AppManager appManager;

    public ViewFactory(AppManager appManager) {
        this.appManager = appManager;
    }

    public void showMainWindow(){
        MainWindowController controller = new MainWindowController(appManager, this);

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainWindow.fxml"));
        loader.setController(controller);

        Parent parent = null;

        try {
            parent = loader.load();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Pogoda na lato");
        stage.setScene(scene);
        stage.show();
    }

    public void prepareForecastPanel(Pane destinationForcastField, Pane sourceTownForecastField) {
        int counter = 0;
        int size = appManager.currentCityWeatherModelList.size();
        if(appManager.currentCityWeatherModelList.size() > 0 && appManager.destinationCityWeatherModelList.size() > 0){
            for(int i = 0; i < size; i++){
                if(counter == 8){
                    showPrecastInVBox(i,  appManager.destinationCityWeatherModelList, destinationForcastField);
                    showPrecastInVBox(i,  appManager.currentCityWeatherModelList, sourceTownForecastField);
                    counter = 0;
                }
                counter++;
                if(i == 38){
                    counter++;
                }
            }
        }
    }

    private void showPrecastInVBox(int i, List<WeatherCityModel> list, Pane forecastField) {
        String dateTime = list.get(i).getDt_txt();
        Label labelTimeDestination = new Label(dateTime);
        Double tempDestination = list.get(i).getMain().get("temp");
        Label tempLabeldest = new Label(tempDestination.toString());
        VBox innerBox = new VBox();
        innerBox.getChildren().addAll(labelTimeDestination,tempLabeldest);
        forecastField.getChildren().add(innerBox);
    }
}
