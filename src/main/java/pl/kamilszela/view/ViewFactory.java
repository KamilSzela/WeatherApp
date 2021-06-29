package pl.kamilszela.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pl.kamilszela.AppManager;
import pl.kamilszela.controller.MainWindowController;

import java.io.IOException;

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

    public void prepareForecastPanel() {
        System.out.println(this.appManager.destinationCityWeatherModelList.size());
        System.out.println(this.appManager.currentCityWeatherModelList.size());
    }
}
