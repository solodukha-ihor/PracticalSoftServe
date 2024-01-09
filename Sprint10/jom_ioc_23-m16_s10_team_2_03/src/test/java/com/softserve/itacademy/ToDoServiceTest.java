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
public class ToDoServiceTest {
    private static UserService userService;
    private static ToDoService toDoService;
    private static TaskService taskService;

    @BeforeAll
    public static void setupBeforeClass() throws Exception {
        AnnotationConfigApplicationContext annotationConfigContext = new AnnotationConfigApplicationContext(Config.class);
        userService = annotationConfigContext.getBean(UserService.class);
        toDoService = annotationConfigContext.getBean(ToDoService.class);
        taskService = annotationConfigContext.getBean(TaskService.class);
        annotationConfigContext.close();
    }

    @BeforeEach
    public void setupBeforeEach() {
        userService.getAll().clear();
    }

    @Test
    public void checkAddToDo() {
        User user = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(user);

        ToDo todo = new ToDo("Shopping", user, null);

        ToDo actual = toDoService.addTodo(todo, user);

        Assertions.assertEquals(todo, actual, "Failed to add ToDo");
    }

    @Test
    public void checkUpdateToDo() {
        User user = new User("Alice", "Smith", "alice.smith@example.com", "password");
        userService.addUser(user);

        ToDo todo = new ToDo("Workout", user, null);
        toDoService.addTodo(todo, user);

        todo.setTitle("Exercise");
        ToDo updatedTodo = toDoService.updateTodo(todo);

        Assertions.assertEquals(todo, updatedTodo, "Failed to update ToDo");
    }

    @Test
    public void checkDeleteToDo() {
        User user = new User("Bob", "Johnson", "bob.johnson@example.com", "password");
        userService.addUser(user);

        ToDo todo = new ToDo("Study", user, null);
        toDoService.addTodo(todo, user);

        toDoService.deleteTodo(todo);

        List<ToDo> todos = toDoService.getAll();
        Assertions.assertTrue(todos.isEmpty(), "Failed to delete ToDo");
    }

    @Test
    public void checkGetAllToDos() {
        User user = new User("Charlie", "Brown", "charlie.brown@example.com", "password");
        userService.addUser(user);

        ToDo todo1 = new ToDo("Read", user, null);
        ToDo todo2 = new ToDo("Write", user, null);
        toDoService.addTodo(todo1, user);
        toDoService.addTodo(todo2, user);

        List<ToDo> todos = toDoService.getAll();

        Assertions.assertEquals(2, todos.size(), "Incorrect number of ToDos");
    }

    @Test
    public void checkGetByUser() {
        // Add User 1
        User user1 = new User("Eva", "Johnson", "eva.johnson@example.com", "password");
        userService.addUser(user1);

        // Add ToDos for User 1
        ToDo todo1_user1 = new ToDo("Read", user1, null);
        ToDo todo2_user1 = new ToDo("Write", user1, null);
        toDoService.addTodo(todo1_user1, user1);
        toDoService.addTodo(todo2_user1, user1);

        // Add User 2
        User user2 = new User("Bob", "Williams", "bob.williams@example.com", "password");
        userService.addUser(user2);

        // Add ToDos for User 2
        ToDo todo1_user2 = new ToDo("Study", user2, null);
        ToDo todo2_user2 = new ToDo("Exercise", user2, null);
        toDoService.addTodo(todo1_user2, user2);
        toDoService.addTodo(todo2_user2, user2);

        // Retrieve ToDos for User 1
        List<ToDo> user1ToDos = toDoService.getByUser(user1);

        Assertions.assertEquals(2, user1ToDos.size(), "Incorrect number of ToDos for User 1");
        Assertions.assertTrue(user1ToDos.contains(todo1_user1), "ToDo 1 should be associated with User 1");
        Assertions.assertTrue(user1ToDos.contains(todo2_user1), "ToDo 2 should be associated with User 1");

        // Retrieve ToDos for User 2
        List<ToDo> user2ToDos = toDoService.getByUser(user2);

        Assertions.assertEquals(2, user2ToDos.size(), "Incorrect number of ToDos for User 2");
        Assertions.assertTrue(user2ToDos.contains(todo1_user2), "ToDo 1 should be associated with User 2");
        Assertions.assertTrue(user2ToDos.contains(todo2_user2), "ToDo 2 should be associated with User 2");
    }

    @Test
    public void checkGetByUserTitle() {
        User user = new User("David", "Williams", "david.williams@example.com", "password");
        userService.addUser(user);

        ToDo todo = new ToDo("Gardening", user, null);
        toDoService.addTodo(todo, user);

        ToDo retrievedTodo = toDoService.getByUserTitle(user, "Gardening");

        Assertions.assertEquals(todo, retrievedTodo, "Failed to retrieve ToDo by user and title");
    }

    @Test
    public void checkTaskToDoAssociation() {
        User user = new User("John", "Doe", "john.doe@example.com", "password");
        userService.addUser(user);

        // Create a ToDo with tasks
        ToDo todo = new ToDo("Project", user, null);
        List<Task> tasks = new ArrayList<>();
        tasks.add(new Task("Task 1", Priority.HIGH));
        tasks.add(new Task("Task 2", Priority.MEDIUM));
        tasks.add(new Task("Task 3", Priority.LOW));

        todo.setTasks(tasks);

        // Add the ToDo to the service
        toDoService.addTodo(todo, user);

        // Retrieve the ToDo and verify task association
        ToDo retrievedToDo = toDoService.getByUserTitle(user, "Project");
        List<Task> retrievedTasks = taskService.getByToDo(retrievedToDo);

        // Verify that the retrieved tasks match the original tasks
        Assertions.assertEquals(tasks.size(), retrievedTasks.size(), "Incorrect number of tasks");
        Assertions.assertTrue(retrievedTasks.containsAll(tasks), "Incorrect task association");
    }

    @Test
    public void testAddToDoWithoutUserAssociation() {
        // Attempt to add a ToDo without associating it with a user
        ToDo invalidToDo = new ToDo("Invalid ToDo", null, null);

        // Ensure that attempting to add an invalid ToDo throws an exception
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> toDoService.addTodo(invalidToDo, null),
                "Adding a ToDo without user association should throw an exception"
        );
    }

    @Test
    public void testRetrieveToDoByUserAndTitle() {
        User testUser = new User("Test", "User", "test.user@example.com", "password");
        userService.addUser(testUser);

        ToDo testToDo = new ToDo("Test ToDo", testUser, null);
        toDoService.addTodo(testToDo, testUser);

        ToDo retrievedToDo = toDoService.getByUserTitle(testUser, "Test ToDo");

        Assertions.assertNotNull(retrievedToDo, "ToDo not retrieved");
        Assertions.assertEquals(testToDo, retrievedToDo, "Retrieved ToDo does not match the original ToDo");
    }
}
