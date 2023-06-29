package pt.ipp.isep.dei.esoft.project.domain.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

/**
 * The Time class represents a specific time of day.
 * It implements the Comparable interface to allow comparison between different instances of Time.
 */
public class Time implements Comparable<Time>, Serializable {

    /**
     * The hours of the Time.
     */
    private int hours;

    /**
     * The minutes of the Time.
     */
    private int minutes;

    /**
     * The seconds of the Time.
     */
    private int seconds;

    /**
     * The default value for hours.
     */
    private static final int DEFAULT_HOURS = 0;

    /**
     * The default value for minutes.
     */
    private static final int DEFAULT_MINUTES = 0;

    /**
     * The default value for seconds.
     */
    private static final int DEFAULT_SECONDS = 0;

    /**
     * Constructs an instance of Time with the specified hours, minutes, and seconds.
     *
     * @param hours   the hours of the Time.
     * @param minutes the minutes of the Time.
     * @param seconds the seconds of the Time.
     */
    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Constructs an instance of Time with the specified hours and minutes, assuming the seconds as the default value.
     *
     * @param hours   the hours of the Time.
     * @param minutes the minutes of the Time.
     */
    public Time(int hours, int minutes) {
        this.hours = hours;
        this.minutes = minutes;
        seconds = DEFAULT_SECONDS;
    }

    /**
     * Constructs an instance of Time with the specified hours, assuming the minutes and seconds as the default values.
     *
     * @param hours the hours of the Time.
     */
    public Time(int hours) {
        this.hours = hours;
        minutes = DEFAULT_MINUTES;
        seconds = DEFAULT_SECONDS;
    }

    /**
     * Constructs an instance of Time with the default values for hours, minutes, and seconds.
     */
    public Time() {
        hours = DEFAULT_HOURS;
        minutes = DEFAULT_MINUTES;
        seconds = DEFAULT_SECONDS;
    }

    /**
     * Constructs an instance of Time with the same characteristics as the given Time.
     *
     * @param otherTime the Time to copy the characteristics from.
     */
    public Time(Time otherTime) {
        hours = otherTime.hours;
        minutes = otherTime.minutes;
        seconds = otherTime.seconds;
    }

    /**
     * Returns the hours of the Time.
     *
     * @return the hours of the Time.
     */
    public int getHours() {
        return hours;
    }

    /**
     * Returns the minutes of the Time.
     *
     * @return the minutes of the Time.
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Returns the seconds of the Time.
     *
     * @return the seconds of the Time.
     */
    public int getSeconds() {
        return seconds;
    }

    /**
     * Modifies the hours of the Time.
     *
     * @param hours the new hours of the Time.
     */
    public void setHours(int hours) {
        this.hours = hours;
    }

    /**
     * Modifies the minutes of the Time.
     *
     * @param minutes the new minutes of the Time.
     */
    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Modifies the seconds of the Time.
     *
     * @param seconds the new seconds of the Time.
     */
    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Modifies the hours, minutes, and seconds of the Time.
     *
     * @param hours   the new hours of the Time.
     * @param minutes the new minutes of the Time.
     * @param seconds the new seconds of the Time.
     */
    public void setTime(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * Returns the textual description of the Time in the format: %02d:%02d:%02d AM/PM.
     *
     * @return the characteristics of the Time.
     */
    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d %s",
                (hours == 12 || hours == 0) ? 12 : hours % 12,
                minutes, seconds, hours < 12 ? "AM" : "PM");
    }

    /**
     * Returns the Time in the format: %02d:%02d:%02d.
     *
     * @return the characteristics of the Time.
     */
    public String toStringHHMMSS() {
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    /**
     * Compares the Time with the given object.
     *
     * @param anotherObject the object to compare with the Time.
     * @return true if the given object represents another Time equivalent to the Time, false otherwise.
     */
    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (anotherObject == null || getClass() != anotherObject.getClass()) {
            return false;
        }
        Time anotherTime = (Time) anotherObject;
        return hours == anotherTime.hours && minutes == anotherTime.minutes
                && seconds == anotherTime.seconds;
    }

    /**
     * Compares the Time with the other Time received as a parameter.
     *
     * @param anotherTime the Time to be compared.
     * @return the value 0 if the received anotherTime is equal to the Time; the value -1 if the received anotherTime is later than the Time; the value 1 if the received anotherTime is earlier than the Time.
     */
    @Override
    public int compareTo(Time anotherTime) {
        return (anotherTime.isGreater(this)) ? -1 : (isGreater(anotherTime)) ? 1 : 0;
    }

    /**
     * Increases the Time by one second.
     */
    public void tick() {
        seconds = ++seconds % 60;
        if (seconds == 0) {
            minutes = ++minutes % 60;
            if (minutes == 0) {
                hours = ++hours % 24;
            }
        }
    }

    /**
     * Returns true if the Time is greater than the Time received as a parameter.
     * Returns false if the Time is less than or equal to the Time received as a parameter.
     *
     * @param anotherTime the other Time to compare with the Time.
     * @return true if the Time is greater than the Time received as a parameter, false otherwise.
     */
    public boolean isGreater(Time anotherTime) {
        return toSeconds() > anotherTime.toSeconds();
    }

    /**
     * Returns true if the Time is greater than the Time (hours, minutes, and
     * seconds) received as parameters. Returns false if the Time is less than or equal to
     * the Time (hours, minutes, and seconds) received as parameters.
     *
     * @param hours   the other hours of the Time to compare with.
     * @param minutes the other minutes of the Time to compare with.
     * @param seconds the other seconds of the Time to compare with.
     * @return true if the Time is greater than the Time (hours, minutes, and seconds) received as parameters, false otherwise.
     */
    public boolean isGreater(int hours, int minutes, int seconds) {
        Time anotherTime = new Time(hours, minutes, seconds);
        return this.toSeconds() > anotherTime.toSeconds();
    }

    /**
     * Returns the difference in seconds between the Time and the Time received as
     * a parameter.
     *
     * @param anotherTime the other Time to compare with in order to calculate the difference in seconds.
     * @return the difference in seconds between the Time and the Time received as a parameter.
     */
    public int differenceInSeconds(Time anotherTime) {
        return Math.abs(toSeconds() - anotherTime.toSeconds());
    }

    /**
     * Returns a Time instance representing the difference between the Time and
     * the Time received as a parameter.
     *
     * @param anotherTime the other Time to compare with in order to obtain a Time instance representing                    the difference between the Time and the Time received as a parameter.
     * @return a Time instance representing the difference between the Time and the Time received as a parameter.
     */
    public Time differenceInTime(Time anotherTime) {
        int diff = differenceInSeconds(anotherTime);
        int s = diff % 60;
        diff = diff / 60;
        int m = diff % 60;
        int h = diff / 60;
        return new Time(h, m, s);
    }

    /**
     * Returns the current Time of the system.
     *
     * @return the current Time of the system.
     */
    public static Time currentTime() {
        Calendar now = Calendar.getInstance();
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int minute = now.get(Calendar.MINUTE);
        int second = now.get(Calendar.SECOND);
        return new Time(hour, minute, second);
    }

    /**
     * Returns the number of seconds corresponding to the Time.
     *
     * @return the number of seconds corresponding to the Time.
     */
    private int toSeconds() {
        return hours * 3600 + minutes * 60 + seconds;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes, seconds);
    }
}