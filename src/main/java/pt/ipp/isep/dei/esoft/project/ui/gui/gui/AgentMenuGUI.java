package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Agent menu gui.
 */
public class AgentMenuGUI implements Initializable {

    /**
     * The Lbl agent name.
     */
    @FXML
    private Label lblAgentName;

    /**
     * The List booking requests.
     */
    @FXML
    private Button listBookingRequests;

    /**
     * The Btn logout.
     */
    @FXML
    private Button btnLogout;


    /**
     * Initialize.
     *
     * @param url            the url
     * @param resourceBundle the resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblAgentName.setText("Welcome, Agent " + Utils.getEmployeeInSession().getName() + "!");
    }

    /**
     * Switch to list booking requests.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToListBookingRequests(ActionEvent event) throws IOException {
        HelloController ctrl = new HelloController();
        ctrl.switchTo(event, "bookingRequests");
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
