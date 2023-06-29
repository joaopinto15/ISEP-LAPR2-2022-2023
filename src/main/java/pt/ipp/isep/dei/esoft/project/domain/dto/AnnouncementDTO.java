package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Commission;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.io.Serializable;
import java.util.List;

/**
 * The AnnouncementDTO class represents an announcement with information about the request,
 * commission, date, sold status, and a list of orders associated with the announcement.
 */
public class AnnouncementDTO implements Serializable {

    /**
     * The Request.
     */
    private final Request request;
    /**
     * The Commission.
     */
    private final Commission commission;
    /**
     * The Date.
     */
    private final Date date;
    /**
     * The Sold.
     */
    private final boolean sold;
    /**
     * The Orders.
     */
    private List<OrderDTO> orders;
    /**
     * The Final price.
     */
    private final double finalPrice;

    /**
     * Constructs an AnnouncementDTO object with the specified request and commission.
     * The date is set to the current date and the sold status is set to false by default.
     *
     * @param request    the request associated with the announcement
     * @param commission the commission associated with the announcement
     */
    public AnnouncementDTO(Request request, Commission commission) {
        this.request = request;
        this.commission = commission;
        this.date = Date.currentDate();
        this.sold = false;
        this.finalPrice = getFinalPrice();
    }

    /**
     * Constructs an AnnouncementDTO object with the specified request, commission, date, sold status, and orders.
     *
     * @param request    the request associated with the announcement
     * @param commission the commission associated with the announcement
     * @param date       the date of the announcement
     * @param sold       whether the announcement has been sold or not
     * @param orders     the list of orders associated with the announcement
     */
    public AnnouncementDTO(Request request, Commission commission, Date date, boolean sold, List<OrderDTO> orders) {
        this.request = request;
        this.commission = commission;
        this.date = date;
        this.sold = sold;
        this.orders = orders;
        this.finalPrice = getFinalPrice();
    }

    /**
     * Gets final price.
     *
     * @return the final price
     */
    public double getFinalPrice() {
        double price = getDealPrice(this.getRequest().getProperty());
        float commissionValue = this.getCommission().getValue();
        boolean isPercentage = this.getCommission().isPercentage();

        if (isPercentage) {
            return price + commissionValue * price;
        } else {
            return price + commissionValue;
        }
    }

    /**
     * Gets deal price.
     *
     * @param property the property
     * @return the deal price
     */
    public double getDealPrice(Property property) {
        if (property.getDeal().getDealType() == Deal.DealType.SALE) {
            return ((Sale) property.getDeal()).getPropertyPrice();
        } else {
            return ((Rent) property.getDeal()).getPropertyRent();
        }
    }

    /**
     * Returns the request associated with the announcement.
     *
     * @return the request associated with the announcement
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Returns the commission associated with the announcement.
     *
     * @return the commission associated with the announcement
     */
    public Commission getCommission() {
        return commission;
    }

    /**
     * Returns the date of the announcement.
     *
     * @return the date of the announcement
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns whether the announcement has been sold or not.
     *
     * @return true if the announcement has been sold, false otherwise
     */
    public boolean isSold() {
        return sold;
    }

    /**
     * Returns the list of orders associated with the announcement.
     *
     * @return the list of orders associated with the announcement
     */
    public List<OrderDTO> getOrders() {
        return orders;
    }

    /**
     * Returns a string representation of the AnnouncementDTO object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return "[Announcement Owned by "+ request.getUser().getName()+", Published on " + date +"]\n\n"+
                "\n     -> Price: "+ finalPrice+
                request.getProperty().toString() +"\n";

    }
}