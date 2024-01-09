<%@ page import="com.softserve.itacademy.model.Priority" %>
<%@ page import="com.softserve.itacademy.model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Create new Task</title>

    <style>
        <%@include file="../styles/main.css"%>
    </style>
    
</head>
<body>
    <%@include file="header.html"%>
    <h2>Create new Task</h2>
    <%
        if(null!=request.getAttribute("identicalNames")) {
            %>
            Task with a given name already exists!
    <br>
    <br>
            <%
        }
    %>
    <%
        if(null!=request.getAttribute("emptyName")) {
    %>
            There is no entered name of the task!
    <br>
    <br>
    <%
        }
    %>
<form action="/create-task" method="post">
    <table>
        <tr>
            <td>
                <label for="title">Name: </label>
            </td>
            <td>
                <input type="text" id="title" name="title" value="<%if((request.getAttribute("duplicateTitle") != null)){%><%=request.getAttribute("duplicateTitle")%><%}%>">
            </td>
        </tr>
        <tr>
            <td>
                <label for="priority">Priority: </label>
            </td>
            <td>
                <select name="priority" id="priority">
                    <option value="LOW" <%if((request.getAttribute("duplicatePriority") != null) && request.getAttribute("duplicatePriority").equals("LOW")){ %>selected<%} %>>Low</option>
                    <option value="MEDIUM" <%if((request.getAttribute("duplicatePriority") != null) && request.getAttribute("duplicatePriority").equals("MEDIUM")){ %>selected<%} %>>Medium</option>
                    <option value="HIGH" <%if((request.getAttribute("duplicatePriority") != null) && request.getAttribute("duplicatePriority").equals("HIGH")){ %>selected<%} %>>High</option>
                </select>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" value="Create">
            </td>
            <td>
                <input type="reset" value="Clear">
            </td>
        </tr>
    </table>
</form>


</body>
</html>
