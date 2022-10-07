<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="trackBean" type="com.example.p0510.beans.TrackBean" scope="request" />
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${trackBean.album.title}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>

<div class="container">
    <h1>Album ${trackBean.album.title}</h1>
    <table class="table table-striped table-hover table-info">
        <thead class="table-dark">
        <tr>
            <th scope="col">Id</th>
            <th scope="col">Track Name</th>
            <th scope="col">Genre</th>
            <th scope="col">Composer</th>
            <th scope="col">Length (ms)</th>
            <th scope="col">Size (bytes)</th>
            <th scope="col">Price ($)</th>
            <th scope="col"></th>
            <th scope="col"></th>
        </tr>
        </thead>
        <tbody class="table-group-divider">
        <c:forEach items="${trackBean.tracks}" var="t">
            <tr style="transform: rotate(0);" >
                <td>${t.trackId}</td>
                <td>${t.name}</td>
                <c:forEach items="${trackBean.genres}" var="genre">
                    <c:if test="${t.genreId == genre.genreId}">
                        <td>${genre.name}</td>
                    </c:if>
                </c:forEach>
                <td>${t.composer}</td>
                <td>${t.milliseconds}</td>
                <td>${t.bytes}</td>
                <td>${t.unitPrice}</td>
<%--                <td><a class="stretched-link" href="edit_track?id=${t.trackId}">${t.trackId}</a></td>--%>
                <td><a class="btn btn-info" href="edit_track?id=${t.trackId}">Edit</a></td>
                <td>
                    <form method="post" action="delete_track">
                        <input type="hidden" value="${t.trackId}" name="trackId">
                        <input type="hidden" value="${trackBean.album.id}" name="albumId">
                        <button class="btn btn-danger" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <div class="col-md-4">
        <a class="btn btn-info" href="add_track?albumId=${trackBean.album.id}">Add new Track</a>
    </div>

</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>
