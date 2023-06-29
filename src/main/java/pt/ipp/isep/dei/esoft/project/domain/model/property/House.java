package pt.ipp.isep.dei.esoft.project.domain.model.property;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A class representing a house, which is a type of home.
 */
public class House extends Home {

    /**
     * Indicates whether the house has a basement.
     */
    private boolean basement;

    /**
     * Indicates whether the house has an inhabitable loft.
     */
    private boolean inhabitableLoft;

    /**
     * The sun exposure of the house.
     */
    private String sunExposure;

    /**
     * Constructs a house object with basic attributes and equipment availability.
     *
     * @param area                   The area of the house.
     * @param distanceFromCityCentre The distance of the house from the city centre.
     * @param photoURL               The URLs of the photos of the house.
     * @param address                The address of the house.
     * @param Deal                   The deal of the house.
     * @param numberOfBedrooms       The number of bedrooms in the house.
     * @param numberOfBathrooms      The number of bathrooms in the house.
     * @param numberOfParkingSpaces  The number of parking spaces in the house.
     * @param availableEquipment     The available equipment in the house.
     */
    public House(double area, double distanceFromCityCentre, ArrayList<String> photoURL, Address address,
                 pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal Deal, int numberOfBedrooms, int numberOfBathrooms,
                 int numberOfParkingSpaces, ArrayList<String> availableEquipment) {
        super(area, distanceFromCityCentre, photoURL, TypeOfProperty.HOUSE, address, Deal, numberOfBedrooms, numberOfBathrooms,
                numberOfParkingSpaces, availableEquipment);
    }

    /**
     * Constructs a house object with all attributes and equipment availability.
     *
     * @param area                   The area of the house.
     * @param distanceFromCityCentre The distance of the house from the city centre.
     * @param photoURL               The URLs of the photos of the house.
     * @param address                The address of the house.
     * @param Deal                   The deal of the house.
     * @param numberOfBedrooms       The number of bedrooms in the house.
     * @param numberOfBathrooms      The number of bathrooms in the house.
     * @param numberOfParkingSpaces  The number of parking spaces in the house.
     * @param availableEquipment     The available equipment in the house.
     * @param basement               Indicates whether the house has a basement.
     * @param inhabitableLoft        Indicates whether the house has an inhabitable                               loft.
     * @param sunExposure            The sun exposure of the house.
     */
    public House(double area, double distanceFromCityCentre, ArrayList<String> photoURL, Address address,
                 pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal Deal, int numberOfBedrooms, int numberOfBathrooms,
                 int numberOfParkingSpaces, ArrayList<String> availableEquipment, boolean basement, boolean inhabitableLoft,
                 String sunExposure) {
        super(area, distanceFromCityCentre, photoURL, TypeOfProperty.HOUSE, address, Deal, numberOfBedrooms, numberOfBathrooms,
                numberOfParkingSpaces, availableEquipment);
        this.basement = basement;
        this.inhabitableLoft = inhabitableLoft;
        this.sunExposure = sunExposure;
    }

    /**
     * Instantiates a new House.
     *
     * @param apartment       the apartment
     * @param basement        the basement
     * @param inhabitableLoft the inhabitable loft
     * @param sunExposure     the sun exposure
     */
    public House(Apartment apartment, boolean basement, boolean inhabitableLoft, String sunExposure) {
        super(apartment.getArea(), apartment.getDistanceFromCityCentre(), apartment.getPhotoURL(), TypeOfProperty.HOUSE, apartment.getAddress(), apartment.getDeal(), apartment.getNumberOfBedrooms(), apartment.getNumberOfBathrooms(), apartment.getNumberOfParkingSpaces(), apartment.getAvailableEquipment());
        this.basement = basement;
        this.inhabitableLoft = inhabitableLoft;
        this.sunExposure = sunExposure;
    }

    /**
     * Gets the value of the basement property.
     *
     * @return the value of the basement property.
     */
    public boolean isBasement() {
        return basement;
    }

    /**
     * Sets the value of the basement property.
     *
     * @param basement the new value of the basement property.
     */
    public void setBasement(boolean basement) {

        this.basement = basement;

    }


    /**
     * Returns whether or not the loft is inhabitable.
     *
     * @return a boolean representing whether or not the loft is inhabitable
     */
    public boolean isInhabitableLoft() {

        return inhabitableLoft;
    }

    /**
     * Sets the inhabitable status of the loft.
     *
     * @param inhabitableLoft a boolean representing whether or not the loft is inhabitable
     */
    public void setInhabitableLoft(boolean inhabitableLoft) {

        this.inhabitableLoft = inhabitableLoft;

    }

    /**
     * Returns the level of sun exposure the loft receives.
     *
     * @return a String representing the level of sun exposure the loft receives
     */
    public String getSunExposure() {
        
        return sunExposure;
    }

    /**
     * Sets the level of sun exposure the loft receives.
     *
     * @param sunExposure a String representing the level of sun exposure the loft receives
     */
    public void setSunExposure(String sunExposure) {

        this.sunExposure = sunExposure;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        
        String stringBasement;
        String stringLoft;
        if(basement){stringBasement = "Available";}else{stringBasement = "Not available";}
        if(inhabitableLoft){stringLoft = "Available";}else{stringLoft = "Not available";}
        return super.toString() +
                "\n     -> Basement: " + stringBasement +
                "\n     -> Inhabitable Loft: " + stringLoft +
                "\n     -> Sun Exposure: " + sunExposure ;
                
    }

    /**
     * Checks if this House object is equal to another object.
     *
     * @param otherHouse the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherHouse) {
        if (this == otherHouse) return true;
        if (otherHouse == null || getClass() != otherHouse.getClass()) return false;
        if (!super.equals(otherHouse)) return false;
        House house = (House) otherHouse;
        return basement == house.basement && inhabitableLoft == house.inhabitableLoft && Objects.equals(sunExposure, house.sunExposure);
    }
}
