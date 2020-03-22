package com.perseus.userservice.services.models;

import com.perseus.userservice.entities.EmailModel;
import com.perseus.userservice.entities.PhoneNumber;

import java.util.HashSet;
import java.util.Set;

public class UserModel {
    private Integer id;
    private String lastName;
    private String firstName;
    private Set<PhoneNumber> phoneNumbers = new HashSet<>();
    private Set<EmailModel> emails = new HashSet<>();

    public UserModel() {
    }

    public UserModel(Integer id, String lastName, String firstName, Set<PhoneNumber> phoneNumbers, Set<EmailModel> emails) {
        this.id = id;
        this.lastName = lastName;
        this.firstName = firstName;
        this.phoneNumbers = phoneNumbers;
        this.emails = emails;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Set<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(Set<PhoneNumber> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public Set<EmailModel> getEmails() {
        return emails;
    }

    public void setEmails(Set<EmailModel> emails) {
        this.emails = emails;
    }
}
