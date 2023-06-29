package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the Announcement class.
 */
class AnnouncementTest {

    private Announcement announcement;
    private Request request;
    private Commission commission;

    /**
     * Initial setup for each test.
     */
    @BeforeEach
    void setUp() {

        Repositories.reset();

        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        AnnouncementRepository announcementRepository = Repositories.getInstance().getAnnouncementRepository();

        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_NETWORK_MANAGER, AuthenticationController.ROLE_NETWORK_MANAGER);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_MANAGER, AuthenticationController.ROLE_MANAGER);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_CLIENT, AuthenticationController.ROLE_CLIENT);

        Address address = new Address("Rua A", "City X", "District 1", "State A", "12345");
        String email = "example1@example.com";
        String clientEmail = "example3@example.com";
        Agency agency = new Agency("ID1", address, "Designation 1", "123456789", email);
        Employee employee = new Employee("John Doe", 123456789, "654321456", "987654321", email, "1",
                new String[] {AuthenticationController.ROLE_EMPLOYEE}, address, "Porto");
        Person person = new Person("João", 652344321, "789275012", clientEmail, "912121211", "hello",
                new String[] {AuthenticationController.ROLE_CLIENT});
        Address spAddress = new Address("Liberdade Street", "São Paulo", "Liberdade", "SP", "21503");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/bh/land/photo1.jpg");
        Land land = new Land(1000.0, 3.5, spPhotos, spAddress, new Sale(90000));
        commission = new Commission(10.5f, true);
        request = new Request(employee, person, land, true);
        announcement = new Announcement(request, commission);
    }

    /**
     * Tests the getCommission() method of the Announcement class.
     */
    @Test
    public void getCommission() {
        assertEquals(commission, announcement.getCommission());
    }

    /**
     * Tests the getRequest() method of the Announcement class.
     */
    @Test
    public void getRequest() {
        assertEquals(request, announcement.getRequest());
    }

    /**
     * Tests the addOrder() method of the Announcement class.
     */
    @Test
    public void addOrder() {
        Date date = new Date(2023, 2, 4);
        String email = "teste@gmail.com";
        Person person = new Person("João", 123456789, "123456789", email, "912121211","boas",new String[] {AuthenticationController.ROLE_ADMIN});

        Order order = new Order(person, 100000, date);
        announcement.addOrder(order);

        assertTrue(announcement.getOrders().contains(order));
    }
}