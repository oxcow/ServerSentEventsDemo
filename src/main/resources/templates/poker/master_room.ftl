<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src='http://libs.baidu.com/jquery/2.1.1/jquery.min.js'></script>
    <script type="text/javascript" src="/base.js"></script>
    <script type="text/javascript">
        let evtSource;
        $(function () {
            $("#flop").click(() => {
                console.log("翻牌");
                $.ajax({
                    url: "/poker/send",
                    datatype: 'json',
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify({action: 'FLOP', roomId: '${room.id}', pokerId: '${user.id}'}),
                    type: 'post',
                    success: function (result) {
                        outPrint('output', "Master click flop Response: " + result);
                    }
                });
            })

            $("#shuffle").click(() => {
                console.log("洗牌");
                $.ajax({
                    url: "/poker/send",
                    type: 'post',
                    contentType: "application/json;charset=utf-8",
                    data: JSON.stringify({action: 'SHUFFLE', roomId: '${room.id}', pokerId: '${user.id}'}),
                    function(result) {
                        outPrint('output', "Master click shuffle Response: " + result);
                    }
                });
            })

            if (typeof (EventSource) !== "undefined") {
                evtSource = new EventSource("/poker/subscribe/${room.id}/${user.id}", {withCredentials: true});

                evtSource.onopen = function (event) {
                    outPrint('output', "connected...");
                }

                evtSource.addEventListener("FLOP", function (e) {
                    const messages = JSON.parse(e.data);
                    messages.map((msg, idx) => {
                        $("#poker_" + msg.pokerId).html(msg.votes);
                    });
                    outPrint('output', event.data, 'green');
                });

                evtSource.addEventListener("SHUFFLE", function (e) {
                    $(".poker_vote").text('');
                    outPrint('output', event.data, 'red');
                    //$("#voteInput").val('');
                });

                evtSource.addEventListener("VOTE", function (event) {
                    const messages = JSON.parse(event.data);
                    messages.map((msg, idx) => {
                        $("#poker_" + msg.pokerId).html(msg.voteStatus);
                    });
                    outPrint('output', event.data, 'blue');
                });

            } else {
                outPrint('output', 'Sorry! No server-sent events support.');
            }

        })
    </script>
    <title>${user.name} - ${room.name}</title>
</head>
<body>
<h1>Welcome to ${user.name} enter room ${room.name}</h1>

<div>
    <button id="flop">翻牌</button>
</div>
<div>
    <button id="shuffle">洗牌</button>
</div>

<table border="1">
    <thead>
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Role</th>
        <th>Votes Result</th>
    </tr>
    </thead>
    <tbody>
    <#list room.pokers as poker>
        <tr>
            <td>${poker.id}</td>
            <td>${poker.name}</td>
            <td>${poker.role}</td>
            <td>
                <div id="poker_${poker.id}" class="poker_vote"></div>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<div id="output"></div>
</body>
</html>