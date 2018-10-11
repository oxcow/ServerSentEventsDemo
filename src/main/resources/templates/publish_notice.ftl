<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="/base.js"></script>
    <script src='http://libs.baidu.com/jquery/2.1.1/jquery.min.js'></script>
    <title>Administrator Publish Message</title>
    <script type="text/javascript">
        $(function () {
            $('#pmessage').click(function () {
                $.post("/message/publish",
                        {notice: $('#msginput').val(), ttl: $("#id_ttl").val()},
                        function (result) {
                            outPrint('output', $('#msginput').val());
                            $('#msginput').val('');
                        });
            });
        });
    </script>
</head>
<body>
<h1>Administrator Publish Notice</h1>
<p>
    <label for="id_ttl">TTL:
        <select id="id_ttl">
            <option label="1分钟" value="60" selected/>
            <option label="5分钟" value="300"/>
            <option label="10分钟" value="600"/>
            <option label="30分钟" value="1800"/>
        </select>
    </label>
</p>
<p>
    <label for="msginput">Message:
        <textarea rows="10" cols="80" id="msginput"></textarea>
    </label>
</p>
<p>
    <span><button onclick="cleanPrint('output');">Clean Log</button></span>
    <span><button id="pmessage">Publish</button></span>
</p>

<div id="output"></div>
</body>
</html>