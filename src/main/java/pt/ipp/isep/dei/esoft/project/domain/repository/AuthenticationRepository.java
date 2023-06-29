package pt.ipp.isep.dei.esoft.project.domain.repository;


import pt.isep.lei.esoft.auth.AuthFacade;
import pt.isep.lei.esoft.auth.UserSession;
import pt.isep.lei.esoft.auth.domain.model.Email;
import pt.isep.lei.esoft.auth.mappers.dto.UserDTO;

import java.util.List;
import java.util.Optional;

/**
 * The AuthenticationRepository class represents a repository object that
 * provides data access and manipulation
 * <p>
 * services related to authentication and user management in the application.
 */
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
     * @param name    the name of the new user
     * @param email   the email of the new user
     * @param pwd     the password of the new user
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
