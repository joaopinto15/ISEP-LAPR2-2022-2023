package pt.ipp.isep.dei.esoft.project.domain.model.agency;


import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Request.
 */
public class Request implements Serializable {

    /**
     * the variable employee holds the employee of the Request
     */
    private final Employee employee;

    /**
     * the variable user holds the user of the request
     */
    private final Person person;

    /**
     * the variable property holds the property of the Request
     */
    private final Property property;

    /**
     * the variable isPending holds the user of the request
     */
    private final boolean isPending;
    /**
     * this variable date holds the date of the request
     */
    private final Date date;

    /**
     * This function creates an instance receiving employee, user, property, rent as parameters
     *
     * @param employee  the variable employee holds the employee of the Request
     * @param person    the variable user holds the user of the Request
     * @param property  the variable property holds the property of the Request
     * @param isPending the is pending
     */
    public Request(Employee employee, Person person, Property property, boolean isPending) {
        this.employee = employee;
        this.person = person;
        this.property = property;
        this.isPending = isPending;
        this.date = Date.currentDate();
    }

    /**
     * Instantiates a new Request.
     *
     * @param employee  the employee
     * @param person    the person
     * @param property  the property
     * @param isPending the is pending
     * @param date      the date
     */
    public Request(Employee employee, Person person, Property property, boolean isPending,Date date) {
        this.employee = employee;
        this.person = person;
        this.property = property;
        this.isPending = isPending;
        this.date = date;
    }

    /**
     * This function creates an instance of Request with the same parameters as Request
     *
     * @param otherRequest the Request with the parameters to copy
     */
    public Request(Request otherRequest) {
        this.employee = otherRequest.employee;
        this.person = otherRequest.person;
        this.property = otherRequest.property;
        this.isPending = otherRequest.isPending;
        this.date = otherRequest.date;
    }

    /**
     * Method to return the employee of the Request
     *
     * @return the employee of the Request
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Method to return the property of the Request
     *
     * @return the property of the Request
     */
    public Property getProperty() {
        return property;
    }

    /**
     * Method to return the user of the Request
     *
     * @return the user of the Request
     */
    public Person getUser() {
        return person;
    }

    /**
     * Method to return the date of the Request
     *
     * @return the date of the request
     */
    public Date getDate() {
        return date;
    }

    /**
     * Method to return the isPending of the Request
     *
     * @return the isPending of the Request
     */
    public boolean isPending() {
        return isPending;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "Request for " + employee.getName() + " from " + person.getName() + " for the following property: \n" + property + "\n";
    }

    /**
     * Checks if this House object is equal to another object.
     *
     * @param otherRequest the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object otherRequest) {
        if (this == otherRequest) return true;
        if (otherRequest == null || getClass() != otherRequest.getClass()) return false;
        Request request = (Request) otherRequest;
        return isPending == request.isPending && Objects.equals(employee, request.employee) && Objects.equals(person, request.person) && Objects.equals(property, request.property) && Objects.equals(date, request.date);
    }
}