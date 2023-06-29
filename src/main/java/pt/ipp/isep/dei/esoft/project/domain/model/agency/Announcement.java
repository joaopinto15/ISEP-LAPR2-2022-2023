package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The type Announcement.
 */
public class Announcement implements Serializable {

    /**
     * the variable request holds the request of the Announcement.
     */
    private final Request request;

    /**
     * the variable commission holds the commission of the Announcement.
     */
    private final Commission commission;

    /**
     * the variable date holds the date of the Announcement.
     */
    private final Date date;
    /**
     * the variable historic holds the state of the announcement.
     */
    private final boolean sold;

    /**
     * The Final price.
     */
    private final double finalPrice;

    /**
     * the variable orders holds the orders of the Announcement.
     */
    List<Order> orders = new ArrayList<>();

    /**
     * The Requests.
     */
    List<Request> requests = new ArrayList<>();


    /**
     * Instantiates a new Announcement.
     *
     * @param request    the request
     * @param commission the commission
     */
    public Announcement(Request request, Commission commission) {
        this.request = request;
        this.commission = commission;
        this.date = Date.currentDate();
        this.sold = false;
        this.finalPrice = getFinalPrice();
    }

    /**
     * Instantiates a new Announcement.
     *
     * @param request    the request
     * @param commission the commission
     * @param date       the date
     * @param sold       the sold
     * @param orders     the orders
     */
    public Announcement(Request request, Commission commission, Date date, boolean sold, List<Order> orders) {
        this.commission = commission;
        this.date = date;
        this.sold = sold;
        this.orders = orders;
        this.request = request;
        this.finalPrice = getFinalPrice();
    }

    /**
     * Instantiates a new Announcement.
     *
     * @param request    the request
     * @param commission the commission
     * @param date       the date
     * @param sold       the sold
     */
    public Announcement(Request request, Commission commission, Date date, boolean sold) {
        this.request = request;
        this.commission = commission;
        this.date = date;
        this.sold = sold;
        this.finalPrice = getFinalPrice();
    }

    /**
     * This function creates an instance of Announcement with the same parameters as Announcement
     *
     * @param otherAnnouncement the Announcement with the parameters to copy
     */
    public Announcement(Announcement otherAnnouncement) {

        this.request = otherAnnouncement.request;
        this.commission = otherAnnouncement.commission;
        this.date = otherAnnouncement.date;
        this.sold = otherAnnouncement.sold;
        this.orders = otherAnnouncement.orders;
        this.finalPrice = getFinalPrice();
    }

    /**
     * Method to return the request of the Announcement
     *
     * @return the request of the Announcement
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public double getPrice() {
        return finalPrice;
    }


    /**
     * Gets requests.
     *
     * @return the requests
     */
    public List<Request> getRequests() {
        return requests;
    }

    /**
     * Method to return the commission of the Announcement
     *
     * @return the commission of the Announcement
     */
    public Commission getCommission() {
        return commission;
    }

    /**
     * Method to return the data of the Announcement
     *
     * @return the data of the Announcement
     */
    public Date getDate() {
        return date;
    }

    /**
     * Method to return the orders of the Announcement
     *
     * @return the orders of the Announcement
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * This function add a order in list of orders
     *
     * @param order The order related to the announcement.
     * @return the boolean
     */
    public boolean addOrder(Order order) {
        return orders.add(order);
    }

    /**
     * To string string.
     *
     * @param showOrders the show orders
     * @return the string
     */
    public String toString(boolean showOrders) {
        if (showOrders) {
            return  "[Announcement Owned by "+ request.getUser().getName()+", Published on " + date +"]\n\n"+
                    "\n     -> Price: "+ finalPrice+
                    request.getProperty().toString() +"\n"+
                    "\n     Orders: " +toStringOrder()+"\n";
        }
        return "[Announcement Owned by " + request.getUser().getName() + ", Published on " + date + "]\n\n" +
                "\n     -> Price: " + finalPrice +
                request.getProperty().toString() + "\n";
    }

    /**
     * To string order string.
     *
     * @return the string
     */
    private String toStringOrder(){
        String str = "";
        if (orders.isEmpty()) {

            str += "\n          -> There are no orders yet.";
        }else{
            for (Order order : orders) {
                str += "\n     -> "+order.toString();
            }
        }
        return str;
    }


    /**
     * Calculates the final price for this announcement, considering the deal price and commission.
     *
     * @return the final price for the announcement
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
     * Retrieves the deal price for a property.
     *
     * @param property the Property object for which to retrieve the deal price
     * @return the deal price of the property
     */
    public double getDealPrice(Property property) {
        if (property.getDeal().getDealType() == Deal.DealType.SALE) {
            return ((Sale) property.getDeal()).getPropertyPrice();
        } else {
            return ((Rent) property.getDeal()).getPropertyRent();
        }
    }

    /**
     * Is sold boolean.
     *
     * @return the boolean
     */
    public boolean isSold() {
        return sold;
    }

    /**
     * Checks if this House object is equal to another object.
     *
     * @param otherAnnouncement the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherAnnouncement) {
        if (this == otherAnnouncement) return true;
        if (otherAnnouncement == null || getClass() != otherAnnouncement.getClass()) return false;
        Announcement that = (Announcement) otherAnnouncement;
        return Objects.equals(request, that.request) && Objects.equals(commission, that.commission) && Objects.equals(date, that.date) && Objects.equals(orders, that.orders);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(request, commission, date, sold, orders);
    }
}