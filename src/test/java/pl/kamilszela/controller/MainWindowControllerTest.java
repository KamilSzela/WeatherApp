package pl.kamilszela.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import pl.kamilszela.AppManager;
import pl.kamilszela.Runner;
import pl.kamilszela.view.ViewFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


class MainWindowControllerTest extends ApplicationTest {

    AppManager manager;
    ViewFactory factory;
    MainWindowController controller;
    @Start
    public void start(Stage stage) throws Exception{
        manager = new AppManager();
        factory = new ViewFactory(manager);
        controller = new MainWindowController(manager, factory);
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
        controller.getSourceTown().clear();
        //when
        clickOn(".button", MouseButton.PRIMARY);
        //then
        assertThat(controller.getErrorLabel().getText(), equalTo("Proszę wpisać obie nazwy miast w odpowiednie pola"));
    }

}