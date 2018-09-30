<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="/base.js"></script>
    <script src='http://libs.baidu.com/jquery/2.1.1/jquery.min.js'></script>
    <title>Administrator Post Message</title>
    <script type="text/javascript">
        $(function () {
            $('#pmessage').click(function () {
                $.post("/message/publish",
                        {notice: $('#msginput').val()},
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

<textarea rows="10" cols="80" id="msginput"></textarea>
<div>
    <span><button onclick="cleanPrint('output');">Clean Log</button></span>
    <span><button id="pmessage">Publish</button></span>
</div>

<div id="output"></div>
</body>
</html>