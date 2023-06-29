package pt.ipp.isep.dei.esoft.project.domain.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pt.isep.lei.esoft.auth.UserSession;

class AuthenticationRepositoryTest {
    private final AuthenticationRepository authenticationRepository = new AuthenticationRepository();

    @Test
    void getCurrentUserSessionTest() {
        // test if current user session is returned
        UserSession userSession = authenticationRepository.getCurrentUserSession();
        Assertions.assertNotNull(userSession);

        // add assertion to check if user session matches expected session
    }

    @Test
    void addUserRoleTest() {
        // test successful addition of user role
        boolean isRoleAdded = authenticationRepository.addUserRole("role_id", "role_description");
        Assertions.assertTrue(isRoleAdded);

        // test adding role with existing ID
        isRoleAdded = authenticationRepository.addUserRole("role_id", "new_description");
        Assertions.assertFalse(isRoleAdded);
    }

    @Test
    void addUserWithRoleTest() {
        // test successful addition of user with role
        boolean isUserAdded = authenticationRepository.addUser("Test User", "testuser@example.com", "password", new String[]{"role_id"});
        Assertions.assertTrue(isUserAdded);

        // test adding user with invalid role ID
        isUserAdded = authenticationRepository.addUser("Test User", "testuser@example.com", "password", new String[] {"invalid_role_id"});
        Assertions.assertFalse(isUserAdded);
    }
    @Test
    void doLoginTest() {
        // test successful login
        boolean isUserAdded = authenticationRepository.addUser("Test User", "testuser2@example.com", "password", new String[]{"role_id"});
        boolean isLoggedIn = authenticationRepository.doLogin("testuser2@example.com", "password");
        Assertions.assertTrue(isLoggedIn);
        authenticationRepository.doLogout();
        // test invalid email and password
        boolean isLoggedIn2 = authenticationRepository.doLogin("invalid@example.com", "password");
        Assertions.assertFalse(isLoggedIn2);
    }

}