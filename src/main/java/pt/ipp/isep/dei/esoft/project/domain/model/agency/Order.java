package pt.ipp.isep.dei.esoft.project.domain.model.agency;

import pt.ipp.isep.dei.esoft.project.domain.model.Date;

import java.io.Serializable;
import java.util.Objects;

/**
 * Represents an order made by a person, including the person's information, price, and date.
 */
public class Order implements Serializable {
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
     * The Verification
     */
    private boolean accepted;

    /**
     * Constructs a new Order object with the specified person, price, date, verification.
     *
     * @param person The person who made the order.
     * @param price  The price of the order.
     * @param date   The date when the order was made.
     */
    public Order(Person person, double price, Date date) {
        this.person = person;
        this.price = price;
        this.date = date;
        this.accepted = false;
    }

    /**
     * Instantiates a new Order.
     *
     * @param person   the person
     * @param price    the price
     * @param date     the date
     * @param accepted the accepted
     */
    public Order(Person person, double price, Date date, boolean accepted) {
        this.person = person;
        this.price = price;
        this.date = date;
        this.accepted = accepted;
    }

    /**
     * Constructs a new Order object by copying the values from another Order object.
     *
     * @param otherOrder The other Order object to copy from.
     */
    public Order(Order otherOrder) {
        this.person = otherOrder.person;
        this.price = otherOrder.price;
        this.date = otherOrder.date;
        this.accepted = otherOrder.accepted;
    }

    /**
     * Returns the person who made the order.
     *
     * @return The person who made the order.
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Returns the price of the order.
     *
     * @return The price of the order.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Returns the date when the order was made.
     *
     * @return The date when the order was made.
     */
    public Date getDate() {
        return date;
    }

    /**
     * Returns the validation.
     *
     * @return The Accepted validation.
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * Sets the value of the "accepted" flag.
     *
     * @param accepted the new value for the "accepted" flag
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    /**
     * Returns a string representation of the Order object.
     *
     * @return A string representation of the Order object.
     */
    @Override
    public String toString() {
        return person +
                "\n   Price:" + price + "$" +
                "\n   Date:" + date;
    }

    /**
     * Checks if this Order object is equal to another object.
     *
     * @param o The object to compare for equality.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(order.price, price) == 0 && accepted == order.accepted && Objects.equals(person, order.person) && Objects.equals(date, order.date);
    }

    /**
     * Compares this Order object with another Order object for ordering purposes.
     *
     * @param otherOrder The other Order object to compare.
     * @return A negative integer if this Order is less than the other Order,         a positive integer if this Order is greater than the other Order,         or zero if they are equal.
     */
    public int compareTo(Order otherOrder) {
        return (otherOrder.isBigger(this)) ? -1 : (this.isBigger(otherOrder)) ? 1 : 0;
    }

    /**
     * Checks if this Order object has a greater price than the other Order object.
     *
     * @param otherOrder The other Order object to compare.
     * @return True if this Order has a greater price, false otherwise.
     */
    public boolean isBigger(Order otherOrder) {
        return this.price > otherOrder.price;
    }


}
