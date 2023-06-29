package pt.ipp.isep.dei.esoft.project.domain.model;


import org.apache.commons.lang3.StringUtils;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.Serializable;
import java.util.Objects;

/**
 * The type Visit.
 */
public class Visit implements Serializable {

    /**
     * The Name.
     */
    private String name;
    /**
     * The Phone number.
     */
    private String phoneNumber;
    /**
     * The Email.
     */
    private final String email;
    /**
     * The Date.
     */
    private Date date;
    /**
     * The Start time.
     */
    private Time startTime, /**
     * The End time.
     */
    endTime;
    /**
     * The Chosen announcement.
     */
    private final Announcement chosenAnnouncement;

    /**
     * Instantiates a new Visit.
     *
     * @param name               the name
     * @param phoneNumber        the phone number
     * @param date               the date
     * @param startTime          the start time
     * @param endTime            the end time
     * @param email              the email
     * @param chosenAnnouncement the chosen announcement
     */
    public Visit(String name, String phoneNumber, Date date, Time startTime, Time endTime,String email, Announcement chosenAnnouncement) {

        checkName(name);

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.chosenAnnouncement = chosenAnnouncement;
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
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * Sets phone number.
     *
     * @param phoneNumber the phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * Gets start time.
     *
     * @return the start time
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public Email getEmail() {
        return new Email(email);
    }

    /**
     * Sets start time.
     *
     * @param startTime the start time
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets end time.
     *
     * @return the end time
     */
    public Time getEndTime() {
        return endTime;
    }

    /**
     * Sets end time.
     *
     * @param endTime the end time
     */
    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    /**
     * Method to get the announcement referent to the visit
     *
     * @return chosen announcement
     */
    public Announcement getChosenAnnouncement() {
        return chosenAnnouncement;
    }

    /**
     * Check name.
     *
     * @param name the name
     */
    private void checkName(String name) {
        if (StringUtils.isBlank(name)) {
            throw new IllegalArgumentException("The name can't be blank");
        }
    }

    /**
     * This function checks if the variable passportNumber is 9 digits.
     *
     * @param phoneNumber the variable passportNumber holds the citizen card number of the person
     */
    private void checkphoneNumber(int phoneNumber) {
        if (phoneNumber < 100000000 || phoneNumber > 999999999) {
            throw new IllegalArgumentException("The phone number must be 9 digits");
        }
    }


    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "\nVisit: " +
                "\n     -> Name: " + name +
                "\n     -> PhoneNumber: " + phoneNumber +
                "\n     -> Date: " + date +
                "\n     -> StartTime: " + startTime +
                "\n     -> EndTime: " + endTime +
                "\n     -> chosenAnnouncement:\n " + chosenAnnouncement;
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
        Visit visit = (Visit) o;
        return Objects.equals(name, visit.name) && Objects.equals(phoneNumber, visit.phoneNumber) && Objects.equals(email, visit.email) && Objects.equals(date, visit.date) && Objects.equals(startTime, visit.startTime) && Objects.equals(endTime, visit.endTime) && Objects.equals(chosenAnnouncement, visit.chosenAnnouncement);
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(name, phoneNumber, email, date, startTime, endTime, chosenAnnouncement);
    }
}
