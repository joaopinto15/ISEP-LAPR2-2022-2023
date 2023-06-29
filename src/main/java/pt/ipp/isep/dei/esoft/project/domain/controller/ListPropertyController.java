package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.filter.*;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.PropertyRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The ListPropertyController class is responsible for managing the list of properties
 * and displaying them based on user input filters and sorting criteria.
 */
public class ListPropertyController {
    /**
     * The Property repository.
     */
    private final PropertyRepository propertyRepository;
    /**
     * The Announcement repository.
     */
    private final AnnouncementRepository announcementRepository;

    /**
     * Constructs a new ListPropertyController object and initializes the property
     * repository.
     */
    public ListPropertyController() {
        this.propertyRepository = Repositories.getInstance().getPropertyRepository();
        this.announcementRepository=Repositories.getInstance().getAnnouncementRepository();
    }

    /**
     * Retrieves all properties from the property repository.
     *
     * @return a list of all properties in the repository.
     */
    public List<Property> getAllProperties() {
        return propertyRepository.getAllPropertyList();
    }


    /**
     * Gets announcement list.
     *
     * @return the announcement list
     */
    public List<Announcement> getAnnouncementList() {
        return announcementRepository.getNotSoldAnnouncementList();
    }

    /**
     * Displays a list of properties to the console.
     *
     * @param lis the list of properties to display.
     */
    public static void displayProperties(List<Announcement> lis) {
        for (int i = 0; i < lis.size(); i++) {
            System.out.println(lis.get(i).toString(true));
        }
    }

    /**
     * Filtro list.
     *
     * @param filter the filter
     * @param lis    the lis
     * @param rooms  the rooms
     * @return the list
     */
    public List<Announcement> filtro(String filter, List<Announcement> lis, int rooms) {
        if (filter.equals("1")) {// land
            Filtro land = new LandFilter();
            System.out.println("LANDS;");
            displayProperties(land.typeFilter(lis));
            return land.typeFilter(lis);
        }
        if (filter.equals("2")) {// house
            Filtro house = new HouseFilter();
            System.out.println("HOUSE;");
            displayProperties(house.typeFilter(lis));
            return house.typeFilter(lis);
        }
        if (filter.equals("3")) {// apartment
            Filtro apartment = new ApartmentFilter();
            System.out.println("APARTMENT;");
            displayProperties(apartment.typeFilter(lis));
            return apartment.typeFilter(lis);
        }
        if (filter.equals("4")) {// sale
            Filtro sale = new SaleFilter();
            System.out.println("SALE;");
            displayProperties(sale.typeFilter(lis));
            return sale.typeFilter(lis);
        }
        if (filter.equals("5")) {// rent
            Filtro rent = new RentFilter();
            System.out.println("RENT;");
            displayProperties(rent.typeFilter(lis));
            return rent.typeFilter(lis);
        }
        if (filter.equals("6")) {// bath
            Filtro bathroom = new BathroomsFilter();
            System.out.println("BATHROOMS;");
            displayProperties(bathroom.roomsFilter(lis,rooms));
            if (bathroom.roomsFilter(lis,rooms).isEmpty()) {
                return new ArrayList<Announcement>(); // ou return new ArrayList<Announcement>(); para retornar uma lista vazia
            }
            return bathroom.typeFilter(lis);
        }
        if (filter.equals("7")) {// bed
            Filtro bedroom = new BedroomsFilter();
            System.out.println("BEDROOMS");
            displayProperties(bedroom.roomsFilter(lis,rooms));
            if (bedroom.roomsFilter(lis,rooms).isEmpty()) {
                return new ArrayList<Announcement>(); // ou return new ArrayList<Announcement>(); para retornar uma lista vazia
            }
            return bedroom.typeFilter(lis);
        }
        return lis;
    }

    /**
     * Sort.
     *
     * @param filter the filter
     * @param lis    the lis
     */
    public void sort(String filter, List<Announcement> lis) {
        if (filter.equals("1")) {
            Collections.sort(lis, new Properties_By_City());
            displayProperties(lis);
        }
        if (filter.equals("2")) {
            Collections.sort(lis, new Properties_By_City().reversed());
            displayProperties(lis);
        }
        if (filter.equals("3")) {
            Collections.sort(lis, new Properties_By_Price());
            displayProperties(lis);
        }
        if (filter.equals("4")) {
            Collections.sort(lis, new Properties_By_Price().reversed());
            displayProperties(lis);
        }
    }


    /**
     * Overrides the default toString method to display the property repository.
     *
     * @return a string representation of the ListPropertyController object.
     */
    @Override
    public String toString() {
        return "ListPropertyController{" +
                "propertyRepository=" + propertyRepository.getAllPropertyList() +
                '}';
    }
}
