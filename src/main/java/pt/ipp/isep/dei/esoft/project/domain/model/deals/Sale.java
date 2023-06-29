package pt.ipp.isep.dei.esoft.project.domain.model.deals;

/**
 * The Sale class represents a type of Deal that involves selling a property.
 */
public class Sale extends Deal {

    /**
     * The price of the property being sold.
     */
    double propertyPrice;

    /**
     * Creates a new Sale object with default values.
     */
    public Sale() {
        super(DealType.SALE);
    }

    /**
     * Creates a new Sale object with the specified property price.
     *
     * @param propertyPrice The price of the property being sold.
     */
    public Sale(double propertyPrice) {
        super(DealType.SALE);
        this.propertyPrice = propertyPrice;
    }

    /**
     * Creates a new Sale object that is a copy of another Sale object.
     *
     * @param otherSale The Sale object to copy.
     */
    public Sale(Sale otherSale) {
        super(DealType.SALE);
        this.propertyPrice = otherSale.propertyPrice;
    }

    /**
     * Gets the price of the property being sold.
     *
     * @return The price of the property being sold.
     */
    public double getPropertyPrice() {
        return propertyPrice;
    }

    /**
     * Sets the price of the property being sold.
     *
     * @param propertyPrice The new price of the property being sold.
     */
    public void setPropertyPrice(double propertyPrice) {
        this.propertyPrice = propertyPrice;
    }
}
