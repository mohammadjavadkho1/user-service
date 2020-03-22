package com.perseus.userservice.controllers;

import com.perseus.userservice.services.UserService;
import com.perseus.userservice.services.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    @Autowired
    UserService userService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel createUser(@RequestBody @Valid UserCreateRequest request) {
        return userService.create(request);
    }

    @GetMapping
    public UserListModel search(@RequestParam("name") String name) {
        return userService.searchByName(name);
    }

    @GetMapping("{id}")
    public UserModel getUser(@PathVariable("id") Integer id) {
        return userService.findById(id);
    }


    @DeleteMapping("{id}")
    public void deleteUser(@PathVariable("id") Integer id) {
        userService.deleteById(id);
    }

    @PostMapping("{id}/phones")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel addPhoneNumber(@PathVariable("id") Integer id, @RequestBody @Valid PhoneNumberCreateModel request) {
        return userService.addPhoneNumber(id, request.getPhoneNumber());
    }

    @PostMapping("{id}/emails")
    @ResponseStatus(HttpStatus.CREATED)
    public UserModel addEmail(@PathVariable("id") Integer id, @RequestBody @Valid EmailCreateModel request) {
        return userService.addEmail(id, request.getEmail());
    }

    @PostMapping("{id}/phones/{phoneId}")
    public UserModel updatePhoneNumber(@PathVariable("id") Integer id,
                                       @RequestBody @Valid PhoneNumberCreateModel request,
                                       @PathVariable("phoneId") Integer phoneId
    ) {
        return userService.updatePhoneNumber(id, phoneId, request.getPhoneNumber());
    }

    @PostMapping("{id}/emails/{emailId}")
    public UserModel updateEmail(@PathVariable("id") Integer id,
                                 @PathVariable("emailId") Integer emailId,
                                 @RequestBody @Valid EmailCreateModel request) {
        return userService.updateEmail(id, emailId, request.getEmail());
    }
}
