package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RequestTest {
    String emailTest;
    Employee employeeTest;
    Address addressTest;
    Agency agencyTest;

    @BeforeEach
    void setUp(){
        emailTest = "Emailtest1@gmail.com";
        addressTest = new Address("Rua 1","Porto","Porto","Porto","12345");
        agencyTest = new Agency("1",addressTest,"Agency1","919100055","agency1@gmail.com");
        employeeTest = new Employee("João",123123123,"123123123","919191910",emailTest,"passwordTest",new String[] {AuthenticationController.ROLE_EMPLOYEE},addressTest,"agency1");

    }
    @Test
    void getEmployee() {
        Employee expectedResult = new Employee("João",123123123,"123123123","919191910","Emailtest1@gmail.com","passwordTest",new String[] {AuthenticationController.ROLE_EMPLOYEE},new Address("Rua 1","Porto","Porto","Porto","12345"),"agency1");
        Employee result = employeeTest;

        assertEquals(expectedResult,result);
    }

}