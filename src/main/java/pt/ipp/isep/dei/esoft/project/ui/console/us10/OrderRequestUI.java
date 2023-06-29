package pt.ipp.isep.dei.esoft.project.ui.console.us10;

import pt.ipp.isep.dei.esoft.project.domain.controller.OrderRequestController;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Order;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.List;

/**
 * The type Order request ui.
 */
public class OrderRequestUI implements Runnable {

    /**
     * The Controller.
     */
    private final OrderRequestController controller = new OrderRequestController();

    /**
     * Run.
     */
    public void run() {
        System.out.println("-------------------\n");
        System.out.println("|  Order Request  |\n");
        System.out.println("-------------------\n");

        boolean isUnique;
        boolean isHigherThanAnnouncement;
        double offer;
            List<Announcement> announcements = controller.getAnnouncements(Utils.getPersonInSession().getEmailAddress());
            
            Announcement chosenAnnouncement = (Announcement) Utils.showAndSelectOneTest(announcements, "Choose an announcement:", true);
            if (chosenAnnouncement == null) {
                System.out.println("No announcement was chosen.");
                return;
            }
            
        do{
            offer = Utils.readDoubleFromConsole("Insert offer:");
            isUnique = controller.isRequestPriceUnique(offer,chosenAnnouncement);
            if(!isUnique) {

                boolean answer = Utils.confirm("\nOffer already exists! Do you want to change the value Price? (y/n)\n");
                if(answer){
                    do{
                        offer = Utils.readDoubleFromConsole("Insert a different offer:");
                        isUnique = controller.isRequestPriceUnique(offer,chosenAnnouncement);
                    }while(!isUnique);
                }
                
        
            }
            isHigherThanAnnouncement = controller.isRequestPriceIsHigherThanAnnouncementPrice(offer,chosenAnnouncement);
            if(isHigherThanAnnouncement) {
                System.out.println("\nOffer is higher than the announcement price! Please insert a lower price\n");
            }
        }while(isHigherThanAnnouncement) ;
        
        if(controller.addOrder(chosenAnnouncement, new Order(Utils.getPersonInSession(),offer,Date.currentDate()))){
            System.out.println("Order added successfully!");
        }

    }
}
