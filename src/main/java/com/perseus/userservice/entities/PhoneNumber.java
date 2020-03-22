package com.perseus.userservice.entities;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
public class PhoneNumber {

    private Integer id;

    @NotNull
    @Size(max = 11)
    private String number;

    public PhoneNumber() {
    }

    public PhoneNumber(Integer id, @NotNull @Size(max = 11) String number) {
        this.id = id;
        this.number = number;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
