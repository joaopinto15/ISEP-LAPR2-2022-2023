package pt.ipp.isep.dei.esoft.project.domain.model.agency;


import org.apache.commons.lang3.StringUtils;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * The type Person.
 */
public class Person implements Serializable {
    /**
     * this variable name holds the name of the person
     */
    private final String name;
    /**
     * the variable passportNumber holds the passport number of the person
     */
    private final int passportNumber;
    /**
     * The variable taxNumber holds the tax number of the person
     */
    private final String taxNumber;
    /**
     * The variable emailAddress holds the address of the person
     */
    private final String emailAddress;
    /**
     * The variable phoneNumber holds the phone number of the person
     */
    private final String phoneNumber;

    /**
     * The variable pwd holds the password of the user
     */
    private final String pwd;
    /**
     * The Roles id.
     */
    private final String[] rolesId;


    /**
     * This function creates an instance receiving name, passportNumber, taxNumber, emailAddress, phoneNumber and address as parameters
     *
     * @param name           the variable name holds the name of the person
     * @param passportNumber the variable passportNumber holds the citizen card number of the person
     * @param taxNumber      the variable taxNumber holds the tax number of the person
     * @param emailAddress   the variable emailAddress holds the name of the person
     * @param phoneNumber    the variable phoneNumber holds the phone number of the person
     * @param pwd            the password
     * @param rolesId        the ids of the roles
     */
    public Person(String name, int passportNumber, String taxNumber, String emailAddress, String phoneNumber, String pwd, String[] rolesId) {
        checkName(name);
        checkpassportNumber(passportNumber);
        checkEmailAddress(String.valueOf(emailAddress));


        this.name = name;
        this.passportNumber = passportNumber;
        this.taxNumber = taxNumber;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.pwd = pwd;
        this.rolesId=rolesId;

    }

    /**
     * Instantiates a new Person.
     *
     * @param rolesId the roles id
     */
    public Person(String rolesId){
        this.name = "Legacy Agent";
        this.passportNumber = 000000000;
        this.taxNumber = "000000000";
        this.emailAddress = "legacy@realstateUSA.com";
        this.phoneNumber = "000000000";
        this.pwd = "pwd";
        this.rolesId = new String[1];
        this.rolesId[0] = rolesId;

    }

    /**
     * Instantiates a new Person.
     *
     * @param name           the name
     * @param passportNumber the passport number
     * @param taxNumber      the tax number
     * @param emailAddress   the email address
     * @param phoneNumber    the phone number
     */
    public Person(String name, int passportNumber, String taxNumber, String emailAddress, String phoneNumber){
        this.name = name;
        this.passportNumber = passportNumber;
        this.taxNumber = taxNumber;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.pwd = "pwd";
        this.rolesId = new String[1];
        this.rolesId[0] = "CLIENT";

    }

    /**
     * This function creates an instance of Person with the same parameters of Person
     *
     * @param otherPerson the person with the parameters to copy
     */
    public Person(Person otherPerson){
        name = otherPerson.name;
        passportNumber = otherPerson.passportNumber;
        taxNumber = otherPerson.taxNumber;
        emailAddress = otherPerson.emailAddress;
        phoneNumber = otherPerson.phoneNumber;
        pwd = otherPerson.pwd;
        rolesId= otherPerson.rolesId;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Get roles id string [ ].
     *
     * @return the string [ ]
     */
    public String[] getRolesId() {
        return rolesId;
    }

    /**
     * Method to return the Person's citizen card number
     *
     * @return the citizen card number of the person
     */
    public int getPassportNumber() {
        return passportNumber;
    }

    /**
     * Method to return the tax number of the person
     *
     * @return the tax number of the person
     */
    public String getTaxNumber() {
        return taxNumber;
    }

    /**
     * Gets pwd.
     *
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }

    /**
     * method to return the Person's email address
     *
     * @return the email address of the person
     */
    public Email getEmailAddress() {
        return new Email(emailAddress);
    }

    /**
     * method to return the Person's phone number
     *
     * @return the phone number of the person
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }


    /**
     * This function checks if the variable name is null.
     *
     * @param name the variable name holds the name of the person.
     */
    private void checkName(String name){
        if(StringUtils.isBlank(name)){
            throw new IllegalArgumentException("The name can't be blank");
        }
    }

    /**
     * This function checks if the variable passportNumber is 9 digits.
     *
     * @param passportNumber the variable passportNumber holds the citizen card number of the person
     */
    private void checkpassportNumber(int passportNumber){
        if(passportNumber<100000000 || passportNumber>999999999){
            throw new IllegalArgumentException("The citizen card number must be 9 digits");
        }
    }


    /**
     * this function checks if the variable emailAddress is null
     *
     * @param emailAddress holds the email address of the person
     */
    private void checkEmailAddress(String emailAddress){
        if(StringUtils.isBlank(emailAddress)){
            throw new IllegalArgumentException("The email address can't be blank");
        }
    }


    /**
     * This function constructs and returns a String representative of the object
     *
     * @return a string representing the object
     */
    @Override
    public String toString() {
        return name+":" +"\n"+
                "\n     -> Passport Number: " + passportNumber +
                "\n     -> Tax number: " + taxNumber +
                "\n     -> Email address: " + emailAddress +
                "\n     -> Phone number: " + phoneNumber ;
        
    }


    /**
     * Equals boolean.
     *
     * @param o the o
     * @return the boolean
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return passportNumber == person.passportNumber && Objects.equals(name, person.name) && Objects.equals(taxNumber, person.taxNumber) && Objects.equals(emailAddress, person.emailAddress) && Objects.equals(phoneNumber, person.phoneNumber) && Objects.equals(pwd, person.pwd) && Arrays.equals(rolesId, person.rolesId);
    }


    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        int result = Objects.hash(name, passportNumber, taxNumber, emailAddress, phoneNumber, pwd);
        result = 31 * result + Arrays.hashCode(rolesId);
        return result;
    }
}

