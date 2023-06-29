package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Network manager menu gui.
 */
public class NetworkManagerMenuGUI implements Initializable {

    /**
     * The Lbl agent name.
     */
    @FXML
    private Label lblAgentName;


    /**
     * Initialize.
     *
     * @param url            the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblAgentName.setText("Welcome, Sr. " + Utils.getEmployeeInSession().getName() + "!");
    }

    /**
     * Switch to divide stores.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToDivideStores(ActionEvent event) throws IOException {
        HelloController ctrl = new HelloController();
        ctrl.switchTo(event, "divideStores");
    }

    /**
     * Switch to list deals by area.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToListDealsByArea(ActionEvent event) throws IOException {
        HelloController ctrl = new HelloController();
        ctrl.switchTo(event, "listDealsByArea");
    }


    /**
     * Switch to main.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToMain(ActionEvent event) throws IOException {
        HelloController ctrl = new HelloController();
        AuthenticationController authController = new AuthenticationController();
        authController.doLogout();
        ctrl.switchTo(event, "mainMenu");
    }
}
