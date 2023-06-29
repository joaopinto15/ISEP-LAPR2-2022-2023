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

	/**
     * Tests the addOrder() method of the Announcement class.
     */
    @Test
    public void addOrder() {
        Date date = new Date(2023, 2, 4);
        String email = "teste@gmail.com";
        Person person = new Person("João", 123456789, "123456789", email, "912121211","boas",new String[] {AuthenticationController.ROLE_ADMIN});

        Order order = new Order(person, 100000, date);
        announcement.addOrder(order);

        assertTrue(announcement.getOrders().contains(order));
    }

**Test 4:** Check if the AnnouncementController can publish correctly an announcement

	/**
     * Tests the publishAnnouncement() method of the AnnouncementController class.
     */
    @Test
    public void publishAnnouncement() {
        Announcement announcement = new Announcement(request1, commission1);
        AnnouncementDTO announcementDto = AnnouncementMapper.toDto(announcement);

        assertTrue(controller.publishAnnouncement(announcementDto));
        assertTrue(announcementRepository.getNotSoldAnnouncementList().contains(announcement));
    }

**Test 5:** Check if the AnnouncementController can return correctly the AnnouncementList

	/**
     * Tests the getAnnouncementList() method of the AnnouncementController class.
     */
    @Test
    public void getAnnouncementList() {
        Announcement announcement1 = new Announcement(request1, commission1);
        Announcement announcement2 = new Announcement(request2, commission2);

        announcementRepository.saveAnnouncement(announcement1);
        announcementRepository.saveAnnouncement(announcement2);
        assertTrue(AnnouncementMapper.toModel(controller.getAnnouncementList()).contains(announcement1));
        assertTrue(AnnouncementMapper.toModel(controller.getAnnouncementList()).contains(announcement2));
    }

# 5. Construction (Implementation)


## Class CreateAnnouncementController

```java
package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.mapper.AnnouncementMapper;
import pt.ipp.isep.dei.esoft.project.domain.model.Message;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.List;


/**
 * The CreateAnnouncementController class is responsible for handling the creation and publication of announcements.
 */
public class CreateAnnouncementController {

    /**
     * The Announcement repository.
     */
    private final AnnouncementRepository announcementRepository;

    /**
     * Constructs a CreateAnnouncementController object.
     * Retrieves the AnnouncementRepository instance from Repositories and assigns it to the announcementRepository variable.
     */
    public CreateAnnouncementController() {
        Repositories repositories = Repositories.getInstance();
        announcementRepository = repositories.getAnnouncementRepository();
    }

    /**
     * Publishes an announcement by converting the provided AnnouncementDTO to an Announcement object
     * and saving it in the announcement repository.
     *
     * @param announcementDto the AnnouncementDTO to be published
     * @return true if the announcement is successfully saved, false otherwise
     */
    public boolean publishAnnouncement(AnnouncementDTO announcementDto) {
        Announcement announcement = AnnouncementMapper.toModel(announcementDto);
        return announcementRepository.saveAnnouncement(announcement);
    }

    /**
     * Retrieves the list of unsold announcements from the announcement repository and converts them
     * to a list of AnnouncementDTO objects.
     *
     * @return a list of AnnouncementDTO objects representing the unsold announcements
     */
    public List<AnnouncementDTO> getAnnouncementList() {
        return AnnouncementMapper.toDto(announcementRepository.getNotSoldAnnouncementList());
    }

    /**
     * Notifies the client about the publication of an announcement by creating and saving a message.
     * Converts the provided AnnouncementDTO to an Announcement object and retrieves relevant information
     * to compose the message.
     *
     * @param announcementDto the AnnouncementDTO for which the client should be notified
     */
    public void notifyClient(AnnouncementDTO announcementDto) {
        Announcement announcement = AnnouncementMapper.toModel(announcementDto);

        String messageBody = "Dear " +
                announcement.getRequest().getUser().getName() + ",\n\n" +
                "Your announcement was published with success in the system.\n\n" +
                announcement.getRequest().getProperty();
        Message message = new Message(announcement.getRequest().getEmployee(),
                announcement.getRequest().getUser().getPhoneNumber(), messageBody);
        message.send();
    }
}
```

## Class CreateAnnouncementUI

```java
package pt.ipp.isep.dei.esoft.project.ui.console.us2;

import pt.ipp.isep.dei.esoft.project.domain.controller.CreateAnnouncementController;
import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * The type CreateAnnouncement ui.
 */
public class CreateAnnouncementUI implements Runnable {

    /**
     * The CreteAnnouncementUI class implements the Runnable interface and represents the user interface for creating and managing announcements.
     * It provides methods for running the UI, creating announcements, and interacting with the user to gather necessary information.
     */
    private final CreateAnnouncementController controller = new CreateAnnouncementController();

    /**
     * Implements the Runnable interface's run() method. This method is executed when the class is run as a thread.
     * It creates an announcement by getting the employee in session, choosing a client, submitting a property,
     * and creating an announcement request.
     */
    public void run() {
        try {
            Employee employeeInSession = Utils.getEmployeeInSession();
            Person chosenClient = chooseClient();
            if (chosenClient == null) return;
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
                    double salePrice = Utils.readDoubleFromConsole("Price: ($)");
                    deal = new Sale(salePrice);
                    break;
                case 1:
                    double rentPrice = Utils.readDoubleFromConsole("Price: ($)");
                    int contractDuration = Utils.readIntegerFromConsole("Contract duration: (month)");
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
                    float fixAmount = Utils.readFloatFromConsole("Value: ($)");
                    commission = new Commission(fixAmount, false);
                    break;
                case 1:
                    float percentage = Utils.readFloatFromConsole("Value: (%)");
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
        String zipcode = Utils.readLineFromConsole("Zip Code (5 digits):");

        Address address = new Address(street, city, district, state, zipcode);

        double area = Utils.readDoubleFromConsole("Area: (feet²)");
        double distanceFromCityCentre = Utils.readDoubleFromConsole("Distance from City Centre: (miles)");
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
        boolean hasBasement = Utils.readBooleanFromConsole("have basement? (true/false)");
        boolean hasInhabitableLoft = Utils.readBooleanFromConsole("have inhabitableLoft? (true/false)");
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
        List<Person> clients = Utils.getClient();
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
        AnnouncementDTO announcementDto = new AnnouncementDTO(request, commission);
        System.out.println("\n" + announcementDto.getRequest().getProperty().toString());
        try {
            boolean confirm = Utils.confirm("you want confirm the announcement? (y/n)");
            if (confirm) {
                submitAnnouncement(announcementDto);
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
     * @param announcementDto The announcement to be submitted
     */
    private void submitAnnouncement(AnnouncementDTO announcementDto) {
        try {
            if (controller.publishAnnouncement(announcementDto)) {
                controller.notifyClient(announcementDto);
                System.out.println("Operation success!");
            }
        } catch (Exception e) {
            System.out.println("An error occurred while submitting an announcement: " + e.getMessage());
        }
    }
}
```

## Class AnnouncementRepository

```java
package pt.ipp.isep.dei.esoft.project.domain.repository;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Order;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Announcement Repository
 */
public class AnnouncementRepository implements Serializable {

    /**
     * the list containing the not sold announcement registered in the system
     */
    private List<Announcement> announcementList = new ArrayList<>();


    /**
     * this function validates the announcement if not null
     *
     * @param announcement represents an announcement through request and commission
     * @return false if the instance announcement is null, if not will return !this.announcementList.contains(announcement)
     */
    private boolean validateAnnouncement(Announcement announcement) {
        if (announcement == null) {
            return false;
        } else {
            return !this.announcementList.contains(announcement);
        }
    }

    /**
     * this function will add an announcement to the announcement list
     *
     * @param announcement represents an announcement through request and commission
     * @return the call of a function that add to the announcement list
     */
    private boolean addAnnouncement(Announcement announcement) {
        return this.announcementList.add(announcement);
    }
    private boolean addAnnouncement(List<Announcement> announcementList) {
        return this.announcementList.addAll(announcementList);
    }

    /**
     * this function will remove an announcement from the announcement list
     *
     * @param announcement represents an announcement through request and commission
     * @return the call of a function that remove from the announcement list
     */
    public boolean removeAnnouncement(Announcement announcement) {
        return this.announcementList.remove(announcement);
    }

    public boolean removeOrderFromAnnouncement(Announcement announcement, Order order) {

        for (int i = 0; i < this.announcementList.size(); i++) {
            if (this.announcementList.get(i).equals(announcement)) {
                announcement.getOrders().remove(order);
                this.announcementList.set(i, announcement);
                return true;
            }
        }
        return false;
    }

    /**
     * this function saves the announcement if is validated
     *
     * @param announcement announcement represents an announcement through request and commission
     * @return save the instance of announcement in announcementList
     */
    public boolean saveAnnouncement(Announcement announcement) {
        if (validateAnnouncement(announcement)) {
            boolean add = addAnnouncement(announcement);
            serializeAnnouncementList();
            return add;
        } else {
            return false;
        }
    }
    public boolean saveAnnouncement(List<Announcement> announcementList) {
        List<Announcement> validAnnouncements = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            if (validateAnnouncement(announcement)) {
                validAnnouncements.add(announcement);
            }
        }
        boolean add = addAnnouncement(validAnnouncements);
        if(add){
            serializeAnnouncementList();
        }
        return add;
    }

    public List<Announcement> getNotSoldAnnouncementList() {
        List<Announcement> notSoldAnnouncements = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            if(!announcement.isSold()){
                notSoldAnnouncements.add(announcement);
            }
        }
        return notSoldAnnouncements;
    }
    public List<Announcement> getSoldAnnouncementList() {
        List<Announcement> soldAnnouncements = new ArrayList<>();
        for (Announcement announcement : announcementList) {
            if(announcement.isSold()){
                soldAnnouncements.add(announcement);
            }
        }
        return soldAnnouncements;
    }
    public List<Announcement> getAnnouncementList() {
        return announcementList;
    }


    public void serializeAnnouncementList() {
        try {
            File folder = new File(storageFolder);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    System.out.println("an error occurred when creating the folder -> " + storageFolder + " <-");
                    return;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(storageFolder + File.separator + fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(announcementList);
            objectOut.close();
            fileOut.close();
            System.out.println("A lista foi salva com sucesso.");//take out
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void removeAllAnnouncements() {
        this.announcementList.clear();
    }

    public void deserializeAnnouncementList() {
        try {
            FileInputStream fileIn = new FileInputStream(storageFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            announcementList = (List<Announcement>) objectIn.readObject();
            objectIn.close();
            fileIn.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String fileName = "AnnouncementList.byte";
    private static final String storageFolder="serialized.files";
}
```

## Class Announcement

```java
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




