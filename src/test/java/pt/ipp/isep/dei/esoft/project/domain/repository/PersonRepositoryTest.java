package pt.ipp.isep.dei.esoft.project.domain.repository;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;

import java.util.ArrayList;
import java.util.List;
//FIXME: Este teste não está a ser feito corretamente. testValidatePerson() não está a testar nada.
class PersonRepositoryTest {

    private static final PersonRepository personRepository = new PersonRepository();
    List<Employee> employeeList = new ArrayList<Employee>();


    Address address1 = new Address("Rua 1","Porto","Porto","Porto","12345");
    Address address2 = new Address("Rua 2","Lisboa","Lisboa","Lisboa","54321");
    Agency agency1 = new Agency("1",address1,"agencia","911111111","teste3@gmail.com");
    Agency agency2 = new Agency("2",address2,"agencia2","92222222","teste4@gmail.com");
//    Employee employee1 = new Employee("João",123456789,987654321,912345678,new Email("teste@gmail.com"),"123",
//            AuthenticationController.ROLE_EMPLOYEE, address1,agency1);
//    Employee employee2 = new Employee("Maria",111222333,333222111,987654321,new Email("teste2@gmail.com"),"123",
//            AuthenticationController.ROLE_EMPLOYEE, address2,agency2);


//    @Test
//    public void testValidatePerson(){
//        employeeList.add(employee1);
//        employeeList.add(employee2);
//
//        boolean validate1 = personRepository.validatePerson(null);
//
//        assertFalse(validate1);
//
//    }
    }



