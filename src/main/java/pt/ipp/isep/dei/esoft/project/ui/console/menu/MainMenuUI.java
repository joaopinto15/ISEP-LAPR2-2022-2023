package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.*;
//import pt.ipp.isep.dei.esoft.project.ui.console.us7.RegisterUserUI;
import pt.ipp.isep.dei.esoft.project.ui.console.authorization.AuthenticationUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us1.ListPropertiesUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us7.RegisterUserUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.ipp.isep.dei.esoft.project.ui.gui.gui.AnnouncementListGUI;
import pt.ipp.isep.dei.esoft.project.ui.gui.gui.MainMenuGUI;

import java.util.ArrayList;
import java.util.List;

/**
 * The MainMenuUI class represents the main user interface of the application.
 * It implements the Runnable interface to be executed in a separate thread.
 */
public class MainMenuUI implements Runnable {

    /**
     * Constructs a new MainMenuUI object.
     */
    public MainMenuUI() {
    }

    /**
     * Executes the main menu user interface logic.
     */
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Do Login", new AuthenticationUI()));
        options.add(new MenuItem("Know the Development Team", new DevTeamUI()));
        options.add(new MenuItem("List Properties", new ListPropertiesUI()));
        options.add(new MenuItem("Register a User", new RegisterUserUI()));
        options.add(new MenuItem("Enter in the Graphical Interface", new MainMenuGUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nMain Menu", true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}