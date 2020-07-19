<%@ page import="ru.job4j.accident.model.Rule" %>
<%@ page import="java.util.Collection" %>
<%@ page import="ru.job4j.accident.model.Accident" %>
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
        </ul>
    </div>
    <form action="${pageContext.request.contextPath}/save" method="post">
        <input type="hidden" name="id" value="${accident.id}">
        <div class="form-group">
            <label>Название:</label>
            <input  type="text" class="form-control" name="name" value="${accident.name}">
        </div>
        <div class="form-group">
            <label>Описание:</label>
            <input  type="text" class="form-control" name="text" value="${accident.text}">
        </div>
        <div class="form-group">
            <label>Адресс:</label>
            <input  type="text" class="form-control" name="address" value="${accident.address}">
        </div>
        <div class="form-group">
            <label>Тип:</label>
            <select class="form-control" name="type.id">
                <c:forEach var="type" items="${types}" >
                    <c:if test="${type.id == accident.type.id}">
                        <option class="form-control" value="${type.id}" selected>${type.name}</option>
                    </c:if>
                    <c:if test="${type.id != accident.type.id}">
                        <option class="form-control" value="${type.id}">${type.name}</option>
                    </c:if>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label>Статья:</label>
            <select class="form-control" name="rIds" multiple>
                <%
                    Accident accident = (Accident) request.getAttribute("accident");
                    Collection<Rule> accidentRules = accident.getRules();
                    Collection<Rule> allRules = (Collection<Rule>) request.getAttribute("rules");
                    for (Rule rule : allRules) {
                        if (accidentRules.contains(rule)) { %>
                            <option class="form-control" value="<%=rule.getId()%>" selected><%=rule.getName()%></option>
                    <% } else { %>
                             <option class="form-control" value="<%=rule.getId()%>"><%=rule.getName()%></option>
                    <% } %>
                <% } %>
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
