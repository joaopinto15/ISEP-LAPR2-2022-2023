package pt.ipp.isep.dei.esoft.project.domain.model;

class PropertyTest {

//    @Test
//    void sortByCity() {
//        List<Property> properties = new ArrayList<>();
//        properties.add(land1);
//        properties.add(land2);
//        properties.add(house1);
//        properties.add(house2);
//        properties.add(apartment1);
//        properties.add(apartment2);
//
//
//        System.out.println("SORT_CITY;");
//        Collections.sort(properties,new Properties_By_City());
//        listProperties(properties);
//
//    }
//    @Test
//    void sortByPrice() {
//        List<Property> properties = new ArrayList<>();
//        properties.add(land1);
//        properties.add(land2);
//        properties.add(house1);
//        properties.add(house2);
//        properties.add(apartment1);
//        properties.add(apartment2);
//
//        System.out.println("SORT_PRICE;");
//        Collections.sort(properties,new Properties_By_Price());
//        listProperties(properties);
//    }
//    @Test
//    void filertByLand() {
//        List<Property> properties = new ArrayList<>();
//        properties.add(land1);
//        properties.add(land2);
//        properties.add(house1);
//        properties.add(house2);
//        properties.add(apartment1);
//        properties.add(apartment2);
//        Filtro land = new LandFilter();
//
//        System.out.println("LANDS;");
//        listProperties(land.typeFilter(properties));
//
//        //número de propriedades com o respetivo filtro
//        assertEquals(2,land.typeFilter(properties).size());
//    }
//
//        @Test
//    void filterByHouse() {
//        List<Property> properties = new ArrayList<>();
//        properties.add(land1);
//        properties.add(land2);
//        properties.add(house1);
//        properties.add(house2);
//        properties.add(house2);
//
//        Filtro house=new HouseFilter();
//
//        System.out.println("HOUSE;");
//        listProperties(house.typeFilter(properties));
//
//            //número de propriedades com o respetivo filtro
//            assertEquals(3,house.typeFilter(properties).size());
//
//    }
//    @Test
//    void filterByApartment() {
//        List<Property> properties = new ArrayList<>();
//        properties.add(land1);
//        properties.add(house1);
//        properties.add(house2);
//        properties.add(apartment2);
//
//        Filtro apartment=new ApartmentFilter();
//
//        System.out.println("APARTMENT;");
//        listProperties(apartment.typeFilter(properties));
//
//        //número de propriedades com o respetivo filtro
//        assertEquals(1,apartment.typeFilter(properties).size());
//    }
//
//    @Test
//    void filterBySale() {
//        List<Property> properties = new ArrayList<>();
//
//        properties.add(house1);
//        properties.add(house2);
//        properties.add(apartment1);
//        properties.add(apartment2);
//
//        Filtro sale=new SaleFilter();
//
//
//        System.out.println("SALE;");
//        listProperties(sale.typeFilter(properties));
//
//        //número de propriedades com o respetivo filtro
//        assertEquals(1,sale.typeFilter(properties).size());
//    }
//
//    @Test
//    void filterByRent() {
//        List<Property> properties = new ArrayList<>();
//        properties.add(land1);
//        properties.add(land2);
//        properties.add(house1);
//        properties.add(house2);
//        properties.add(apartment1);
//        properties.add(apartment2);
//
//        Filtro rent=new RentFilter();
//
//        System.out.println("RENT;");
//        listProperties(rent.typeFilter(properties));
//
//        //número de propriedades com o respetivo filtro
//        assertEquals(3,rent.typeFilter(properties).size());
//    }
//
//    @Test
//    void ensurethatPropertyIsNotSaleAndRent () {
//        Deal.DealType sale= Deal.DealType.SALE;
//        Deal.DealType rent= Deal.DealType.RENT;
//        if (land1.getDeal().getDealType()== rent && land1.getDeal().getDealType()== sale){
//            fail("an instance has sell and rent characteristics");
//        }
//
//    }
}