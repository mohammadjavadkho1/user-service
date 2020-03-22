package com.perseus.userservice;


import com.perseus.userservice.exceptions.AlreadyExistException;
import com.perseus.userservice.exceptions.EntityNotFoundException;
import com.perseus.userservice.services.UserService;
import com.perseus.userservice.services.models.UserCreateRequest;
import com.perseus.userservice.services.models.UserListModel;
import com.perseus.userservice.services.models.UserModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class TestUserService {
    @Autowired
    UserService userService;


    @DisplayName("Test create user")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testCreateUser() {

        List<String> emails = new ArrayList<>();
        emails.add("julia@gmail.com");

        List<String> phones = new ArrayList<>();
        phones.add("543-6548654");
        UserCreateRequest newUser = new UserCreateRequest(
                "julia",
                "julia",
                phones,
                emails
        );
        UserModel createdUser = userService.create(newUser);

        assertAll(
                "Test every field",
                () -> assertEquals(newUser.getFirstName(), createdUser.getFirstName(), "First name should be equal"),
                () -> assertEquals(newUser.getLastName(), createdUser.getLastName(), "Last Name should be equal"),
                () -> assertEquals(emails.get(0), createdUser.getEmails().iterator().next().getEmail(), "Email should be equal"),
                () -> assertEquals(phones.get(0), createdUser.getPhoneNumbers().iterator().next().getNumber(), "Phone should be equal")
        );


    }

    @DisplayName("Test get user by name")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testGetUserByName() {

        UserListModel users = userService.searchByName("khoshtarash");

        assertEquals(1, users.getUsers().size());

    }

    @DisplayName("Test get user by id")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testGetUserById() {

        UserModel user = userService.findById(1);

        assertAll(
                "Test every field",
                () -> assertEquals(user.getId(), 1, "User id should be equal"),
                () -> assertEquals(user.getFirstName(), "mohammadjavad", "First name should be equal"),
                () -> assertEquals(user.getLastName(), "khoshtarash", "Last Name should be equal"),
                () -> assertEquals(user.getEmails().iterator().next().getEmail(), "mohammadjavadkho@gmail.com",
                        "Email should be equal"),
                () -> assertEquals(user.getPhoneNumbers().iterator().next().getNumber(), "123-1234567", "Phone should be equal")
        );

    }

    @DisplayName("Delete user by id")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testDeleteUserById() {
        assertDoesNotThrow(() -> userService.deleteById(1));
        assertThrows(EntityNotFoundException.class, () -> userService.findById(1));

    }

    @DisplayName("Add phone number")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testAddPhoneNumber() {
        String newNumber = "124-6547324";
        UserModel user = userService.addPhoneNumber(1, newNumber);
        boolean exist = user.getPhoneNumbers().stream()
                .anyMatch(it -> it.getNumber().equalsIgnoreCase(newNumber));
        assertTrue(exist);
    }

    @DisplayName("Add duplicate phone number")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testAddDuplicatePhoneNumber() {
        String newNumber = "123-1234567";
        assertThrows(AlreadyExistException.class, () -> userService.addPhoneNumber(1, newNumber));
    }

    @DisplayName("Add email")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testAddEmail() {
        String newEmail = "mohammadjavadkho1@gmail.com";
        UserModel user = userService.addEmail(1, newEmail);
        boolean exist = user.getEmails().stream()
                .anyMatch(it -> it.getEmail().equalsIgnoreCase(newEmail));
        assertTrue(exist);
    }

    @DisplayName("Add duplicate email")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testAddDuplicateEmail() {
        String newEmail = "mohammadjavadkho@gmail.com";
        assertThrows(AlreadyExistException.class, () -> userService.addEmail(1, newEmail));
    }

    @DisplayName("Update email")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testUpdateEmail() {
        String newEmail = "mohammadjavadkho12@gmail.com";
        UserModel user = userService.updateEmail(1, 43, newEmail);
        boolean exist = user.getEmails().stream()
                .anyMatch(it -> it.getEmail().equalsIgnoreCase(newEmail));
        assertTrue(exist);
    }

    @DisplayName("Update phone number")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testUpdatePhoneNumber() {
        String newNumber = "124-6547374";
        UserModel user = userService.updatePhoneNumber(1, 200, newNumber);
        boolean exist = user.getPhoneNumbers().stream()
                .anyMatch(it -> it.getNumber().equalsIgnoreCase(newNumber));
        assertTrue(exist);
    }


}
