# US 007 - Register to buy, rent or buy a property

# 4. Tests 

**Test 1:** Check the user implementation 
	```java
	@BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        user1 = new User(new Email("user1@example.com"), new Password("password"), "User 1");
        user2 = new User(new Email("user2@example.com"), new Password("password"), "User 2");
        List<User> userList = new ArrayList<>();
        userList.add(user1);
        userRepository.setUserList(userList);
    }

    @Test
    void testSaveUser() {
        assertTrue(userRepository.saveUser(user2));
        assertFalse(userRepository.saveUser(user1));
    }

    @Test
    void testGetUserList() {
        List<User> userList = userRepository.getUserList();
        assertEquals(1, userList.size());
        assertTrue(userList.contains(user1));
        assertFalse(userList.contains(user2));
    }
	```



*It is also recommended to organize this content by subsections.* 

# 5. Construction (Implementation)


## Class UserRepository 

```java
public class UserRepository {
    /**
     * The list containing the user registered in the system.
     */
    private List<User> userList;

    /**
     * This function validates the user if it's not null
     * @param user represents a user through name, passport number, tax number, email, phone number and password
     * @return false if the instance user is null, if not will return !this.userList.contains(user)
     */
    public boolean validateUser(User user){
        if(user == null){
            return false;
        } else {
            return !this.userList.contains(user);
        }
    }

    /**
     * This function will add a user to the user list
     * @param user represents a user through name, passport number, tax number, email, phone number and password
     * @return the call of a function that add to the userList
     */
    private boolean addUser(User user){
        return this.userList.add(user);
    }

    /**
     * This function save's the user if it is validated
     * @param user represents a user through name, passport number, tax number, email, phone number and password
     * @return false if it is not validated, if it is validated will call the function addUser and pass user as parameter
     */
    public boolean saveUser(User user){
        if(!validateUser(user)){
            return false;
        } else {
            return addUser(user);
        }
    }

    /**
     * this function creates a new instance of a user
     * @param name          holds the name of the user
     * @param id            holds the id of the user
     * @param password      holds the password of the user
     * @return a new instance of a User
     */
    public User registerUser(String name, Password password, Email id){
        return new User(id, password, name);
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList2) {
        this.userList = userList2;
    }

}
```



# 6. Integration and Demo 

* verify the registration of a new user


# 7. Observations






