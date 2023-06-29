package pt.ipp.isep.dei.esoft.project.ui.console.menu;

import pt.ipp.isep.dei.esoft.project.ui.console.us8.DeleteAnnouncementUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us11.OrderCheckUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us2.CreateAnnouncementUI;
import pt.ipp.isep.dei.esoft.project.ui.console.us8.PostAnnouncementUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The EmployeeUI class represents the user interface for the employee functionality.
 * It implements the Runnable interface to be executed in a separate thread.
 */
public class EmployeeUI implements Runnable {

    /**
     * Executes the employee user interface logic.
     */
    @Override
    public void run() {
        List<MenuItem> options = new ArrayList<MenuItem>();
        options.add(new MenuItem("Create Announcement", new CreateAnnouncementUI()));
        options.add(new MenuItem("Check announcements orders", new OrderCheckUI()));
        options.add(new MenuItem("Post existing Request", new PostAnnouncementUI()));
        options.add(new MenuItem("Delete Request", new DeleteAnnouncementUI()));

        int option = 0;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nEmployee Menu:", true);

            if ((option >= 0) && (option < options.size())) {
                options.get(option).run();
            }
        } while (option != -1);
    }
}