<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>signIn</title>
</head>
<body>
    <form action="/signIn" method="post">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <input name="email" placeholder="email" value="admin@gmail.com">
        <input name="password" type="password" placeholder="password" value="qwerty">
        <label for="">
            <input type="checkbox" name="remember-me">запомни меня)
        </label>
        <input type="submit" value="SignIn">
    </form>
</body>
</html>