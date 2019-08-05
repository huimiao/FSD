<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" %>
<%@page pageEncoding="UTF-8" %>
<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Jekyll v3.8.5">
    <title>Lab05 Account Update</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.3/examples/checkout/">

    <!-- Bootstrap core CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
          integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="/static/index.css"/>

</head>
<body class="bg-light">
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

<div class="container">
    <div class="py-5 text-center">
        <h2>Account Update</h2>
        <p class="lead">Please input the following information to register</p>
    </div>

    <div class="row">
        <div class="col-md-12 order-md-1">
            <h4 class="mb-3">User Information</h4>
            <form class="needs-validation" novalidate>
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="firstName">First name</label>
                        <input type="text" class="form-control" id="firstName" placeholder="" value="${user.first_name}"
                               required>
                        <div class="invalid-feedback">
                            Valid first name is required.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="lastName">Last name</label>
                        <input type="text" class="form-control" id="lastName" placeholder="" value="${user.last_name}"
                               required>
                        <div class="invalid-feedback">
                            Valid last name is required.
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="username">Username</label>
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <span class="input-group-text">@</span>
                        </div>
                        <input disabled type="text" class="form-control" id="username" placeholder="Username"
                               value="${user.username}" required>
                        <input type="hidden" class="form-control" id="username_h" value="${user.username}">
                        <div class="invalid-feedback" style="width: 100%;">
                            Your username is required.
                        </div>
                    </div>
                </div>

                <div class="mb-3">
                    <label for="email">Email</label>
                    <input type="email" class="form-control" id="email" placeholder="you@example.com"
                           value="${user.email}">
                    <div class="invalid-feedback">
                        Please enter a valid email address.
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label for="password">Password</label>
                        <input type="password" class="form-control" id="password" required>
                        <div class="invalid-feedback">
                            Please enter password.
                        </div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label for="repeatPassword">Password</label>
                        <input type="password" class="form-control" id="repeatPassword" required>
                        <div class="invalid-feedback">
                            Please repeat password.
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="col-9">
                        <input id="captcha" type="text" class="form-control" placeholder="Captcha">
                    </div>
                    <div class="col-3">
                        <img src="/captcha" onclick="this.src=this.src+'&k='+Math.random();">
                    </div>
                </div>
                <hr class="mb-4">

                <button class="btn btn-primary btn-lg btn-block" type="button" onclick="register()">Submit</button>
            </form>
        </div>
    </div>

    <footer class="my-5 pt-5 text-muted text-center text-small">
        <p class="mb-1">2019-2019</p>
    </footer>
</div>
<script src="https://code.jquery.com/jquery-3.3.1.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
        integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
        integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
        crossorigin="anonymous"></script>
<script>
    function register() {
        var firstName = $("#firstName").val();
        var lastName = $("#lastName").val();
        var username = $("#username_h").val();
        var password = $("#password").val();
        var repeatPassword = $("#repeatPassword").val();
        var email = $("#email").val();
        var captcha = $("#captcha").val();

        if (firstName && lastName && password && username && captcha && email) {
            if (password !== repeatPassword) {
                alert("Input same password");
                return;
            }

            $.ajax({
                type: "put",
                url: "/account",
                data: JSON.stringify({
                    "username": username,
                    "password": password,
                    "first_name": firstName,
                    "last_name": lastName,
                    "email": email,
                    "captcha": captcha
                }),
                contentType: "application/json",
                dataType: "json",
                statusCode: {
                    401: function () {
                        alert("Check your input values");
                    },
                    200: function () {
                        window.location.href = "/";
                    },
                    500: function () {
                        alert("Error occured when submitting the request.");
                    }
                }
            });
        } else {
            alert("Input all fields");
        }

        return;
    }
</script>
</body>
</html>
