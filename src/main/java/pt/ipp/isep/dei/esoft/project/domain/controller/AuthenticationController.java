package pt.ipp.isep.dei.esoft.project.domain.controller;

import pt.ipp.isep.dei.esoft.project.domain.repository.AuthenticationRepository;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;
import pt.ipp.isep.dei.esoft.project.domain.repository.Repositories;

import java.io.Serializable;
import java.util.List;

/**
 * @author Paulo Maio pam@isep.ipp.pt
 */

/**
 * The AuthenticationController class represents the controller responsible for
 * handling user authentication-related operations.
 */
public class AuthenticationController implements Serializable {

    /**
     * Constant for the administrator role.
     */
    public static final String ROLE_ADMIN = "ADMINISTRATOR";
    /**
     * Constant for the Network Manager role.
     */
    public static final String ROLE_NETWORK_MANAGER = "NETWORK_MANAGER";
    /**
     * Constant for the manager role.
     */
    public static final String ROLE_MANAGER = "MANAGER";
    /**
     * Constant for the employee role.
     */
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
    /**
     * Constant for the client role.
     */
    public static final String ROLE_CLIENT = "CLIENT";
    /**
     * The authentication repository used to perform authentication-related database
     * operations.
     */
    private final AuthenticationRepository authenticationRepository;

    /**
     * Creates a new instance of the AuthenticationController class.
     */
    public AuthenticationController() {
        this.authenticationRepository = Repositories.getInstance().getAuthenticationRepository();
    }

    /**
     * Performs user login with the specified email and password.
     *
     * @param email The email of the user to log in.
     * @param pwd   The password of the user to log in.
     * @return True if the login was successful, false otherwise.
     */
    public boolean doLogin(String email, String pwd) {
        try {
            return authenticationRepository.doLogin(email, pwd);
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }

    /**
     * Gets the roles associated with the currently logged-in user.
     *
     * @return A list of UserRoleDTO objects representing the roles associated with         the currently logged-in user,         or null if no user is currently logged in.
     */
    public List<UserRoleDTO> getUserRoles() {
        if (authenticationRepository.getCurrentUserSession().isLoggedIn()) {
            return authenticationRepository.getCurrentUserSession().getUserRoles();
        }
        return null;
    }

    /**
     * Performs user logout.
     */
    public void doLogout() {
        authenticationRepository.doLogout();
    }
}