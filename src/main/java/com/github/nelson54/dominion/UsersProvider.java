package com.github.nelson54.dominion;

import java.util.HashMap;
import java.util.Map;

public class UsersProvider {

    Map<String, User> users;

    public UsersProvider() {
        this.users = new HashMap<>();
    }

    public User getUserById(String id){
        return users.get(id);
    }

    public void addUser(User user){
        users.put(user.getId(), user);
    }
}
