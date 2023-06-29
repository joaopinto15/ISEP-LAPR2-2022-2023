package pt.ipp.isep.dei.esoft.project.ui.console.us8;

import pt.ipp.isep.dei.esoft.project.domain.controller.PostAnnouncementController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Commission;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

/**
 * The type Post announcement ui.
 */
public class PostAnnouncementUI implements Runnable {

    /**
     * The Controller.
     */
    final PostAnnouncementController controller = new PostAnnouncementController();


    /**
     * Choose request request.
     *
     * @return request request
     */
    public Request chooseRequest(){
       return (Request) Utils.showAndSelectOne(controller.getRequestByDate(Utils.getEmployeeInSession()),"### Choose one of the Requests ###",false);
    }

    /**
     * Create commission commission.
     *
     * @return the commission
     */
    public Commission createCommission(){
       float value=Utils.readFloatFromConsole("### What is the value of the commission? ###");
        boolean isPercentage =Utils.confirm("### Is the Commission a percentage? (y/n)###");
        return new Commission(value,isPercentage);
    }

    /**
     * Instantiates a new Post announcement ui.
     */
    public PostAnnouncementUI() {
    }


    /**
     * Run.
     */
    public void run(){
            Request request = chooseRequest();
            Commission commission = createCommission();

        Announcement announcement = new Announcement(request,commission);
        controller.submitAnnouncement(announcement);
        controller.deleteRequest(request);
        System.out.println("The Announcement was posted Successfully");
    }
}
