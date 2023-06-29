package pt.ipp.isep.dei.esoft.project.domain.model.property;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;

import java.util.ArrayList;

/**
 * Apartment class extends Home class and represents a type of property that is
 * an apartment.
 * It contains information about the area, distance from the city centre, photo
 * URLs, type of property,
 * address, deal, number of bedrooms, number of bathrooms, number of parking
 * spaces, and available equipment.
 */
public class Apartment extends Home {

    /**
     * Constructs an instance of Apartment with the given parameters.
     *
     * @param area                   the area of the apartment in square meters.
     * @param distanceFromCityCentre the distance in kilometers from the city                               centre.
     * @param photoURL               an array of strings containing the URLs of the                               photos of the apartment.
     * @param address                the address of the apartment as an instance of                               the Address class.
     * @param Deal                   the deal of the apartment as an instance of the                               Deal class.
     * @param numberOfBedrooms       the number of bedrooms in the apartment.
     * @param numberOfBathrooms      the number of bathrooms in the apartment.
     * @param numberOfParkingSpaces  the number of parking spaces available for the                               apartment.
     * @param availableEquipment     an ArrayList of strings containing the                               available equipment for the apartment.
     */
    public Apartment(double area, double distanceFromCityCentre, ArrayList<String> photoURL,
                     Address address, pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal Deal, int numberOfBedrooms,
                     int numberOfBathrooms, int numberOfParkingSpaces, ArrayList<String> availableEquipment) {
        super(area, distanceFromCityCentre, photoURL, TypeOfProperty.APARTMENT, address, Deal, numberOfBedrooms, numberOfBathrooms,
                numberOfParkingSpaces, availableEquipment);
    }

    /**
     * Instantiates a new Apartment.
     *
     * @param land                  the land
     * @param numberOfBedrooms      the number of bedrooms
     * @param numberOfBathrooms     the number of bathrooms
     * @param numberOfParkingSpaces the number of parking spaces
     * @param availableEquipment    the available equipment
     */
    public Apartment(Land land, int numberOfBedrooms, int numberOfBathrooms, int numberOfParkingSpaces, ArrayList<String> availableEquipment) {
        super(land.getArea(),land.getDistanceFromCityCentre(),land.getPhotoURL(),TypeOfProperty.APARTMENT,land.getAddress(),land.getDeal(), numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return super.toString();
    }
}