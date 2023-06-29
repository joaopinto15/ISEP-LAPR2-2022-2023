package pt.ipp.isep.dei.esoft.project.domain.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Request;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.util.ArrayList;

class RequestRepositoryTest {

    Request request1;
    Request request2;
    RequestRepository requestRepository;

    @BeforeEach
    void setup(){
        RequestRepository requestRepository = Repositories.getInstance().getRequestRepository();
        Person person1 = new Person("Haaland",123123123,"321321321","Haaland@gmail.com","919100055","haaland", new String[] {AuthenticationController.ROLE_CLIENT});
        Person person2 = new Person("DeBruyne",111222333,"333222111","DeBruyne@gmail.com","929200055","debruyne",new String[] {AuthenticationController.ROLE_CLIENT});

        Address address1 = new Address("Manchester Street","Manchester","Manchester","Manchester","32100");
        Address address2 = new Address("Liverpool street","Liverpool","Liverpool","Liverpool","50030");


        Rent rent1 = new Rent(500,2);
        Sale sale1 = new Sale(1000);

        Address spAddress = new Address("Rua da Liberdade", "São Paulo", "Liberdade", "SP", "21503");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/sp/land/photo1.jpg");

        Address bhAddress = new Address("Rua da Paz", "Belo Horizonte", "Serra", "MG", "30130");
        ArrayList<String> bhPhotos = new ArrayList<>();
        bhPhotos.add("https://example.com/bh/land/photo1.jpg");
        bhPhotos.add("https://example.com/bh/land/photo2.jpg");


        Property property1 = new Property(250,40,spPhotos, Property.TypeOfProperty.LAND,spAddress,rent1) {
        };
        Property property2 = new Property(100, 25,bhPhotos, Property.TypeOfProperty.LAND,bhAddress,sale1) {
        };


        Employee employee1 = new Employee("Ruben Dias",222333111,"321123321","939300055","RubenDias@gmail.com","rubendias",new String[] {AuthenticationController.ROLE_EMPLOYEE},address1,"Agency1");
        Employee employee2 = new Employee("Bernardo Silva",987987987,"776688999","959500011","BernardoSilva@gmail.com","bernardosilva",new String[]{AuthenticationController.ROLE_EMPLOYEE},address2,"Agency1");

        Request request1 = new Request(employee1,person1,property1,true);
        Request request2 = new Request(employee2,person2,property2,false);

        requestRepository.getRequestList().add(request1);
        requestRepository.getRequestList().add(request2);

    }
    @Test
    void addRequest() {
    RequestRepository expectedResult = new RequestRepository();
    expectedResult.getRequestList().add(request1);
    expectedResult.getRequestList().add(request2);
    

    }

    @Test
    void saveRequest() {
    }

    @Test
    void deleteRequest() {
    }
}