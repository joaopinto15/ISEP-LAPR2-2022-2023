package pt.ipp.isep.dei.esoft.project.domain.controller;

import org.apache.commons.lang3.RandomStringUtils;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.repository.AgencyRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.PersonRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Register employee controller.
 */
public class RegisterEmployeeController {

    /**
     * Initiates the instance repositories of the class Repositories
     */
    private final Repositories repositories;
    /**
     * Initiates the agencies Array List of the class agency
     */
    private ArrayList<Agency> agencies;
    /**
     * Initiates the instance agencyRepository of the class AgencyRepository
     */
    private final AuthenticationRepository authenticationRepository=Repositories.getInstance().getAuthenticationRepository();
    /**
     * The Agency repository.
     */
    private AgencyRepository agencyRepository = Repositories.getInstance().getAgencyRepository();
    /**
     * The Person repository.
     */
    private final PersonRepository personRepository = Repositories.getInstance().getPersonRepository();

    /**
     * Builds an instance of RegisterEmployeeController
     */
    public RegisterEmployeeController(){
      this.repositories = Repositories.getInstance();
      this.agencies = new ArrayList<>();
   }

    /**
     * Method to return a list of the agencies
     *
     * @return agency list
     */
    public ArrayList<Agency> getAgencies(){
      this.agencyRepository = repositories.getAgencyRepository();
      this.agencies = this.agencyRepository.getAgencyList();
      return agencies;
   }

    /**
     * method to create a new instance of employee
     *
     * @param employee the employee
     * @param password the password
     * @return a new employee
     */
    public boolean registerEmployee (Employee employee,String password){
        if (personRepository.savePerson(employee)){
            return authenticationRepository.addUser(employee.getName(), employee.getEmailAddress(), password, AuthenticationController.ROLE_EMPLOYEE);
        }
         return false;
   }

    /**
     * Generate password string.
     *
     * @return the string
     */
//TODO: Look into merging with RegisterUserController's generatePassword method
    public String generatePassword() {
        /*
         * Generate a string of 3 upper case letters
         */
        String upperCaseStr = RandomStringUtils.random(3, 65, 90, true, true);
        /*
         * Generate a string of 2 numeric letters
         */
        String numbersStr = RandomStringUtils.randomNumeric(2);
        /*
         * generate a string of 2 alphanumeric letters
         */
        String totalCharsStr = RandomStringUtils.randomAlphanumeric(2);
        /*
         * puts together all the strings into one single string
         */
        String demoPassword = upperCaseStr.concat(numbersStr).concat(totalCharsStr);
        /*
         * creates a list of char that stores all the characters, numbers and special characters
         */
        List<Character> listOfChar = demoPassword.chars().mapToObj(data -> (char) data).collect(Collectors.toList());
        /*
         * shuffle the elements in the list
         */
        Collections.shuffle(listOfChar);
        /*
         * generate a random String (secure password) by using list stream() method and collect() method
         */
        return listOfChar.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }


    /**
     * Method to set an employee as the agency manager
     *
     * @param agencyId the agency id
     * @param employee the employee
     */
    public void setManager(String agencyId, Employee employee){
      agencyRepository.getAgency(agencyId).setManager(employee);
   }

}
