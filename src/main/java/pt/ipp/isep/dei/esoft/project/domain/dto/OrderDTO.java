package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;

import java.io.Serializable;

/**
 * The OrderDTO class represents an order with information about the person placing the order,
 * the price of the order, the date of the order, and whether the order has been accepted.
 */
public class OrderDTO implements Serializable {

    /**
     * The Person.
     */
    private final Person person;
    /**
     * The Price.
     */
    private final double price;
    /**
     * The Date.
     */
    private final Date date;
    /**
     * The Accepted.
     */
    private final boolean accepted;

    /**
     * Constructs an OrderDTO object with the specified person, price, and date.
     * The accepted field is set to false by default.
     *
     * @param person the person placing the order
     * @param price  the price of the order
     * @param date   the date of the order
     */
    public OrderDTO(Person person, double price, Date date) {
        this.person = person;
        this.price = price;
        this.date = date;
        this.accepted = false;
    }

    /**
     * Constructs an OrderDTO object with the specified person, price, date, and accepted status.
     *
     * @param person   the person placing the order
     * @param price    the price of the order
     * @param date     the date of the order
     * @param accepted whether the order has been accepted or not
     */
    public OrderDTO(Person person, double price, Date date, boolean accepted) {
        this.person = person;
        this.price = price;
        this.date = date;
        this.accepted = accepted;
    }

    /**
     * Returns the person placing the order.
     *
     * @return the person placing the order
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Returns the price of the order.
     *
     * @return the price of the order
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the date of the order.
     *
     * @return the date of the order
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns whether the order has been accepted or not.
     *
     * @return true if the order has been accepted, false otherwise
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Returns a string representation of the OrderDTO object.
     *
     * @return a string representation of the object
     */
    @Override
    public String toString() {
        return person +
                "\n   Price:" + price + "$" +
                "\n   Date:" + date;
    }
}