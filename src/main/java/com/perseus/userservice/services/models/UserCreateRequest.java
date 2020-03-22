package com.perseus.userservice.services.models;

import javax.validation.constraints.*;
import java.util.List;

public class UserCreateRequest {

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotNull
    private List<@NotBlank(message = "phone number must not be blank")
    @Pattern(regexp = "\\d{3}-\\d{7}", message = "phone number must be like 123-3245632") String> phoneNumbers;

    @NotNull
    private List<@NotBlank(message = "email must not be blank") @Email String> emails;

    public UserCreateRequest() {
    }

    public UserCreateRequest(@NotBlank @Size(max = 100) String lastName,
                             @NotBlank @Size(max = 100) String firstName,
                             @NotNull List<@NotBlank(message = "phone number must not be blank") @Pattern(regexp = "\\d{3}-\\d{7}", message = "phone number must be like 123-3245632") String> phoneNumbers,
                             @NotNull List<@NotBlank(message = "email must not be blank") @Email String> emails) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(List<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }
}
