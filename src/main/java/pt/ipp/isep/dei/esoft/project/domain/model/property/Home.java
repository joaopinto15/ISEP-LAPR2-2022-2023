package pt.ipp.isep.dei.esoft.project.domain.model.property;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Home is an abstract class that extends Property and represents a type of
 * property that is a home.
 * It contains information about the number of bedrooms, number of bathrooms,
 * number of parking spaces, and available equipment.
 */
public abstract class Home extends Property {

    /**
     * The number of bedrooms in the home.
     */
    private int numberOfBedrooms;
    /**
     * The number of bathrooms in the home.
     */
    private int numberOfBathrooms;
    /**
     * The number of parking spaces available for the home.
     */
    private int numberOfParkingSpaces;
    /**
     * An ArrayList of strings containing the available equipment for the home.
     */
    private ArrayList<String> availableEquipment;

    /**
     * Constructs an instance of Home with the given parameters.
     *
     * @param area                   the area of the home in square meters.
     * @param distanceFromCityCentre the distance in kilometers from the city                               centre.
     * @param photoURL               an array of strings containing the URLs of the                               photos of the home.
     * @param typeProperty           the type of the property as a string.
     * @param address                the address of the home as an instance of the                               Address class.
     * @param Deal                   the deal of the home as an instance of the Deal                               class.
     * @param numberOfBedrooms       the number of bedrooms in the home.
     * @param numberOfBathrooms      the number of bathrooms in the home.
     * @param numberOfParkingSpaces  the number of parking spaces available for the                               home.
     * @param availableEquipment     an ArrayList of strings containing the                               available equipment for the home.
     */
    public Home(double area, double distanceFromCityCentre, ArrayList<String> photoURL, TypeOfProperty typeProperty, Address address,
                pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal Deal, int numberOfBedrooms, int numberOfBathrooms, int numberOfParkingSpaces,
                ArrayList<String> availableEquipment) {
        super(area, distanceFromCityCentre, photoURL, typeProperty, address, Deal);
        this.numberOfBathrooms = numberOfBathrooms;
        this.numberOfBedrooms = numberOfBedrooms;
        this.availableEquipment = availableEquipment;
        this.numberOfParkingSpaces = numberOfParkingSpaces;
    }

    /**
     * Returns the number of bedrooms in the home.
     *
     * @return the number of bedrooms in the home.
     */
    public int getNumberOfBedrooms() {
        return numberOfBedrooms;
    }

    /**
     * Sets the number of bedrooms in the home.
     *
     * @param numberOfBedrooms the number of bedrooms in the home.
     */
    public void setNumberOfBedrooms(int numberOfBedrooms) {
        this.numberOfBedrooms = numberOfBedrooms;
    }

    /**
     * Returns the number of bathrooms in the home.
     *
     * @return the number of bathrooms in the home.
     */
    public int getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    /**
     * Sets the number of bathrooms in the home.
     *
     * @param numberOfBathrooms the number of bathrooms in the home.
     */
    public void setNumberOfBathrooms(int numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    /**
     * Returns the number of parking spaces available for the home.
     *
     * @return the number of parking spaces available for the home.
     */
    public int getNumberOfParkingSpaces() {
    return numberOfParkingSpaces;
    }

    /**
     * Sets the number of parking spaces available for the home.
     *
     * @param numberOfParkingSpaces the number of parking spaces available for the home.
     */
    public void setNumberOfParkingSpaces(int numberOfParkingSpaces) {
        this.numberOfParkingSpaces = numberOfParkingSpaces;
    }

    /**
     * Gets the available equipment.
     *
     * @return the available equipment
     */
    public ArrayList<String> getAvailableEquipment() {
        return availableEquipment;
    }

    /**
     * Sets the available equipment.
     *
     * @param availableEquipment the new available equipment
     */
    public void setAvailableEquipment(ArrayList<String> availableEquipment) {
        this.availableEquipment = availableEquipment;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return super.toString() +
                "\n     -> Number of Bedrooms: " + numberOfBedrooms + 
                "\n     -> Number of Bathrooms: " + numberOfBathrooms + 
                "\n     -> Number of Parking Spaces: " + numberOfParkingSpaces +
                "\n     -> Available Equipment: " + toStringAvailableEquipment() ;
    }

    /**
     * To string available equipment string.
     *
     * @return the string
     */
    private String toStringAvailableEquipment(){
        String str = "";
        if (availableEquipment.isEmpty()) {
            str += "\n       *This announcement has no available Equipament .\n";
        }else{
            for (String x : availableEquipment) {
                str += "\n        * "+ x;
            }
        }
        return str;
    }

    /**
     * Checks if this Home object is equal to another object.
     *
     * @param otherHome the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherHome) {
        if (this == otherHome) return true;
        if (otherHome == null || getClass() != otherHome.getClass()) return false;
        if (!super.equals(otherHome)) return false;
        Home home = (Home) otherHome;
        return numberOfBedrooms == home.numberOfBedrooms && numberOfBathrooms == home.numberOfBathrooms && numberOfParkingSpaces == home.numberOfParkingSpaces && Objects.equals(availableEquipment, home.availableEquipment);
    }
}