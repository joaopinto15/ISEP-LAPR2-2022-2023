package pt.ipp.isep.dei.esoft.project.domain.model.property;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * The type Property.
 */
public abstract class Property implements Serializable {
    /**
     * Property area.
     */
    private double area;

    /**
     * Distance from the property to the city center.
     */
    private double distanceFromCityCentre;

    /**
     * photos of the property.
     */
    private ArrayList<String> photoURL;

    /**
     * Type of property (house, apartment, land).
     */
//TODO: check for relevancy given instanceof is a thing
    public enum TypeOfProperty implements Serializable{
        /**
         * House type of property.
         */
        HOUSE,
        /**
         * Apartment type of property.
         */
        APARTMENT,
        /**
         * Land type of property.
         */
        LAND
    }

    /**
     * The Type of property.
     */
    private TypeOfProperty typeOfProperty;

    /**
     * Property address.
     */
    private Address address;

    /**
     * Information about the negotiation of the property (sale or rent).
     */
    private Deal Deal;

    /**
     * Property class constructor.
     *
     * @param area                   The area of the property.
     * @param distanceFromCityCentre is the property's distance from the city center.
     * @param photoURL               the URLs of the property's photos.
     * @param typeOfProperty         the type of property.
     * @param address                the address of the property.
     * @param Deal                   the information about the property deal.
     */
    public Property(double area, double distanceFromCityCentre, ArrayList<String> photoURL, TypeOfProperty typeOfProperty, Address address, Deal Deal) {
        this.area = area;
        this.distanceFromCityCentre = distanceFromCityCentre;
        this.photoURL = photoURL;
        this.typeOfProperty = typeOfProperty;
        this.address = address;
        this.Deal = Deal;
    }

    /**
     * Returns the area of the property.
     *
     * @return the area of the property.
     */
    public double getArea() {
        return area;
    }

    /**
     * Defines the area of the property.
     *
     * @param area the new property area.
     */
    public void setArea(double area) {
        this.area = area;
    }

    /**
     * Returns the distance from the property to the city center.
     *
     * @return the distance from the property to the city center.
     */
    public double getDistanceFromCityCentre() {
        return distanceFromCityCentre;
    }

    /**
     * Defines the distance of the property from the city center.
     *
     * @param distanceFromCityCentre the new distance from the property to the city center.
     */
    public void setDistanceFromCityCentre(double distanceFromCityCentre) {
        this.distanceFromCityCentre = distanceFromCityCentre;
    }

    /**
     * Returns the URLs of property photos.
     *
     * @return the property photo URLs.
     */
    public ArrayList<String> getPhotoURL() {
        return photoURL;
    }

    /**
     * Sets URLs for property photos.
     *
     * @param photoURL the new property photo URLs.
     */
    public void setPhotoURL(ArrayList<String> photoURL) {
        this.photoURL = photoURL;
    }

    /**
     * Returns the type of the property.
     *
     * @return the type of the property.
     */
    public TypeOfProperty getTypeOfProperty() {
        return typeOfProperty;
    }

    /**
     * Setter for the type of property.
     *
     * @param typeOfProperty a String representing the type of property.
     */
    public void setTypeOfProperty(TypeOfProperty typeOfProperty) {
        this.typeOfProperty = typeOfProperty;
    }

    /**
     * Getter for the address of the property.
     *
     * @return an Address object representing the address of the property.
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Setter for the address of the property.
     *
     * @param address an Address object representing the address of the property.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

    /**
     * Returns the deal associated with the property.
     *
     * @return the deal associated with the property.
     */
    public Deal getDeal() {
        return Deal;
    }

    /**
     * Sets the deal associated with the property.
     *
     * @param deal the deal to be associated with the property.
     */
    public void setDeal(Deal deal) {
        Deal = deal;
    }


    /**
     * To string string.
     *
     * @return the string
     */
    @Override
     public String toString() {
        pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal.DealType dealType= pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal.DealType.SALE;
        double price=((Deal.getDealType() == dealType) ? ((Sale) Deal).getPropertyPrice() : ((Rent) Deal).getPropertyRent() );
  
        return  "\n     -> Area: " + area +" feet\u00B2"+
                "\n     -> Distance From City Centre: " + distanceFromCityCentre +" m"+
                "\n     -> Photo URL: " + toStringPhotoUrl() +
                "\n     -> Type of Property: " + typeOfProperty +
                "\n     -> Address: " + address +
                "\n     -> Deal Type: " + Deal.getDealType();
    }

    /**
     * To string photo url string.
     *
     * @return the string
     */
    private String toStringPhotoUrl(){
        String str = "";
        if (photoURL.isEmpty()) {
            str += "\n       *This announcement has no Photos .\n";
        }else{
            for (String x : photoURL) {
                str += "\n        * "+ x;
            }
        }
        return str;
    }

    /**
     * Checks if this House object is equal to another object.
     *
     * @param otherProperty the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherProperty) {
        if (this == otherProperty) return true;
        if (otherProperty == null || getClass() != otherProperty.getClass()) return false;
        Property property = (Property) otherProperty;
        return Double.compare(property.area, area) == 0 && Double.compare(property.distanceFromCityCentre, distanceFromCityCentre) == 0 && Objects.equals(photoURL, property.photoURL) && typeOfProperty == property.typeOfProperty && Objects.equals(address, property.address) && Objects.equals(Deal, property.Deal);
    }
}
