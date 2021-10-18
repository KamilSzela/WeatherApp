package pl.kamilszela.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import pl.kamilszela.AppManager;
import pl.kamilszela.view.ColorTheme;
import pl.kamilszela.view.ViewFactory;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;


class MainWindowControllerTest extends ApplicationTest {

    AppManager manager;
    ViewFactory factory;
    MainWindowController controller;

    @Start
    public void start(Stage stage) throws Exception{
//        manager = new AppManager();
//        factory = new ViewFactory(manager);
        manager = mock(AppManager.class);
        factory = mock(ViewFactory.class);
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
    @Test
    void shouldSetErrorLabelWhenBothFieldsAreEmpty(){
        //given
        controller.getSourceTown().clear();
        controller.getDestinationTown().clear();
        //when
        clickOn(".button", MouseButton.PRIMARY);
        //then
        assertThat(controller.getErrorLabel().getText(), equalTo("Proszę wpisać obie nazwy miast w odpowiednie pola"));
    }
    @Test
    void afterDownloadingForecastFromExternalSiteDataShouldBeSetInAppManager() {
        //given
        //when
        clickOn(".button", MouseButton.PRIMARY);
        sleep(500);
        //then
        verify(manager, times(2)).setParametersInWeatherCityModel();
    }
    @Test
    void afterDownloadingForecastDataForecastBoxesShouldBeLoadedToVBoxes(){
        //given
        //when
        clickOn(".button", MouseButton.PRIMARY);
        sleep(500);
        //then
        verify(factory, times(2)).prepareForecastPanel(controller.getDestinationForcastField(), controller.getSourceTownForcastField());
    }
    @Test
    void clickingOnDarkThemeMenuItemShouldSetDarkTheme(){
        //given
        //when
        clickOn("#themesMenu", MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#darkThemeMenuItem", MouseButton.PRIMARY);
        //then
        verify(factory).updateStyle(controller.getErrorLabel().getScene());
        verify(factory).setColorTheme(ColorTheme.DARK);
    }
    @Test
    void clickingOnLightThemeMenuItemShouldSetLightTheme(){
        //given
        //when
        clickOn("#themesMenu", MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#lightThemeMenuItem", MouseButton.PRIMARY);
        //then
        verify(factory).updateStyle(controller.getErrorLabel().getScene());
        verify(factory).setColorTheme(ColorTheme.LIGHT);
    }
    @Test
    void clickingOnShowAboutWindowMenuItemShouldCallMethodFromViewFactory(){
        //given
        //when
        clickOn("#aboutMenu", MouseButton.PRIMARY);
        WaitForAsyncUtils.waitForFxEvents();
        clickOn("#showAboutMenuItem", MouseButton.PRIMARY);
        //then
        verify(factory).showAboutWindow();
    }
}