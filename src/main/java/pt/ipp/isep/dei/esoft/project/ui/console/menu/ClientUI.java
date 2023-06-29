package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.us1.ListPropertiesUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us4.ListingRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us10.OrderRequestUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us9.VisitUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The ClientUI class represents the user interface for the client functionality.
 * It implements the Runnable interface to be executed in a separate thread.
 */
public class ClientUI implements Runnable {

    /**
     * Constructs a new ClientUI object.
     */
    public ClientUI() {}

    /**
     * The Ui.
     */
    ListPropertiesUI ui = new ListPropertiesUI();

    /**
     * Executes the client user interface logic.
     */
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Request for listing a property", new ListingRequestUI()));
        options.add(new MenuItem("Request a visit", new VisitUI()));
        options.add(new MenuItem("Request to buy a property", new OrderRequestUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nClient Menu:", true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}