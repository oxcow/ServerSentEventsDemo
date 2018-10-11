<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login Page | 消息广播测试</title>
</head>
<body>
<h1>消息广播测试</h1>

<ul>
    <li>打开多个页面，选择不同的用户登录</li>
    <li>admin用户进入消息发布页面。发布消息后，其他已登陆用户可以看到其发布的消息</li>
    <li>选择其他用户登录后，点击 Start Event 开始接收消息；点击 Stop Event 停止接收并销毁链接</li>
    <li>系统自动生成消息然后等客户端来取。</li>
    <li>已登陆用户暂停接收后，如果再次开启接收，未过期消息会继续被推送</li>
    <li>已登陆用户点击LoginOut后，待再次登录后可继续接收未过期消息</li>
</ul>
<form action="/login/in" method="post">

    <div style="color:red">${errorMessage!}</div>

    <div>
        <label for="ln">Login Name:</label>
        <select id="ln" name="name">
            <option value="admin">admin</option>
            <option value="jack">jack</option>
            <option value="joe">joe</option>
            <option value="grace">grace</option>
        </select>

        <button type="submit">Login In</button>
    </div>
    <!-- <div>
         <label for="pw">Login Name:</label>
         <input id='pw' type="password" name="pass">
     </div>-->
</form>

</body>
</html>