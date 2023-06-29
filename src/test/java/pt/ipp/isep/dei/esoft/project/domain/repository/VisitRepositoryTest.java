package pt.ipp.isep.dei.esoft.project.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.Time;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VisitRepositoryTest {
    private VisitRepository visitRepository=Repositories.getInstance().getVisitRepository();
    private Visit visit;

    @BeforeEach
    void setUp() {
        PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
        AnnouncementRepository announcementRepository1 = Repositories.getInstance().getAnnouncementRepository();
        Date data1 = new Date(2023, 5, 25);
        Date data2 = new Date(2023, 12, 31);

        Commission commission1 = new Commission(10.5f, true);
        Commission commission2 = new Commission(500, false);

        Address address1 = new Address("Rua A", "Cidade X", "Bairro 1", "Estado A", "12345");
        Address address2 = new Address("Rua B", "Cidade Y", "Bairro 2", "Estado B", "54321");

        String email1 = "example1@example.com";
        String email2 = "example2@example.com";
        String email3 = "example3@example.com";
        String email4 = "example4@example.com";

        Agency agency1 = new Agency("ID1", address1, "Designation 1", "123456789", email1);
        Agency agency2 = new Agency("ID2", address2, "Designation 2", "987654321", email2);

        Employee employee1 = new Employee("John Doe", 123456789, "654321456", "987654321", email1, "1", new String[] {AuthenticationController.ROLE_EMPLOYEE}, address1, "1");
        Employee employee2 = new Employee("Jane Smith", 654321456, "210923487", "123456789", email2, "password2", new String[] {AuthenticationController.ROLE_EMPLOYEE}, address2, "2");
        Person person1 = new Person("João", 652344321, "789275012", email3, "912121211", "alo", new String[] {AuthenticationController.ROLE_CLIENT});
        personRepository.savePerson(person1);
        Person person2 = new Person("Ma", 123423456, "755053112", email4, "123456789", "julio", new String[] {AuthenticationController.ROLE_CLIENT});

        Address spAddress = new Address("Rua da Liberdade", "São Paulo", "Liberdade", "SP", "21503");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/sp/land/photo1.jpg");
        Land land1 = new Land(1000.0, 3.5, spPhotos, spAddress, new Sale(90000));

        Address bhAddress = new Address("Rua da Paz", "Belo Horizonte", "Serra", "MG", "30130");
        ArrayList<String> bhPhotos = new ArrayList<>();
        bhPhotos.add("https://example.com/bh/land/photo1.jpg");
        bhPhotos.add("https://example.com/bh/land/photo2.jpg");
        Land land2 = new Land(500.0, 8.2, bhPhotos, bhAddress, new Sale(12500));

        Request request1 = new Request(employee1, person1, land1, true);
        Request request2 = new Request(employee2, person2, land2, false);

        Order order1 = new Order(person1, 100000, data1);
        Order order2 = new Order(person2, 110000, data2);

        Announcement choosenAnnouncement = new Announcement(request1, commission1);
        Announcement announcement2 = new Announcement(request2, commission2);


        announcement2.addOrder(order1);
        announcement2.addOrder(order2);

        announcementRepository1.saveAnnouncement(announcement2);
        //
        visitRepository = new VisitRepository();
        Date date = new Date(2023, 5, 1);
        Time startTime = new Time(9);
        Time endTime = new Time(11);
        visit = new Visit("Sandro","934555768", date, startTime, endTime, "sandro@gmail.com" ,choosenAnnouncement);
    }

    @Test
    void saveVisit_ValidVisit_ReturnsTrue() {
        boolean result = visitRepository.saveVisit(visit);

        assertTrue(result);
        assertTrue(visitRepository.getVisitList().contains(visit));
    }

    @Test
    void saveVisit_NullVisit_ReturnsFalse() {
        boolean result = visitRepository.saveVisit(null);

        assertFalse(result);
        assertFalse(visitRepository.getVisitList().contains(visit));
    }

    @Test
    void saveVisit_DuplicateVisit_ReturnsFalse() {
        visitRepository.saveVisit(visit);
        boolean result = visitRepository.saveVisit(visit);

        assertFalse(result);
        assertEquals(1, visitRepository.getVisitList().size());
    }



    @Test
    void getVisitList_EmptyList_ReturnsEmptyList() {
        List<Visit> visitList = visitRepository.getVisitList();

        assertNotNull(visitList);
        assertEquals(0, visitList.size());
    }


}