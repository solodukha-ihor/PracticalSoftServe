<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>

    <style>
        <%@include file="../styles/main.css"%>
    </style>

</head>
<body>
    <%@include file="header.html" %>

    <h2>Task with ID '<%=request.getAttribute("id")%>' not found in To-Do List!</h2>
    <h4>url: <%=request.getAttribute("path")%></h4>
</body>
</html>
