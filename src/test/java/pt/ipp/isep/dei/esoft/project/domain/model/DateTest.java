package pt.ipp.isep.dei.esoft.project.domain.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DateTest {
    @Test
    void testConstructorAndGetters() {
        Date date = new Date(2023, 5, 28);
        Assertions.assertEquals(2023, date.getYear());
        Assertions.assertEquals(5, date.getMonth());
        Assertions.assertEquals(28, date.getDay());
    }

    @Test
    void testDefaultConstructor() {
        Date date = new Date();
        Assertions.assertEquals(1, date.getYear());
        Assertions.assertEquals(1, date.getMonth());
        Assertions.assertEquals(1, date.getDay());
    }

    @Test
    void testToString() {
        Date date = new Date(2023, 5, 28);
        Assertions.assertEquals("Sunday, 28 of May of 2023", date.toString());
    }

    @Test
    void testToYearMonthDayString() {
        Date date = new Date(2023, 5, 28);
        Assertions.assertEquals("2023/05/28", date.toYearMonthDayString());
    }

    @Test
    void testEquals() {
        Date date1 = new Date(2023, 5, 28);
        Date date2 = new Date(2023, 5, 28);
        Date date3 = new Date(2023, 5, 29);

        Assertions.assertEquals(date1, date2);
        Assertions.assertNotEquals(date1, date3);
    }

    @Test
    void testCompareTo() {
        Date date1 = new Date(2023, 5, 28);
        Date date2 = new Date(2023, 5, 29);
        Date date3 = new Date(2023, 5, 27);

        //noinspection EqualsWithItself
        Assertions.assertEquals(0, date1.compareTo(date1));
        Assertions.assertEquals(-1, date1.compareTo(date2));
        Assertions.assertEquals(1, date1.compareTo(date3));
    }

    @Test
    void testDayOfWeek() {
        Date date = new Date(2023, 5, 28);
        Assertions.assertEquals("Sunday", date.dayOfWeek());
    }

    @Test
    void testIsGreater() {
        Date date1 = new Date(2023, 5, 28);
        Date date2 = new Date(2023, 5, 29);
        Date date3 = new Date(2023, 5, 27);

        Assertions.assertFalse(date1.isGreater(date1));
        Assertions.assertTrue(date2.isGreater(date1));
        Assertions.assertFalse(date3.isGreater(date1));
    }

    @Test
    void testDifference() {
        Date date1 = new Date(2023, 5, 28);
        Date date2 = new Date(2023, 5, 29);
        Date date3 = new Date(2023, 5, 27);

        Assertions.assertEquals(0, date1.difference(date1));
        Assertions.assertEquals(1, date1.difference(date2));
        Assertions.assertEquals(1, date2.difference(date1));
        Assertions.assertEquals(1, date3.difference(date1));
    }
    @Test
    public void testConstructor_ValidDate() {
        Date date = new Date(2023, 5, 28);
        Assertions.assertEquals(2023, date.getYear());
        Assertions.assertEquals(5, date.getMonth());
        Assertions.assertEquals(28, date.getDay());
    }


    @Test
    public void testConstructor_CopyDate() {
        Date originalDate = new Date(2023, 5, 28);
        Date copiedDate = new Date(originalDate);
        Assertions.assertEquals(originalDate.getYear(), copiedDate.getYear());
        Assertions.assertEquals(originalDate.getMonth(), copiedDate.getMonth());
        Assertions.assertEquals(originalDate.getDay(), copiedDate.getDay());
    }

    @Test
    public void testIsLeapYear() {
        Assertions.assertTrue(Date.isLeapYear(2020));
        Assertions.assertFalse(Date.isLeapYear(2021));
    }

    @Test
    public void testCurrentDate() {
        Date currentDate = Date.currentDate();
        Assertions.assertEquals(java.time.LocalDate.now().getYear(), currentDate.getYear());
        Assertions.assertEquals(java.time.LocalDate.now().getMonthValue(), currentDate.getMonth());
        Assertions.assertEquals(java.time.LocalDate.now().getDayOfMonth(), currentDate.getDay());
    }
}