package pt.ipp.isep.dei.esoft.project.domain.model.deals;

import java.io.Serializable;

/**
 * Deal is an abstract class representing a deal for a property. It contains information about the type of deal.
 */
public abstract class Deal implements Serializable {

    /**
     * An enumeration representing the type of deal for a property, either SALE or RENT.
     */
    public enum DealType {
        /**
         * Sale deal type.
         */
        SALE,
        /**
         * Rent deal type.
         */
        RENT
    }

    /**
     * The Deal type.
     */
    private final DealType dealType;

    /**
     * Constructs an instance of Deal with the given type of deal.
     *
     * @param dealType the type of deal for the property as an instance of the DealType enumeration.
     */
    public Deal(DealType dealType) {
        this.dealType = dealType;
    }

    /**
     * Returns the type of deal for the property.
     *
     * @return the type of deal for the property as an instance of the DealTypeenumeration.
     */
    public DealType getDealType() {
        return dealType;
    }
}
