<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script type="text/javascript" src="/base.js"></script>
    <title>Html5 Server Send Event Demo</title>
</head>
<body>
<h1>Welcome: ${user.name} | ${user.role} |
    <button onclick="loginOut('${user.name}')">LoginOut</button>
</h1>

<script type="text/javascript">
    var evtSource;

    function startEvent() {
        if (typeof(EventSource) !== "undefined") {

            evtSource = new EventSource("/message/broadcast/${user.name}");

            evtSource.onopen = function (event) {
                // outPrint('output', "connected");
            }

            evtSource.addEventListener("broadcast", function () {
                outPrint('output', event.data, 'green');
            });

            evtSource.addEventListener("admin_notice", function () {
                outPrint('output', event.data, 'blue');
            });

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
        }
    }
</script>
<div>
    <span><button onclick="cleanPrint('output');">Clean Log</button></span>
    <span><button onclick="startEvent();">Start Event</button></span>
    <span><button onclick="stopEvent();">Stop Event</button></span>
</div>
<div id="output"></div>
</body>
</html>