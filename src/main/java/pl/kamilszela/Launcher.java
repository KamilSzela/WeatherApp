package pl.kamilszela;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import pl.kamilszela.view.ViewFactory;

/**
 * Created by kamil on 2021-05-27.
 */
public class Launcher extends Application {

    public AppManager appManager = new AppManager();

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {

//        stage.setScene(new Scene(new Pane(), 300, 300));
//        stage.show();
        ViewFactory viewFactory = new ViewFactory(appManager);
        viewFactory.showMainWindow();

    }
}
