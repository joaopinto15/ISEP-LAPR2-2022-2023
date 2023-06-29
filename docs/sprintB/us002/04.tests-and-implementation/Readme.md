# US 002 - Publish announcement

# 4. Tests 

**Test 1:** Check that it is not possible to create an instance of the Task class with null values for land

	@Test
    public void ensureCreateLand() {
        //Arrange
        Land instance = new Land(100, 1000, new String[4], "Rent", new Address("perlinhas", "Rio Tinto", "Porto", "Porto", 44353), new Sale(10000));
        Land expected = new Land(100, 1000, new String[4], "Rent", new Address("perlinhas", "Rio Tinto", "Porto", "Porto", 44353), new Sale(10000));
        //Act
        Land result = null;
        //Assert
        assertNotEquals(expected, result);
    }
	

**Test 2:** Check that it is not possible to create an instance of the Task class with null values for house 

	@Test
    public void ensureCreateHouse() {
        //Arrange
        House instance = new House(100, 1000, new String[4], "Rent", new Address("perlinhas", "Rio Tinto", "Porto", "Porto", 44352), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        House expected = new House(100, 1000, new String[4], "Rent", new Address("perlinhas", "Rio Tinto", "Porto", "Porto", 44352), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        //Act
        Land result = null;
        //Assert
        assertNotEquals(expected, result);
    }


**Test 3:** Check that it is not possible to create an instance of the Task class with null values for apartment

    @Test
    public void ensureCreateApartment() {
        //Arrange
        Apartment instance = new Apartment(100, 1000, new String[4], "Rent", new Address("perlinhas", "Rio Tinto", "Porto", "Porto", 44352), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        Apartment expected = new Apartment(100, 1000, new String[4], "Rent", new Address("perlinhas", "Rio Tinto", "Porto", "Porto", 44352), new Sale(10000), 2, 2, 2, new ArrayList<>(2));
        //Act
        Apartment result = null;
        //Assert
        assertNotEquals(expected, result);
    }

# 5. Construction (Implementation)


## Class PublishAnnouncementController

```java
package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.model.*;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Deal;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.ipp.isep.dei.esoft.project.domain.repository.UserRepository;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.domain.model.Password;
import pt.isep.lei.esoft.auth.domain.model.User;

import java.util.ArrayList;
import java.util.Arrays;

public class PublishAnnouncementController {

    /**
     * the variable repositories holds the repositories of Repositories
     */
    private final Repositories repositories;

    /**
     * the variable announcementRepository holds the announcementRepository of AnnouncementRepository
     */
    private AnnouncementRepository announcementRepository;

    private final UserRepository userRepository;

    /**
     * @return
     */
    public Repositories getRepositories() {
        return repositories;
    }

    /**
     *
     */
    public PublishAnnouncementController() {
        this.repositories = Repositories.getInstance();
        this.announcementRepository = repositories.getAnnouncementRepository();
        this.userRepository = repositories.getUserRepository();
    }

    public ArrayList<pt.ipp.isep.dei.esoft.project.domain.model.agency.User> ShowUserList() {
        return userRepository.getUserList();
    }

    public User publishUser(Email id, Password password, String name) {
        return new User(id, password, name);
    }

    public Employee publishEmployee(Person person, Address address, EmployeeRole role, Agency agency) {
        return new Employee(person, address, role, agency);
    }

    public Email publishEmail(String email) {
        return new Email(email);
    }

    public Password publishPassword(String password) {
        return new Password(password);
    }

    public Person publishPerson(String nameAgent, int citizenCardNumber, int taxNumber, String emailAddress, int phoneNumber) {
        return new Person(nameAgent, citizenCardNumber, taxNumber, emailAddress, phoneNumber);
    }

    public EmployeeRole publishEmployeeRole(String id, String designation) {
        return new EmployeeRole(id, designation);
    }

    public Agency publishAgency(String idAgency, Address agencyAddress, String designationAgency, String phoneNumberAgency, String emailAddressAgency) {
        return new Agency(idAgency, agencyAddress, designationAgency, phoneNumberAgency, emailAddressAgency);
    }

    public String[] publishPhotoURIToList(String PhotoURI, String[] PhotoURIList, int count, int maxPhotos) {

        if (count <= maxPhotos) {
            Arrays.fill(PhotoURIList, PhotoURI);

        } else {
            System.out.println("Photo storage full!");
        }
        return PhotoURIList;
    }

    public ArrayList<String> publishEquipmentToList(String equipment, ArrayList<String> availableEquipment) {

        availableEquipment.add(equipment);

        return availableEquipment;
    }

    public Sale publishSale(Double price) {

        return new Sale(price);
    }

    public Rent publishRent(Double price, int contractDuration) {

        return new Rent(price, contractDuration);
    }

    public Land publishLand(double area, double distanceFromCityCentre, String[] photoURL, String typeOfProperty, Address address, Deal Deal) {

        return new Land(area, distanceFromCityCentre, photoURL, typeOfProperty, address, Deal);
    }

    public Apartment publishApartment(double area, double distanceFromCityCentre, String[] photoURL, String typeOfProperty, Address address, Deal Deal, int numberOfBedrooms, int numberOfBathrooms, int numberOfParkingSpaces, ArrayList<String> availableEquipment) {

        return new Apartment(area, distanceFromCityCentre, photoURL, typeOfProperty, address, Deal, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment);
    }

    public House publishHouse(double area, double distanceFromCityCentre, String[] photoURI, String typeOfProperty, Address address, Deal Deal, int numberOfBedrooms, int numberOfBathrooms, int numberOfParkingSpaces, ArrayList<String> availableEquipment, boolean basement, boolean inhabitableLoft, String sunExposure) {

        return new House(area, distanceFromCityCentre, photoURI, typeOfProperty, address, Deal, numberOfBedrooms, numberOfBathrooms, numberOfParkingSpaces, availableEquipment, basement, inhabitableLoft, sunExposure);
    }

    public Address publishAddress(String street, String city, String district, String state, int zipcode) {

        return new Address(street, city, district, state, zipcode);
    }

    public Commission publishCommission(String commission) {

        return new Commission(commission);
    }

    public Announcement publishAnnouncement(Request request, Commission commission) {

        return new Announcement(request, commission);
    }

    public Request publishRequest(Employee employee, User user, Property property) {

        return new Request(employee, user, property);
    }

    public boolean addAnnouncement(Announcement announcement) {
        this.announcementRepository = repositories.getAnnouncementRepository();
        return this.announcementRepository.saveAnnouncement(announcement);
    }

    public String showAnnouncement(Announcement announcement) {
        return announcement.toString();
    }

}
```


## Class Announcement

```java
package pt.ipp.isep.dei.esoft.project.domain.model;

import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.domain.model.Password;
import pt.isep.lei.esoft.auth.domain.model.User;

import java.util.ArrayList;
import java.util.Arrays;


public class AnnouncementTest {

    //create the employee attributes

    //create commission
    Commission commission = new Commission("0.5%");
    //

    //create request
    //create the request attributes

    //create user
    //create the user attributes
    Email email = new Email("client@gmail.com");
    Password password = new Password("AAAaa11");
    //
    User user = new User(email, password, "Rui");
    //


    //create employee
    //create the employee attributes
    Person person = new Person("João", 123456789, 123456789, "teste@gmail.com", 912121211);
    Address address = new Address("21 Street", "Porto", "Porto", "Porto", 11111);
    EmployeeRole employeeRole = new EmployeeRole("1", "Agent");
    Agency agency = new Agency("1", address, "Porto", "123456789", "PortoAgency@gmail.com");
    //
    Employee employee = new Employee(person, address, employeeRole, agency);
    //

    //create apartment
    //create the apartment attributes
    String[] apartmentPhotos = {"https://example.com/apartment2/photo1.jpg", "https://example.com/apartment2/photo2.jpg", "https://example.com/apartment2/photo3.jpg"};
    Address apartmentAddress = new Address("Rua Barão de Mesquita", "Rio de Janeiro", "Tijuca", "RJ", 21503);
    ArrayList<String> apartmentEquipment = new ArrayList<>(Arrays.asList("Ar condicionado", "Aquecedor", "Internet"));
    Sale sale = new Sale(500000.0);
    //
    Apartment apartment = new Apartment(120.0, 3.0, apartmentPhotos, "Apartment", apartmentAddress, sale, 3, 2, 2, apartmentEquipment);
    //
    Request request = new Request(employee, user, apartment, new Rent(2, 1));
    //

    //create announcement
    Announcement announcement = new Announcement(request, commission);
    //

    //addAnnouncement(announcement);

    //return announcement;
}

```





