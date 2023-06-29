package pt.ipp.isep.dei.esoft.project.ui.console.authorization;

import pt.ipp.isep.dei.esoft.project.domain.controller.AuthenticationController;
import pt.ipp.isep.dei.esoft.project.ui.console.menu.*;
import pt.ipp.isep.dei.esoft.project.ui.console.utils.Utils;
import pt.isep.lei.esoft.auth.mappers.dto.UserRoleDTO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Authentication UI
 * @author Paulo Maio pam@isep.ipp.pt
 */

/**
 * The AuthenticationUI class represents the user interface for authentication and role selection.
 * It implements the Runnable interface to be executed in a separate thread.
 */
public class AuthenticationUI implements Runnable {
    /**
     * The Ctrl.
     */
    private final AuthenticationController ctrl;

    /**
     * Creates an instance of AuthenticationUI and initializes the AuthenticationController.
     */
    public AuthenticationUI() {
        ctrl = new AuthenticationController();
    }

    /**
     * Executes the authentication and role selection logic.
     */
    public void run() {
        boolean success = doLogin();

        if (success) {
            List<UserRoleDTO> roles = this.ctrl.getUserRoles();
            if ((roles == null) || (roles.isEmpty())) {
                System.out.println("No role assigned to user.");
            } else {
                UserRoleDTO role = selectsRole(roles);
                if (!Objects.isNull(role)) {
                    List<MenuItem> rolesUI = getMenuItemForRoles();
                    this.redirectToRoleUI(rolesUI, role);
                } else {
                    System.out.println("No role selected.");
                }
            }
        }
        this.logout();
    }

    /**
     * Retrieves the menu items for each available role.
     *
     * @return A list of MenuItem objects representing the available roles and their associated UIs.
     */
    private List<MenuItem> getMenuItemForRoles() {
        List<MenuItem> rolesUI = new ArrayList<>();
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_ADMIN, new AdminUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_CLIENT, new ClientUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_EMPLOYEE, new EmployeeUI()));
        rolesUI.add(new MenuItem(AuthenticationController.ROLE_MANAGER, new StoreManagerUI()));
        return rolesUI;
    }

    /**
     * Performs the login process by prompting the user for their credentials.
     *
     * @return true if the login is successful, false otherwise.
     */
    private boolean doLogin() {
        System.out.println("\nLogin UI:");

        int maxAttempts = 3;
        boolean success = false;
        do {
            maxAttempts--;
            String id = Utils.readLineFromConsole("Enter UserId/Email: ");
            String pwd = Utils.readLineFromConsole("Enter Password: ");

            success = ctrl.doLogin(id, pwd);
            if (!success) {
                System.out.println("Invalid UserId and/or Password. \n You have  " + maxAttempts + " more attempt(s).");
            }

        } while (!success && maxAttempts > 0);
        return success;
    }

    /**
     * Logs out the current user.
     */
    private void logout() {
        ctrl.doLogout();
    }

    /**
     * Redirects the user to the appropriate UI based on their selected role.
     *
     * @param rolesUI The list of available roles and their associated UIs.
     * @param role    The selected role.
     */
    private void redirectToRoleUI(List<MenuItem> rolesUI, UserRoleDTO role) {
        boolean found = false;
        Iterator<MenuItem> it = rolesUI.iterator();
        while (it.hasNext() && !found) {
            MenuItem item = it.next();
            found = item.hasDescription(role.getDescription());
            if (found) {
                item.run();
            }
        }
        if (!found) {
            System.out.println("There is no UI for users with role '" + role.getDescription() + "'");
        }
    }

    /**
     * Prompts the user to select a role from the available roles.
     *
     * @param roles The list of available roles.
     * @return The selected UserRoleDTO object representing the chosen role.
     */
    private UserRoleDTO selectsRole(List<UserRoleDTO> roles) {
        if (roles.size() == 1) {
            return roles.get(0);
        } else {
            return (UserRoleDTO) Utils.showAndSelectOne(roles, "Select the role you want to adopt in this session:", true);
        }
    }
}