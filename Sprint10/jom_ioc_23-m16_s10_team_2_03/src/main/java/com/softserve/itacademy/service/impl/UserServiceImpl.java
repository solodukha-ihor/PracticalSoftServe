package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

@Service
public class UserServiceImpl implements UserService {

    private final List<User> users;

    public UserServiceImpl() {
        users = new ArrayList<>();
    }

    @Override
    public User addUser(User user) {
        boolean status = users.stream().anyMatch(u -> u.getEmail().equals(user.getEmail()));
        if (!status){
            users.add(user);
        }
        return user;
    }

    @Override
    public User readUser(String email){
        return users.stream().filter(user -> Objects.equals(user.getEmail(), email)).findFirst().orElse(null);
    }

    @Override
    public User updateUser(User user) {
        User oldUser = readUser(user.getEmail());
        if (oldUser != null && !oldUser.equals(user)){
            users.set(users.indexOf(oldUser), user);
        }
        return user;
    }

    @Override
    public void deleteUser(User user) {
        if (user != null){
            users.remove(user);
        }
    }

    @Override
    public List<User> getAll() {
        if (!users.isEmpty()){
            return users;
        }
        return new ArrayList<>();
    }

}
