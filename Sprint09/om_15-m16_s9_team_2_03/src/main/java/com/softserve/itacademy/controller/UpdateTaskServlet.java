package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@WebServlet("/edit-task")
public class UpdateTaskServlet extends HttpServlet {
    private TaskRepository taskRepository;
    private Task task;

    @Override
    public void init() {
        taskRepository = TaskRepository.getTaskRepository();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        var id = getIdFromRequest(request);
        initTaskUsingId(id);

        if (Objects.isNull(task)) {
            redirectErrorPage(request, response, id);
        } else {
            openEditPage(request, response);
        }
    }


    private static int getIdFromRequest(HttpServletRequest request) {
        return Integer.parseInt(request.getParameter("id"));
    }

    private void initTaskUsingId(int id) {
        task = taskRepository.read(id);
    }

    private void redirectErrorPage(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        request.setAttribute("path", request.getServletPath());
        response.setStatus(404);
        request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
    }

    private void openEditPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("task", task);
        request.getRequestDispatcher("/WEB-INF/pages/edit-task.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        initTaskIfNull(request);
        String oldTitle = task.getTitle();
        Priority oldPriority = task.getPriority();

        setDataToTask(request);

        if (taskRepository.update(task)) {
            redirectToTasks(response);
        } else{
            redirectToEditPage(request, response);
            setDataToTask(oldTitle,oldPriority);
        }

    }

    private void initTaskIfNull(HttpServletRequest request) {
        if (task == null) {
            int id = getIdFromRequest(request);
            initTaskUsingId(id);
        }
    }
    private void setDataToTask(HttpServletRequest request) {
        task.setTitle(request.getParameter("title"));
        task.setPriority(Priority.valueOf(request.getParameter("priority")));
    }

    private void setDataToTask(String title, Priority priority) {
        task.setTitle(title);
        task.setPriority(priority);
    }

    private static void redirectToTasks(HttpServletResponse response) throws IOException {
        response.sendRedirect("/tasks-list");
    }

    private void redirectToEditPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("isPresent", true);
        openEditPage(request, response);
        response.setContentType("text/html;charset=UTF-8");
    }
}