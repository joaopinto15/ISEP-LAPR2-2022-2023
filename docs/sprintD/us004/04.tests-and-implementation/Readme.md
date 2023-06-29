# US 004 - Create a request for listing a property

# 5. Construction (Implementation)


## Class ListingRequestController

```java
public class ListingRequestController {
    /**
     * Initiates the instance repositories of the class Repositories
     */
    private final Repositories repositories = Repositories.getInstance();

    /**
     * Instantiates a new Listing request controller.
     */
    public ListingRequestController() {
    }

    /**
     * Submit property.
     *
     * @param property the property
     * @param person   the person
     * @param employee the employee
     */
    public void submitProperty(Property property, Person person, Employee employee) {
        Request request = new Request(employee, person, property, true);
        RequestRepository requestRepository = Repositories.getInstance().getRequestRepository();
        requestRepository.saveRequest(request);
    }

    /**
     * Generates a random agent from the list of employees based on the given
     * agency.

     * @return a random agent from the list of employees
     */
    public Employee randomAgent() {
        List<Employee> employees = Repositories.getInstance().getPersonRepository().getEmployees();
        Random random = new Random();
        int index = random.nextInt(employees.size());
        return employees.get(index);
    }

}
```


## Class Property

```java
public abstract class Property implements Serializable {
    //TODO: Check if we need to include the owner here or request only
    //TODO: Add documentation
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
    //FIXME: Check if this is the correct way to do this
    @Override
    public String toString() {
        pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal.DealType dealType= pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal.DealType.SALE;
        double price1=((Deal.getDealType() == dealType) ? ((Sale) Deal).getPropertyPrice() : ((Rent) Deal).getPropertyRent() );

        return "Announcement: " +
                "\nPrice: " + price1 + "$"+
                "\nArea: " + area +" m²"+
                "\nDistance From City Centre: " + distanceFromCityCentre +" m"+
                "\nPhoto URL: " + photoURL.toString() +
                "\nType of Property: " + typeOfProperty +
                "\nAddress: " + address +
                "\nDeal Type: " + Deal.getDealType();
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
```

# 6. Integration and Demo 

* A new option on the Client menu options was added.

* Some announcements are bootstrapped while system starts.


# 7. Observations

We decided to create a new class called ListingRequestController to handle the creation of a new request. This class is responsible for creating a new request and saving it in the repository. We also decided to create a new method called randomAgent() to generate a random agent from the list of employees based on the given agency. This method is used to assign a random agent to the request.






