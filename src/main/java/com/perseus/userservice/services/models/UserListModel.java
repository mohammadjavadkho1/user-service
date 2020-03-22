package com.perseus.userservice.services.models;

import java.util.List;

public class UserListModel {
    private List<UserModel> users;

    public UserListModel() {
    }

    public UserListModel(List<UserModel> users) {
        this.users = users;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}
