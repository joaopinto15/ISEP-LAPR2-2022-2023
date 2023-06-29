# US 001 - List properties 

# 4. Tests 

**Test 1:** The property can’t be in sale and rent at the same time.-AC1

    @Test
    void ensurethatPropertyIsNotSaleAndRent () {
        Deal.DealType sale= Deal.DealType.SALE;
        Deal.DealType rent= Deal.DealType.RENT;
        if (land1.getDeal().getDealType()== rent && land1.getDeal().getDealType()== sale){
            fail("an instance has sell and rent characteristics");
        }

    }
**Test 2:** check if the filters are working.

        @Test
    void filterByHouse() {
        List<Property> properties = new ArrayList<>();
        properties.add(land1);
        properties.add(land2);
        properties.add(house1);
        properties.add(house2);
        properties.add(house2);

        Filtro house=new HouseFilter();

        System.out.println("HOUSE;");
        listProperties(house.typeFilter(properties));

            //número de propriedades com o respetivo filtro
            assertEquals(3,house.typeFilter(properties).size());

    }
    @Test
    void filterByApartment() {
        List<Property> properties = new ArrayList<>();
        properties.add(land1);
        properties.add(house1);
        properties.add(house2);
        properties.add(apartment2);

        Filtro apartment=new ApartementFilter();

        System.out.println("APARTMENT;");
        listProperties(apartment.typeFilter(properties));

        //número de propriedades com o respetivo filtro
        assertEquals(1,apartment.typeFilter(properties).size());
    }

    @Test
    void filterBySale() {
        List<Property> properties = new ArrayList<>();

        properties.add(house1);
        properties.add(house2);
        properties.add(apartment1);
        properties.add(apartment2);

        Filtro sale=new SaleFilter();


        System.out.println("SALE;");
        listProperties(sale.typeFilter(properties));

        //número de propriedades com o respetivo filtro
        assertEquals(1,sale.typeFilter(properties).size());
    }

    @Test
    void filterByRent() {
        List<Property> properties = new ArrayList<>();
        properties.add(land1);
        properties.add(land2);
        properties.add(house1);
        properties.add(house2);
        properties.add(apartment1);
        properties.add(apartment2);

        Filtro rent=new RentFilter();

        System.out.println("RENT;");
        listProperties(rent.typeFilter(properties));

        //número de propriedades com o respetivo filtro
        assertEquals(3,rent.typeFilter(properties).size());
    }

    @Test
    void filertByLand() {
        List<Property> properties = new ArrayList<>();
        properties.add(land1);
        properties.add(land2);
        properties.add(house1);
        properties.add(house2);
        properties.add(apartment1);
        properties.add(apartment2);
        Filtro land = new LandFilter();

        System.out.println("LANDS;");
        listProperties(land.typeFilter(properties));

        //número de propriedades com o respetivo filtro
        assertEquals(2,land.typeFilter(properties).size());
    }



*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)


## Class PropertyRepository

```java
public class PropertyRepository {
    private final List<Property> propertiesList=new ArrayList<>();

    public boolean validateProperty(Property property){
        if(property == null){
            return false;
        } else {
            return !this.propertiesList.contains(property);
        }
    }

    private boolean addProperty(Property property){
        return this.propertiesList.add(property);
    }


    public boolean saveProperty(Property property){
        if(!validateProperty(property)){
            return false;
        } else {
            return addProperty(property);
        }
    }
    public List<Property> getPropertyList() {
        return propertiesList;
    }

}

```
## Class PropertyRepository

```java
public class PropertyRepository {
    private final List<Property> propertiesList = new ArrayList<>();

    public boolean validateProperty(Property property) {
        if (property == null) {
            return false;
        } else {
            return !this.propertiesList.contains(property);
        }
    }

    private boolean addProperty(Property property) {
        return this.propertiesList.add(property);
    }


    public boolean saveProperty(Property property) {
        if (!validateProperty(property)) {
            return false;
        } else {
            return addProperty(property);
        }
    }

    public List<Property> getPropertyList() {
        return propertiesList;
    }

}
```
## Class ListPropertyController
```java
public class ListPropertyController {
    private final PropertyRepository propertyRepository;

    public ListPropertyController() {
        this.propertyRepository = Repositories.getInstance().getPropertyRepository();
    }

    @Override
    public String toString() {
        return "ListPropertyController{" +
                "propertyRepository=" + propertyRepository.getPropertyList() +
                '}';
    }
}

```

# 6. Integration and Demo 

* A new option on the Employee menu options was added.

* Some demo purposes some tasks are bootstrapped while system starts.


# 7. Observations

Platform and Organization classes are getting too many responsibilities due to IE pattern and, therefore, they are becoming huge and harder to maintain. 

Is there any way to avoid this to happen?





