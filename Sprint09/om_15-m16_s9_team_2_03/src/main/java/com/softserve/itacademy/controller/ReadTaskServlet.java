package com.softserve.itacademy.controller;

import com.softserve.itacademy.model.Task;
import com.softserve.itacademy.repository.TaskRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/read-task")
public class ReadTaskServlet extends HttpServlet {

    private TaskRepository taskRepository;

    @Override
    public void init() {
        taskRepository = TaskRepository.getTaskRepository();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int taskId = Integer.parseInt(request.getParameter("id"));
        Task task = taskRepository.read(taskId);
        if (task == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            redirectErrorPage(request, response, taskId);
        } else {
            request.setAttribute("task", task);
            request.getRequestDispatcher("/WEB-INF/pages/read-task.jsp").forward(request, response);
        }

    }
    private void redirectErrorPage(HttpServletRequest request, HttpServletResponse response, int id) throws ServletException, IOException {
        request.setAttribute("id", id);
        request.setAttribute("path", request.getServletPath());
        request.getRequestDispatcher("/WEB-INF/pages/error.jsp").forward(request, response);
    }
}
