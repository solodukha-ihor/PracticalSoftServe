package com.softserve.itacademy;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.model.ToDo;
import com.softserve.itacademy.service.TaskService;
import com.softserve.itacademy.service.ToDoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.softserve.itacademy.model.User;
import com.softserve.itacademy.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnitPlatform.class)
public class TaskServiceTest {
    private static UserService userService;
    private static TaskService taskService;
    private static ToDoService toDoService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        taskService = annotationConfigContext.getBean(TaskService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        annotationConfigContext.close();
    }

    @BeforeEach
    public void setupBeforeEach() {
        userService.getAll().clear();
    }

    @Test
    public void checkAddTask() {
        User testUser = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(testUser);

        Task task = new Task("Sample Task", Priority.HIGH);

        ToDo todo = new ToDo("Sample ToDo", testUser, new ArrayList<>());

        toDoService.addTodo(todo, testUser);

        Task addedTask = taskService.addTask(task, todo);

        Assertions.assertNotNull(addedTask, "Task should be added successfully");
        Assertions.assertEquals(task, addedTask, "Added task should match the original task");
        Assertions.assertTrue(todo.getTasks().contains(task), "ToDo should contain the added task");
    }

    @Test
    public void checkDeleteTask() {
        User testUser = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(testUser);

        ToDo todo = new ToDo("Sample ToDo", testUser, new ArrayList<>());

        toDoService.addTodo(todo, testUser);

        Task task = new Task("Sample Task", Priority.HIGH);
        taskService.addTask(task, todo);

        taskService.deleteTask(task);

        Assertions.assertTrue(taskService.getByToDo(todo).isEmpty());
    }

    @Test
    public void checkUpdateTask() {
        User testUser = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(testUser);

        ToDo todo = new ToDo("Sample ToDo", testUser, new ArrayList<>());

        toDoService.addTodo(todo, testUser);

        Task task = new Task("Sample Task", Priority.HIGH);
        taskService.addTask(task, todo);

        task.setName("Updated Task");
        Task updatedTask = taskService.updateTask(task);

        Assertions.assertNotNull(updatedTask, "Task should be updated successfully");
        Assertions.assertEquals("Updated Task", updatedTask.getName(), "Task name should be updated");
    }

    @Test
    public void checkGetAll() {
        User testUser = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(testUser);

        ToDo todo1 = new ToDo("ToDo 1", testUser, new ArrayList<>());
        ToDo todo2 = new ToDo("ToDo 2", testUser, new ArrayList<>());

        toDoService.addTodo(todo1, testUser);
        toDoService.addTodo(todo2, testUser);

        Task task1 = new Task("Task 1", Priority.LOW);
        Task task2 = new Task("Task 2", Priority.MEDIUM);

        taskService.addTask(task1, todo1);
        taskService.addTask(task2, todo2);

        List<Task> allTasks = taskService.getAll();

        Assertions.assertEquals(2, allTasks.size(), "Number of tasks should match");
        Assertions.assertTrue(allTasks.contains(task1), "Task 1 should be in the list");
        Assertions.assertTrue(allTasks.contains(task2), "Task 2 should be in the list");
    }

    @Test
    public void checkGetByToDo() {
        User testUser = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(testUser);

        ToDo todo1 = new ToDo("ToDo 1", testUser, new ArrayList<>());
        ToDo todo2 = new ToDo("ToDo 2", testUser, new ArrayList<>());

        toDoService.addTodo(todo1, testUser);
        toDoService.addTodo(todo2, testUser);

        Task task1 = new Task("Task 1", Priority.LOW);
        Task task2 = new Task("Task 2", Priority.MEDIUM);
        Task task3 = new Task("Task 3", Priority.HIGH);

        taskService.addTask(task1, todo1);
        taskService.addTask(task2, todo1);
        taskService.addTask(task3, todo2);

        List<Task> tasksForToDo1 = taskService.getByToDo(todo1);
        List<Task> tasksForToDo2 = taskService.getByToDo(todo2);

        Assertions.assertEquals(2, tasksForToDo1.size(), "Number of tasks for ToDo 1 should match");
        Assertions.assertTrue(tasksForToDo1.contains(task1), "Task 1 should be in the list for ToDo 1");
        Assertions.assertTrue(tasksForToDo1.contains(task2), "Task 2 should be in the list for ToDo 1");
        Assertions.assertEquals(1, tasksForToDo2.size(), "Number of tasks for ToDo 2 should match");
        Assertions.assertTrue(tasksForToDo2.contains(task3), "Task 3 should be in the list for ToDo 1");
    }

    @Test
    public void checkGetByToDoName() {
        User testUser = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(testUser);

        ToDo todo1 = new ToDo("ToDo 1", testUser, new ArrayList<>());
        ToDo todo2 = new ToDo("ToDo 2", testUser, new ArrayList<>());

        toDoService.addTodo(todo1, testUser);
        toDoService.addTodo(todo2, testUser);

        Task task1 = new Task("Task 1", Priority.LOW);
        Task task2 = new Task("Task 2", Priority.MEDIUM);

        taskService.addTask(task1, todo1);
        taskService.addTask(task2, todo2);

        Task taskForToDo1 = taskService.getByToDoName(todo1, "Task 1");
        Task taskForToDo2 = taskService.getByToDoName(todo2, "Task 2");

        Assertions.assertNotNull(taskForToDo1, "Task for ToDo 1 should be found");
        Assertions.assertEquals(task1, taskForToDo1, "Found task for ToDo 1 should match");

        Assertions.assertNotNull(taskForToDo2, "Task for ToDo 2 should be found");
        Assertions.assertEquals(task2, taskForToDo2, "Found task for ToDo 2 should match");
    }

    @Test
    public void testExceptionHandlingInTaskService() {
        // Attempt to add a task without associating it with a ToDo
        Task invalidTask = new Task("Invalid Task", Priority.LOW);

        // Ensure that attempting to add an invalid task throws an exception
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> taskService.addTask(invalidTask, null),
                "Adding a task without a ToDo should throw an exception"
        );
    }

    @Test
    public void checkGetByUserName() {
        User testUser1 = new User("John", "Doe", "john.doe@example.com", "password");
        User testUser2 = new User("Jane", "Lin", "jane.doe@example.com", "password");

        userService.addUser(testUser1);
        userService.addUser(testUser2);

        ToDo todo1 = new ToDo("ToDo 1", testUser1, new ArrayList<>());
        ToDo todo2 = new ToDo("ToDo 2", testUser2, new ArrayList<>());

        toDoService.addTodo(todo1, testUser1);
        toDoService.addTodo(todo2, testUser2);

        Task task1 = new Task("Task 1", Priority.LOW);
        Task task2 = new Task("Task 2", Priority.MEDIUM);

        taskService.addTask(task1, todo1);
        taskService.addTask(task2, todo2);

        Task taskForUser1 = taskService.getByUserName(testUser1, "Task 1");
        Task taskForUser2 = taskService.getByUserName(testUser2, "Task 2");

        Assertions.assertNotNull(taskForUser1, "Task for User 1 should be found");
        Assertions.assertEquals(task1, taskForUser1, "Found task for User 1 should match");

        Assertions.assertNotNull(taskForUser2, "Task for User 2 should be found");
        Assertions.assertEquals(task2, taskForUser2, "Found task for User 2 should match");
    }

    @Test
    public void checkTaskToDoAssociation() {
        User johnDoe = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(johnDoe);

        ToDo todo = new ToDo("Project", johnDoe, null);
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1", Priority.HIGH));
        tasks.add(new Task("Task 2", Priority.MEDIUM));
        tasks.add(new Task("Task 3", Priority.LOW));

        todo.setTasks(tasks);

        toDoService.addTodo(todo, johnDoe);

        ToDo retrievedToDo = toDoService.getByUserTitle(johnDoe, "Project");
        List<Task> retrievedTasks = taskService.getByToDo(retrievedToDo);

        Assertions.assertEquals(tasks.size(), retrievedTasks.size(), "Incorrect number of tasks");
        Assertions.assertTrue(retrievedTasks.containsAll(tasks), "Incorrect task association");
    }
}
