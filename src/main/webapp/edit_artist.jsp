<%--
  Created by IntelliJ IDEA.
  User: eugen
  Date: 05.10.2022
  Time: 18:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit artist</title>
</head>
<body>
    <jsp:useBean id="artist" scope="request" type="com.example.p0510.data.Artist"/>
    <form action="update_artist" method="post">
        <input type="text" name="name" value="${artist.name}">
        <input type="hidden" name="id" value="${artist.id}">
        <input type="submit" value="Обновить">
    </form>
</body>
</html>
