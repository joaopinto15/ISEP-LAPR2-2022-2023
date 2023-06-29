package pt.ipp.isep.dei.esoft.project.domain.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.dto.AnnouncementDTO;
import pt.ipp.isep.dei.esoft.project.domain.dto.OrderDTO;
import pt.ipp.isep.dei.esoft.project.domain.mapper.AnnouncementMapper;
import pt.ipp.isep.dei.esoft.project.domain.mapper.OrderMapper;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.repository.AnnouncementRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the OrderCheckController class.
 */
class OrderCheckControllerTest {

    OrderCheckController controller;
    Repositories repositories = Repositories.getInstance();
    AnnouncementRepository announcementRepository = repositories.getAnnouncementRepository();
    Announcement announcement;
    Order order1;
    Order order2;

    /**
     * Initial setup for each test.
     */
    @BeforeEach
    void setUp() {

        Repositories.reset();
        controller = new OrderCheckController();

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
        Employee employee = new Employee("John Doe", 123456789, "654321456", "987654321", email, "1",
                new String[] {AuthenticationController.ROLE_EMPLOYEE}, address, "Porto");
        Person person = new Person("João", 652344321, "789275012", clientEmail, "912121211", "hello",
                new String[] {AuthenticationController.ROLE_CLIENT});
        Address spAddress = new Address("Liberdade Street", "São Paulo", "Liberdade", "SP", "21503");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/bh/land/photo1.jpg");
        Land land = new Land(1000.0, 3.5, spPhotos, spAddress, new Sale(90000));
        Commission commission = new Commission(10.5f, true);
        Request request = new Request(employee, person, land, true);

        announcement = new Announcement(request, commission);

        Date date1 = new Date(2023, 2, 4);
        String email1 = "teste@gmail.com";
        Person person1 = new Person("João", 123456789, "123456789", email1, "912121211","boas",new String[] {AuthenticationController.ROLE_ADMIN});

        order1 = new Order(person1, 100000, date1);


        Date date2 = new Date(2023, 3, 5);
        String email2 = "teste2@gmail.com";
        Person person2 = new Person("Rui", 123456789, "123456789", email2, "912121211","ola",new String[] {AuthenticationController.ROLE_CLIENT});

        order2 = new Order(person2, 200000, date2);

    }

    /**
     * Tests the getOrdersByOrder() method of the OrderCheckController class.
     */
    @Test
    void getOrdersByOrder() {
        //order2 > order1
        announcement.addOrder(order1);
        announcement.addOrder(order2);
        announcementRepository.saveAnnouncement(announcement);

        AnnouncementDTO announcementDto = AnnouncementMapper.toDto(announcement);

        assertEquals(OrderMapper.toModel(controller.getOrdersByPrice(announcementDto).get(0)), order2);
        assertEquals(OrderMapper.toModel(controller.getOrdersByPrice(announcementDto).get(1)), order1);
    }

    /**
     * Tests the removeThisOrder() method of the OrderCheckController class.
     */
    @Test
    void removeThisOrder() {
        announcement.addOrder(order1);
        announcement.addOrder(order2);
        announcementRepository.saveAnnouncement(announcement);

        //remove order2 in announcement
        announcement.getOrders().remove(order2);

        //convert announcement and orders into Dto´s
        AnnouncementDTO announcementDto = AnnouncementMapper.toDto(announcement);
        OrderDTO orderDto = OrderMapper.toDto(order1);

        //remove order2 in announcementRepository
        controller.removeOrder(announcementDto, orderDto);

        assertEquals(announcement.getOrders(), OrderMapper.toModel(controller.getOrdersByPrice(announcementDto)));
    }

    /**
     * Tests the notifyClient() method of the OrderCheckController class.
     */
    @Test
    void notifyClient() {
    }
}
