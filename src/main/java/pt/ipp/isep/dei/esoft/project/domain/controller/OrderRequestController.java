package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Order;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.util.List;

/**
 * The type Order request controller.
 */
public class OrderRequestController {
    /**
     * Initiates the instance repositories of the class Repositories
     */
    private final AnnouncementRepository announcementRepository = Repositories.getInstance().getAnnouncementRepository();


    /**
     * Instantiates a new Order request controller.
     */
    public OrderRequestController() {

    }

    /**
     * Gets announcements.
     *
     * @return the announcements
     */
    public List<Announcement> getAnnouncements() {
        return announcementRepository.getNotSoldAnnouncementList();
    }

    /**
     * Gets announcements.
     *
     * @param email the email
     * @return the announcements
     */
    public List<Announcement> getAnnouncements(Email email) {
        List<Announcement> announcements = getAnnouncements();
        //Remove announcements that contain offers from the client
        announcements.removeIf(announcement ->
                announcement.getOrders().stream().anyMatch(order ->
                        order.getPerson().getEmailAddress().equals(email)));
        return announcements;
    }

    /**
     * Gets orders.
     *
     * @param announcement the announcement
     * @return the orders
     */
    public List<Order> getOrders(Announcement announcement) {
        return announcement.getOrders();
    }

    /**
     * Add order.
     *
     * @param announcement the announcement
     * @param order        the order
     * @return the boolean
     */
    public boolean addOrder(Announcement announcement, Order order) {
        boolean save = announcement.addOrder(order);
        if (save) {
            serializeAnnouncement();
            return true;
        }
        return false;
    }

    /**
     * Checkif request price is unique boolean.
     *
     * @param offer        the offer
     * @param announcement the announcement
     * @return the boolean
     */
    public boolean isRequestPriceUnique(double offer, Announcement announcement){
        for (Order order : announcement.getOrders()) {
            if (order.getPrice() == offer) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if request price is lower than announcement price boolean.
     *
     * @param offer        the offer
     * @param announcement the announcement
     * @return the boolean
     */
    public boolean isRequestPriceIsHigherThanAnnouncementPrice(double offer, Announcement announcement){
        return offer > announcement.getFinalPrice();
    }

    /**
     * Serialize announcement.
     */
    private void serializeAnnouncement() {
        announcementRepository.serializeAnnouncementList();
    }
}
