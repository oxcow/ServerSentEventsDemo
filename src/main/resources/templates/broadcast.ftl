<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="/base.js"></script>
    <title>Subscribe | Html5 Server Send Event Demo</title>
</head>
<body>

<h1>Welcome: ${systemUser.name?cap_first} | ${systemUser.role} |
    <a href="/login/out/${systemUser.name}">Login out</a>
</h1>

<script type="text/javascript">
    var evtSource;

    function startEvent() {

        if (typeof(EventSource) !== "undefined") {

            outPrint('output', "ready connect...");

            evtSource = new EventSource("/message/subscribe/${systemUser.name}",{withCredentials:true});

            evtSource.onopen = function (event) {
                outPrint('output', "connected...");
            }

            evtSource.addEventListener("broadcast", function () {
                outPrint('output', event.data, 'green');
            });

            evtSource.addEventListener("admin_notice", function () {
                outPrint('output', event.data, 'blue');
            });

            evtSource.onmessage = function (event) {
                //console.log(event);
                outPrint('output', event.data, 'red');
            }

            evtSource.onerror = function (event) {
                if (this.readyState == 2) {
                    outPrint('output', 'Error:' + this.readyState + ' > ' + JSON.stringify(this));
                    this.close();
                }
            }

            function stopEvent() {
                evtSource.close();
            }

        } else {
            console.warn('Sorry! No server-sent events support.');
        }
    }

    function stopEvent() {
        if (evtSource) {
            evtSource.close();
            outPrint('output', 'disconnected...', 'red');
        }
    }

</script>
<div>
    <span><button onclick="cleanPrint('output');">清理日志</button></span>
    <span><button onclick="startEvent();">开始订阅</button></span>
    <span><button onclick="stopEvent();">结束订阅</button></span>
</div>
<div id="output"></div>
</body>
</html>