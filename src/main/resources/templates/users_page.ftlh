<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Document</title>
</head>
<body>
    <div>
        <h1>Hello</h1>
        <h2>Spring Boot!.. is ready</h2>

        <a href="/logout">Выйти</a>
        
        <h3>All users are:</h3>
        <div>
            <table>
                <tr>
                    <th>firstName</th>
                    <th>lastName</th>
                </tr>
                <#list users as user>
                    <tr>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                    </tr>
                </#list>
            </table>
        </div>
    </div>

</body>
</html>