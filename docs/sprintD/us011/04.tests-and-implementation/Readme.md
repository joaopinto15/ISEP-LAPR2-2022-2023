# US 011 - Order check from announcement

# 4. Tests 

**Test 1:** Check that it is possible to add an order in announcement. 

	@Test
    public void addOrder() {
        Date date = new Date(2023, 2, 4);
        String email = "teste@gmail.com";
        Person person = new Person("João", 123456789, "123456789", email, "912121211","boas",new String[] {AuthenticationController.ROLE_ADMIN});

        Order order = new Order(person, 100000, date);
        announcement.addOrder(order);

        assertTrue(announcement.getOrders().contains(order));
    }

**Test 2:** Check that OrderCheckController returns a correctly sorted OrderList

	/**
     * Tests the getOrdersByOrder() method of the OrderCheckController class.
     */
    @Test
    void getOrdersByOrder() {
        //order2 > order1
        announcement.addOrder(order1);
        announcement.addOrder(order2);
        announcementRepository.saveAnnouncement(announcement);

        AnnouncementDTO announcementDto = AnnouncementMapper.toDto(announcement);

        assertEquals(OrderMapper.toModel(controller.getOrdersByPrice(announcementDto).get(0)), order2);
        assertEquals(OrderMapper.toModel(controller.getOrdersByPrice(announcementDto).get(1)), order1);
    }

**Test 3:** Check that OrderCheckController can remove correctly a order from the respective announcement

	/**
     * Tests the removeThisOrder() method of the OrderCheckController class.
     */
    @Test
    void removeThisOrder() {
        announcement.addOrder(order1);
        announcement.addOrder(order2);
        announcementRepository.saveAnnouncement(announcement);

        //remove order2 in announcement
        announcement.getOrders().remove(order2);

        //convert announcement and orders into Dto´s
        AnnouncementDTO announcementDto = AnnouncementMapper.toDto(announcement);
        OrderDTO orderDto = OrderMapper.toDto(order1);

        //remove order2 in announcementRepository
        controller.removeOrder(announcementDto, orderDto);

        assertEquals(announcement.getOrders(), OrderMapper.toModel(controller.getOrdersByPrice(announcementDto)));
    }

# 5. Construction (Implementation)

## Class OrderCheckController

```java
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
     */
    public boolean acceptOrder(AnnouncementDTO announcementDto, OrderDTO orderDto) {
        Announcement chosenAnnouncement = AnnouncementMapper.toModel(announcementDto);
        Order chosenOrder = OrderMapper.toModel(orderDto);

        chosenOrder.setAccepted(true);
        notifyClient(chosenOrder.getPerson(), chosenAnnouncement, true);
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

        //notifyClient(chosenOrder.getPerson(), chosenAnnouncement, false);
        return announcementRepository.removeOrderFromAnnouncement(chosenAnnouncement, chosenOrder);
    }

    /**
     * Notifies the client about the order acceptance or rejection.
     *
     * @param person       The client to notify.
     * @param announcement the announcement
     * @param wasAccepted  True if the order wasAccepted, false if rejected.
     */
    public void notifyClient(Person person, Announcement announcement, boolean wasAccepted) {
        String subject = "Order validation!";
        String body;
        if (wasAccepted) {
            body = "You order was accepted\n\nAddress: " + announcement.getRequest().getProperty().getAddress().toString();
        } else {
            body = "You order was not accepted\n\nAddress: " + announcement.getRequest().getProperty().getAddress().toString();
        }
        Message message = new Message(subject, announcement.getRequest().getEmployee(), person.getEmailAddress(), body);
        message.send();
    }
}


```

## Class OrderCheckUI

```java
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
            OrderDTO chosenOrder = selectOrder(chosenAnnouncement);
            if(chosenOrder == null) {
                return;
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
            announcementsByDate.add(announcementDto.getRequest().getProperty().toString());
        }
        int chosenOption = Utils.showAndSelectIndex(announcementsByDate, "\n\nAnnouncements", true);
        return announcementsDto.get(chosenOption);
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

```

## Class Order

```java
package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import pt.ipp.isep.dei.esoft.project.domain.model.Date;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an order made by a person, including the person's information, price, and date.
 */
public class Order implements Serializable {
    /**
     * The Person.
     */
    private final Person person;
    /**
     * The Price.
     */
    private final double price;
    /**
     * The Date.
     */
    private final Date date;

    /**
     * The Verification
     */
    private boolean accepted;

    /**
     * Constructs a new Order object with the specified person, price, date, verification.
     *
     * @param person The person who made the order.
     * @param price  The price of the order.
     * @param date   The date when the order was made.
     */
    public Order(Person person, double price, Date date) {
        this.person = person;
        this.price = price;
        this.date = date;
        this.accepted = false;
    }

    public Order(Person person, double price, Date date, boolean accepted) {
        this.person = person;
        this.price = price;
        this.date = date;
        this.accepted = accepted;
    }

    /**
     * Constructs a new Order object by copying the values from another Order object.
     *
     * @param otherOrder The other Order object to copy from.
     */
    public Order(Order otherOrder) {
        this.person = otherOrder.person;
        this.price = otherOrder.price;
        this.date = otherOrder.date;
        this.accepted = otherOrder.accepted;
    }

    /**
     * Returns the person who made the order.
     *
     * @return The person who made the order.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Returns the price of the order.
     *
     * @return The price of the order.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the date when the order was made.
     *
     * @return The date when the order was made.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the validation.
     *
     * @return The Accepted validation.
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the "accepted" flag.
     *
     * @param accepted the new value for the "accepted" flag
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * Returns a string representation of the Order object.
     *
     * @return A string representation of the Order object.
     */
    @Override
    public String toString() {
        return "Order from " + person.getName() +" for " + price + " placed at " + date.toString();
    }

    /**
     * Checks if this Order object is equal to another object.
     *
     * @param o The object to compare for equality.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.price, price) == 0 && accepted == order.accepted && Objects.equals(person, order.person) && Objects.equals(date, order.date);
    }

    /**
     * Compares this Order object with another Order object for ordering purposes.
     *
     * @param otherOrder The other Order object to compare.
     * @return A negative integer if this Order is less than the other Order,         a positive integer if this Order is greater than the other Order,         or zero if they are equal.
     */
    public int compareTo(Order otherOrder) {
        return (otherOrder.isBigger(this)) ? -1 : (this.isBigger(otherOrder)) ? 1 : 0;
    }

    /**
     * Checks if this Order object has a greater price than the other Order object.
     *
     * @param otherOrder The other Order object to compare.
     * @return True if this Order has a greater price, false otherwise.
     */
    public boolean isBigger(Order otherOrder) {
        return this.price > otherOrder.price;
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
        pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal.DealType dealType= pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal.DealType.SALE;
        double price=((request.getProperty().getDeal().getDealType() == dealType) ? ((Sale) request.getProperty().getDeal()).getPropertyPrice() : ((Rent) request.getProperty().getDeal()).getPropertyRent() );


        return  " Announcement Owned by "+ request.getUser().getName()+", Published on " + date +"\n\n"+
                request.getProperty().toString() +"\n"+
                "\nOrders: " +toStringOrder()+"\n";


    }
    private String toStringOrder(){
        String str = "";
        if (orders.isEmpty()) {

            str += "\n     -> There are no orders yet.";
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

    @Override
    public int hashCode() {
        return Objects.hash(request, commission, date, sold, orders);
    }
}
```

# 6. Integration and Demo 

* A new option on the Employee menu options was added.

* Some demo purposes some tasks are bootstrapped while system starts.









