package pt.ipp.isep.dei.esoft.project.domain.model;

import org.junit.jupiter.api.BeforeEach;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.isep.lei.esoft.auth.domain.model.Email;


class EmployeeTest {
    Email email1,email2;
    Person person1,person2;
    Address address1,address2;

    String emailAgency1 = "Agencia1@gmail.com";
    String emailAgency2 = "Agencia2@gmail.com";
    Agency agency1 = new Agency("1",address1,"Agencia1","987654321",emailAgency1);
    Agency agency2 = new Agency("2",address2,"Agencia2","912345678",emailAgency2);
    @BeforeEach
    void setUp() {
        email1 = new Email("rui@gmail.com");
        email2 = new Email("Maria@gmail.com");
        address1 = new Address("Rua 1","Porto","Porto","Porto","12345");
        address2 = new Address("Rua 2","Lisboa","Lisboa","Lisboa","54321");
    }
    // @AfterEach
    // void cleanUp() {
    //     Repositories.reset();
    // }
//@Test
//    void ensureTwoEmployeesWithSameEmail(){
//    Employee employee1 = new Employee("Rui", 123456789, 987654321, 912345678, email1, "aaa",
//            AuthenticationController.ROLE_CLIENT,address1,agency1);
//    Employee employee2 = new Employee("Maria", 111111111, 222222222, 987654321, email2, "bbb",
//            AuthenticationController.ROLE_EMPLOYEE,address2,agency2);
//
//    Email email1 = employee1.getEmailAddress();
//    Email email2 = employee2.getEmailAddress();
//
//    assertEquals(email1,email2);
//}
//@Test
//void ensureTwoEmployeesWithDifferentEmail(){
//    Employee employee1 = new Employee("Rui", 123456789, 987654321, 912345678, email1, "aaa",
//            AuthenticationController.ROLE_CLIENT,address1,agency1);
//    Employee employee2 = new Employee("Maria", 111111111, 222222222, 987654321, email2, "bbb",
//            AuthenticationController.ROLE_EMPLOYEE,address2,agency2);
//
//    Email email1 = employee1.getEmailAddress();
//    Email email2 = employee2.getEmailAddress();
//
//    assertNotEquals(email1,email2);
//}

}