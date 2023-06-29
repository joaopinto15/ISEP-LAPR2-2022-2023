package pt.ipp.isep.dei.esoft.project.ui;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.controller.ListingRequestController;
import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.Time;
import pt.ipp.isep.dei.esoft.project.domain.model.Visit;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.*;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Rent;
import pt.ipp.isep.dei.esoft.project.domain.model.deals.Sale;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Apartment;
import pt.ipp.isep.dei.esoft.project.domain.model.property.House;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.ipp.isep.dei.esoft.project.domain.repository.*;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * The Bootstrap class implements the Runnable interface and is responsible for initializing and populating the repositories with sample data.
 */
public class Bootstrap implements Runnable , Serializable {

    /**
     * This method is called when the Bootstrap instance is executed.
     * It adds sample data to the repositories.
     */
    public void run() {
        //addAgencies();
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        addRoles(authenticationRepository);     //keep
        deserialize();                        //keep            
        addEmployees();
        addClients();
        addProperty();
        addRequests();
        addAnnouncement();
        addVisit();
        addAdmin();
        addManagers();
        addNetworkManagers();
        addAgencies();
        addRequests2();
    }

    /**
     * Adds sample requests to the request repository.
     */
    private void addRequests() {
        ListingRequestController listingRequestController = new ListingRequestController();
        ArrayList<Request> requests = new ArrayList<>();
        Address spAddress = new Address("Rua da Liberdade", "São Paulo", "Liberdade", "SP", "21503");
        Address spAddress2 = new Address("Avenida Paulista", "São Paulo", "Bela Vista", "SP", "12345");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/sp/land/photo1.jpg");
        spPhotos.add("https://example.com/sp/land/photo2.jpg");
        Land land1 = new Land(1000.0, 3.5, spPhotos, spAddress, new Sale(90000));
        Land land2 = new Land(2000.0, 4.5, spPhotos, spAddress2, new Sale(100000));
        listingRequestController.submitProperty(land1, Utils.getClient(new Email("client@this.app")), Utils.getEmployee(new Email("john.doe@example.com")));
        listingRequestController.submitProperty(land2, Utils.getClient(new Email("joao@this.app")), Utils.getEmployee(new Email("john.doe@example.com")));
    }

    /**
     * Add visit.
     */
    private void addVisit() {
        VisitRepository visitRepository = Repositories.getInstance().getVisitRepository();
        visitRepository.saveVisit(new Visit("Sandro", "1234567890", Date.currentDate(), new Time(12, 21, 32), new Time(13, 32, 12),"sandro@gmail.com", Repositories.getInstance().getAnnouncementRepository().getNotSoldAnnouncementList().get(0)));
        visitRepository.saveVisit(new Visit("Jaqueline", "1234567390", new Date(Date.currentDate().getYear()+1,Date.currentDate().getMonth(), Date.currentDate().getDay()), new Time(12, 21, 32), new Time(13, 32, 12), "jaqueline@this.app", Repositories.getInstance().getAnnouncementRepository().getNotSoldAnnouncementList().get(0)));
    }

    /**
     * Add employees.
     */
    private void addEmployees() {
        String[] roles = new String[2];
        roles[0] = AuthenticationController.ROLE_EMPLOYEE;

        PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

        Address address1 = new Address("123 Main St", "City1", "District1", "State1", "12345");
        Address address2 = new Address("456 Elm St", "City2", "District2", "State2", "67890");
        Address address3 = new Address("789 Oak St", "City3", "District3", "State3", "24680");
        Address address4 = new Address("321 Pine St", "City4", "District4", "State4", "13579");
        Address address5 = new Address("654 Maple St", "City5", "District5", "State5", "97531");
        Address address6 = new Address("987 Cedar St", "City6", "District6", "State6", "86420");

        Employee employee1 = new Employee("John Doe", 123456789, "123456789", "123456789", "john.doe@example.com", "ss", roles, address1, "agency1");
        Employee employee2 = new Employee("Jane Smith", 987654321, "123456789", "123456789", "jane.smith@example.com", "ss", roles, address2, "agency2");
        Employee employee3 = new Employee("Alice Johnson", 456789123, "123456789", "123456789", "alice.johnson@example.com", "ss", roles, address3, "agency3");
        Employee employee4 = new Employee("Bob Anderson", 789123456, "123456789", "123456789", "bob.anderson@example.com", "ss", roles, address4, "agency1");
        Employee employee5 = new Employee("Emily Davis", 159357852, "123456789", "123456789", "emily.davis@example.com", "ss", roles, address5, "agency2");
        Employee employee6 = new Employee("Michael Wilson", 852963741, "123456789", "123456789", "michael.wilson@example.com", "ss", roles, address6, "agency3");

        personRepository.savePerson(employee1);
        authenticationRepository.addUser(employee1.getName(), employee1.getEmailAddress().toString(), employee1.getPwd(), employee1.getRolesId());
        personRepository.savePerson(employee2);
        authenticationRepository.addUser(employee2.getName(), employee2.getEmailAddress().toString(), employee2.getPwd(), employee2.getRolesId());
        personRepository.savePerson(employee3);
        authenticationRepository.addUser(employee3.getName(), employee3.getEmailAddress().toString(), employee3.getPwd(), employee3.getRolesId());
        personRepository.savePerson(employee4);
        authenticationRepository.addUser(employee4.getName(), employee4.getEmailAddress().toString(), employee4.getPwd(), employee4.getRolesId());
        personRepository.savePerson(employee5);
        authenticationRepository.addUser(employee5.getName(), employee5.getEmailAddress().toString(), employee5.getPwd(), employee5.getRolesId());
        personRepository.savePerson(employee6);
        authenticationRepository.addUser(employee6.getName(), employee6.getEmailAddress().toString(), employee6.getPwd(), employee6.getRolesId());
    }

    /**
     * Add network managers.
     */
    public void addNetworkManagers() {
    String[] roles = new String[2];
    roles[0] = AuthenticationController.ROLE_NETWORK_MANAGER;

    PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
    AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

    Address address1 = new Address("111 Network St", "City1", "District1", "State1", "12345");
    Address address2 = new Address("222 Network St", "City2", "District2", "State2", "67890");
    Address address3 = new Address("333 Network St", "City3", "District3", "State3", "24680");

    Employee networkManager1 = new Employee("Tom Johnson", 111111111, "123456789", "123456789", "networkManager@this.app", "nm", roles, address1, "agency1");
    Employee networkManager2 = new Employee("Sarah Smith", 222222222, "123456789", "123456789", "networkManager2@this.app", "nm", roles, address2, "agency2");
    Employee networkManager3 = new Employee("Mike Davis", 333333333, "123456789", "123456789", "networkManager3@this.app", "nm", roles, address3, "agency3");

    personRepository.savePerson(networkManager1);
    authenticationRepository.addUser(networkManager1.getName(), networkManager1.getEmailAddress().toString(), networkManager1.getPwd(), networkManager1.getRolesId());
    personRepository.savePerson(networkManager2);
    authenticationRepository.addUser(networkManager2.getName(), networkManager2.getEmailAddress().toString(), networkManager2.getPwd(), networkManager2.getRolesId());
    personRepository.savePerson(networkManager3);
    authenticationRepository.addUser(networkManager3.getName(), networkManager3.getEmailAddress().toString(), networkManager3.getPwd(), networkManager3.getRolesId());
}

    /**
     * Add managers.
     */
    public void addManagers() {
    String[] roles = new String[2];
    roles[0] = AuthenticationController.ROLE_MANAGER;

    PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
    AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

    Address address1 = new Address("111 Network St", "City1", "District1", "State1", "12345");
    Address address2 = new Address("222 Network St", "City2", "District2", "State2", "67890");
    Address address3 = new Address("333 Network St", "City3", "District3", "State3", "24680");

    Employee networkManager1 = new Employee("Tom Johnson", 111111111, "123456789", "123456789", "manager@this.app", "m", roles, address1, "agency1");
    Employee networkManager2 = new Employee("Sarah Smith", 222222222, "123456789", "123456789", "manager2@this.app", "m", roles, address2, "agency2");
    Employee networkManager3 = new Employee("Mike Davis", 333333333, "123456789", "123456789", "manager3@this.app", "m", roles, address3, "agency3");

    personRepository.savePerson(networkManager1);
    authenticationRepository.addUser(networkManager1.getName(), networkManager1.getEmailAddress().toString(), networkManager1.getPwd(), networkManager1.getRolesId());
    personRepository.savePerson(networkManager2);
    authenticationRepository.addUser(networkManager2.getName(), networkManager2.getEmailAddress().toString(), networkManager2.getPwd(), networkManager2.getRolesId());
    personRepository.savePerson(networkManager3);
    authenticationRepository.addUser(networkManager3.getName(), networkManager3.getEmailAddress().toString(), networkManager3.getPwd(), networkManager3.getRolesId());
}

    /**
     * Add admin.
     */
    private void addAdmin() {
        String[] roles = new String[2];
        roles[0] = AuthenticationController.ROLE_ADMIN;

        PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

        Address address4 = new Address("321 Pine St", "City4", "District4", "State4", "13579");
        Address address5 = new Address("654 Maple St", "City5", "District5", "State5", "97531");
        Employee employee4 = new Employee("Joao Pinto", 789123456, "123456789", "123456789", "joao@example.com", "ss", roles, address4, "agency1");
        Employee employee5 = new Employee("Sandro", 159357852, "123456789", "123456789", "sandro@example.com", "ss", roles, address5, "agency2");

        personRepository.savePerson(employee4);
        authenticationRepository.addUser(employee4.getName(), employee4.getEmailAddress().toString(), employee4.getPwd(), employee4.getRolesId());
        personRepository.savePerson(employee5);
        authenticationRepository.addUser(employee5.getName(), employee5.getEmailAddress().toString(), employee5.getPwd(), employee5.getRolesId());
    }

    /**
     * Add clients.
     */
    private void addClients() {
        String[] roles = new String[6];
        roles[3] = AuthenticationController.ROLE_CLIENT;
        PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();

        Person client1 = new Person("Luis", 123456789, "123456789", "client@this.app", "123456789", "cl", roles);
        Person client2 = new Person("Antonio", 987654321, "987654321", "client2@this.app", "987654321", "cl", roles);
        Person client3 = new Person("Ze", 456789123, "456789123", "client3@this.app", "456789123", "cl", roles);
        Person client4 = new Person("Miguel", 789123456, "789123456", "client4@this.app", "789123456", "cl", roles);
        Person client5 = new Person("Sandro", 654321987, "654321987", "client5@this.app", "654321987", "cl", roles);
        Person client6 = new Person("Joao", 321987654, "321987654", "client6@this.app", "321987654", "cl", roles);
         
        personRepository.savePerson(client1);
        authenticationRepository.addUser(client1.getName(), client1.getEmailAddress().toString(), client1.getPwd(), client1.getRolesId());
        personRepository.savePerson(client2);
        authenticationRepository.addUser(client2.getName(), client2.getEmailAddress().toString(), client2.getPwd(), client2.getRolesId());
        personRepository.savePerson(client3);
        authenticationRepository.addUser(client3.getName(), client3.getEmailAddress().toString(), client3.getPwd(), client3.getRolesId());
        personRepository.savePerson(client4);
        authenticationRepository.addUser(client4.getName(), client4.getEmailAddress().toString(), client4.getPwd(), client4.getRolesId());
        personRepository.savePerson(client5);
        authenticationRepository.addUser(client5.getName(), client5.getEmailAddress().toString(), client5.getPwd(), client5.getRolesId());
        personRepository.savePerson(client6);
        authenticationRepository.addUser(client6.getName(), client6.getEmailAddress().toString(), client6.getPwd(), client6.getRolesId());

    }

    /**
     * Adds user roles to the authentication repository.
     *
     * @param authenticationRepository The authentication repository to add the roles to.
     */
    private void addRoles(AuthenticationRepository authenticationRepository) {
        authenticationRepository.addUserRole(AuthenticationController.ROLE_ADMIN, AuthenticationController.ROLE_ADMIN);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_NETWORK_MANAGER, AuthenticationController.ROLE_NETWORK_MANAGER);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_MANAGER, AuthenticationController.ROLE_MANAGER);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_EMPLOYEE, AuthenticationController.ROLE_EMPLOYEE);
        authenticationRepository.addUserRole(AuthenticationController.ROLE_CLIENT, AuthenticationController.ROLE_CLIENT);
    }

    /**
     * Adds sample agencies to the agency repository.
     */
    private void addAgencies() {
        String emailAgency1 = "agency1@gmail.com";
        String emailAgency2 = "agency2@gmail.com";
        String emailAgency3 = "agency3@gmail.com";
        AgencyRepository agencyRepository = Repositories.getInstance().getAgencyRepository();

        agencyRepository.addAgency(new Agency("agency1", new Address("Rua 1", "Porto", "Porto", "Porto", "12345"), "Agency 1", "911111111", emailAgency1));
        agencyRepository.addAgency(new Agency("agency2", new Address("Rua 2", "Lisbon", "Lisbon", "Lisbon", "12345"), "Agency 2", "922222222", emailAgency2));
        agencyRepository.addAgency(new Agency("agency3", new Address("Rua 3", "Braga", "Braga", "Braga", "12345"), "Agency 3", "933333333", emailAgency3));
    }


    /**
     * Adds announcements to the announcement repository.
     */
    public void addAnnouncement() {
        String[] roles = new String[1];
        roles[0] = AuthenticationController.ROLE_EMPLOYEE;
        Person person1 = new Person("John Doe", 123456789, "123456789", "john.doe@example.com", "123456789", "ss", roles);
        Person person2 = new Person("Jane Smith", 987654321, "123456789", "jane.smith@example.com", "123456789", "ss", roles);
        Person person3 = new Person("Haaland",123123123,"321321321","Haaland@gmail.com","919100055","haaland", new String[] {AuthenticationController.ROLE_CLIENT});
        PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
        AnnouncementRepository announcementRepository1 = Repositories.getInstance().getAnnouncementRepository();
        Date data1 = new Date(2023, 5, 25);
        Date data2 = new Date(2023, 12, 31);
        Date data3 = new Date(2023,8,10);

        Commission commission1 = new Commission(10.5f, true);
        Commission commission2 = new Commission(500, false);

        Address address1 = new Address("Rua A", "Cidade X", "Bairro 1", "Estado A", "12345");
        Address address2 = new Address("Rua B", "Cidade Y", "Bairro 2", "Estado B", "12345");

        String email1 = "example1@example.com";
        String email2 = "example2@example.com";


        Agency agency1 = new Agency("ID1", address1, "Designation 1", "123456789", email1);
        Agency agency2 = new Agency("ID2", address2, "Designation 2", "987654321", email2);


        Rent rent1 = new Rent(2000.0, 12);

        // Creating an instance of sale with a price of 500,000.00 $
        Sale sale1 = new Sale(500000.0);

        // Creating an instance for a land in São Paulo
        Address spAddress = new Address("Rua da Liberdade", "São Paulo", "Liberdade", "SP", "12345");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/sp/land/photo1.jpg");
        Land land1 = new Land(1000.0, 3.5, spPhotos, spAddress, new Sale(90000));

        // Creating an instance for a land in Belo Horizonte
        Address bhAddress = new Address("Rua da Paz", "Belo Horizonte", "Serra", "MG", "12345");
        ArrayList<String> bhPhotos = new ArrayList<>();
        bhPhotos.add("https://example.com/bh/land/photo1.jpg");
        bhPhotos.add("https://example.com/bh/land/photo2.jpg");
        Land land2 = new Land(500.0, 8.2, bhPhotos, bhAddress, new Sale(12500));


        // Creating an instance of a house with basement and habitable attic
        Address house1Address = new Address("Rua das Flores", "São Paulo", "Centro", "SP", "12345");
        ArrayList<String> house1Photos = new ArrayList<>(Arrays.asList("https://example.com/house1/photo2.jpg", "https://example.com/house1/photo3.jpg"));
        ArrayList<String> house1Equipment = new ArrayList<>(Arrays.asList("Air conditioning", "Heater", "Internet"));
        House house1 = new House(200.0, 3.5, house1Photos, house1Address, rent1, 4, 3, 2, house1Equipment, true, true, "North");

        // Creating an instance of a house without basement and habitable attic
        Address house2Address = new Address("Avenida da Praia", "Rio de Janeiro", "Beira Mar", "RJ", "12345");
        ArrayList<String> house2Photos = new ArrayList<>(Arrays.asList("https://example.com/house2/photo1.jpg", "https://example.com/house2/photo2.jpg"));
        ArrayList<String> house2Equipment = new ArrayList<>(Arrays.asList("Internet", "Cable TV"));
        House house2 = new House(150.0, 2.0, house2Photos, house2Address, rent1, 3, 2, 1, house2Equipment, false, false, "East");


        // Creating an instance of an apartment with 2 bedrooms, 1 bathroom, and 1 parking space
        Address apartment1Address = new Address("Avenida Paulista", "São Paulo", "Bela Vista", "SP", "12345");
        ArrayList<String> apartment1Photos = new ArrayList<>(Arrays.asList("https://example.com/apartment1/photo1.jpg", "https://example.com/apartment1/photo2.jpg"));
        ArrayList<String> apartment1Equipment = new ArrayList<>(Arrays.asList("Air conditioning", "Heater", "Cable TV", "Internet"));
        Apartment apartment1 = new Apartment(80.0, 1.5, apartment1Photos, apartment1Address, rent1, 2, 1, 1, apartment1Equipment);

        // Creating an instance of an apartment with 3 bedrooms, 2 bathrooms, and 2 parking spaces
        Address apartment2Address = new Address("Rua Barão de Mesquita", "Rio de Janeiro", "Tijuca", "RJ", "12345");
        ArrayList<String> apartment2Photos = new ArrayList<>(Arrays.asList("https://example.com/apartment2/photo1.jpg", "https://example.com/apartment2/photo2.jpg", "https://example.com/apartment2/photo3.jpg"));
        ArrayList<String> apartment2Equipment = new ArrayList<>(Arrays.asList("Air conditioning", "Heater", "Internet"));
        Apartment apartment2 = new Apartment(120.0, 3.0, apartment2Photos, apartment2Address, sale1, 3, 2, 2, apartment2Equipment);

        Employee employee1 = Utils.getEmployee(new Email("john.doe@example.com"));
        Employee employee2 = Utils.getEmployee(new Email("jane.smith@example.com"));
        Request request1 = new Request(employee1, person1, land1, true);
        Request request2 = new Request(employee2, person2, house1, false);
        Request request3 = new Request(employee1, person1, land2, true);
        Request request4 = new Request(employee2, person2, apartment1, true);
        Request request5 = new Request(employee1, person1, house2, false);
        Request request6 = new Request(employee2, person2, apartment2, false);

        Order order1 = new Order(person1, 999, data1);
        Order order2 = new Order(person2, 500, data2);
        Order order3 = new Order(person3, 390,data3);

        Announcement announcement1 = new Announcement(request1, commission1);
        Announcement announcement2 = new Announcement(request2, commission2);
        Announcement announcement3 = new Announcement(request3, commission2);
        Announcement announcement4 = new Announcement(request4, commission1);
        Announcement announcement5 = new Announcement(request5, commission1);
        Announcement announcement6 = new Announcement(request6, commission2);

        announcement1.addOrder(order1);
        announcement1.addOrder(order2);

        announcement2.addOrder(order1);
        announcement2.addOrder(order2);

        announcement3.addOrder(order3);

        announcementRepository1.saveAnnouncement(announcement1);
        announcementRepository1.saveAnnouncement(announcement2);
        announcementRepository1.saveAnnouncement(announcement3);
        announcementRepository1.saveAnnouncement(announcement4);
        announcementRepository1.saveAnnouncement(announcement5);
        announcementRepository1.saveAnnouncement(announcement6);
    }

    /**
     * Adds properties to the property repository.
     */
    private void addProperty() {
        PropertyRepository propertyRepository = Repositories.getInstance().getPropertyRepository();

        // Creating an instance of rent with a 12-month contract and a rent of 2,000.00 $
        Rent rent1 = new Rent(2000.0, 12);

        // Creating an instance of sale with a price of 500,000.00 $
        Sale sale1 = new Sale(500000.0);

        // Creating an instance for a land in São Paulo
        Address spAddress = new Address("Rua da Liberdade", "São Paulo", "Liberdade", "SP", "12345");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/sp/land/photo1.jpg");
        Land land1 = new Land(1000.0, 3.5, spPhotos, spAddress, new Sale(90000));

        // Creating an instance for a land in Belo Horizonte
        Address bhAddress = new Address("Rua da Paz", "Belo Horizonte", "Serra", "MG", "12345");
        ArrayList<String> bhPhotos = new ArrayList<>();
        bhPhotos.add("https://example.com/bh/land/photo1.jpg");
        bhPhotos.add("https://example.com/bh/land/photo2.jpg");
        Land land2 = new Land(500.0, 8.2, bhPhotos, bhAddress, new Sale(12500));


        // Creating an instance of a house with basement and habitable attic
        Address house1Address = new Address("Rua das Flores", "São Paulo", "Centro", "SP", "12345");
        ArrayList<String> house1Photos = new ArrayList<>(Arrays.asList("https://example.com/house1/photo2.jpg", "https://example.com/house1/photo3.jpg"));
        ArrayList<String> house1Equipment = new ArrayList<>(Arrays.asList("Air conditioning", "Heater", "Internet"));
        House house1 = new House(200.0, 3.5, house1Photos, house1Address, rent1, 4, 3, 2, house1Equipment, true, true, "North");

        // Creating an instance of a house without basement and habitable attic
        Address house2Address = new Address("Avenida da Praia", "Rio de Janeiro", "Beira Mar", "RJ", "12345");
        ArrayList<String> house2Photos = new ArrayList<>(Arrays.asList("https://example.com/house2/photo1.jpg", "https://example.com/house2/photo2.jpg"));
        ArrayList<String> house2Equipment = new ArrayList<>(Arrays.asList("Internet", "Cable TV"));
        House house2 = new House(150.0, 2.0, house2Photos, house2Address, rent1, 3, 2, 1, house2Equipment, false, false, "East");


        // Creating an instance of an apartment with 2 bedrooms, 1 bathroom, and 1 parking space
        Address apartment1Address = new Address("Avenida Paulista", "São Paulo", "Bela Vista", "SP", "12345");
        ArrayList<String> apartment1Photos = new ArrayList<>(Arrays.asList("https://example.com/apartment1/photo1.jpg", "https://example.com/apartment1/photo2.jpg"));
        ArrayList<String> apartment1Equipment = new ArrayList<>(Arrays.asList("Air conditioning", "Heater", "Cable TV", "Internet"));
        Apartment apartment1 = new Apartment(80.0, 1.5, apartment1Photos, apartment1Address, rent1, 2, 1, 1, apartment1Equipment);

        // Creating an instance of an apartment with 3 bedrooms, 2 bathrooms, and 2 parking spaces
        Address apartment2Address = new Address("Rua Barão de Mesquita", "Rio de Janeiro", "Tijuca", "RJ", "12345");
        ArrayList<String> apartment2Photos = new ArrayList<>(Arrays.asList("https://example.com/apartment2/photo1.jpg", "https://example.com/apartment2/photo2.jpg", "https://example.com/apartment2/photo3.jpg"));
        ArrayList<String> apartment2Equipment = new ArrayList<>(Arrays.asList("Air conditioning", "Heater", "Internet"));
        Apartment apartment2 = new Apartment(120.0, 3.0, apartment2Photos, apartment2Address, sale1, 3, 2, 2, apartment2Equipment);

        propertyRepository.saveProperty(land1);
        propertyRepository.saveProperty(land2);
        propertyRepository.saveProperty(house1);
        propertyRepository.saveProperty(house2);
        propertyRepository.saveProperty(apartment1);
        propertyRepository.saveProperty(apartment2);
    }

    /**
     * Deserialize.
     */
    private void deserialize() {
        AnnouncementRepository announcementRepository = Repositories.getInstance().getAnnouncementRepository();
        RequestRepository requestRepository = Repositories.getInstance().getRequestRepository();
        PropertyRepository propertyRepository = Repositories.getInstance().getPropertyRepository();
        AgencyRepository agencyRepository = Repositories.getInstance().getAgencyRepository();
        PersonRepository personRepository = Repositories.getInstance().getPersonRepository();
        VisitRepository visitRepository = Repositories.getInstance().getVisitRepository();

        visitRepository.desserializeVisit();

        announcementRepository.deserializeAnnouncementList();

        requestRepository.deserializeRequest();

        propertyRepository.deserializeProperty();

        agencyRepository.deserializeagencyList();

        personRepository.deserializePerson();

    }

    /**
     * Add requests 2.
     */
    public void addRequests2(){
        RequestRepository requestRepository = Repositories.getInstance().getRequestRepository();
        Person person1 = new Person("Haaland",123123123,"321321321","Haaland@gmail.com","919100055","haaland", new String[] {AuthenticationController.ROLE_CLIENT});
        Person person2 = new Person("DeBruyne",111222333,"333222111","DeBruyne@gmail.com","929200055","debruyne",new String[] {AuthenticationController.ROLE_CLIENT});
        Person person3 = new Person("Bernardo Silva",321321321,"223344559","Bernardo@gmail.com","939300055","bernardo",new String[]{AuthenticationController.ROLE_CLIENT});

        Address address1 = new Address("Manchester Street","Manchester","Manchester","Manchester","32100");
        Address address2 = new Address("Liverpool street","Liverpool","Liverpool","Liverpool","50030");
        Address address3 = new Address("Lisboa Street 38","Lisboa","Lisboa","Lisboa","33442");

        Rent rent1 = new Rent(500,2);
        Sale sale1 = new Sale(1000);
        Rent rent2 = new Rent(231,12);

        Address spAddress = new Address("Rua da Liberdade", "São Paulo", "Liberdade", "SP", "21503");
        ArrayList<String> spPhotos = new ArrayList<>();
        spPhotos.add("https://example.com/sp/land/photo1.jpg");

        Address bhAddress = new Address("Rua da Paz", "Belo Horizonte", "Serra", "MG", "30130");
        ArrayList<String> bhPhotos = new ArrayList<>();
        bhPhotos.add("https://example.com/bh/land/photo1.jpg");
        bhPhotos.add("https://example.com/bh/land/photo2.jpg");

        Address lsAddress = new Address("Rua da Luz nº 38", "Lisboa", "Lisboa", "Lisbon", "21567");

        Property property1 = new Property(250,40,spPhotos, Property.TypeOfProperty.LAND,spAddress,rent1) {
        };
        Property property2 = new Property(100, 25,bhPhotos, Property.TypeOfProperty.LAND,bhAddress,sale1) {
        };
        Property property3 = new Property(200,32,spPhotos, Property.TypeOfProperty.LAND,lsAddress,rent2) {
        };

        Employee employee1 = Utils.getEmployee(new Email("john.doe@example.com"));
        Employee employee2 = Utils.getEmployee(new Email("john.doe@example.com"));
        Employee employee3= Utils.getEmployee(new Email("john.doe@example.com"));


        Request request1 = new Request(employee1,person1,property1,true);
        Request request2 = new Request(employee2,person2,property2,false);
        Request request3 = new Request(employee3,person3,property3,true);

        requestRepository.addRequest(request3);


        requestRepository.addRequest(request2);
        requestRepository.addRequest(request1);
    }

}