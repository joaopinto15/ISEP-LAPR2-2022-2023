package pt.ipp.isep.dei.esoft.project.domain.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Objects;

/**
 * The Date class represents a specific date in time.
 * It implements the Comparable interface to allow comparison between dates.
 */
public class Date implements Comparable<Date> , Serializable {

    /**
     * The year of the date.
     */
    private int year;

    /**
     * The month of the date.
     */
    private Month month;

    /**
     * The day of the date.
     */
    private int day;

    /**
     * The default year.
     */
    private static final int DEFAULT_YEAR = 1;

    /**
     * The default month.
     */
    private static final Month DEFAULT_MONTH = Month.JANUARY;

    /**
     * The default day.
     */
    private static final int DEFAULT_DAY = 1;

    /**
     * Represents the days of the week.
     */
    private enum DayOfWeek implements Serializable {

        /**
         * The days of the week.
         */
        SUNDAY {
            @Override
            public String toString() {
                return "Sunday";
            }
        },
        /**
         * Monday.
         */
        MONDAY {
            @Override
            public String toString() {
                return "Monday";
            }
        },
        /**
         * Tuesday.
         */
        TUESDAY {
            @Override
            public String toString() {
                return "Tuesday";
            }
        },
        /**
         * Wednesday.
         */
        WEDNESDAY {
            @Override
            public String toString() {
                return "Wednesday";
            }
        },
        /**
         * Thursday.
         */
        THURSDAY {
            @Override
            public String toString() {
                return "Thursday";
            }
        },
        /**
         * Friday.
         */
        FRIDAY {
            @Override
            public String toString() {
                return "Friday";
            }
        },
        /**
         * Saturday.
         */
        SATURDAY {
            @Override
            public String toString() {
                return "Saturday";
            }
        };

        /**
         * Returns the name of the day of the week based on the received order.
         *
         * @param dayOfWeekOrder the order of the day of the week, ranging from zero to six inclusive. The smallest order corresponds to Sunday.
         * @return the name of the day of the week.
         */
        public static String getDayOfWeekName(int dayOfWeekOrder) {
            return DayOfWeek.values()[dayOfWeekOrder].toString();
        }
    }

    /**
     * Represents the months of the year.
     */
    private enum Month implements Serializable {

        /**
         * The months of the year.
         */
        JANUARY(31) {
            @Override
            public String toString() {
                return "January";
            }
        },
        /**
         * February.
         */
        FEBRUARY(28) {
            @Override
            public String toString() {
                return "February";
            }
        },
        /**
         * March.
         */
        MARCH(31) {
            @Override
            public String toString() {
                return "March";
            }
        },
        /**
         * April.
         */
        APRIL(30) {
            @Override
            public String toString() {
                return "April";
            }
        },
        /**
         * May.
         */
        MAY(31) {
            @Override
            public String toString() {
                return "May";
            }
        },
        /**
         * June.
         */
        JUNE(30) {
            @Override
            public String toString() {
                return "June";
            }
        },
        /**
         * July.
         */
        JULY(31) {
            @Override
            public String toString() {
                return "July";
            }
        },
        /**
         * August.
         */
        AUGUST(31) {
            @Override
            public String toString() {
                return "August";
            }
        },
        /**
         * September.
         */
        SEPTEMBER(30) {
            @Override
            public String toString() {
                return "September";
            }
        },
        /**
         * October.
         */
        OCTOBER(31) {
            @Override
            public String toString() {
                return "October";
            }
        },
        /**
         * November.
         */
        NOVEMBER(30) {
            @Override
            public String toString() {
                return "November";
            }
        },
        /**
         * December.
         */
        DECEMBER(31) {
            @Override
            public String toString() {
                return "December";
            }
        };

        /**
         * The number of days in a month.
         */
        private final int numberOfDays;

        /**
         * Constructs a month with the given number of days.
         *
         * @param numberOfDays the number of days in the month.
         */
        Month(int numberOfDays) {
            this.numberOfDays = numberOfDays;
        }

        /**
         * Returns the number of days in the month for the given year.
         *
         * @param year the year of the month.
         * @return the number of days in the month for the given year.
         */
        public int getNumberOfDays(int year) {
            if (ordinal() == 1 && Date.isLeapYear(year)) {
                return numberOfDays + 1;
            }
            return numberOfDays;
        }

        /**
         * Returns the month based on the given month order.
         *
         * @param monthOrder the order of the month.
         * @return the month corresponding to the given order.
         */
        public static Month getMonth(int monthOrder) {
            return Month.values()[monthOrder - 1];
        }
    }

    /**
     * Constructs a Date instance with the given year, month, and day.
     *
     * @param year  the year of the date.
     * @param month the month of the date.
     * @param day   the day of the date.
     */
    public Date(int year, int month, int day) {
        verifyDate(year, month, day);
        this.year = year;
        this.month = Month.getMonth(month);
        this.day = day;
    }

    /**
     * Constructs a Date instance with the default date.
     */
    public Date() {
        year = DEFAULT_YEAR;
        month = DEFAULT_MONTH;
        day = DEFAULT_DAY;
    }

    /**
     * Constructs a Date instance with the same characteristics as the given date.
     *
     * @param anotherDate the date to copy the characteristics from.
     */
    public Date(Date anotherDate) {
        year = anotherDate.year;
        month = anotherDate.month;
        day = anotherDate.day;
    }

    /**
     * Returns the year of the date.
     *
     * @return the year of the date.
     */
    public int getYear() {
        return year;
    }

    /**
     * Returns the month of the date.
     *
     * @return the month of the date.
     */
    public int getMonth() {
        return month.ordinal() + 1;
    }

    /**
     * Returns the day of the date.
     *
     * @return the day of the date.
     */
    public int getDay() {
        return day;
    }

    /**
     * Modifies the year, month, and day of the date.
     *
     * @param year  the new year of the date.
     * @param month the new month of the date.
     * @param day   the new day of the date.
     */
    public final void setDate(int year, int month, int day) {
        this.year = year;
        this.month = Month.getMonth(month);
        this.day = day;
    }

    /**
     * Returns the textual description of the date in the format: dayOfWeek, day of month
     * of year.
     *
     * @return the characteristics of the date.
     */
    @Override
    public String toString() {
        return String.format("%s, %d of %s of %d", dayOfWeek(), day, month, year);
    }

    /**
     * Returns the date in the format: %04d/%02d/%02d.
     *
     * @return the characteristics of the date.
     */
    public String toYearMonthDayString() {
        return String.format("%04d/%02d/%02d", year, month.ordinal() + 1, day);
    }

    /**
     * Compares the date with the given object.
     *
     * @param anotherObject the object to compare with the date.
     * @return true if the given object represents a date that is equivalent to the date; otherwise, returns false.
     */
    @Override
    public boolean equals(Object anotherObject) {
        if (this == anotherObject) {
            return true;
        }
        if (anotherObject == null || getClass() != anotherObject.getClass()) {
            return false;
        }
        Date anotherDate = (Date) anotherObject;
        return year == anotherDate.year && month.equals(anotherDate.month)
                && day == anotherDate.day;
    }

    /**
     * Hash code int.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(year, month, day);
    }

    /**
     * Compares the date with the other date received as a parameter.
     *
     * @param anotherDate the date to compare.
     * @return the value 0 if the otherDate received is equal to the date; the value -1 if the otherDate is later than the date; the value 1 if the otherDate is earlier than the date.
     */
    @Override
    public int compareTo(Date anotherDate) {
        return (anotherDate.isGreater(this)) ? -1 : (isGreater(anotherDate)) ? 1 : 0;
    }

    /**
     * Returns the day of the week of the date.
     *
     * @return the day of the week of the date.
     */
    public String dayOfWeek() {
        int totalDays = countDays();
        totalDays = totalDays % 7;

        return DayOfWeek.getDayOfWeekName(totalDays);
    }


    /**
     * Returns true if the date is greater than the date received as a parameter. If
     * the date is less than or equal to the date received as a parameter, returns false.
     *
     * @param anotherDate the other date to compare with.
     * @return true if the date is greater than the date received as a parameter, otherwise returns false.
     */
    public boolean isGreater(Date anotherDate) {
        int totalDays = countDays();
        int totalDays1 = anotherDate.countDays();

        return totalDays > totalDays1;
    }

    /**
     * Returns the difference in number of days between the date and the date received as a parameter.
     *
     * @param anotherDate the other date to calculate the difference in number of days.
     * @return the difference in number of days between the date and the date received as a parameter.
     */
    public int difference(Date anotherDate) {
        int totalDays = countDays();
        int totalDays1 = anotherDate.countDays();

        return Math.abs(totalDays - totalDays1);
    }

    /**
     * Returns the difference in number of days between the date and the date received as a parameter with year, month, and day.
     *
     * @param year  the year of the date to compare with to calculate the difference in number of days.
     * @param month the month of the date to compare with to calculate the difference in number of days.
     * @param day   the day of the date to compare with to calculate the difference in number of days.
     * @return the difference in number of days between the date and the date received as a parameter with year, month, and day.
     */
    public int difference(int year, int month, int day) {
        int totalDays = countDays();
        Date anotherDate = new Date(year, month, day);
        int totalDays1 = anotherDate.countDays();

        return Math.abs(totalDays - totalDays1);
    }

    /**
     * Returns true if the year passed as a parameter is a leap year. If the year
     * passed as a parameter is not a leap year, returns false.
     *
     * @param year the year to validate.
     * @return true if the year passed as a parameter is a leap year, otherwise returns false.
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Returns the current system date.
     *
     * @return the current system date.
     */
    public static Date currentDate() {
        Calendar today = Calendar.getInstance();
        int year = today.get(Calendar.YEAR);
        int month = today.get(Calendar.MONTH) + 1;    // January is represented by 0.
        int day = today.get(Calendar.DAY_OF_MONTH);
        return new Date(year, month, day);
    }

    /**
     * Returns the number of days from January 1, 1 to the date.
     *
     * @return the number of days from January 1, 1 to the date.
     */
    private int countDays() {
        int totalDays = 0;

        for (int i = 1; i < year; i++) {
            totalDays += isLeapYear(i) ? 366 : 365;
        }
        for (int i = 1; i < month.ordinal() + 1; i++) {
            totalDays += Month.getMonth(i).getNumberOfDays(year);
        }
        totalDays += day;

        return totalDays;
    }

    /**
     * This function verify if the date is valid.
     *
     * @param year  holds the year.
     * @param month holds the month.
     * @param day   holds the day.
     */
    private void verifyDate(int year, int month, int day) {
//        //verify year
//        if (year < 1900 || year > 2100) {
//            throw new IllegalArgumentException("Invalid year");
//        }
//
//        //verify moth
//        if (year == LocalDate.now().getYear()) {
//            if (month < 1 || month > 12) {
//                throw new IllegalArgumentException("Invalid month");
//            }
//        }
//
//        //verify day
//        if (day < 1 || day > Month.values()[month - 1].numberOfDays) {
//            throw new IllegalArgumentException("Invalid day");
//        }
    }

    /**
     * This function checks if the date is valid.
     *
     * @param year  holds the year.
     * @param month holds the month.
     * @param day   holds the day.
     * @return the boolean
     */
    public static boolean checkDate(int year, int month, int day) {
        boolean check_date;

        //check day
        if (year == LocalDate.now().getYear() && month == LocalDate.now().getMonthValue()) {
            if (day < LocalDate.now().getDayOfMonth() || day > Month.values()[month - 1].numberOfDays) {
                System.out.println("The day must be between " + LocalDate.now().getDayOfMonth() + " and 31");
                return check_date=false;
            }
        }

        //check moth
        if (year == LocalDate.now().getYear()) {
            if (month < LocalDate.now().getMonthValue() || month > 12) {
                System.out.println("The moth must be between " + LocalDate.now().getMonthValue() + " and 12");
                return check_date=false;
            }
        }


        //check year
        if (year < LocalDate.now().getYear() || year > 9999) {
            System.out.println("The year must be at least the current: " + LocalDate.now().getYear());
            return check_date=false;
        }

        return true;
    }

}