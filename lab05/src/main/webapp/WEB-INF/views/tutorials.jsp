<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="UTF-8">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
    <title>Lab05 Tutorial Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/index.css"/>
</head>
<body>
<nav class="navbar navbar-expand-sm navbar-default fixed-top navbar-light">
    <div class="container">
        <a href="/" class="navbar-brand">Lab05</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarText"
                aria-controls="navbarText" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarText">
            <ul class="navbar-nav mr-auto d-md-flex">
                <c:if test='${logged.equalsIgnoreCase("true")}'>
                    <li class="nav-item"><a class="nav-link" href="/account">Account Update</a></li>
                    <li class="nav-item"><a class="nav-link" href="/tutorials">Tutorials </a></li>
                    <c:if test='${role.indexOf("ADMIN") != -1 }'>
                        <li class="nav-item"><a class="nav-link" href="/admin/">Admin</a></li>
                    </c:if>
                    <li class="nav-item"><a class="nav-link" href="/logoff">Logout</a></li>
                </c:if>
                <c:if test='${logged.equalsIgnoreCase("false")}'>
                    <li class="nav-item"><a class="nav-link" href="/login">Login</a></li>
                    <li class="nav-item"><a class="nav-link" href="/register">Register</a></li>
                </c:if>

            </ul>
        </div>
    </div>
</nav>
tutorials page
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
</body>
</html>