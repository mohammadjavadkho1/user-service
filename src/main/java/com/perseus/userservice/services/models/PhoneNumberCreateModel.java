package com.perseus.userservice.services.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class PhoneNumberCreateModel {

    @NotBlank(message = "phone number must not be blank")
    @Pattern(regexp = "\\d{3}-\\d{7}", message = "phone number must be like 123-3245632")
    private String phoneNumber;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
