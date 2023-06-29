package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import pt.ipp.isep.dei.esoft.project.domain.model.Address;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Employee.
 */
public class Employee extends Person implements Serializable {
    /**
     * this variable holds the address of the employee
     */
    private final Address address;

    /**
     * this variable holds the agency of the employee
     */
    private final String agencyId;

    /**
     * builds an instance of Employee receiving person, address and role as parameters
     *
     * @param name           holds the name of the employee
     * @param passportNumber holds the citizen card number of the employee
     * @param taxNumber      holds the tax number of the employee
     * @param phoneNumber    holds the phone number of the employee
     * @param emailAddress   holds the email address of the employee
     * @param pwd            holds the password of the employee
     * @param roleId         holds the role id of the employee
     * @param address        holds the address of the employee
     * @param agencyId       holds the agency of the employee
     */
    public Employee(String name, int passportNumber, String taxNumber, String phoneNumber, String emailAddress, String pwd, String[] roleId, Address address, String agencyId) {
        super(name, passportNumber, taxNumber, emailAddress, phoneNumber, pwd, roleId);
        this.address = address;
        this.agencyId = agencyId;
    }

    /**
     * Instantiates a new Employee.
     *
     * @param rolesId  the roles id
     * @param address  the address
     * @param agencyId the agency id
     */
    public Employee(String rolesId,Address address, String agencyId) {
        super(rolesId);
        this.address = address;
        this.agencyId = agencyId;

    }

    /**
     * Method to return the employee's address
     *
     * @return the address of the employee
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Method to return the employee's agency
     *
     * @return the agency of the employee
     */
    public String getAgencyId() {
        return agencyId;
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
        if (!super.equals(o)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(address, employee.address) && Objects.equals(agencyId, employee.agencyId);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), address, agencyId);
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return getName()+":" +"\n"+
                "\n     -> Email address: " + getEmailAddress() +
                "\n     -> Phone number: " + getPhoneNumber() +
                "\n     -> Agency: " + agencyId;
    }



}

