<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page import="com.softserve.itacademy.model.Priority" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit existing Task</title>

    <style>
        <%@include file="../styles/main.css"%>
    </style>

</head>
<body>
    <%@include file="header.html"%>


    <% Task task = (Task)request.getAttribute("task");
        String priority = task.getPriority().name();
        Boolean isPresent = (Boolean) request.getAttribute("isPresent");
    %>

    <h1>Edit existing Task</h1>

    <% if (isPresent != null && isPresent) { %>
    <h2>Error: Task with a given name already exists!</h2>
    <% } %>

    <form method="post">
        <table>
            <tr>
                <td><label for="id">Id:</label></td>
                <td><input  id="id" name="id" type="text" value="<%=task.getId()%>" disabled></td>
            </tr>
            <tr>
                <td><label for="name">Name:</label></td>
                <td><input id="name" name="title" value="<%=task.getTitle()%>" type="text"> </td>
            </tr>
           <tr>
                <td><label for="priority">Priority:</label></td>
                <td>
                    <select name="priority" id="priority">
                        <option value="LOW" <%= priority.equals("LOW") ? "selected": "" %>>Low</option>
                        <option value="MEDIUM" <%= priority.equals("MEDIUM") ? "selected": "" %>>Medium</option>
                        <option value="HIGH" <%= priority.equals("HIGH") ? "selected": "" %>>High</option>
                    </select>
                </td>
           </tr>
            <tr>
                <td><input value="Update" type="submit"></td>
            </tr>
        </table>
    </form>
</body>
</html>