package com.perseus.userservice.converters;

import com.perseus.userservice.entities.User;
import com.perseus.userservice.services.models.UserModel;
import org.springframework.core.convert.converter.Converter;

public class UserModelConverter implements Converter<User, UserModel> {

    @Override
    public UserModel convert(User user) {
        return new UserModel(
                user.getId(),
                user.getLastName(),
                user.getFirstName(),
                user.getPhoneNumbers(),
                user.getEmails()
        );
    }
}
