package pt.ipp.isep.dei.esoft.project.domain.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.isep.lei.esoft.auth.domain.model.Email;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PersonTest {
    
    Address testAddress;
    String email;
    Person instance ;
    String[]role=new String[5];

    @BeforeEach
    void setUp() {
        role[0]=AuthenticationController.ROLE_ADMIN;
        testAddress = new Address("21 Street", "Porto", "Porto", "Porto", "11111");
        email = "teste@gmail.com";
        instance = new Person("João", 123456789, "123456789", email, "9121121211","boas",role);
    }

    @Test
    void getName() {
        String expectedResult = "João";

        String result = instance.getName();

        assertEquals(expectedResult,result);
    }

    @Test
    void getCitizenCardNumber() {
        int expectedResult = 123456789;
        int result = instance.getPassportNumber();

        assertEquals(expectedResult,result);
    }

    @Test
    void getTaxNumber() {
        String expectedResult ="123456789";
        String result = instance.getTaxNumber();

        assertEquals(expectedResult,result);
    }

    @Test
    void getEmailAddress() {
        Email expectedResult= new Email("teste@gmail.com");
        Email result = instance.getEmailAddress();

        assertEquals(expectedResult,result);
    }

    @Test
    void getPhoneNumber() {
        String expectedResult = "9121121211";
        String result = instance.getPhoneNumber();

        assertEquals(expectedResult,result);
    }


}
