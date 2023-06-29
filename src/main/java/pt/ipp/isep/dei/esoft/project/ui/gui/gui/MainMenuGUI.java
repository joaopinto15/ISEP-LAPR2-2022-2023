package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import java.util.Objects;
import java.util.ResourceBundle;

import javax.print.DocFlavor.URL;

import io.jsonwebtoken.io.IOException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;


/**
 * The type Main menu gui.
 */
public class MainMenuGUI extends Application implements  Runnable{
    /**
     * The constant APP_TITLE.
     */
    private static final String APP_TITLE = "Real Estate USA - Main Menu";
    /**
     * The Ctrl.
     */
    private HelloController ctrl;

    /**
     * Switch to login.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToLogin(ActionEvent event) throws IOException, java.io.IOException {
        ctrl = new HelloController();
        ctrl.switchTo(event,"login");
    }

    /**
     * Switch to list deal by area.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToListDealByArea(ActionEvent event) throws IOException, java.io.IOException {
        ctrl = new HelloController();
        ctrl.switchTo(event,"listDealsByArea");

    }

    /**
     * Switch to publish announcement.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToPublishAnnouncement(ActionEvent event) throws IOException, java.io.IOException {

        ctrl = new HelloController();
        ctrl.switchTo(event, "US002/publishAnnouncement");
    }

    /**
     * Switch to development team.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToDevelopmentTeam(ActionEvent event) throws IOException, java.io.IOException {

        ctrl = new HelloController();
        ctrl.switchTo(event, "developmentTeam");
    }

    /**
     * Switch to divide stores.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToDivideStores(ActionEvent event) throws IOException, java.io.IOException {

        ctrl = new HelloController();
        ctrl.switchTo(event, "divideStores");
    }

    /**
     * Run.
     */
    @Override
    public void run() {
        launch();
    }

    /**
     * Start.
     *
     * @param stage the stage
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    @Override
    public void start(Stage stage) throws IOException, java.io.IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/fxml/mainMenu.fxml")));
        Scene scene = new Scene(root);
        stage.setTitle(APP_TITLE);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}
