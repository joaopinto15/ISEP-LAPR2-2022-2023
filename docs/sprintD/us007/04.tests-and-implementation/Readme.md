# US 004 - Create a request for listing a property


# 4. Tests 

**Test 1:** Check that it is not possible to create an instance of the Task class with null values. 

	@Test(expected = IllegalArgumentException.class)
		public void ensureNullIsNotAllowed() {
		Task instance = new Task(null, null, null, null, null, null, null);
	}
	

**Test 2:** Check that it is not possible to create an instance of the Task class with a reference containing less than five chars - AC2. 

	@Test(expected = IllegalArgumentException.class)
		public void ensureReferenceMeetsAC2() {
		Category cat = new Category(10, "Category 10");
		
		Task instance = new Task("Ab1", "Task Description", "Informal Data", "Technical Data", 3, 3780, cat);
	}


*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)


## Class RegisterUserController

```java

public class RegisterUserController {

    /**
     * The Authentication repository.
     */
    private final AuthenticationRepository authenticationRepository;
    /**
     * The Person repository.
     */
    private final PersonRepository personRepository;

    /**
     * Instantiates a new Register user controller.
     */
    public RegisterUserController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
        this.personRepository = Repositories.getInstance().getPersonRepository();
    }

    /**
     * Register user boolean.
     *
     * @param person   the person
     * @param password the password
     * @return the boolean
     */
    //TODO: check for redundancy vs saveUser
    public Boolean registerUser(Person person, String password, String[] role) {
        if (authenticationRepository.getUser(person.getEmailAddress()).isPresent()) return false;
        if (!authenticationRepository.addUser(person.getName(), person.getEmailAddress().getEmail(), password, role))
            return false;
        return personRepository.savePerson(person);
    }
    /**
     * Save user boolean.
     *
     * @param person the person
     * @return the boolean
     */
    public boolean saveUser(Person person, String password) {
        if (personRepository.savePerson(person)) {
            return authenticationRepository.addUser(person.getName(), person.getEmailAddress().getEmail(), password, person.getRolesId());
        }
        return false;
    }

     /**
     * Generate password string.
     *
     * @return the string
     */
    public String generatePassword() {
        /*
         * Generate a string of 2 upper case letters
         */
        String upperCaseStr = RandomStringUtils.random(2, 65, 90, true, true);
        /*
         * Generate a string of 2 lower case letters
         */
        String lowerCaseStr = RandomStringUtils.random(2, 97, 122, true, true);
        /*
         * Generate a string of 2 numeric letters
         */
        String numbersStr = RandomStringUtils.randomNumeric(2);
        /*
         * Generate a string of 2 special chars
         */
        String specialCharsStr = RandomStringUtils.random(2, 33, 47, false, false);
        /*
         * generate a string of 2 alphanumeric letters
         */
        String totalCharsStr = RandomStringUtils.randomAlphanumeric(2);
        /*
         * puts together all the strings into one single string
         */
        String demoPassword = upperCaseStr.concat(lowerCaseStr).concat(numbersStr).concat(specialCharsStr).concat(totalCharsStr);
        /*
         * creates a list of char that stores all the characters, numbers and special characters
         */
        List<Character> listOfChar = demoPassword.chars().mapToObj(data -> (char) data).collect(Collectors.toList());
        /*
         * shuffle the elements in the list
         */
        Collections.shuffle(listOfChar);
        /*
         * generate a random String (secure password) by using list stream() method and collect() method
         */
        return listOfChar.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString();
    }
}


```


## AuthenticationRepository

```java
public class AuthenticationRepository {
// This cannot be Serializable due to AuthFacade
    /**
     * The Authentication facade.
     */
// The AuthFacade object provides the underlying implementation for the
    // repository methods
    private final AuthFacade authenticationFacade = new AuthFacade();

    /**
     * Gets user.
     *
     * @param email the email
     * @return the user
     */
    public Optional<UserDTO> getUser(Email email) {
        return authenticationFacade.getUser(email.getEmail());
    }

    /**
     * Attempts to log in the user with the specified email and password.
     *
     * @param email the email of the user to log in
     * @param pwd   the password of the user to log in
     * @return true if the login was successful, false otherwise
     */
    public boolean doLogin(String email, String pwd) {
        return authenticationFacade.doLogin(email, pwd).isLoggedIn();
    }

    /**
     * Logs out the current user.
     */
    public void doLogout() {
        authenticationFacade.doLogout();
    }

    /**
     * Returns the UserSession object representing the current user's session.
     *
     * @return the UserSession object representing the current user's session
     */
    public UserSession getCurrentUserSession() {
        return authenticationFacade.getCurrentUserSession();
    }

    /**
     * Returns the UserDTO objects representing all users.
     *
     * @return list of UserDTO objects representing all users
     */
    public List<UserDTO> getUsers() {
        return authenticationFacade.getUsers();
    }

    /**
     * Returns the list of users with the specified role.
     *
     * @param roleId the id of the role to search for
     * @return list of UserDTO objects representing all users with the specified role
     */
    public List<UserDTO> getUsersWithRole(String roleId) {
        return authenticationFacade.getUsersWithRole(roleId);
    }

    /**
     * Adds a new user role with the specified id and description.
     *
     * @param id          the id of the new user role
     * @param description the description of the new user role
     * @return true if the user role was added successfully, false otherwise
     */
    public boolean addUserRole(String id, String description) {
        return authenticationFacade.addUserRole(id, description);
    }

    /**
     * Adds a new user with the specified name, email, password, and role.
     *
     * @param name   the name of the new user
     * @param email  the email of the new user
     * @param pwd    the password of the new user
     * @param roleId the id of the role to assign to the new user
     * @return true if the user was added successfully, false otherwise
     */
    public boolean addUser(String name, Email email, String pwd, String roleId) {
        return authenticationFacade.addUserWithRole(name, email.toString(), pwd, roleId);
    }

    /**
     * Adds a new user with the specified name, email, password, and multiple roles.
     *
     * @param name   the name of the new user
     * @param email  the email of the new user
     * @param pwd    the password of the new user
     * @param rolesId the ids of the roles to assign to the new user
     * @return true if the user was added successfully, false otherwise
     */
    public boolean addUser(String name, String email, String pwd, String[] rolesId) {
        return authenticationFacade.addUserWithRoles(name, email, pwd, rolesId);
    }

    /**
     * Update user boolean.
     *
     * @param dto the dto
     * @return the boolean
     */
    public boolean updateUser(UserDTO dto) {
        return authenticationFacade.updateUser(dto);
    }
}
```

# 6. Integration and Demo 

* A new option on the main menu options was added.

* Some users are bootstrapped while system starts.


# 7. Observations

We decide to create a class Person so we can save all the information of a user that can be in a user class since that class is from an external library.






