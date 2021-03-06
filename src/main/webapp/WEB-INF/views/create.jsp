<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

<!-- Bootstrap CSS -->
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css" integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">

<title>Save</title>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href='<c:url value="/" />'>Правонарушения</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/create'/>">Добавить правонарушение</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="#">${user.username}</a>
            </li>
            <li class="nav-item">
                <a class="nav-link" href="<c:url value='/login?logout=true'/>">Выйти</a>
            </li>
        </ul>
    </div>
    <form action="${pageContext.request.contextPath}/save" method="post">
        <div class="form-group">
            <label>Название:</label>
            <input  type="text" class="form-control" name="name">
        </div>
        <div class="form-group">
            <label>Описание:</label>
            <input  type="text" class="form-control" name="text">
        </div>
        <div class="form-group">
            <label>Адресс:</label>
            <input  type="text" class="form-control" name="address">
        </div>
        <div class="form-group">
            <label>Тип:</label>
            <select class="form-control" name="type.id">
                <c:forEach var="type" items="${types}" >
                    <option class="form-control" value="${type.id}">${type.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Статья:</label>
            <select class="form-control" name="rIds" multiple>
                <c:forEach var="rule" items="${rules}" >
                    <option class="form-control" value="${rule.id}">${rule.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </div>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js" integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI" crossorigin="anonymous"></script>

</body>
</html>
