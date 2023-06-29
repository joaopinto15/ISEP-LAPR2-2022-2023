package pt.ipp.isep.dei.esoft.project.ui.gui.gui;

import io.jsonwebtoken.io.IOException;
import javafx.event.ActionEvent;

import pt.ipp.isep.dei.esoft.project.ui.gui.controller.HelloController;

/**
 * The type Development team gui.
 */
public class DevelopmentTeamGUI {

    /**
     * Switch to main.
     *
     * @param event the event
     * @throws IOException the io exception
     * @throws IOException the io exception
     */
    public void switchToMain(ActionEvent event) throws IOException, java.io.IOException {

        HelloController ctrl = new HelloController();
        ctrl.switchTo(event, "mainMenu");
    }
}
