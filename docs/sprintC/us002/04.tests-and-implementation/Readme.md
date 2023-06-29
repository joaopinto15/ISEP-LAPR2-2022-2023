# US 002 - Publish announcement

# 4. Tests 

**Test 1:** Check that it is possible to get commission from announcement

	/**
     * Tests the getCommission() method of the Announcement class.
     */
    @Test
    public void getCommission() {
        assertEquals(commission, announcement.getCommission());
    }
	

**Test 2:** Check that it is possible to get request from announcement

	/**
     * Tests the getRequest() method of the Announcement class.
     */
    @Test
    public void getRequest() {
        assertEquals(request, announcement.getRequest());
    }

**Test 3:** Check that it is possible to add an order in announcement.

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

**Test 4:** Check that it is possible to save a announcement in the AnnouncementRepository.

	/**
     * Tests the saveAnnouncement() method of the AnnouncementRepository class.
     */
    @Test
    void saveAnnouncement() {
        announcementRepository.saveAnnouncement(announcement);
        assertTrue(announcementRepository.getAnnouncementList().contains(announcement));
    }

**Test 5:** Check that a invalid announcement is invalidated by the AnnouncementRepository.

	/**
     * Tests the validateAnnouncement() method of the AnnouncementRepository class.
     */
    @Test
    void validateAnnouncement() {
        announcementRepository.saveAnnouncement(announcement);
        assertFalse(announcementRepository.validateAnnouncement(announcement));
        assertFalse(announcementRepository.validateAnnouncement(null));
    }

**Test 6:** Check if the AnnouncementController can publish correctly an announcement

	/**
     * Tests the publishAnnouncement() method of the AnnouncementController class.
     */
    @Test
    public void publishAnnouncement() {
        Announcement announcement = new Announcement(request1, commission1);
        assertTrue(controller.publishAnnouncement(request1, commission1));
        assertTrue(announcementRepository.getAnnouncementList().contains(announcement));
    }

**Test 7:** Check if the AnnouncementController can return correctly the AnnouncementList

	/**
     * Tests the getAnnouncementList() method of the AnnouncementController class.
     */
    @Test
    public void getAnnouncementList() {
        Announcement announcement1 = new Announcement(request1, commission1);
        Announcement announcement2 = new Announcement(request2, commission2);
        announcementRepository.saveAnnouncement(announcement1);
        announcementRepository.saveAnnouncement(announcement2);
        assertTrue(controller.getAnnouncementList().contains(announcement1));
        assertTrue(controller.getAnnouncementList().contains(announcement2));
    }

# 5. Construction (Implementation)


## Class AnnouncementController

```java
package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.*;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.repository.UserRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.domain.model.Password;
import pt.isep.lei.esoft.auth.domain.model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class AnnouncementController {

    /**
     * The AnnouncementController class handles the logic and operations related to announcements.
     */
    public class AnnouncementController {

        private final Repositories repositories;
        private final AnnouncementRepository announcementRepository;
        private final PersonRepository personRepository;
        private final AuthenticationRepository authenticationRepository;

        /**
         * Constructs an instance of AnnouncementController.
         */
        public AnnouncementController() {
            repositories = Repositories.getInstance();
            announcementRepository = repositories.getAnnouncementRepository();
            personRepository = repositories.getPersonRepository();
            authenticationRepository = repositories.getAuthenticationRepository();
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

        /**
         * Retrieves a list of all clients.
         *
         * @return A list of Person objects representing all clients.
         */
        public List<Person> getClientList() {
            return personRepository.getClients();
        }

        /**
         * Publishes a new announcement with the given parameters.
         *
         * @param request    The request related to the announcement.
         * @param commission The commission related to the announcement.
         * @return {@code true} if the announcement is published successfully, {@code false} otherwise.
         */
        public boolean publishAnnouncement(Request request, Commission commission) {
            Announcement announcement = new Announcement(request, commission);
            announcementRepository.saveAnnouncement(announcement);
            return !announcementRepository.validateAnnouncement(announcement);
        }

        public List<Announcement> getAnnouncementList() {
            return announcementRepository.getAnnouncementList();
        }

    }
}
```

## Class AnnouncementUI

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

## Class AnnouncementRepository

```java
/**
 * Announcement Repository
 */
public class AnnouncementRepository {
    /**
     * the list containing the announcement registered in the system
     */
    private final List<Announcement> announcementList = new ArrayList<>();



    /**
     * this function validates the announcement if not null
     * @param announcement      represents an announcement through request and commission
     * @return false if the instance announcement is null, if not will return !this.announcementList.contains(announcement)
     */
    public boolean validateAnnouncement(Announcement announcement){
        if(announcement == null){
            return false;
        } else {
            return !this.announcementList.contains(announcement);
        }
    }

    /**
     * this function will add an announcement to the announcement list
     * @param announcement      represents an announcement through request and commission
     * @return the call of a function that add to the announcement list
     */
    private boolean addAnnouncement(Announcement announcement){
        return this.announcementList.add(announcement);
    }

    /**
     * this function saves the announcement if is validated
     * @param announcement      announcement represents an announcement through request and commission
     * @return save the instance of announcement in announcementList
     */
    public boolean saveAnnouncement(Announcement announcement){
        if(!validateAnnouncement(announcement)){
            return false;
        } else {
            return addAnnouncement(announcement);
        }
    }

    /**
     * this function returns the announcement list
     * @return the announcement list
     */
    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }
    /**
     * this function creates an instance of announcement
     * @param request           holds the request of the announcement
     * @param commission        holds the commission of the announcement
     * @return a new instance of announcement
     */
    public Announcement createAnnouncement(Request request, Commission commission){
        return new Announcement(request, commission);
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




