package pt.ipp.isep.dei.esoft.project.domain.model.filter;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;

import java.util.ArrayList;
import java.util.List;

/**
 * This class filters a list of properties by the type of deal (Sale).
 */
public class SaleFilter implements Filtro {
    /**
     * Method used to filter properties for sale.
     *
     * @param properties List of properties to filter on.
     * @return list of properties for sale.
     */
    @Override
    public List<Announcement> typeFilter(List<Announcement> properties) {
        List<Announcement> sale=new ArrayList<Announcement>();
        for (Announcement property : properties) {
            if(Deal.DealType.SALE == property.getRequest().getProperty().getDeal().getDealType()){
                sale.add(property);
            }
        }
        return sale;
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
