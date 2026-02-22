package org.shalash.service;

import org.shalash.object.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    List<User> users = new ArrayList<>();
    UserService() {
        users.add(new User("1", "shalash", "shalash@shalash"));
        users.add(new User("2", "mohamed", "mohamed@shalash"));
        users.add(new User("3", "ahmed", "ahmed@shalash"));
    }
    public User getUser(String id) {
        return users.stream().filter(user -> user.getId().equals(id)).findFirst().orElse(null);
    }

}
