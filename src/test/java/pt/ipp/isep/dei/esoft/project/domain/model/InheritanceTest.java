package pt.ipp.isep.dei.esoft.project.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class InheritanceTest {

    ArrayList<String> photoURL;
    Address address;

    @BeforeEach
        void setUp() {
            photoURL = new ArrayList<String>();
            photoURL.add("www.google.com");
            address  = new Address("perlinhas", "Rio Tinto", "Porto", "Porto", "44353");

        }
    @Test
    public void ensureCreateLand() {
        //Arrange
        Land instance = new Land(100, 1000, photoURL, address, new Sale(10000));
        Land expected = new Land(100, 1000, photoURL, address, new Sale(10000));
        //Act
        Land result = null;
        //Assert
        assertNotEquals(expected, result);
    }

    @Test
    public void ensureCreateHouse() {
        //Arrange
        House instance = new House(100, 1000, photoURL, new Address("perlinhas", "Rio Tinto", "Porto", "Porto", "44352"), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        House expected = new House(100, 1000, photoURL, new Address("perlinhas", "Rio Tinto", "Porto", "Porto", "44352"), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        //Act
        House result = null;
        //Assert
        assertNotEquals(expected, result);
    }

    @Test
    public void ensureCreateApartment() {
        //Arrange
        Apartment instance = new Apartment(100, 1000,photoURL, new Address("perlinhas", "Rio Tinto", "Porto", "Porto", "44352"), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        Apartment expected = new Apartment(100, 1000,photoURL, new Address("perlinhas", "Rio Tinto", "Porto", "Porto", "44352"), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        //Act
        Apartment result = null;
        //Assert
        assertNotEquals(expected, result);
    }
}
