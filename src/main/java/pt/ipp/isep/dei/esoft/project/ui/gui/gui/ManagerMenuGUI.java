package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * The type Manager menu gui.
 */
public class ManagerMenuGUI implements Initializable {

    /**
     * The Btn logout.
     */
    @FXML
    private Button btnLogout;


    /**
     * The Text view.
     */
    @FXML
    private TextArea textView;

    /**
     * Multiple regression.
     *
     * @param event the event
     */
    @FXML
    void multipleRegression(MouseEvent event) {

    }

    /**
     * Simple regression.
     *
     * @param event the event
     */
    @FXML
    void simpleRegression(MouseEvent event) {

    }

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
     * Switch to list booking requests.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToListBookingRequests(ActionEvent event) throws IOException {
        HelloController ctrl = new HelloController();
        ctrl.switchTo(event, "upcomming");
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
