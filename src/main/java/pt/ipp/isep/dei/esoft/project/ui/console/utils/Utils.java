package pt.ipp.isep.dei.esoft.project.ui.console.utils;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Agency;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Announcement;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * A utility class that provides various methods for reading input from the console and performing common operations.
 */
public class Utils {

    /**
     * Reads a line of text from the console.
     *
     * @param prompt the prompt to display before reading the input
     * @return the line of text read from the console, or null if an exception occurs
     */
    static public String readLineFromConsole(String prompt) {
        try {
            if (prompt != null) System.out.println("\n" + prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Reads a non-empty line of text from the console.
     *
     * @param prompt the prompt to display before reading the input
     * @return the non-empty line of text read from the console
     */
    static public String readNonNullLineFromConsole(String prompt) {
        String input;
        do {
            input = readLineFromConsole(prompt);
        } while (input == null || input.isEmpty());

        return input;
    }

    /**
     * Reads multiple lines of text from the console until a specific key is entered.
     *
     * @param prompt the prompt to display before reading each line
     * @param key    the key that indicates the end of input
     * @return the list of lines read from the console
     */
    static public ArrayList<String> readLinesFromConsole(String prompt, String key) {
        ArrayList<String> input = new ArrayList<>();
        String line;
        System.out.println("\n" + prompt + " (enter " + key + " to finish):");
        do {
            //append to input array with each new line
            line = readNonNullLineFromConsole(null);
            if (!line.equals(key)) {
                input.add(line);
            } else if (input.size() == 0){
                System.out.println("you must enter at least one");
            }
        } while (!(Objects.equals(line, key) && input.size() > 0));
        return input;
    }

    /**
     * Reads an integer value from the console.
     *
     * @param prompt the prompt to display before reading the input
     * @return the integer value read from the console
     */
    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readNonNullLineFromConsole(prompt);

                return Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Reads a double value from the console.
     *
     * @param prompt the prompt to display before reading the input
     * @return the double value read from the console
     */
    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readNonNullLineFromConsole(prompt);

                return Double.parseDouble(input);
            } catch (NumberFormatException ex) {
                System.out.println("\nInvalid input. Please try again.\n");
            }
        } while (true);
    }

    /**
     * Reads a float value from the console.
     *
     * @param prompt the prompt to display before reading the input
     * @return the float value read from the console
     */
    static public float readFloatFromConsole(String prompt) {
        do {
            try {
                String input = readNonNullLineFromConsole(prompt);

                return Float.parseFloat(input);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Reads a boolean value from the console.
     *
     * @param prompt the prompt to display before reading the input
     * @return the boolean value read from the console
     */
    static public Boolean readBooleanFromConsole(String prompt) {
        do {
            try {
                String input = readNonNullLineFromConsole(prompt);

                return Boolean.parseBoolean(input);
            } catch (NumberFormatException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Reads a date value from the console.
     *
     * @param prompt the prompt to display before reading the input
     * @return the date value read from the console
     */
    static public Date readDateFromConsole(String prompt) {
        do {
            try {
                String strDate = readLineFromConsole(prompt);

                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                return df.parse(strDate);
            } catch (ParseException ex) {
                Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (true);
    }

    /**
     * Asks the user to confirm a message by entering 'y' or 'n'.
     *
     * @param message the message to display for confirmation
     * @return true if the user confirms, false otherwise
     */
    static public boolean confirm(String message) {
        String input;
        do {
            input = Utils.readNonNullLineFromConsole("\n" + message);
        } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("y");
    }

    /**
     * Displays a list of objects and allows the user to select one.
     *
     * @param <E>         the type parameter
     * @param list        the list of objects to display
     * @param header      the header to display before the list
     * @param cancellable true if the user can cancel the selection, false otherwise
     * @return the selected object, or null if canceled or the list is empty/null
     */
    static public <E> Object showAndSelectOne(List<E> list, String header, boolean cancellable) {
        if (list == null || list.isEmpty()) {
            System.out.println("No items to select.");
            return null;
        }
        showList(list, header, cancellable);
        return selectsObject(list, cancellable);
    }

    /**
     * Show and select one test object.
     *
     * @param <E>         the type parameter
     * @param list        the list
     * @param header      the header
     * @param cancellable the cancellable
     * @return the object
     */
    static public <E> Object showAndSelectOneTest(List<E> list, String header, boolean cancellable) {
        if (list == null || list.isEmpty()) {
            System.out.println("No items to select.");
            return null;
        }
        showListTest(list, header, cancellable);
        return selectsObject(list, cancellable);
    }

    /**
     * Displays a list of objects and allows the user to select an index.
     *
     * @param <E>         the type parameter
     * @param list        the list of objects to display
     * @param header      the header to display before the list
     * @param cancellable true if the user can cancel the selection, false otherwise
     * @return the selected index, or -1 if canceled or the list is empty/null
     */
    static public <E> int showAndSelectIndex(List<E> list, String header, boolean cancellable) {
        if (list == null || list.isEmpty()) {
            System.out.println("No items to select.");
            return -1;
        }
        showList(list, header, cancellable);
        return selectsIndex(list, cancellable);
    }

    /**
     * Displays a list of objects.
     *
     * @param <E>         the type parameter
     * @param list        the list of objects to display
     * @param header      the header to display before the list
     * @param cancellable true if the user can cancel the selection, false otherwise
     */
    static public <E> void showList(List<E> list, String header, boolean cancellable) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + o.toString());
        }
        if (cancellable) {
            System.out.println();
            System.out.println("0 - Cancel");
        }
    }

    /**
     * Show list test.
     *
     * @param <E>         the type parameter
     * @param list        the list
     * @param header      the header
     * @param cancellable the cancellable
     */
    static public <E> void showListTest(List<E> list, String header, boolean cancellable) {
        System.out.println(header);

        int index = 0;
        for (Object o : list) {
            index++;

            System.out.println(index + ". " + ((Announcement) o).toString(true));
        }
        if (cancellable) {
            System.out.println();
            System.out.println("0 - Cancel");
        }
    }

    /**
     * Allows the user to select an object from a list by entering the corresponding option.
     *
     * @param <E>         the type parameter
     * @param list        the list of objects to select from
     * @param cancellable true if the user can cancel the selection, false otherwise
     * @return the selected object, or null if canceled
     */
    static public <E> Object selectsObject(List<E> list, boolean cancellable) {
        String input;
        int value;
        do {
            input = Utils.readNonNullLineFromConsole("Type your option: ");
            value = Integer.parseInt(input);
        } while (value < ((cancellable) ? 0 : 1) || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    /**
     * Allows the user to select an index from a list by entering the corresponding option.
     *
     * @param <E>         the type parameter
     * @param list        the list of objects
     * @param cancellable true if the user can cancel the selection, false otherwise
     * @return the selected index, or -1 if canceled
     */
    static public <E> int selectsIndex(List<E> list, boolean cancellable) {
        String input;
        int value;
        do {
            input = Utils.readNonNullLineFromConsole("Type your option: ");
            try {
                value = Integer.parseInt(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < ((cancellable) ? 0 : 1) || value > list.size());

        return value - 1;
    }

    /**
     * Gets employee in session.
     *
     * @return the employee in session
     */
    static public Employee getEmployeeInSession() {
        Email idInSession = Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId();
        return Utils.getEmployee(idInSession);
    }

    /**
     * Gets person in session.
     *
     * @return the person in session
     */
    static public Person getPersonInSession() {
        Email idInSession = Repositories.getInstance().getAuthenticationRepository().getCurrentUserSession().getUserId();
        return Utils.getPerson(idInSession);
    }

    /**
     * Gets person.
     *
     * @return the person
     */
    static public ArrayList<Person> getPerson() {
        return Repositories.getInstance().getPersonRepository().getPeople();
    }

    /**
     * Gets person.
     *
     * @param email the email
     * @return the person
     */
    static public Person getPerson(Email email) {
        return Repositories.getInstance().getPersonRepository().getPersonById(email);
    }

    /**
     * Gets employee.
     *
     * @return the employee
     */
    static public ArrayList<Employee> getEmployee() {
        return Repositories.getInstance().getPersonRepository().getEmployees();
    }

    /**
     * Gets employee by agency.
     *
     * @param agencyId the agency id
     * @return the employee by agency
     */
    static public ArrayList<Employee> getEmployeeByAgency(String agencyId) {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Employee employee : Repositories.getInstance().getPersonRepository().getEmployees()) {
            if(employee.getAgencyId().equals(agencyId)){
                employees.add(employee);
            }
        }
        if(employees.isEmpty())
        {
            return null;
        }
        return employees;
    }


    /**
     * Gets employee.
     *
     * @param agencyId the agency id
     * @return the employee
     */
    static public ArrayList<Employee> getEmployee(String agencyId) {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Employee employee : Repositories.getInstance().getPersonRepository().getEmployees()) {
            if (employee.getAgencyId().equals(agencyId)) {
                employees.add(employee);
            }
        }
        return employees;
    }

    /**
     * Gets agency.
     *
     * @return the agency
     */
    static public ArrayList<Agency> getAgency() {
        return Repositories.getInstance().getAgencyRepository().getAgencyList();
    }

    /**
     * Gets employee.
     *
     * @param email the email
     * @return the employee
     */
    static public Employee getEmployee(Email email) {
        if (Repositories.getInstance().getPersonRepository().getPersonById(email) instanceof Employee) {
            return (Employee) Repositories.getInstance().getPersonRepository().getPersonById(email);
        }
        return null;
    }

    /**
     * Gets client.
     *
     * @return the client
     */
    static public ArrayList<Person> getClient() {
        ArrayList<Person> clients = new ArrayList<>();
        for (Person person : Repositories.getInstance().getPersonRepository().getPeople()) {
            //check if person does not have employee role
            if (Arrays.asList(person.getRolesId()).contains(AuthenticationController.ROLE_CLIENT)) {
                clients.add(person);
            }
        }
        return clients;
    }

    /**
     * Gets client.
     *
     * @param email the email
     * @return the client
     */
    static public Person getClient(Email email) {
        if (Repositories.getInstance().getPersonRepository().getPersonById(email) != null && !(Repositories.getInstance().getPersonRepository().getPersonById(email) instanceof Employee)) {
            return Repositories.getInstance().getPersonRepository().getPersonById(email);
        }
        return null;
    }

    /**
     * method to confirm a 9 digit string
     *
     * @param str the string
     * @return true if the string has exactly 9 digits, false if the string doesn't have exactly 9 digits
     */
    static public boolean confirm9DigitString(String str){
        boolean isValid= false;
        int countNumbers=0;

        if(str.length() != 9){
            isValid = false;
        }
        for (int i = 0; i <str.length() ; i++) {
            char ch = str.charAt(i);
            if(Character.isDigit(ch)){
                countNumbers++;
            }
        }
        if(countNumbers==9){
            isValid=true;
        }
        return isValid;
    }

    /**
     * method to confirm a 5 digit string
     *
     * @param str the string
     * @return true if the string has exactly 5 digits, false if it hasn't exactly 5 digits
     */
    public static boolean confirm5DigitString(String str){
        boolean isValid= false;
        int countNumbers=0;
        if(str.length() != 5){
            isValid = false;
        }
        for (int i = 0; i <str.length() ; i++) {
            char ch = str.charAt(i);
            if (Character.isDigit(ch)){
                countNumbers++;
            }
        }
        if(countNumbers==5){
            isValid= true;
        }
        return isValid;
    }


    /**
     * Confirm email boolean.
     *
     * @param email the email
     * @return the boolean
     */
    public static boolean confirmEmail(String email){
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * Confirm names boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public static boolean confirmNames(String name){
        String regex = "^[A-Z](?=.{1,29}$)[A-Za-z]*(?:\\h+[A-Z][A-Za-z]*)*$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    /**
     * Confirm int 9 digits boolean.
     *
     * @param numb the numb
     * @return the boolean
     */
    public static boolean confirmInt9Digits(int numb){
        boolean isValid=false;
        int countNumb=0;
        while(numb !=0){
            numb = numb/10;
            countNumb++;
        }
        if (countNumb==9){
            isValid = true;
        }
        return isValid;
    }
}