package pt.ipp.isep.dei.esoft.project.domain.model.filter;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;

import java.util.ArrayList;
import java.util.List;

/**
 * This class filters a list of properties by the type of business(Rent).
 */
public class RentFilter implements Filtro {
    /**
     * Method used to filter properties for rent.
     *
     * @param properties List of properties to filter on.
     * @return list of properties for rent.
     */
    @Override
    public List<Announcement> typeFilter(List<Announcement> properties) {
        List<Announcement> rent=new ArrayList<Announcement>();
        for (Announcement property : properties) {
            if(Deal.DealType.RENT == property.getRequest().getProperty().getDeal().getDealType()){
                rent.add(property);
            }
        }
        return rent;
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
        return null;
    }
}
