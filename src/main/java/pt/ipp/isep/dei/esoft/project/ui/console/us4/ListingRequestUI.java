package pt.ipp.isep.dei.esoft.project.ui.console.us4;

import pt.ipp.isep.dei.esoft.project.domain.controller.ListingRequestController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.EmployeeUI;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import static pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils.confirm;

/**
 * The type Listing request ui.
 */
public class ListingRequestUI implements Runnable{
    /**
     * The Listing request ctrl.
     */
    private final ListingRequestController ListingRequestCtrl = new ListingRequestController();

    /**
     * Run.
     */
    @Override
    public void run() {
        Employee chosenEmployee;
        Agency chosenAgency;
        chosenAgency = chooseAgency();
        if (chosenAgency == null) return;
        chosenEmployee = chooseEmployee(chosenAgency);
        if (chosenEmployee == null) return;
        Property property = createProperty();
        if (property == null) return;
        System.out.println("Summary:\n" + property + "\n This request is to be handled by " + chosenEmployee.getName());
        confirm("Do you want to submit this request? (Y/N)");
        ListingRequestCtrl.submitProperty(property, Utils.getPersonInSession(),chosenEmployee);
        System.out.println("Property submitted successfully");
    }

    /**
     * Create property.
     *
     * @return the property
     */
    private Property createProperty(){
        List<String> options = new ArrayList<>();
        options.add("Land");
        options.add("House");
        options.add("Apartment");
        int option;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nSelect Type of Property", true);

            if ((option >= 0) && (option < options.size())) {
                switch (option) {
                    case 0:
                        return createLand();
                    case 1:
                        return createHouse();
                    case 2:
                        return createApartment();
                }
            }
        } while (option != -1);
        return null;
    }

    /**
     * Create deal.
     *
     * @return the deal
     */
    private Deal createDeal(){
        double price;
        int contractDuration;
        List<String> options = new ArrayList<>();
        options.add("Sale");
        options.add("Rent");
        int option;
        do {
            option = Utils.showAndSelectIndex(options, "\n\nSelect a deal type", false);

            if ((option >= 0) && (option < options.size())) {
                switch (option) {
                    case 0:
                        price = Utils.readDoubleFromConsole("Price ($):");
                        return new Sale(price);
                    case 1:
                        price = Utils.readDoubleFromConsole("Price ($):");
                        contractDuration = Utils.readIntegerFromConsole("contract duration (Months):");
                        return new Rent(price,contractDuration);
                }
            }
        } while (option != -1);
        return null;
    }

    /**
     * Land.
     *
     * @return the land
     */
    private Land createLand(){
        String zipcode = "1" ;
        System.out.println("Address");
        String street = Utils.readNonNullLineFromConsole("Street (e.g. 404 E Street):");
        String city = Utils.readNonNullLineFromConsole("City (e.g. Birmingham):");
        String district = Utils.readNonNullLineFromConsole("District (e.g. Northern):");
        String state = Utils.readNonNullLineFromConsole("State (e.g. Alabama):");
        do{
        zipcode = Utils.readNonNullLineFromConsole("Zip Code (e.g. 12345):");
        }while(zipcode.length()!=5);
        Address address=new Address(street,city,district,state,zipcode);

        double area = Utils.readDoubleFromConsole("Area (feet²):");
        double distanceFromCityCentre = Utils.readDoubleFromConsole("Distance from the city centre (miles):");
        ArrayList<String> photoURL = Utils.readLinesFromConsole("Please add at least one link to a photo of your house (e.g. https://example.com/housephoto.png):", "/end");
        Deal deal = createDeal();
        return new Land(area, distanceFromCityCentre, photoURL, address, deal);
    }

    /**
     * Apartment
     *
     * @return the apartment
     */
    private Apartment createApartment(){
        Land land = createLand();

        int numberOfBedrooms = Utils.readIntegerFromConsole("Number of bedrooms:");
        int numberOfBathrooms = Utils.readIntegerFromConsole("Number of bathrooms:");
        int numberOfParkingSpace = Utils.readIntegerFromConsole("Number of Parking Spaces:");
        ArrayList<String> availableEquipment = Utils.readLinesFromConsole("Are there any available equipments? (e.g. Internet or N/A)", "/end");
        return new Apartment(land,numberOfBedrooms,numberOfBathrooms,numberOfParkingSpace,availableEquipment);

    }

    /**
     * House.
     *
     * @return the house
     */
    private House createHouse(){
        Apartment apartment = createApartment();
        boolean basement= confirm("Does it have a basement? (y/n)");
        boolean inhabitableLoft= confirm("Does it have an inhabitable loft? (y/n)");
        String sunExposure;
        do {sunExposure = Utils.readNonNullLineFromConsole("how is the sun exposure? (north/south/east/west)");}
        while (sunExposure.equalsIgnoreCase("north") || sunExposure.equalsIgnoreCase("south") || sunExposure.equalsIgnoreCase("east") || sunExposure.equalsIgnoreCase("west"));
        return new House(apartment,basement,inhabitableLoft,sunExposure);
    }

    /**
     * Allows the user to choose a client from the available client list.
     * This method returns the chosen client as a Person object.
     *
     * @param agency the agency
     * @return The chosen client as a Person object.
     */
    private Employee chooseEmployee(Agency agency) {
        //Check if client wants to choose an employee or if he wants a random one

        List<String> options = List.of("Choose an Agent", "Random Agent");
        int option = Utils.showAndSelectIndex(options, "\n\nSelect an Agent", true);
        if (option == 0) {
            ArrayList<Employee> employees = new ArrayList<>();
            if(agency.getId().equals("allEmployees")){
                employees.addAll(Utils.getEmployee());
            }else{
                ArrayList<Employee> Agencyemployees = Utils.getEmployeeByAgency(agency.getId());
                if(Agencyemployees == null){
                    System.out.println("No employees in this agency");
                    chooseAgency();
                }
                employees.addAll(Utils.getEmployeeByAgency(agency.getId()));
            }
            return (Employee) Utils.showAndSelectOne(employees, "\n\nAvailable agents\r\n" + //
                    "", true);
        } else {if (option == 1){
            return ListingRequestCtrl.randomAgent(agency.getId());
        }else{
            return null;
        }
        }
    }

    /**
     * Choose agency agency.
     *
     * @return the agency
     */
    private Agency chooseAgency(){
        ArrayList<Agency> options = new ArrayList<>(Utils.getAgency()); 
        if(options.size()==0){
            return new Agency(null, null, null, null, null);
        } 
        return (Agency) Utils.showAndSelectOne(options, "\n\nAvailable agents\r\n" + //
                    "", true); 
    }
}