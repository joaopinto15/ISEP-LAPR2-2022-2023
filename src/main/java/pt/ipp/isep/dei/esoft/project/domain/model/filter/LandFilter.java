package pt.ipp.isep.dei.esoft.project.domain.model.filter;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.util.ArrayList;
import java.util.List;

/**
 * This class filters a list of properties by their type(Land).
 */
public class LandFilter implements Filtro {
    /**
     * Method used to filter land.
     *
     * @param properties List of properties to filter on.
     * @return list of land.
     */
    @Override
    public List<Announcement> typeFilter(List<Announcement> properties) {
        List<Announcement> type=new ArrayList<Announcement>();
        for (Announcement property : properties) {
            if(property.getRequest().getProperty().getTypeOfProperty() == Property.TypeOfProperty.LAND){
                type.add(property);
            }
        }
        return type;
    }

    /**
     * Rooms filter list.
     *
     * @param properties the properties
     * @param number     the number
     * @return the list
     */
//TODO: add methods
    @Override
    public List<Announcement> roomsFilter(List<Announcement> properties, int number) {
        return null;
    }
}
