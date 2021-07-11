<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src='http://libs.baidu.com/jquery/2.1.1/jquery.min.js'></script>
    <script type="text/javascript">

    </script>
    <title>${user.name} - ${room.name}</title>
</head>
<body>
<h1>Welcome to ${user.name} enter room ${room.name}</h1>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Role</th>
        <th>Event</th>
    </tr>
    </thead>
    <tbody>
    <#list room.pokers as poker>
        <tr>
            <td>${poker.id}</td>
            <td>${poker.name}</td>
            <td>${poker.role}</td>
            <td>Votes</td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>