<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page</title>
</head>
<body>
<h1>消息广播测试</h1>

<ul>
    <li>打开多个页面，选择不同的用户登录</li>
    <li>admin用户进入消息发布页面。发布消息后，其他已登陆用户可以看到其发布的消息</li>
    <li>选择其他用户登录后，点击 Start Event 开始接收消息；点击 Stop Event 停止接收</li>
    <li>系统自动生成消息然后等客户端来取。</li>
    <li>已登陆用户暂停接收后，如果再次开启接收，之前没有接收过的未过期消息会被继续推送</li>
    <li>已登陆用户点击LoginOut按钮后，见不能获得消息。待再次登录后可继续接收未过期消息</li>
</ul>
<form action="/login/in" method="post">

    <div style="font-color:red">${errorMessage!}</div>

    <div>
        <label for="ln">Login Name:</label>
        <select id="ln" name="name">
            <option value="admin">admin</option>
            <option value="jack">jack</option>
            <option value="joe">joe</option>
            <option value="grace">grace</option>
        </select>
    </div>
    <!-- <div>
         <label for="pw">Login Name:</label>
         <input id='pw' type="password" name="pass">
     </div>-->

    <div>
        <button type="submit">Login In</button>
    </div>

</form>

</body>
</html>