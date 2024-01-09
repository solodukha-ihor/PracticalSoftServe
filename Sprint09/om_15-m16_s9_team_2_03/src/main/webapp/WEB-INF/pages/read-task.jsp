<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Read existing Task</title>

    <style>
        <%@include file="../styles/main.css"%>
    </style>

</head>
<body>
<% Task task = (Task) request.getAttribute("task"); %>
<table>
    <%@include file="header.html"%>

        <p>ID: <%=task.getId()%></p>
        <p>Title: <%=task.getTitle()%></p>
        <p>Priority: <%=task.getPriority()%></p>
        <button onclick="location.href='/edit-task?id=<%=task.getId()%>'">Update</button>
        <button onclick="location.href='/delete-task?id=<%=task.getId()%>'">Delete</button>



</body>
</html>
