package pt.ipp.isep.dei.esoft.project.domain.model;


import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * Address
 */
public class Address implements Serializable {
    /**
     * the variable street holds the street of the address.
     */
    private final String street;
    /**
     * the variable city holds the city of the address.
     */
    private final String city;
    /**
     * the variable district holds the district of the address.
     */
    private final String district;
    /**
     * the variable state holds the state of the address.
     */
    private final String state;
    /**
     * the variable zipcode holds the state of the address.
     */
    private final String zipcode;

    /**
     * This function creates an instance receiving street, city, district, state and zipcode as parameters
     *
     * @param street   the variable street holds the street of the address
     * @param city     the variable city holds the city of the address
     * @param district the variable district holds the district of the address
     * @param state    the variable state holds the state of the address
     * @param zipcode  the variable zipcode holds the zipcode of the address
     */
    public Address(String street, String city, String district, String state, String zipcode) {
        checkStreet(street);
        checkCity(city);
        checkDistrict(district);
        checkState(state);
        checkZipcode(zipcode);

        this.street = street;
        this.city = city;
        this.district = district;
        this.state = state;
        this.zipcode = zipcode;
    }

    /**
     * This function creates an instance of Address with the same parameters as Address
     *
     * @param anotherAddress the address with the parameters to copy
     */
    public Address(Address anotherAddress){
        street = anotherAddress.street;
        city = anotherAddress.city;
        district = anotherAddress.district;
        state = anotherAddress.state;
        zipcode = anotherAddress.zipcode;
    }

    /**
     * Method to return the street of the address
     *
     * @return the street of the address
     */
    public String getStreet() {
        return street;
    }

    /**
     * Method to return the city of the address
     *
     * @return the city of the address
     */
    public String getCity() {
        return city;
    }

    /**
     * Method to return the district of the address
     *
     * @return the district of the address
     */
    public String getDistrict() {
        return district;
    }

    /**
     * Method to return the state of the address
     *
     * @return the state of the address
     */
    public String getState() {
        return state;
    }

    /**
     * Method to return the zipcode of the address
     *
     * @return zipcode of the address
     */
    public String getZipcode() {
        return zipcode;
    }

    /**
     * This function constructs and returns a String representative of the object
     *
     * @return a string representing the object
     */
    @Override
    public String toString() {
        return String.format("Street: %s | City: %s | District: %s | State: %s | Zipcode: %s", this.street, this.city, this.district, this.state, this.zipcode);
    }

    /**
     * This function checks if the variable street is null.
     *
     * @param street the variable street holds the street of the address.
     */
    private void checkStreet(String street){
        if(StringUtils.isBlank(street)){
            throw new IllegalArgumentException("The street can't be blank");
        }
    }

    /**
     * This function checks if the variable city is null.
     *
     * @param city the variable city holds the city of the address.
     */
    private void checkCity(String city){
        if(StringUtils.isBlank(city)){
            throw new IllegalArgumentException("The city can't be blank");
        }
    }

    /**
     * This function checks if the variable district is null.
     *
     * @param district the variable district holds the district of the address.
     */
    private void checkDistrict(String district){
        if(StringUtils.isBlank(district)){
            throw new IllegalArgumentException("The District can't be blank");
        }
    }

    /**
     * This function checks if the variable state is null.
     *
     * @param state the variable state holds the state of the address.
     */
    private void checkState(String state){
        if(StringUtils.isBlank(state)){
            throw new IllegalArgumentException("The state can't be blank");
        }
    }

    /**
     * This function checks if the variable zipcode has 5 digits.
     *
     * @param zipcode the variable zipcode holds the zipcode of the address.
     */
    private void checkZipcode(String zipcode){
        if(zipcode.trim().length() != 5){
            throw new IllegalArgumentException("The zipcode must be 5 digits");
        }
    }

    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(street, address.street) && Objects.equals(city, address.city) && Objects.equals(district, address.district) && Objects.equals(state, address.state) && Objects.equals(zipcode, address.zipcode);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(street, city, district, state, zipcode);
    }
}

