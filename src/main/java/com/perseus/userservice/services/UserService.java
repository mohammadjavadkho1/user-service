package com.perseus.userservice.services;

import com.perseus.userservice.entities.EmailModel;
import com.perseus.userservice.entities.PhoneNumber;
import com.perseus.userservice.entities.User;
import com.perseus.userservice.exceptions.AlreadyExistException;
import com.perseus.userservice.exceptions.EntityNotFoundException;
import com.perseus.userservice.repositories.UserRepository;
import com.perseus.userservice.services.models.UserCreateRequest;
import com.perseus.userservice.services.models.UserListModel;
import com.perseus.userservice.services.models.UserModel;
import com.perseus.userservice.utility.RandomUtility;
import org.springframework.core.convert.ConversionService;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final ConversionService conversionService;

    public UserService(UserRepository userRepository, ConversionService conversionService) {
        this.userRepository = userRepository;
        this.conversionService = conversionService;
    }


    public UserModel create(UserCreateRequest request) {
        User user = conversionService.convert(request, User.class);
        return save(user);
    }

    public UserModel findById(Integer userId) {
        User user = getUser(userId);
        return conversionService.convert(user, UserModel.class);
    }

    public UserListModel searchByName(String name) {
        List<User> users = userRepository.findByFirstNameIgnoreCaseOrLastNameIgnoreCase(name, name);
        List<UserModel> result = users
                .stream()
                .map(it -> conversionService.convert(it, UserModel.class))
                .collect(Collectors.toList());
        return new UserListModel(result);
    }


    public void deleteById(Integer userId) {
        try {
            userRepository.deleteById(userId);
        } catch (EmptyResultDataAccessException ex) {
            throw new EntityNotFoundException("User with id " + userId + " does not exist");
        }
    }

    public UserModel addPhoneNumber(Integer userId, String phoneNumber) {
        User user = getUser(userId);
        user.getPhoneNumbers()
                .forEach(it -> {
                    if (it.getNumber().equalsIgnoreCase(phoneNumber))
                        throw new AlreadyExistException("This phone number already exist in user phone list");
                });
        user.getPhoneNumbers().add(new PhoneNumber(RandomUtility.getRandomInteger(), phoneNumber));
        return save(user);
    }

    public UserModel updatePhoneNumber(Integer userId, Integer phoneId, String newValue) {
        User user = getUser(userId);
        boolean phoneIdExist = user.getPhoneNumbers()
                .stream()
                .anyMatch(it -> it.getId().equals(phoneId));
        if (!phoneIdExist)
            throw new EntityNotFoundException("Phone id not found");

        boolean duplicateNumber = user.getPhoneNumbers()
                .stream()
                .anyMatch(it -> it.getNumber().equalsIgnoreCase(newValue));
        if (duplicateNumber)
            throw new AlreadyExistException("Phone number already exist");

        user.getPhoneNumbers()
                .forEach(it -> {
                    if (it.getId().equals(phoneId)) {
                        it.setNumber(newValue);
                    }
                });
        return save(user);
    }

    public UserModel addEmail(Integer userId, String email) {
        User user = getUser(userId);
        user.getEmails()
                .forEach(it -> {
                    if (it.getEmail().equalsIgnoreCase(email))
                        throw new AlreadyExistException("This email  already exist in user email list");
                });
        user.getEmails().add(new EmailModel(RandomUtility.getRandomInteger(), email));
        return save(user);
    }


    public UserModel updateEmail(Integer userId, Integer emailId, String newValue) {
        User user = getUser(userId);
        boolean emailIdExist = user.getEmails()
                .stream()
                .anyMatch(it -> it.getId().equals(emailId));
        if (!emailIdExist)
            throw new EntityNotFoundException("Email id not found");

        boolean duplicateEmail = user.getEmails()
                .stream()
                .anyMatch(it -> it.getEmail().equalsIgnoreCase(newValue));
        if (duplicateEmail)
            throw new AlreadyExistException("Email already  found");


        user.getEmails()
                .forEach(it -> {
                    if (it.getId().equals(emailId)) {
                        it.setEmail(newValue);
                    }
                });
        return save(user);
    }

    private UserModel save(User user) {
        userRepository.save(user);
        return conversionService.convert(user, UserModel.class);
    }

    private User getUser(Integer userId) {
        return userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException
                ("User not found with id " + userId));
    }
}

