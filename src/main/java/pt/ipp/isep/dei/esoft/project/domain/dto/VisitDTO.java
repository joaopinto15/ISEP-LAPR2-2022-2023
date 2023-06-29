package pt.ipp.isep.dei.esoft.project.domain.dto;

import pt.ipp.isep.dei.esoft.project.domain.model.Date;
import pt.ipp.isep.dei.esoft.project.domain.model.Time;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;

import java.io.Serializable;

/**
 * The type Visit dto.
 */
public class VisitDTO implements Serializable {

    /**
     * The Name.
     */
    private final String name;

    /**
     * The Phone number.
     */
    private final String phoneNumber;

    /**
     * The Date.
     */
    private final Date date;

    /**
     * The Email.
     */
    private final String email;

    /**
     * The Start time.
     */
    private final Time startTime;

    /**
     * The End time.
     */
    private final Time endTime;

    /**
     * The Chosen announcement.
     */
    private final Announcement chosenAnnouncement;

    /**
     * constructs a VisitDTO with the specific information
     *
     * @param name               the name
     * @param email              the email
     * @param phoneNumber        the phone number
     * @param date               the date
     * @param startTime          the start time
     * @param endTime            the end time
     * @param chosenAnnouncement the chosen announcement
     */
    public VisitDTO(String name,String email, String phoneNumber, Date date, Time startTime, Time endTime, Announcement chosenAnnouncement) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
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
     * Gets phone number.
     *
     * @return the phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
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
     * Gets start time.
     *
     * @return the start time
     */
    public Time getStartTime() {
        return startTime;
    }

    /**
     * Get email string.
     *
     * @return the string
     */
    public String getEmail(){
        return email;
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
     * Gets chosen announcement.
     *
     * @return the chosen announcement
     */
    public Announcement getChosenAnnouncement() {
        return chosenAnnouncement;
    }

    /**
     * To string string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "VisitDTO{" +
                "name='" + name + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", chosenAnnouncement=" + chosenAnnouncement +
                '}';
    }
}
