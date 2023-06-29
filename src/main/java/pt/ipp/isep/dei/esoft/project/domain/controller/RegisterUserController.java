package pt.ipp.isep.dei.esoft.project.domain.controller;

import org.apache.commons.lang3.RandomStringUtils;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.PersonRepository;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * The type Register user controller.
 */
public class RegisterUserController {

    /**
     * The Authentication repository.
     */
    private final AuthenticationRepository authenticationRepository;
    /**
     * The Person repository.
     */
    private final PersonRepository personRepository;

    /**
     * Instantiates a new Register user controller.
     */
    public RegisterUserController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        this.personRepository = Repositories.getInstance().getPersonRepository();
    }

    /**
     * Register user boolean.
     *
     * @param person   the person
     * @param password the password
     * @param role     the role
     * @return the boolean
     */
//TODO: check for redundancy vs saveUser
    public Boolean registerUser(Person person, String password, String[] role) {
        if (authenticationRepository.getUser(person.getEmailAddress()).isPresent()) return false;
        if (!authenticationRepository.addUser(person.getName(), person.getEmailAddress().getEmail(), password, role))
            return false;
        return personRepository.savePerson(person);
    }

    /**
     * Save user boolean.
     *
     * @param person   the person
     * @param password the password
     * @return the boolean
     */
    public boolean saveUser(Person person, String password) {
        if (personRepository.savePerson(person)) {
            return authenticationRepository.addUser(person.getName(), person.getEmailAddress().getEmail(), password, person.getRolesId());
        }
        return false;
    }

    /**
     * Generate password string.
     *
     * @return the string
     */
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
     * Confirm password boolean.
     *
     * @param password the password
     * @return the boolean
     */
    public  boolean confirmPassword(String password) {
        boolean isValid= false;
        int countUpper = 0;
        int countNumbers = 0;
        if (password.length() != 7) {
            isValid=false;
        }

        for (int i = 0; i < password.length(); i++) {
        char ch = password.charAt(i);
        if(Character.isDigit(ch)) {
            countNumbers++;
        }
        if(Character.isUpperCase(ch)) {
            countUpper++;
        }
        }

        if(countNumbers>=2 && countUpper>=3){
            isValid=true;
        }
    return isValid;
    }
}