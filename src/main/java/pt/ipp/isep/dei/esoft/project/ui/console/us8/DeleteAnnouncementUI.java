package pt.ipp.isep.dei.esoft.project.ui.console.us8;

import pt.ipp.isep.dei.esoft.project.domain.controller.PostAnnouncementController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

/**
 * The type Delete announcement ui.
 */
public class DeleteAnnouncementUI implements Runnable{
    /**
     * The Controller.
     */
    final PostAnnouncementController controller = new PostAnnouncementController();

    /**
     * this function will show the request list and will ask to select one to remove
     *
     * @return the request list
     */
    public Request chooseRequest(){
        return (Request) Utils.showAndSelectOne(controller.getRequestByDate(Utils.getEmployeeInSession()),"### Select one of the requests to Delete ###",false);
    }

    /**
     * Instantiates a new Delete announcement ui.
     */
    public DeleteAnnouncementUI() {
    }

    /**
     * Run.
     */
    public void run(){
        Request request = chooseRequest();

        controller.deleteRequest(request);
        System.out.println("### The Request was Successfully deleted ###");

    }
}
