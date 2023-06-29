package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;

import java.io.Serializable;


/**
 * The RequestDTO class represents a Data Transfer Object (DTO) for a request's information
 */
public class RequestDTO implements Serializable {

    /**
     * The Employee.
     */
    private final Employee employee;

    /**
     * The Person.
     */
    private final Person person;

    /**
     * The Property.
     */
    private final Property property;

    /**
     * The Is pending.
     */
    private final boolean isPending;

    /**
     * The Date.
     */
    private final Date date;

    /**
     * Constructs a RequestDTO object with the information
     *
     * @param employee  the employee responsible for the request
     * @param person    the person that made the request
     * @param property  the property in the request
     * @param isPending the status of the request
     * @param date      the date of the request
     */
    public RequestDTO(Employee employee, Person person, Property property, boolean isPending, Date date) {
        this.employee = employee;
        this.person = person;
        this.property = property;
        this.isPending = isPending;
        this.date = date;
    }


    /**
     * method to get the employee responsible for the request
     *
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * method to get the person that made the request
     *
     * @return the person
     */
    public Person getPerson() {
        return person;
    }

    /**
     * method to get the property of the request
     *
     * @return the property
     */
    public Property getProperty() {
        return property;
    }

    /**
     * method to return the status of the request
     *
     * @return the status
     */
    public boolean isPending() {
        return isPending;
    }

    /**
     * method to return the date of the request
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * To string string.
     *
     * @return the string with the information of the request
     */
    @Override
    public String toString() {
        return "RequestDTO{" +
                "employee=" + employee +
                ", person=" + person +
                ", property=" + property +
                ", isPending=" + isPending +
                ", date=" + date +
                '}';
    }
}
