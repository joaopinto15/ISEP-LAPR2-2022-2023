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
        List<String> options = List.of("North", "East", "South", "West");
        String sunExposure = null;

        while (sunExposure == null) {
            int option = Utils.showAndSelectIndex(options, "\n\nHow is the sun exposure?", false);

            switch (option) {
                case 0:
                    sunExposure = options.get(0);
                    break;
                case 1:
                    sunExposure = options.get(1);
                    break;
                case 2:
                    sunExposure = options.get(2);
                    break;
                case 3:
                    sunExposure = options.get(3);
                    break;
            }
        }
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
        System.out.println("\n" + announcementDto.toString());
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