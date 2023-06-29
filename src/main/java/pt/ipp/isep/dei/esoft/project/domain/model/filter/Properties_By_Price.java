package pt.ipp.isep.dei.esoft.project.domain.model.filter;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;

import java.util.Comparator;

/**
 * The class compares two Property instances by the price attribute.
 */
public class Properties_By_Price implements Comparator<Announcement> {
    /**
     * Method used to sort properties by price.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return the value 0 if o2 is equal to o1; the value -1 if o2 is later than o1; the value 1 if o2 is before o1.
     */
    @Override
    public int compare(Announcement o1, Announcement o2) {
        double price1=(o1.getRequest().getProperty().getDeal().getDealType() == Deal.DealType.SALE) ? ((Sale) o1.getRequest().getProperty().getDeal()).getPropertyPrice() : ((Rent) o1.getRequest().getProperty().getDeal()).getPropertyRent();
        double price2=(o2.getRequest().getProperty().getDeal().getDealType() == Deal.DealType.SALE) ? ((Sale) o2.getRequest().getProperty().getDeal()).getPropertyPrice() : ((Rent) o2.getRequest().getProperty().getDeal()).getPropertyRent();
        return (int) (price1-price2);
    }
}
