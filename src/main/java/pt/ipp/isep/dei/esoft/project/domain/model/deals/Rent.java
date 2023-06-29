package pt.ipp.isep.dei.esoft.project.domain.model.deals;

/**
 * Rent class represents a type of Deal that involves renting a property for a
 * specified duration.
 */
public class Rent extends Deal {

    /**
     * The rent amount of the property being rented.
     */
    double PropertyRent;

    /**
     * The duration of the rental contract.
     */
    int contractDuration;

    /**
     * Creates a new Rent object with default values.
     */
    public Rent() {
        super(DealType.RENT);
    }

    /**
     * Creates a new Rent object with the specified property rent and contract
     * duration.
     *
     * @param propertyRent     The rent amount of the property being rented.
     * @param contractDuration The duration of the rental contract.
     */
    public Rent(double propertyRent, int contractDuration) {
        super(DealType.RENT);
        PropertyRent = propertyRent;
        this.contractDuration = contractDuration;
    }

    /**
     * Creates a new Rent object that is a copy of another Rent object.
     *
     * @param otherRent The Rent object to copy.
     */
    public Rent(Rent otherRent) {
        super(DealType.RENT);
        this.PropertyRent = otherRent.PropertyRent;
        this.contractDuration = otherRent.getContractDuration();
    }

    /**
     * Gets the rent amount of the property being rented.
     *
     * @return The rent amount of the property being rented.
     */
    public double getPropertyRent() {
        return PropertyRent;
    }

    /**
     * Sets the rent amount of the property being rented.
     *
     * @param propertyRent The new rent amount of the property being rented.
     */
    public void setPropertyRent(double propertyRent) {
        PropertyRent = propertyRent;
    }

    /**
     * Gets the duration of the rental contract.
     *
     * @return The duration of the rental contract.
     */
    public int getContractDuration() {
        return contractDuration;
    }

    /**
     * Sets the duration of the rental contract.
     *
     * @param contractDuration The new duration of the rental contract.
     */
    public void setContractDuration(int contractDuration) {
        this.contractDuration = contractDuration;
    }
}