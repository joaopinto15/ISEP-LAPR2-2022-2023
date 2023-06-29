package pt.ipp.isep.dei.esoft.project.domain.repository;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Employee;
import pt.ipp.isep.dei.esoft.project.domain.model.agency.Person;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Land;
import pt.ipp.isep.dei.esoft.project.domain.model.property.Property;
import pt.isep.lei.esoft.auth.domain.model.Email;

import java.io.*;
import java.util.ArrayList;


/**
 * The type Person repository.
 */
public class PersonRepository implements Serializable {

    /**
     * The list containing the employees registered in the system.
     */
    private ArrayList<Person> people = new ArrayList<>();


    /**
     * Add person boolean.
     *
     * @param person the person
     * @return the boolean
     */
    private boolean addPerson(Person person) {
        return this.people.add(person);
    }

    /**
     * Validate person boolean.
     *
     * @param person the person
     * @return the boolean
     */
    private boolean validatePerson(Person person) {
        if (person == null) {
            return false;
        } else {
            return !this.people.contains(person);
        }
    }

    /**
     * Save person boolean.
     *
     * @param person the person
     * @return the boolean
     */
    public boolean savePerson(Person person) {
        if (!validatePerson(person)) {
            return false;
        } else {
            boolean add = addPerson(person);
            serializePerson();
            return add;
        }
    }


    /**
     * Gets people.
     *
     * @return the people
     */
    public ArrayList<Person> getPeople() {
        return this.people;
    }

    /**
     * Gets employees.
     *
     * @return the employees
     */
    public ArrayList<Employee> getEmployees() {
        ArrayList<Employee> employees = new ArrayList<>();
        for (Person person : this.people) {
            String[] roles = person.getRolesId();
            for (String role : roles) {
                if (role != null) {
                    if (role.equals(AuthenticationController.ROLE_EMPLOYEE)) {
                        employees.add((Employee) person);
                        break;
                    }
                }
            }
        }
        return employees;
    }


    /**
     * Validate employee boolean.
     *
     * @param employee the employee
     * @return the boolean
     */
    public boolean validateEmployee(Employee employee) {//Apagar
        return getEmployees().contains(employee);
    }

    /**
     * Gets person by id.
     *
     * @param email the email
     * @return the person by id
     */
    public Person getPersonById(Email email) {
        for (Person person : this.people) {
            if (person.getEmailAddress().getEmail().equals(email.getEmail())) {
                return person;
            }
        }
        return null;
    }

    /**
     * Serialize person.
     */
    public void serializePerson() {
        try {
            File folder = new File(storageFolder);
            if (!folder.exists()) {
                if (!folder.mkdirs()) {
                    System.out.println("an error occurred when creating the folder -> " + storageFolder + " <-");
                    return;
                }
            }
            FileOutputStream fileOut = new FileOutputStream(storageFolder + File.separator + fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(people);
            objectOut.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Deserialize person.
     */
    public void deserializePerson() {
        AuthenticationRepository authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        try {
            FileInputStream fileIn = new FileInputStream(storageFolder + File.separator + fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            people = (ArrayList<Person>) objectIn.readObject();
            objectIn.close();
            fileIn.close();

            for (Person item : people) {
                authenticationRepository.addUser(item.getName(), item.getEmailAddress().toString(), item.getPwd(), item.getRolesId());
            }
        } catch (FileNotFoundException e) {
            // Lidar com a exceção: exibir mensagem de erro ou realizar ação alternativa
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * The constant fileName.
     */
    private static final String fileName = "PersonList.byte";
    /**
     * The constant storageFolder.
     */
    private static final String storageFolder = "serialized.files";
}
