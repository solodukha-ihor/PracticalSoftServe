package com.softserve.itacademy.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;

@Service
public class TaskServiceImpl implements TaskService {

    private ToDoService toDoService;

    @Autowired
    public TaskServiceImpl(ToDoService toDoService) {
        this.toDoService = toDoService;
    }

    public Task addTask(Task task, ToDo todo) {
        if (todo == null) {
            throw new IllegalArgumentException("ToDo cannot be null");
        }
        todo.getTasks().add(task);
        return task;
    }

    public Task updateTask(Task task) {
        toDoService.getAll().stream()
                .flatMap(x -> x.getTasks().stream())
                .filter(x -> x.getName().equals(task.getName()))
                .forEach(x -> x.setPriority(task.getPriority()));
        return task;
    }

    public void deleteTask(Task task) {
        toDoService.getAll().stream()
                .filter(x -> x.getTasks().contains(task))
                .forEach(x -> x.getTasks().remove(task));
    }

    public List<Task> getAll() {
        List<Task> collect = toDoService.getAll().stream()
                .flatMap(x -> x.getTasks().stream())
                .collect(Collectors.toList());
        return collect;
    }

    public List<Task> getByToDo(ToDo todo) {
        List<Task> collect = toDoService.getAll().stream()
                .filter(x -> x.equals(todo))
                .flatMap(x -> x.getTasks().stream())
                .collect(Collectors.toList());
        return collect;
    }

    public Task getByToDoName(ToDo todo, String name) {
        Task result = getByToDo(todo).stream()
                .filter(x -> x.getName().equals(name))
                .findFirst().get();
        return result;
    }

    public Task getByUserName(User user, String name) {
        Task result = user.getMyTodos().stream()
                .flatMap(x -> x.getTasks().stream())
                .filter(x -> x.getName().equals(name))
                .findFirst().get();
        return result;
    }

}
