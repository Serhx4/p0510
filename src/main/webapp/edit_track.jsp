<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="editTrackBean" type="com.example.p0510.beans.EditTrackBean" scope="request" />
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>${editTrackBean.title} ${editTrackBean.track.name}</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-Zenh87qX5JnK2Jl0vWa8Ck2rdkQ2Bzep5IDxbcnCeuOxjzrPF/et3URy9Bv1WTRi" crossorigin="anonymous">
</head>
<body>
<div class="container">
    <h1>${editTrackBean.title} ${editTrackBean.track.name}</h1>
    <form class="row g-3" action="${editTrackBean.action}" method="post">
        <div class="col-md-6">
            <label for="trackName" class="form-label">Track name</label>
            <input type="text" class="form-control" id="trackName" name="trackName" value="${editTrackBean.track.name}">
        </div>
        <div class="col-md-6">
            <label for="composer" class="form-label">Composer</label>
            <input type="text" class="form-control" id="composer" name="composer" value="${editTrackBean.track.composer}">
        </div>

        <div class="col-md-4">
            <label for="inputGenre" class="form-label">Genre</label>
            <select id="inputGenre" class="form-select" name="genre">
                <c:forEach items="${editTrackBean.genres}" var="genre">
                    <c:if test="${genre.genreId == editTrackBean.track.genreId}">
                        <option selected value="${genre.genreId}">
                                ${genre.name}
                        </option>
                    </c:if>
                    <option value="${genre.genreId}">${genre.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-4">
            <label for="inputAlbum" class="form-label">Album</label>
            <select id="inputAlbum" class="form-select" name="album">
                <c:forEach items="${editTrackBean.albums}" var="album">
                    <c:if test="${album.id == editTrackBean.track.albumId}">
                        <option selected value="${album.id}">
                                ${album.title}
                        </option>
                    </c:if>
                    <option value="${album.id}">${album.title}</option>
                </c:forEach>
            </select>
        </div>
        <div class="col-md-4">
            <label for="inputType" class="form-label">MediaType</label>
            <select id="inputType" class="form-select" name="mediaType">
                <c:forEach items="${editTrackBean.types}" var="type">
                    <c:choose>
                        <c:when test="${type.mediaTypeId == editTrackBean.track.mediaTypeId}">
                            <option selected value="${type.mediaTypeId}">
                                    ${type.name}
                            </option>
                        </c:when>
                        <c:otherwise>
                            <option value="${type.mediaTypeId}">${type.name}</option>
                        </c:otherwise>
                    </c:choose>
<%--                    <c:if test="">--%>
<%--                    </c:if>--%>
                </c:forEach>
            </select>
        </div>

        <div class="col-md-4">
            <label for="milliseconds" class="form-label">Length (ms)</label>
            <input type="number" class="form-control" id="milliseconds" name="milliseconds" value="${editTrackBean.track.milliseconds}">
        </div>
        <div class="col-md-4">
            <label for="bytes" class="form-label">Size (bytes)</label>
            <input type="number" class="form-control" id="bytes" name="bytes" value="${editTrackBean.track.bytes}">
        </div>
        <div class="col-md-4">
            <label for="price" class="form-label">Price</label>
            <input type="number" step="any" class="form-control" id="price" name="price" value="${editTrackBean.track.unitPrice}">
        </div>
        <div class="col-md-12">
            <input type="hidden" name="trackId" value="${editTrackBean.track.trackId}">
        </div>

        <div class="col-12">
            <button type="submit" class="btn btn-primary">${editTrackBean.title}</button>
        </div>
    </form>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-OERcA2EqjJCMA+/3y+gxIOqMEjwtxJY7qPCqsdltbNJuaOe923+mo//f6V8Qbsw3" crossorigin="anonymous"></script>
</body>
</html>
