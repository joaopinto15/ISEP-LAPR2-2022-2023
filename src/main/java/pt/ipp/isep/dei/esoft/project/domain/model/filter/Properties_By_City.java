package pt.ipp.isep.dei.esoft.project.domain.model.filter;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;

import java.util.Comparator;

/**
 * The class compares two Property instances by the city attribute.
 */
public class Properties_By_City implements Comparator <Announcement> {
    /**
     * Method used to sort properties by city.
     *
     * @param o1 the first object to be compared.
     * @param o2 the second object to be compared.
     * @return the value 0 if o2 is equal to o1; the value -1 if o2 is later than o1; the value 1 if o2 is before o1.
     */
    @Override
    public int compare(Announcement o1, Announcement o2) {
        return o1.getRequest().getProperty().getAddress().getCity().compareTo(o2.getRequest().getProperty().getAddress().getCity());
    }

}
//'city and state'