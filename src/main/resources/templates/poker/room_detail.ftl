<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src='http://libs.baidu.com/jquery/2.1.1/jquery.min.js'></script>
    <title>${room.name} info</title>
</head>
<body>
<h1>Room ${room.name} Info</h1>

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
            <td>
                <a href="/demo/pokers/${poker.id}/enterRoom/${room.id}" target="_blank">进入房间</a>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>