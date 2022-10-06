<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 05.10.2022
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Artists</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <jsp:useBean id="artistBean" type="com.example.p0510.beans.ArtistBean" scope="request"/>
    <form action="add_artist" method="post">
        <label for="name">Новый артист:</label>
        <input type="text" name="artistname" id="name">
        <input type="submit" value="Добавить">
    </form>
    <table>
        <thead>
        <tr>
            <th>#</th>
            <th>Артист</th>
            <th>X</th>
            <th>E</th>
            <th>Albums</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${artistBean.artists}" var="a">
            <tr>
                <td>${a.id}</td>
                <td>${a.name}</td>
                <td><a href="delete_artist?id=${a.id}">delete</a></td>
                <td><a href="edit_artist?id=${a.id}">edit</a></td>
                <td><a href="show_albums_of?id=${a.id}">show</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
