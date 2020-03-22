package com.perseus.userservice;


import com.perseus.userservice.services.models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UserServiceApplication.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class TestUserController {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    @DisplayName("API-Test add new user")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    public void testAddUser() {

        UserCreateRequest newUser = getUserCreateRequest();
        ResponseEntity<UserModel> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/users", newUser, UserModel.class);
        assertEquals(HttpStatus.CREATED.value(), responseEntity.getStatusCodeValue());

        UserModel createdUser = responseEntity.getBody();

        assertAll(
                "Test every field",
                () -> assertEquals(newUser.getFirstName(), createdUser.getFirstName(), "First name should be equal"),
                () -> assertEquals(newUser.getLastName(), createdUser.getLastName(), "Last Name should be equal"),
                () -> assertEquals(newUser.getEmails().get(0), createdUser.getEmails().iterator().next().getEmail(), "Email should be equal"),
                () -> assertEquals(newUser.getPhoneNumbers().get(0), createdUser.getPhoneNumbers().iterator().next().getNumber(), "Phone should be equal")
        );
    }


    @DisplayName("API-Test find user by id")
    @Test
    @Sql({"/schema.sql", "/data.sql"})
    public void testFindUserById() {

        ResponseEntity<UserModel> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/users/1", UserModel.class);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        UserModel foundedUser = responseEntity.getBody();

        assertAll(
                "Test every field",
                () -> assertEquals(1, foundedUser.getId(), "Id should be equal"),
                () -> assertEquals("mohammadjavad", foundedUser.getFirstName(), "First name should be equal"),
                () -> assertEquals("khoshtarash", foundedUser.getLastName(), "Last Name should be equal"),
                () -> assertEquals("mohammadjavadkho@gmail.com", foundedUser.getEmails().iterator().next().getEmail(), "Email should be equal"),
                () -> assertEquals("123-1234567", foundedUser.getPhoneNumbers().iterator().next().getNumber(), "Phone should be equal")
        );
    }

    @DisplayName("API-Test find user by name")
    @Test
    @Sql({"/schema.sql", "/data.sql"})
    public void testFindUserByName() {

        ResponseEntity<UserListModel> responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/users?name=khoshtarash", UserListModel.class);
        assertEquals(HttpStatus.OK.value(), responseEntity.getStatusCodeValue());
        UserModel foundedUser = responseEntity.getBody().getUsers().get(0);

        assertAll(
                "Test every field",
                () -> assertEquals(1, foundedUser.getId(), "Id should be equal"),
                () -> assertEquals("mohammadjavad", foundedUser.getFirstName(), "First name should be equal"),
                () -> assertEquals("khoshtarash", foundedUser.getLastName(), "Last Name should be equal"),
                () -> assertEquals("mohammadjavadkho@gmail.com", foundedUser.getEmails().iterator().next().getEmail(), "Email should be equal"),
                () -> assertEquals("123-1234567", foundedUser.getPhoneNumbers().iterator().next().getNumber(), "Phone should be equal")
        );
    }

    @DisplayName("API-Test delete user")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testDeleteUser() {
        this.restTemplate.delete("http://localhost:" + port + "/users/1");

        ResponseEntity<UserModel> user = this.restTemplate
                .getForEntity("http://localhost:" + port + "/users/1", UserModel.class);
        assertEquals(404, user.getStatusCodeValue());
    }

    @DisplayName("API-Test add phone number")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testAddPhoneNumber() {
        PhoneNumberCreateModel newPhoneNumber = new PhoneNumberCreateModel();
        newPhoneNumber.setPhoneNumber("333-3333333");

        ResponseEntity<UserModel> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/users/1/phones", newPhoneNumber, UserModel.class);
        UserModel responseUser = responseEntity.getBody();

        boolean exist = responseUser
                .getPhoneNumbers()
                .stream()
                .anyMatch(it -> it.getNumber().equalsIgnoreCase(newPhoneNumber.getPhoneNumber()));
        assertTrue(exist);
    }

    @DisplayName("API-Test add phone number")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testUpdatePhoneNumber() {
        PhoneNumberCreateModel newPhoneNumber = new PhoneNumberCreateModel();
        newPhoneNumber.setPhoneNumber("333-3333333");

        ResponseEntity<UserModel> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/users/1/phones/200", newPhoneNumber, UserModel.class);
        UserModel responseUser = responseEntity.getBody();

        boolean exist = responseUser
                .getPhoneNumbers()
                .stream()
                .anyMatch(it -> it.getNumber().equalsIgnoreCase(newPhoneNumber.getPhoneNumber()));
        assertTrue(exist);
    }

    @DisplayName("API-Test add email")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testAddEmail() {
        EmailCreateModel newEmail = new EmailCreateModel();
        newEmail.setEmail("khoshtarash@gmail.com");

        ResponseEntity<UserModel> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/users/1/emails", newEmail, UserModel.class);
        UserModel responseUser = responseEntity.getBody();

        boolean exist = responseUser
                .getEmails()
                .stream()
                .anyMatch(it -> it.getEmail().equalsIgnoreCase(newEmail.getEmail()));
        assertTrue(exist);
    }

    @DisplayName("API-Test update email")
    @Sql({"/schema.sql", "/data.sql"})
    @Test
    void testUpdateEmail() {
        EmailCreateModel newEmail = new EmailCreateModel();
        newEmail.setEmail("khoshtarash@gmail.com");

        ResponseEntity<UserModel> responseEntity = this.restTemplate
                .postForEntity("http://localhost:" + port + "/users/1/emails/43", newEmail, UserModel.class);
        UserModel responseUser = responseEntity.getBody();

        boolean exist = responseUser
                .getEmails()
                .stream()
                .anyMatch(it -> it.getEmail().equalsIgnoreCase(newEmail.getEmail()));
        assertTrue(exist);
    }


    private UserCreateRequest getUserCreateRequest() {
        List<String> emails = new ArrayList<>();
        emails.add("julia@gmail.com");

        List<String> phones = new ArrayList<>();
        phones.add("543-6548654");
        return new UserCreateRequest(
                "julia",
                "julia",
                phones,
                emails
        );
    }
}
