package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import io.jsonwebtoken.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;


/**
 * The type Login gui.
 */
public class LoginGUI implements Initializable {

    /**
     * The My email.
     */
    @FXML
    private TextField myEmail;

    /**
     * The My password.
     */
    @FXML
    private PasswordField myPassword;

    /**
     * The Ctrl authentication.
     */
    private AuthenticationController ctrlAuthentication;
    /**
     * The Ctrl.
     */
    private HelloController ctrl;

    /**
     * The Error.
     */
    private Alert error;


    /**
     * Switch to main.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToMain(ActionEvent event) throws IOException, java.io.IOException {

        
        ctrl.switchTo(event, "mainMenu");
    }

    /**
     * Do login.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void doLogin(ActionEvent event) throws java.io.IOException {

        if (!ctrlAuthentication.doLogin(myEmail.getText(), myPassword.getText())) {
            error.setAlertType(AlertType.ERROR);
            error.setContentText("Invalid UserId and/or Password.");
            error.showAndWait();
        }
        else{
            List<UserRoleDTO> roles = this.ctrlAuthentication.getUserRoles();
            if ((roles == null) || (roles.isEmpty())) {
                error.setAlertType(AlertType.ERROR);
                error.setContentText("No role assigned to user!");
                error.showAndWait();
            } else {
                UserRoleDTO role = selectsRole(roles);
                redirectToRoleMenu(event,role);
            }
        }
    }

    /**
     * Redirect to role menu.
     *
     * @param event the event
     * @param role  the role
     * @throws IOException the io exception
     */
    public void redirectToRoleMenu(ActionEvent event, UserRoleDTO role) throws java.io.IOException {
        switch (role.getId()) {
            /*
            case AuthenticationController.ROLE_ADMIN:
                ctrl = new HelloController();
                ctrl.switchTo("adminMenu");
                break;
            */
            case AuthenticationController.ROLE_EMPLOYEE:
                switchToAgentMenu(event);
                break;
            case AuthenticationController.ROLE_NETWORK_MANAGER:
                switchToNetworkManagerMenu(event);
                break;
            case AuthenticationController.ROLE_MANAGER:
                switchToManagerMenu(event);
                break;
            default:
                error.setAlertType(AlertType.ERROR);
                error.setContentText("No role assigned to user.");
                error.showAndWait();
                break;
        }
    }


    /**
     * Switch to agent menu.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToAgentMenu(ActionEvent event) throws IOException, java.io.IOException {
        ctrl = new HelloController();
        ctrl.switchTo(event, "agentMenu");
    }

    /**
     * Switch to network manager menu.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToNetworkManagerMenu(ActionEvent event) throws java.io.IOException {
        ctrl = new HelloController();
        ctrl.switchTo(event, "networkManagerMenu");

    }

    /**
     * Switch to manager menu.
     *
     * @param event the event
     * @throws IOException the io exception
     */
    public void switchToManagerMenu(ActionEvent event) throws java.io.IOException {
        ctrl = new HelloController();
        ctrl.switchTo(event, "managerMenu");

    }

    /**
     * Selects role user role dto.
     *
     * @param roles the roles
     * @return the user role dto
     */
    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
            return roles.get(0);
    }

    /**
     * Initialize.
     *
     * @param location  the location
     * @param resources the resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ctrl = new HelloController();
        ctrlAuthentication = new AuthenticationController();
        error = new Alert(AlertType.NONE);
    }
}
