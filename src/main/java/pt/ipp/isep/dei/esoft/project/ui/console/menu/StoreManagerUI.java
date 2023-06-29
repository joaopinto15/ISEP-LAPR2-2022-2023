package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.us12.ImportBusinessUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us18.AnalyDealUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us3.RegisterEmployeeUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * The AdminUI class represents the user interface for the admin functionality.
 * It implements the Runnable interface to be executed in a separate thread.
 */
public class StoreManagerUI implements Runnable {

    /**
     * Constructs a new AdminUI object.
     */
    public StoreManagerUI() {
    }


    /**
     * Executes the admin user interface logic.
     */
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        try {
            options.add(new MenuItem("Analyse done deals", new AnalyDealUI()));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nAdmin Menu:", true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }

}