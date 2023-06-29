package pt.ipp.isep.dei.esoft.project.domain.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RegisterUserControllerTest {
    private RegisterUserController registerUserController;

    @BeforeEach
    void setUp() {
        registerUserController = new RegisterUserController();
    }

    @Test
    void testGeneratePassword() {
        String password = registerUserController.generatePassword();
        Assertions.assertNotNull(password);
        Assertions.assertEquals(10, password.length());
        Assertions.assertTrue(password.matches("^(?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*()_+\\-={};':\"\\\\|,.<>\\/?]).*$"));
    }
}