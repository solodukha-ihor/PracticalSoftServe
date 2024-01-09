package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Priority;
import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create-task")
public class CreateTaskServlet extends HttpServlet {

    private TaskRepository taskRepository;
    private boolean isUnique;
    private boolean isNameFilled;


    @Override
    public void init() {
        taskRepository = TaskRepository.getTaskRepository();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/pages/create-task.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String title = request.getParameter("title");
        String priority = request.getParameter("priority");

        isUnique = checkUniqueName(title);
        isNameFilled = !title.isEmpty();

        if (isUnique && isNameFilled) {
            Task task = new Task(title, Priority.valueOf(priority));
            taskRepository.create(task);
            response.sendRedirect("/tasks-list");
        } else if (!isUnique) {
            request.setAttribute("identicalNames", "true");
            request.setAttribute("duplicateTitle", title);
            request.setAttribute("duplicatePriority", priority);
            request.getRequestDispatcher("/WEB-INF/pages/create-task.jsp").forward(request, response);
        } else {
            request.setAttribute("emptyName", "true");
            request.getRequestDispatcher("/WEB-INF/pages/create-task.jsp").forward(request, response);
        }
    }

    private boolean checkUniqueName(String title) {
        for (Task task : taskRepository.all()) {
            if (title.equals(task.getTitle())) {
                return false;
            }
        }
        return true;
    }
}
