package pt.ipp.isep.dei.esoft.project.application.session;

import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.List;

/**
 * The UserSession class represents a user session in the application.
 * It provides methods for retrieving user information and managing the session.
 */
public class UserSession {

    /**
     * The User session.
     */
    private final pt.isep.lei.esoft.auth.UserSession userSession;

    /**
     * Constructs a new UserSession object with the specified user session.
     *
     * @param userSession The user session object to be wrapped.
     */
    public UserSession(pt.isep.lei.esoft.auth.UserSession userSession) {
        this.userSession = userSession;
    }

    /**
     * Retrieves the email of the user associated with the session.
     *
     * @return The email of the user.
     */
    public String getUserEmail() {
        return userSession.getUserId().getEmail();
    }

    /**
     * Retrieves the name of the user associated with the session.
     *
     * @return The name of the user.
     */
    public String getUserName() {
        return this.userSession.getUserName();
    }

    /**
     * Retrieves the roles assigned to the user.
     *
     * @return A list of UserRoleDTO objects representing the roles of the user.
     */
    public List<UserRoleDTO> getUserRoles() {
        return this.userSession.getUserRoles();
    }

    /**
     * Performs the logout operation for the current session.
     */
    public void doLogout() {
        this.userSession.doLogout();
    }

    /**
     * Checks if the user is currently logged in.
     *
     * @return true if the user is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return this.userSession.isLoggedIn();
    }

    /**
     * Checks if the user is currently logged in with the specified role.
     *
     * @param roleId The ID of the role to check.
     * @return true if the user is logged in with the specified role, false otherwise.
     */
    public boolean isLoggedInWithRole(String roleId) {
        return this.userSession.isLoggedInWithRole(roleId);
    }
}
