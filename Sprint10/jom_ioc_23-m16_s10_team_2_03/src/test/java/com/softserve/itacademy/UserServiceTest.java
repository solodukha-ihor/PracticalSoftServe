package com.softserve.itacademy;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(JUnitPlatform.class)
public class UserServiceTest {
    private static UserService userService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        annotationConfigContext.close();
    }

    @Test
    public void checkAddUser() {
        final User expected = new User("Andriy", "Mozol", "qwerty@mail.com", "qwerty123" );

        User actual = userService.addUser(expected);
        Assertions.assertEquals(expected, actual, "check addUser don`t work");
    }

    @Test
    public void checkReadUser(){
        final User expected = new User("Andriy", "Mozol", "qwerty@mail.com", "qwerty123" );

        userService.addUser(expected);
        User actual = userService.readUser("qwerty@mail.com");

        Assertions.assertEquals(expected, actual, "check readUser don`t work");

    }

    @Test
    public void checkUpdateUser(){
        final User user = new User("Bohdan", "Mozol", "qwerty@mail.com", "qwerty123" );
        final User expected = new User("Andriy", "Mozol", "qwerty@mail.com", "qwerty123" );

        userService.addUser(user);
        userService.updateUser(new User("Andriy", "Mozol", "qwerty@mail.com", "qwerty123" ));

        User actual = userService.readUser("qwerty@mail.com");

        Assertions.assertEquals(expected, actual);


    }

    @Test
    public void checkDeleteUser(){
        final User user = new User("Andriy", "Mozol", "qwerty@mail.com", "qwerty123" );
        final User expected = null;

        userService.addUser(user);
        userService.deleteUser(user);
        User actual = userService.readUser(user.getEmail());

        Assertions.assertEquals(expected, actual);

    }
}
