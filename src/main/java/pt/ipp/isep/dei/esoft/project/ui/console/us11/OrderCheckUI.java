package pt.ipp.isep.dei.esoft.project.ui.console.us11;

import pt.ipp.isep.dei.esoft.project.domain.controller.OrderCheckController;
import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.dto.OrderDTO;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * User interface for order check operations.
 */
public class OrderCheckUI implements Runnable {

    /**
     * The Controller.
     */
    private final OrderCheckController controller = new OrderCheckController();

    /**
     * Executes the order check UI.
     */
    public void run() {
        try {
            AnnouncementDTO chosenAnnouncement = selectAnnouncement();
            if(chosenAnnouncement == null) {
                return;
            }
            OrderDTO chosenOrder = selectOrder(chosenAnnouncement);
            if(chosenOrder == null) {
                run();
            }
            validateOrder(chosenAnnouncement, chosenOrder);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Selects an announcement from the available options.
     *
     * @return The selected announcement.
     */
    public AnnouncementDTO selectAnnouncement() {
        List<AnnouncementDTO> announcementsDto = controller.getAnnouncementsByDate();
        List<String> announcementsByDate = new ArrayList<>();
        for (AnnouncementDTO announcementDto : announcementsDto) {
            announcementsByDate.add(announcementDto.toString());
        }
        int option = Utils.showAndSelectIndex(announcementsByDate, "\n\nAnnouncements", true);
        if (option != -1) {
            return announcementsDto.get(option);
        }
        return null;
    }

    /**
     * Selects an order related to the given announcement from the available options.
     *
     * @param announcementDto The selected announcement.
     * @return The selected order.
     */
    public OrderDTO selectOrder(AnnouncementDTO announcementDto) {
        List<OrderDTO> ordersDto = controller.getOrdersByPrice(announcementDto);
        if (ordersDto.isEmpty()) {
            System.out.println("There are no orders for this announcement.");
            return null;
        }
        return (OrderDTO) Utils.showAndSelectOne(ordersDto, "\n\nOrders", true);
    }

    /**
     * Validates the selected order for the given announcement.
     *
     * @param announcementDto The selected announcement.
     * @param orderDto        The selected order.
     */
    public void validateOrder(AnnouncementDTO announcementDto, OrderDTO orderDto) {
        try {
            List<String> options = List.of("Accept", "Denied", "Skip");

            int option = Utils.showAndSelectIndex(options, "\n\nSelect an option", false);

            switch (option) {
                case 0:
                    boolean orderAccepted = controller.acceptOrder(announcementDto, orderDto);
                    if (orderAccepted) {
                        System.out.println("Order Accepted");
                    } else {
                        System.out.println("Error, please try again");
                    }
                    run();
                    break;
                case 1:
                    boolean orderRemoved = controller.removeOrder(announcementDto, orderDto);
                    if (orderRemoved) {
                        System.out.println("Order Denied");
                    } else {
                        System.out.println("Error, please try again");
                    }
                    run();
                    break;
                case 2:
                    run();
                    break;
            }
        } catch (Exception e) {
            System.out.println("An error occurred while validating an order: " + e.getMessage());
        }
    }
}
