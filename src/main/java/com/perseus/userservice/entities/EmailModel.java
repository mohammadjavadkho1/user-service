package com.perseus.userservice.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class EmailModel {

    private Integer id;

    @NotNull
    @Email
    @Size(max = 100)
    private String email;

    public EmailModel() {
    }

    public EmailModel(Integer id, @NotNull @Email @Size(max = 100) String email) {
        this.id = id;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
