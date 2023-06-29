package pt.ipp.isep.dei.esoft.project.domain.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.mapper.AnnouncementMapper;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test class for the AnnouncementController class.
 */
class AnnouncementControllerTest {

    private CreateAnnouncementController controller = new CreateAnnouncementController();
    private Repositories repositories = Repositories.getInstance();
    private AnnouncementRepository announcementRepository = repositories.getAnnouncementRepository();
    private AuthenticationRepository authenticationRepository = repositories.getAuthenticationRepository();
    private Request request1;
    private Commission commission1;
    private Request request2;
    private Commission commission2;

    /**
     * Initial setup for each test.
     */
    @BeforeEach
    void setUp() {

        Repositories.reset();

        controller = new CreateAnnouncementController();
        repositories = Repositories.getInstance();

        announcementRepository = Repositories.getInstance().getAnnouncementRepository();
        authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

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
        commission1 = new Commission(10.5f, true);
        request1 = new Request(employee, person, land, true);

        Address buyerAddress = new Address("Avenue B", "City Y", "District 2", "State B", "54321");
        String buyerEmail = "buyer@example.com";
        String agentEmail = "agent@example.com";
        Agency sellingAgency = new Agency("ID2", buyerAddress, "Designation 2", "987654321", agentEmail);
        Employee agent = new Employee("Jane Smith", 987654321, "456789123", "789456123", agentEmail, "2",
                new String[] {AuthenticationController.ROLE_EMPLOYEE}, buyerAddress, "Aveiro");
        Person buyer = new Person("Maria", 987654321, "123456789", buyerEmail, "912121211", "welcome",
                new String[] {AuthenticationController.ROLE_CLIENT});
        Address propertyAddress = new Address("Main Street", "City Z", "Downtown", "State C", "54321");
        ArrayList<String> propertyPhotos = new ArrayList<>();
        propertyPhotos.add("https://example.com/property/photo1.jpg");
        propertyPhotos.add("https://example.com/property/photo2.jpg");
        Land land1 = new Land(1200.0, 4.5, propertyPhotos, propertyAddress, new Sale(150000));
        commission2 = new Commission(8.0f, false);
        request2 = new Request(agent, buyer, land, false);
    }

    /**
     * Tests the publishAnnouncement() method of the AnnouncementController class.
     */
    @Test
    public void publishAnnouncement() {
        Announcement announcement = new Announcement(request1, commission1);
        AnnouncementDTO announcementDto = AnnouncementMapper.toDto(announcement);

        assertTrue(controller.publishAnnouncement(announcementDto));
        assertTrue(announcementRepository.getNotSoldAnnouncementList().contains(announcement));
    }

    /**
     * Tests the getAnnouncementList() method of the AnnouncementController class.
     */
    @Test
    public void getAnnouncementList() {
        Announcement announcement1 = new Announcement(request1, commission1);
        Announcement announcement2 = new Announcement(request2, commission2);

        announcementRepository.saveAnnouncement(announcement1);
        announcementRepository.saveAnnouncement(announcement2);
        assertTrue(AnnouncementMapper.toModel(controller.getAnnouncementList()).contains(announcement1));
        assertTrue(AnnouncementMapper.toModel(controller.getAnnouncementList()).contains(announcement2));
    }

}
