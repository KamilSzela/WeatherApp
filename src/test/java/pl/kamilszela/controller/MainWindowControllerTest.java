package pl.kamilszela.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import pl.kamilszela.AppManager;
import pl.kamilszela.Runner;
import pl.kamilszela.view.ViewFactory;

import static org.mockito.Mockito.mock;

class MainWindowControllerTest extends ApplicationTest {


    @Start
    public void start(Stage stage) throws Exception{
        AppManager manager = new AppManager();
        ViewFactory factory = new ViewFactory(manager);
        MainWindowController controller = new MainWindowController(manager, factory);
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainWindow.fxml"));
        loader.setController(controller);
        Parent mainNode = loader.load();

        stage.setScene(new Scene(mainNode));
        stage.show();
        stage.toFront();
    }
@Test
    void shouldSetErrorLabelWhenAtLeastOneOfTextFieldsIsEmpty(){
    //given
//    AppManager manager = mock(AppManager.class);
//    ViewFactory viewFactory = mock(ViewFactory.class);
//    MainWindowController controller = new MainWindowController(manager, viewFactory);
    //when


}
}