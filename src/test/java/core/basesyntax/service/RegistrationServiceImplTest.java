package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private static RegistrationService registrationService;
    private static User userRegular;
    private static User userDuplicated;
    private static User userWithShortLogin;
    private static User userWithShortPassword;
    private static User userTooYoung;

    @BeforeAll
    static void beforeAll() {
        registrationService = new RegistrationServiceImpl();

        userRegular = new User();
        userRegular.setAge(19);
        userRegular.setLogin("User10");
        userRegular.setPassword("123456");

        userDuplicated = new User();
        userDuplicated.setAge(19);
        userDuplicated.setLogin("User10");
        userDuplicated.setPassword("123457");

        userWithShortLogin = new User();
        userWithShortLogin.setAge(19);
        userWithShortLogin.setLogin("User1");
        userWithShortLogin.setPassword("123457");

        userWithShortPassword = new User();
        userWithShortPassword.setAge(19);
        userWithShortPassword.setLogin("User20");
        userWithShortPassword.setPassword("12345");

        userTooYoung = new User();
        userTooYoung.setAge(17);
        userTooYoung.setLogin("User30");
        userTooYoung.setPassword("123456");
    }

    @Test
    void returnNewUser() {
        User actual = registrationService.register(userRegular);
        assertEquals(userRegular, actual);
    }

    @Test
    void containsUser_NotOk() {
        registrationService.register(userRegular);
        User actual = registrationService.register(userDuplicated);
        assertNull(actual);
    }

    @Test
    void nullValue_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(null);
        });
    }

    @Test
    void loginLength() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithShortLogin);
        });
    }

    @Test
    void passwordLength() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userWithShortPassword);
        });
    }

    @Test
    void userAgeUnder18_NotOk() {
        assertThrows(RegistrationException.class, () -> {
            registrationService.register(userTooYoung);
        });
    }
}
