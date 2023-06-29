package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.dto.OrderDTO;
import pt.ipp.isep.dei.esoft.project.domain.mapper.AnnouncementMapper;
import pt.ipp.isep.dei.esoft.project.domain.mapper.OrderMapper;
import pt.ipp.isep.dei.esoft.project.domain.model.Message;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Order;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.Comparator;
import java.util.List;

/**
 * Controller class for order check operations.
 */
public class OrderCheckController {

    /**
     * The announcementRepository variable holds the instance of AnnouncementRepository.
     */
    private final AnnouncementRepository announcementRepository;

    /**
     * Constructs an instance of OrderCheckController, setting the repositories
     * variable to the instance of Repositories, and the announcementRepository
     * variable to the corresponding instance.
     */
    public OrderCheckController() {
        Repositories repositories = Repositories.getInstance();
        this.announcementRepository = repositories.getAnnouncementRepository();
    }

    /**
     * Returns a list of announcements sorted by date.
     *
     * @return List of announcements sorted by date.
     */
    public List<AnnouncementDTO> getAnnouncementsByDate() {
        List<Announcement> announcementsByDate = announcementRepository.getNotSoldAnnouncementList();
        announcementsByDate.sort(new Comparator<>() {
            @Override
            public int compare(Announcement announcement, Announcement otherAnnouncement) {
                return announcement.getDate().compareTo(otherAnnouncement.getDate());
            }
        });
        return AnnouncementMapper.toDto(announcementsByDate);
    }

    /**
     * Returns a list of orders for a specific announcement, sorted by price.
     *
     * @param announcementDto The announcement for which to retrieve the list of orders.
     * @return List of orders for the announcement, sorted by custom criteria.
     */
    public List<OrderDTO> getOrdersByPrice(AnnouncementDTO announcementDto) {
        Announcement announcement = AnnouncementMapper.toModel(announcementDto);

        List<Order> ordersByOrder = announcement.getOrders();
        ordersByOrder.sort(new Comparator<>() {
            @Override
            public int compare(Order order, Order otherOrder) {
                return otherOrder.compareTo(order);
            }
        });
        return OrderMapper.toDto(ordersByOrder);
    }

    /**
     * Accepts the given order for the announcement and notifies the client.
     *
     * @param announcementDto The announcement of the order.
     * @param orderDto        The order to accept.
     * @return the boolean
     */
    public boolean acceptOrder(AnnouncementDTO announcementDto, OrderDTO orderDto) {
        Announcement chosenAnnouncement = AnnouncementMapper.toModel(announcementDto);
        Order chosenOrder = OrderMapper.toModel(orderDto);

        chosenOrder.setAccepted(true);
        notifyClient(chosenOrder.getPerson(), chosenAnnouncement, chosenOrder, true);
        return announcementRepository.removeAnnouncement(chosenAnnouncement);
    }

    /**
     * Removes the given order from the announcement and notifies the client.
     *
     * @param announcementDto The announcement of the order.
     * @param orderDto        The order to remove.
     * @return True if the order was successfully removed, false otherwise.
     */
    public boolean removeOrder(AnnouncementDTO announcementDto, OrderDTO orderDto) {
        Announcement chosenAnnouncement = AnnouncementMapper.toModel(announcementDto);
        Order chosenOrder = OrderMapper.toModel(orderDto);

        notifyClient(chosenOrder.getPerson(), chosenAnnouncement, chosenOrder, false);
        return announcementRepository.removeOrderFromAnnouncement(chosenAnnouncement, chosenOrder);
    }

    /**
     * Notifies the client about the order acceptance or rejection.
     *
     * @param person       The client to notify.
     * @param announcement the announcement
     * @param order        the order
     * @param wasAccepted  True if the order wasAccepted, false if rejected.
     */
    public void notifyClient(Person person, Announcement announcement,Order order, boolean wasAccepted) {
        String subject = "Order validation!";
        String body;
        if (wasAccepted) {

            body = "You order was accepted\n\n" +
                    order.toString() + "\n\n" +
                    "Address: " + announcement.getRequest().getProperty().getAddress().toString();
        } else {
            body = "You order was not accepted\n\n" +
                    order.toString() + "\n\n" +
                    "Address: " + announcement.getRequest().getProperty().getAddress().toString();
        }
        Message message = new Message(subject, announcement.getRequest().getEmployee(), person.getEmailAddress(), body);
        message.send();
    }
}
