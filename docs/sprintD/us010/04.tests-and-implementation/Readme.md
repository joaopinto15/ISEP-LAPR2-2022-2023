# US 010 - Place an order to purchase the property

# 5. Construction (Implementation)


## Class OrderRequestController 

```java

public class OrderRequestController {
    /**
     * Initiates the instance repositories of the class Repositories
     */
    private final AnnouncementRepository AnnouncementRepository = Repositories.getInstance().getAnnouncementRepository();
    /**
     * The Authentication repository.
     */
    private final AuthenticationRepository AuthenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    /**
     * The Person repository.
     */
    private final PersonRepository PersonRepository = Repositories.getInstance().getPersonRepository();

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
        return AnnouncementRepository.getNotSoldAnnouncementList();
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
     */
    public void addOrder(Announcement announcement, Order order) {
        announcement.addOrder(order);
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
}
```


## Class Announcement

```java

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
     * the variable orders holds the orders of the Announcement.
     */
    List<Order> orders = new ArrayList<>();


    public Announcement(Request request, Commission commission) {
        this.request = request;
        this.commission = commission;
        this.date = Date.currentDate();
        this.sold = false;
    }

    public Announcement(Request request, Commission commission, Date date, boolean sold, List<Order> orders) {
        this.request = request;
        this.commission = commission;
        this.date = date;
        this.sold = sold;
        this.orders = orders;
    }

    public Announcement(Request request, Commission commission, Date date, boolean sold) {
        this.request = request;
        this.commission = commission;
        this.date = date;
        this.sold = sold;
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
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Announcement " +
                "request=" + request +
                ", commission=" + commission +
                ", datePosted=" + date +
                ", orders=" + orders;
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


}
```

# 6. Integration and Demo 

* A new option on the Client menu options was added.

* Some orders are bootstrapped while system starts.


# 7. Observations

We choose to put an orderList inside the announcement, since like that is easier to acess the announcements requests orders and the final order. 





