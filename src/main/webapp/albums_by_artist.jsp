<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 05.10.2022
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Альбомы по артисту</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
    <jsp:useBean id="albumBean" type="com.example.p0510.beans.AlbumBean" scope="request"/>
    <h1>Альбомы исполнителя ${albumBean.artist.name}</h1>
    <table>
        <thead>
        <tr><th>#</th><th>Title</th></tr>
        </thead>
        <tbody>
        <c:forEach items="${albumBean.albums}" var="album">
            <tr>
                <td>${album.id}</td>
                <td><a href="show_tracks_of?id=${album.id}">${album.title}</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</body>
</html>
