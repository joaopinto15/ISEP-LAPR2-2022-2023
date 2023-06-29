package pt.ipp.isep.dei.esoft.project.domain.model.filter;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Home;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * This class filters a list of properties by their type(Bathrooms).
 */
public class BathroomsFilter implements Filtro {

    /**
     * Type filter list.
     *
     * @param properties the properties
     * @return the list
     */
    @Override
    public List<Announcement> typeFilter(List<Announcement> properties) {
        return null;
    }

    /**
     * Rooms filter list.
     *
     * @param properties the properties
     * @param number     the number
     * @return the list
     */
    @Override
    public List<Announcement> roomsFilter(List<Announcement> properties, int number) {
        List<Announcement> rooms = new ArrayList<Announcement>();
        for (Announcement property : properties) {
            if (Property.TypeOfProperty.HOUSE == property.getRequest().getProperty().getTypeOfProperty() && ((Home) property.getRequest().getProperty()).getNumberOfBathrooms() == number) {
                rooms.add(property);
            }
            if (Property.TypeOfProperty.APARTMENT == property.getRequest().getProperty().getTypeOfProperty() && ((Home) property.getRequest().getProperty()).getNumberOfBathrooms() == number) {
                rooms.add(property);
            }
        }
        return rooms;
    }

}
