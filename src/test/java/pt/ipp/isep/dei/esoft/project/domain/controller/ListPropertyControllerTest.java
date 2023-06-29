// package pt.ipp.isep.dei.esoft.project.domain.controller;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import pt.ipp.isep.dei.esoft.project.domain.model.Address;
// import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
// import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
// import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
// import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
// import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
// import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

// import java.util.ArrayList;
// import java.util.Arrays;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.assertEquals;

// public class ListPropertyControllerTest {

//     // Criando uma instância de aluguel com contrato de 12 meses e aluguel de R$ 2.000,00
//     Rent rent1 = new Rent(2000.0, 12);

//     // Criando uma instância de venda com preço de R$ 500.000,00
//     Sale sale1 = new Sale(500000.0);

//     // Criando uma instância para um terreno em São Paulo
//     Address spAddress = new Address("Rua da Liberdade", "São Paulo", "Liberdade", "SP", 21503);
//     ArrayList<String> spPhotos = new ArrayList<>(List.of("https://example.com/sp/land/photo1.jpg"));
//     Land land1 = new Land(1000.0, 3.5, spPhotos,  spAddress, new Sale(123));



//     // Criando uma instância de casa com porão e sótão habitável
//     Address house1Address = new Address("Rua das Flores", "São Paulo", "Centro", "SP", 21503);
//     ArrayList<String> house1Photos = new ArrayList<>(Arrays.asList("https://example.com/house1/photo1.jpg", "https://example.com/house1/photo2.jpg", "https://example.com/house1/photo3.jpg"));
//     ArrayList<String> house1Equipment = new ArrayList<>(Arrays.asList("Ar condicionado", "Aquecedor", "Internet"));
//     House house1 = new House(200.0, 3.5, house1Photos,  house1Address, rent1, 4, 3, 2, house1Equipment, true, true, "Norte");



//     // Criando uma instância de apartamento com 2 quartos, 1 banheiro e 1 vaga de estacionamento
//     Address apartment1Address = new Address("Avenida Paulista", "Aão Paulo", "Bela Vista", "SP", 21503);
//     ArrayList<String> apartment1Photos = new ArrayList<>(Arrays.asList("https://example.com/apartment1/photo1.jpg", "https://example.com/apartment1/photo2.jpg"));
//     ArrayList<String> apartment1Equipment = new ArrayList<>(Arrays.asList("Ar condicionado", "Aquecedor", "TV a cabo", "Internet"));
//     Apartment apartment1 = new Apartment(80.0, 1.5, apartment1Photos,  apartment1Address, rent1, 2, 1, 1, apartment1Equipment);




//     private ListPropertyController controller;
//     private List<Property> propertyList;


//    @BeforeEach
//    public void setUp() {
//        // Cria uma lista de propriedades para usar nos testes
//        propertyList = new ArrayList<>();
//        propertyList.add(land1);
//        propertyList.add(house1);
//        propertyList.add(apartment1);
//
//        // Cria um controller usando a lista de propriedades
//        controller = new ListPropertyController();
//        controller.filtro("1", propertyList, 2); // Exemplo de filtro para land
//    }
//
//    @Test
//    public void testGetAllProperties() {
//        // Verifica se o método getAllProperties retorna a lista correta
//        List<Property> retrievedList = controller.getAllProperties();
//        assertEquals(propertyList, retrievedList);
//    }

//    @Test
//    public void testSort() {
//        // Testa a ordenação por preço crescente
//        controller.sort("3", propertyList);
//        List<Property> expectedList = new ArrayList<>();
//        expectedList.add(propertyList.get(0)); // land
//        expectedList.add(propertyList.get(2)); // apartment
//        expectedList.add(propertyList.get(1)); // house
//        List<Property> retrievedList = controller.getAllProperties();
//        assertEquals(expectedList, retrievedList);
//
//        // Testa a ordenação por preço decrescente
//        controller.sort("4", propertyList);
//        expectedList = new ArrayList<>();
//        expectedList.add(propertyList.get(1)); // house
//        expectedList.add(propertyList.get(2)); // apartment
//        expectedList.add(propertyList.get(0)); // land
//        retrievedList = controller.getAllProperties();
//        assertEquals(expectedList, retrievedList);
//    }



// }