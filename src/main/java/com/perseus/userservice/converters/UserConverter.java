package com.perseus.userservice.converters;

import com.perseus.userservice.entities.EmailModel;
import com.perseus.userservice.entities.PhoneNumber;
import com.perseus.userservice.entities.User;
import com.perseus.userservice.services.models.UserCreateRequest;
import com.perseus.userservice.utility.RandomUtility;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;
import java.util.stream.Collectors;

public class UserConverter implements Converter<UserCreateRequest, User> {
    @Override
    public User convert(UserCreateRequest request) {
        Set<EmailModel> emails = request.getEmails()
                .stream()
                .map(it -> new EmailModel(RandomUtility.getRandomInteger(), it))
                .collect(Collectors.toSet());
        Set<PhoneNumber> phoneNumbers = request.getPhoneNumbers()
                .stream()
                .map(it -> new PhoneNumber(RandomUtility.getRandomInteger(), it))
                .collect(Collectors.toSet());

        return new User(
                request.getLastName(),
                request.getFirstName(),
                phoneNumbers,
                emails
        );
    }
}
