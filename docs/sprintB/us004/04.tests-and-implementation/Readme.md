# US 006 - To create a Task 

# 4. Tests 


**Test 1:** AC3: The application should include a feature to randomly assign one agent.

    ```java
	@Test

    public void testRandomAgent() {
        // create a list of employees
        EmployeeRole role = new EmployeeRole("1", "Agent");
        EmployeeRole role1 = new EmployeeRole("2", "Manager");
        EmployeeRole role2 = new EmployeeRole("3", "Agent");
        Address address = new Address("21 Street", "Porto", "Porto", "Porto", 11111);
        Person person = new Person("João", 123456789, 123456789, "teste@gmail.com", 912121211);
        Agency agency = new Agency("1", address, "Porto", "123456789", "PortoAgency@gmail.com");
        List<Employee> employees = new ArrayList<Employee>();
        employees.add(new Employee(person,address,role,agency));
        employees.add(new Employee(person, address, role1, agency));
        employees.add(new Employee(person, address, role2, agency));
        
        // create a RandomAgent object
        RandomAgent ra = new RandomAgent();

        // call the randomAgent method and get the result
        Employee result = ra.randomAgent(employees,agency);

        // assert that the result is an Employee object
        assertEquals(Employee.class, result.getClass());

        // assert that the result's role designation is "Agent"
        assertEquals("Agent", result.getRole().getDesignation());
    }
    ```

**Test 3:** AC4: The owner should input the URL of each file/photograph.






*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)


## Class Deal

```java
public class RandomAgent {


    public Employee randomAgent(List<Employee> employees, Agency agency) {
        List<Employee> type = new ArrayList<Employee>();
        int count = 0;
        Random rand = new Random();
        for (Employee employee : employees) {
            
            if (employee.getRole().getDesignation() == "Agent" && employee.getAgency().getDesignation() == agency.getDesignation()) {

                type.add(employee);
                count ++;
            }
        }
        int randomNumber = rand.nextInt(count);

        Employee agent = type.get(randomNumber);

        return agent;
    }
}

```


## Class Employee

```java
public class Employee {
    /**
     * this variable holds the informations of the person
     */
    private final Person person;
    /**
     * this variable holds the address of the employee
     */
    private final Address address;
    /**
     * this variable holds the role of the employee
     */
    private final EmployeeRole role;
    /**
     * this variable holds the agency of the employee
     */
    private final Agency agency;

    /**
     * builds an instance of Employee receiving person, address and role as parameters
     * @param person           holds the info of the employee
     * @param address          holds the address of the employee
     * @param role             holds the role of the employee
     * @param agency           holds the agency of the employee
     */
    public Employee(Person person, Address address, EmployeeRole role, Agency agency) {
        this.person = new Person(person);
        this.address = new Address(address);
        this.role = new EmployeeRole(role);
        this.agency = new Agency(agency);
    }

    public Employee(Employee otherEmployee) {
        this.person = otherEmployee.person;
        this.address = otherEmployee.address;
        this.role = otherEmployee.role;
        this.agency = otherEmployee.agency;
    }


    /**
     * Method to return the employee's info
     * @return the info of the employee
     */
    public Person getPerson() {
        return person;
    }

    /**
     * Method to return the employee's address
     * @return the address of the employee
     */
    public Address getAddress() {
        return address;
    }

    /**
     * method to return the employee's role
     * @return the role of the employee
     */
    public EmployeeRole getRole() {
        return role;
    }

    /**
     * Method to return the employee's agency
     * @return the agency of the employee
     */
    public Agency getAgency() {
        return agency;
    }

}
```

### Class Agency

```java
public class Agency {
    /**
     * this variable holds the id of the agency
     */
    private final String id;
    /**
     * this variable holds the address of the agency
     */
    private final Address address;
    /**
     * this variable holds the designation of the agency
     */
    private final String designation;
    /**
     * this variable holds the phone number of the agency
     */
    private final String phoneNumber;
    /**
     * this variable holds the email of the agency
     */
    private final String emailAddress;

    private List<Employee> employees;


    /**
     *  This function creates an instance receiving id, address, designation, phoneNumber and emailAddress as parameters
     * @param id                the variable id holds the id of the agency
     * @param address           the variable address holds the address of the agency
     * @param designation       the variable designation holds the designation of the agency
     * @param phoneNumber       the variable phoneNumber holds the phone number of the agency
     * @param emailAddress      the variable emailAddress holds the email address of the agency
     */
    public Agency(String id, Address address, String designation, String phoneNumber, String emailAddress) {

        this.id = id;
        this.address = new Address(address);
        this.designation = designation;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
    }

    /**
     * This function creates an instance of Agency with the same parameters as Agency
     * @param otherAgency the agency with the parameters to copy
     */
    public Agency(Agency otherAgency) {
        id = otherAgency.id;
        address = otherAgency.address;
        designation = otherAgency.designation;
        phoneNumber = otherAgency.phoneNumber;
        emailAddress = otherAgency.emailAddress;
    }

    public String getDesignation() {
        return designation;
    }
}
```

# 6. Integration and Demo 

* Randomly choose an agency of a given agency



# 7. Observations

The implementation of the class randomAgent was necessary to divide the randomAgent method, since is not related with the other classes.








