package pt.ipp.isep.dei.esoft.project.domain.model.property;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;

import java.util.ArrayList;

/**
 * The type Land.
 */
public class Land extends Property {

    /**
     * Constructs a new Land object with the given attributes.
     *
     * @param area                   the area of the land in square meters
     * @param distanceFromCityCentre the distance of the land from the city center in kilometers
     * @param photoURL               an array of URLs to photos of the land
     * @param address                the address of the land
     * @param deal                   the deal associated with the land (e.g. sale, lease)
     */
    public Land(double area, double distanceFromCityCentre, ArrayList<String> photoURL, Address address, Deal deal) {
        
        super(area, distanceFromCityCentre, photoURL, TypeOfProperty.LAND, address, deal);
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