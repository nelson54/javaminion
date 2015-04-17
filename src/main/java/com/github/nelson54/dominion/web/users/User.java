package com.github.nelson54.dominion.web.users;

public final class User {

    private final String id;
    private String name;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
