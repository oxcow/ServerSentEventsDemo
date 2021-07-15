<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Poker</title>
</head>
<body>
<h1>Room List</h1>

<a href="/demo/rooms/create">Add a new Room</a>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Type</th>
        <th>Pokers</th>
    </tr>
    </thead>
    <tbody>
    <#list rooms as room>
        <tr>
            <td>${room.id}</td>
            <td>
                <a href="/demo/rooms/${room.id}" target="_blank">${room.name}</a>
            </td>
            <td>${room.type}</td>
            <td>
                <#list room.pokers as poker>
                    <div>${poker.id} - ${poker.name} - ${poker.role}</div>
                </#list>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
</body>
</html>