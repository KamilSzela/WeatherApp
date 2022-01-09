package pl.kamilszela;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.kamilszela.view.ViewFactory;

/**
 * Created by kamil on 2021-05-27.
 */
public class Launcher extends Application {

    private final AppManager appManager = new AppManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        ViewFactory viewFactory = new ViewFactory(appManager);
        viewFactory.showMainWindow();

    }
}
