# US 011 - Order check from announcement

# 4. Tests 

**Test 1:** Check that it is possible to add an order in announcement. 

	**
     * Tests the addOrder() method of the Announcement class.
     */
    @Test
    public void addOrder() {
        Date date = new Date(2023, 2, 4);
        Email email = new Email("teste@gmail.com");
        Person person = new Person("João", 123456789, 123456789, email, 912121211,"boas",AuthenticationController.ROLE_ADMIN);

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
        assertEquals(controller.getOrdersByOrder(announcement).get(0), order2);
        assertEquals(controller.getOrdersByOrder(announcement).get(1), order1);
    }

**Test 3:** Check that OrderCheckController can remove correctly a order from the respective announcement

	/**
     * Tests the removeThisOrder() method of the OrderCheckController class.
     */
    @Test
    void removeThisOrder() {
        announcement.addOrder(order1);
        announcement.addOrder(order2);
        controller.removeThisOrder(announcement, order1);
        assertFalse(announcement.getOrders().contains(order1));
        assertTrue(announcement.getOrders().contains(order2));
    }

# 5. Construction (Implementation)

## Class OrderCheckController

```java
/**
 * Controller class for order check operations.
 */
public class OrderCheckController {

    /**
     * The repositories variable holds the instance of Repositories.
     */
    private final Repositories repositories;

    /**
     * The authenticationRepository variable holds the instance of AuthenticationRepository.
     */
    private final AuthenticationRepository authenticationRepository;


    private final PersonRepository personRepository;
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
        this.repositories = Repositories.getInstance();
        this.authenticationRepository = repositories.getAuthenticationRepository();
        this.personRepository = repositories.getPersonRepository();
        this.announcementRepository = repositories.getAnnouncementRepository();
    }

    /**
     * Returns a list of announcements sorted by date.
     *
     * @return List of announcements sorted by date.
     */
    public List<Announcement> getAnnouncementsByOrder() {
        List<Announcement> announcementsByOrder = announcementRepository.getAnnouncementList();
        announcementsByOrder.sort(new Comparator<>() {
            @Override
            public int compare(Announcement announcement, Announcement otherAnnouncement) {
                return announcement.getDate().compareTo(otherAnnouncement.getDate());
            }
        });
        return announcementsByOrder;
    }

    /**
     * Returns a list of orders for a specific announcement, sorted by price.
     *
     * @param announcement The announcement for which to retrieve the list of orders.
     * @return List of orders for the announcement, sorted by custom criteria.
     */
    public List<Order> getOrdersByOrder(Announcement announcement) {
        List<Order> ordersByOrder = announcement.getOrders();
        ordersByOrder.sort(new Comparator<>() {
            @Override
            public int compare(Order order, Order otherOrder) {
                return otherOrder.compareTo(order);
            }
        });
        return ordersByOrder;
    }

    /**
     * Accepts the given order for the announcement and notifies the client.
     *
     * @param announcement The announcement of the order.
     * @param chosenOrder  The order to accept.
     */
    public void acceptThisOrder(Announcement announcement, Order chosenOrder) {
        notifyClient(chosenOrder.getPerson(), announcement, true);
        announcementRepository.getAnnouncementList().remove(announcement);
    }

    /**
     * Removes the given order from the announcement and notifies the client.
     *
     * @param announcement The announcement of the order.
     * @param chosenOrder  The order to remove.
     * @return True if the order was successfully removed, false otherwise.
     */
    public boolean removeThisOrder(Announcement announcement, Order chosenOrder) {
        notifyClient(chosenOrder.getPerson(), announcement, false);
        List<Order> orders = announcement.getOrders();
        orders.remove(chosenOrder);
        return !orders.contains(chosenOrder);
    }

    /**
     * Notifies the client about the order acceptance or rejection.
     *
     * @param person   The client to notify.
     * @param wasAccepted True if the order wasAccepted, false if rejected.
     */
    public void notifyClient(Person person,Announcement announcement, boolean wasAccepted) {
        String subject = "Order validation!";
        String body;
        if (wasAccepted) {
            body = "You order was accepted\n\nAddress: " + announcement.getRequest().getProperty().getAddress().toString();
        } else {
            body = "You order was not accepted\n\nAddress: " + announcement.getRequest().getProperty().getAddress().toString();
        }
        //new Email(getEmployeeInSession(), person, subject, body);
        //TODO : crete data value in email
    }

    /**
     * Retrieves the Employee who is currently active in the session.
     *
     * @return The active Employee in the session.
     */
    public Employee getEmployeeInSession() {
        Email idInSession = authenticationRepository.getCurrentUserSession().getUserId();
        return (Employee) personRepository.getPersonById(idInSession);
    }
}

```

## Class OrderCheckUI

```java
public class AnnouncementUI implements Runnable {

    /**
     * The AnnouncementUI class implements the Runnable interface and represents the user interface for creating and managing announcements.
     * It provides methods for running the UI, creating announcements, and interacting with the user to gather necessary information.
     */
    private final AnnouncementController controller = new AnnouncementController();

    /**
     * Implements the Runnable interface's run() method. This method is executed when the class is run as a thread.
     * It creates an announcement by getting the employee in session, choosing a client, submitting a property,
     * and creating an announcement request.
     */
    public void run() {
        try {
            Employee employeeInSession = controller.getEmployeeInSession();
            Person chosenClient = chooseClient();
            Property property = submitProperty();
            Request request = new Request(employeeInSession, chosenClient, property, false);
            createAnnouncement(request);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    /**
     * Prompts the user to submit a property by selecting its type (Land, Apartment, or House).
     * This method returns the created Property object.
     *
     * @return The created Property object.
     */
    private Property submitProperty() {
        List<String> options = List.of("Land", "Apartment", "House");

        Property property = null;
        while (property == null) {
            int option;

            option = Utils.showAndSelectIndex(options, "\n\nSelect the type of the property:", false);
            switch (option) {
                case 0:
                    property = createLand();
                    break;
                case 1:
                    property = createApartment();
                    break;
                case 2:
                    property = createHouse();
                    break;
            }
        }
        return property;
    }

    /**
     * Prompts the user to create a deal by selecting its type (Sale or Rent).
     * This method returns the created Deal object.
     *
     * @return The created Deal object.
     */
    private Deal createDeal() {
        List<String> options = List.of("Sale", "Rent");
        Deal deal = null;

        while (deal == null) {
            int option = Utils.showAndSelectIndex(options, "\n\nSelect a deal type", false);

            switch (option) {
                case 0:
                    double salePrice = Utils.readDoubleFromConsole("Price:");
                    deal = new Sale(salePrice);
                    break;
                case 1:
                    double rentPrice = Utils.readDoubleFromConsole("Price:");
                    int contractDuration = Utils.readIntegerFromConsole("Contract duration:");
                    deal = new Rent(rentPrice, contractDuration);
                    break;
            }
        }
        return deal;
    }

    /**
     * Prompts the user to create a commission by selecting its type (Fixed amount or Percentage).
     * This method returns the created Commission object.
     *
     * @return The created Commission object.
     */
    private Commission createCommission() {
        List<String> options = List.of("Fix amount", "Percentage");
        Commission commission = null;

        while (commission == null) {
            int option = Utils.showAndSelectIndex(options, "\n\nSelect a commission type", false);

            switch (option) {
                case 0:
                    float fixAmount = Utils.readIntegerFromConsole("Value:");
                    commission = new Commission(fixAmount, false);
                    break;
                case 1:
                    float percentage = Utils.readIntegerFromConsole("Value:");
                    commission = new Commission(percentage, true);
                    break;
            }
        }

        return commission;
    }

    /**
     * Prompts the user to create a Land object by providing the address, area, distance from city center,
     * photos, and the deal details. This method returns the created Land object.
     *
     * @return The created Land object.
     */
    private Land createLand() {
        System.out.println("Address");
        String street = Utils.readLineFromConsole("Street:");
        String city = Utils.readLineFromConsole("City:");
        String district = Utils.readLineFromConsole("District:");
        String state = Utils.readLineFromConsole("State:");
        int zipcode = Utils.readIntegerFromConsole("Zip Code:");

        Address address = new Address(street, city, district, state, zipcode);

        double area = Utils.readDoubleFromConsole("Area");
        double distanceFromCityCentre = Utils.readDoubleFromConsole("Distance from City Centre:");
        ArrayList<String> photoURL = Utils.readLinesFromConsole("Photos", "/end");
        Deal deal = createDeal();

        return new Land(area, distanceFromCityCentre, photoURL, address, deal);
    }

    /**
     * Prompts the user to create an Apartment object by creating a Land object and providing the number of bedrooms,
     * number of bathrooms, number of parking spaces, and available equipment.
     * This method returns the created Apartment object.
     *
     * @return The created Apartment object.
     */
    private Apartment createApartment() {
        Land land = createLand();
        int numberOfBedrooms = Utils.readIntegerFromConsole("Number of bedrooms:");
        int numberOfBathrooms = Utils.readIntegerFromConsole("Number of bathrooms:");
        int numberOfParkingSpaces = Utils.readIntegerFromConsole("Number of parking spaces:");
        ArrayList<String> availableEquipment = Utils.readLinesFromConsole("Available equipment?", "/end");

        return new Apartment(land, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment);
    }

    /**
     * Prompts the user to create a House object by creating an Apartment object and providing additional details
     * such as whether it has a basement, inhabitable loft, and the sun exposure.
     * This method returns the created House object.
     *
     * @return The created House object.
     */
    private House createHouse() {
        Apartment apartment = createApartment();
        boolean hasBasement = Boolean.parseBoolean(Utils.readLineFromConsole("have basement? (true/false)"));
        boolean hasInhabitableLoft = Boolean.parseBoolean(Utils.readLineFromConsole("have inhabitableLoft? (true/false)"));
        String sunExposure = Utils.readLineFromConsole("How is the sun exposure? (high/low/medium)");

        return new House(apartment, hasBasement, hasInhabitableLoft, sunExposure);
    }

    /**
     * Allows the user to choose a client from the available client list.
     * This method returns the chosen client as a Person object.
     *
     * @return The chosen client as a Person object.
     */
    private Person chooseClient() {
        List<Person> clients = controller.getClientList();
        return (Person) Utils.showAndSelectOne(clients, "\n\nClients", true);
    }

    /**
     * Creates an announcement by creating a commission and an announcement request based on the given request object.
     * The announcement is then displayed, and the user is prompted to confirm the announcement.
     *
     * @param request The Request object for the announcement.
     */
    private void createAnnouncement(Request request) {
        Commission commission = createCommission();
        Announcement announcement = new Announcement(request, commission);
        System.out.println("\n" + announcement.toString());
        try {
            boolean confirm = Utils.confirm("you want confirm the announcement?(y/n)");
            if (confirm) {
                submitAnnouncement(request, commission);
            } else {
                System.out.println("Announcement recused!");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while creating an announcement: " + e.getMessage());
        }
    }

    /**
     * Submits the announcement by publishing it using the controller. If the announcement is successfully published,
     * a success message is displayed, and the EmployeeUI is started. Otherwise, an error message is displayed,
     * and the AnnouncementUI is started again.
     *
     * @param request    The Request object for the announcement.
     * @param commission The Commission object for the announcement.
     */
    private void submitAnnouncement(Request request, Commission commission) {
        try {
            if (controller.publishAnnouncement(request, commission)) {
                System.out.println("Operation success!");
            }
        }  catch (Exception e) {
            System.out.println("An error occurred while submitting an announcement: " + e.getMessage());
        }
    }
}
```

## Class Order

```java
/**
 * Represents an order made by a person, including the person's information, price, and date.
 */
public class Order {
    private final Person person;
    private final double price;
    private final Date date;

    /**
     * Constructs a new Order object with the specified person, price, and date.
     *
     * @param person The person who made the order.
     * @param price  The price of the order.
     * @param date   The date when the order was made.
     */
    public Order(Person person, double price, Date date) {
        this.person = person;
        this.price = price;
        this.date = date;
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
     * Returns a string representation of the Order object.
     *
     * @return A string representation of the Order object.
     */
    @Override
    public String toString() {
        return "Order{" +
                "person=" + person +
                ", price=" + price +
                ", date=" + date +
                '}';
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
        if (!(o instanceof Order order)) return false;

        return this.person.equals(order.person) &&
                this.price == order.price &&
                this.date.equals(order.date);
    }

    /**
     * Compares this Order object with another Order object for ordering purposes.
     *
     * @param otherOrder The other Order object to compare.
     * @return A negative integer if this Order is less than the other Order,
     *         a positive integer if this Order is greater than the other Order,
     *         or zero if they are equal.
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
public class Announcement {

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
     * the variable orders holds the orders of the Announcement.
     */
    List<Order> orders = new ArrayList<>();

    /**
     * This function creates an instance receiving request and commission as parameters
     * @param request           the variable request holds the request of the Announcement
     * @param commission        the variable commission holds the commission of the Announcement
     */
    public Announcement(Request request, Commission commission) {
        this.request = request;
        this.commission = commission;
        this.date = Date.dataAtual();
    }

    /**
     * This function creates an instance of Announcement with the same parameters as Announcement
     * @param otherAnnouncement the Announcement with the parameters to copy
     */
    public Announcement(Announcement otherAnnouncement) {
        this.request = otherAnnouncement.request;
        this.commission = otherAnnouncement.commission;
        this.date = otherAnnouncement.date;
    }

    /**
     * Method to return the request of the Announcement
     * @return the request of the Announcement
     */
    public Request getRequest() {
        return request;
    }

    /**
     * Method to return the commission of the Announcement
     * @return the commission of the Announcement
     */
    public Commission getCommission() {
        return commission;
    }

    /**
     * Method to return the data of the Announcement
     * @return the data of the Announcement
     */
    public Date getDate() {
        return date;
    }

    /**
     * Method to return the orders of the Announcement
     * @return the orders of the Announcement
     */
    public List<Order> getOrders() {
        return orders;
    }

    /**
     * This function add a order in list of orders
     * @param order The order related to the announcement.
     */
    public void addOrder(Order order) {
        orders.add(order);
    }

    /**
     * This function remove a order from list of orders
     * @param order The order related to the announcement.
     */
    public void removeOrder(Order order) {
        orders.remove(order);
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "request=" + request +
                ", commission=" + commission +
                ", date=" + date +
                ", orders=" + orders +
                '}';
    }
}
```

# 6. Integration and Demo 

* A new option on the Employee menu options was added.

* Some demo purposes some tasks are bootstrapped while system starts.









