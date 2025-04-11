package ru.evg.mask_logback.models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private String id;
    private String type;
    private List<User> users = new ArrayList<>();

    public Order() {
    }

    public Order(String id, String type, List<User> users) {
        this.id = id;
        this.type = type;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<User> getUser() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
