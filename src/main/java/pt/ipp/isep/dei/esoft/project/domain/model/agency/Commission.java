package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import java.io.Serializable;

/**
 * The type Commission.
 */
public class Commission implements Serializable {

    /**
     * the variable value holds the value of Commission
     */
    private final float value;

    /**
     * the variable isPercentage holds the isPercentage of Commission
     */
    private final boolean isPercentage;

    /**
     * This function creates an instance receiving commission as parameters
     *
     * @param value        the variable value holds the request of the Commission
     * @param isPercentage the variable isPercentage holds the request of the Commission
     */
    public Commission(float value, boolean isPercentage) {

        this.value = value;
        this.isPercentage = isPercentage;
    }

    /**
     * This function creates an instance of Commission with the same parameters as Commission
     *
     * @param otherCommission the Commission with the parameters to copy
     */
    public Commission(Commission otherCommission) {

        this.value = otherCommission.value;
        this.isPercentage = otherCommission.isPercentage;
    }

    /**
     * Method to return the value of the Commission
     *
     * @return the value of the Commission
     */
    public float getValue() {
        return value;
    }

    /**
     * Method to return if isPercentage in Commission
     *
     * @return if isPercentage in Commission
     */
    public boolean isPercentage() {
        return isPercentage;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Commission{" +
                "value=" + value +
                ", isPercentage=" + isPercentage +
                '}';
    }

    /**
     * Checks if this House object is equal to another object.
     *
     * @param otherCommission the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherCommission) {
        if (this == otherCommission) return true;
        if (otherCommission == null || getClass() != otherCommission.getClass()) return false;
        Commission that = (Commission) otherCommission;
        return Float.compare(that.value, value) == 0 && isPercentage == that.isPercentage;
    }
}
