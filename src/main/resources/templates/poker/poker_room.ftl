<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <script src='http://libs.baidu.com/jquery/2.1.1/jquery.min.js'></script>
    <script type="text/javascript" src="/base.js"></script>
    <script type="text/javascript">
      const poker_id = '${user.id}';

      let evtSource;

      $(function () {

        if (typeof (EventSource) !== "undefined") {
          evtSource = new EventSource("/poker/subscribe/${room.id}/${user.id}", {withCredentials: true});

          evtSource.onopen = function (event) {
            outPrint('output', "connected...");
          }

          evtSource.addEventListener("FLOP", function (e) {
            console.log(e == event);
            outPrint('output', event.data, 'green');
          });

          evtSource.addEventListener("SHUFFLE", function (e) {
            console.log(e == event);
            outPrint('output', event.data, 'red');
            //$("#voteInput").val('');
          });

          evtSource.addEventListener("VOTE", function (event) {
            outPrint('output', event.data, 'blue');
          });

        } else {
          outPrint('output', 'Sorry! No server-sent events support.');
        }

        $("#votes").click(() => {
          let vote = $("#voteInput").val();
          console.log(poker_id, "votes:", vote);
          $.post("/poker/vote",
            {roomId: '${room.id}', pokerId: '${user.id}', vote: vote},
            function (result) {
              outPrint('output', "Poker click vote Response: " + result);
            });
        });
      });
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
            <td>
                <#if poker.id == user.id>
                    <input type="range" min="1" max="13" id="voteInput">
                    <button id="votes">投票</button>
                </#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<div id="output"></div>
</body>
</html>