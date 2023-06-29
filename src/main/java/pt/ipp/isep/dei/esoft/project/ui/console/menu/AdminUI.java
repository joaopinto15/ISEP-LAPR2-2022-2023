package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.us12.ImportBusinessUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us3.RegisterEmployeeUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The AdminUI class represents the user interface for the admin functionality.
 * It implements the Runnable interface to be executed in a separate thread.
 */
public class AdminUI implements Runnable {

    /**
     * Constructs a new AdminUI object.
     */
    public AdminUI() {
    }


    /**
     * Executes the admin user interface logic.
     */
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Register an Employee", new RegisterEmployeeUI()));
        options.add(new MenuItem("Import Legacy System", new ImportBusinessUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nAdmin Menu:", true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }

}