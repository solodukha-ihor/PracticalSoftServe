package com.softserve.itacademy.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.ToDoService;
import com.softserve.itacademy.service.UserService;

@Service
public class ToDoServiceImpl implements ToDoService {

    private UserService userService;

    @Autowired
    public ToDoServiceImpl(UserService userService) {
        this.userService = userService;
    }

    public ToDo addTodo(ToDo todo, User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
            user.getMyTodos().add(todo);
            userService.updateUser(user);
        return todo;
    }

    public ToDo updateTodo(ToDo todo) {
        userService.getAll().stream()
                .flatMap(x -> x.getMyTodos().stream())
                .filter(x -> x.getOwner().equals(todo.getOwner()))
                .filter(x -> x.getTitle().equals(todo.getTitle()))
                .forEach(x -> x.setTasks(todo.getTasks()));

        return todo;
    }

    public void deleteTodo(ToDo todo) {
        userService.getAll().stream()
                .filter(x -> x.getMyTodos().contains(todo))
                .forEach(x -> x.getMyTodos().remove(todo));
    }

    public List<ToDo> getAll() {
        List<ToDo> collect = userService.getAll().stream()
                .flatMap(u -> u.getMyTodos().stream())
                .collect(Collectors.toList());
        return collect;
    }

    public List<ToDo> getByUser(User user) {
        List<ToDo> collect = userService.getAll().stream()
                .filter(x -> x.equals(user))
                .flatMap(u -> u.getMyTodos().stream())
                .collect(Collectors.toList());
        return collect;
    }

    public ToDo getByUserTitle(User user, String title) {
        ToDo collect = userService.getAll()
                .stream()
                .filter(x -> x.equals(user))
                .flatMap(u -> u.getMyTodos().stream().filter(x -> x.getTitle().equals(title)))
                .findFirst().get();
        return collect;
    }

}
