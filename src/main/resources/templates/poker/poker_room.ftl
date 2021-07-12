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
            const messages = JSON.parse(e.data);
            messages.map((msg, idx) => {
              $("#poker_" + msg.pokerId).html(msg.votes);
            });
            outPrint('output', event.data, 'green');
          });

          evtSource.addEventListener("SHUFFLE", function (e) {
            $(".poker_vote").text('');
            outPrint('output', event.data, 'red');
            $("#voteInput").val('');
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

        $("#votes").click(() => {
          let vote = $("#voteInput").val();
          if(!vote){
            alert("please vote");
            return;
          }
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
                    <select id="voteInput">
                        <option value="">Vote</option>
                        <option value="1">1</option>
                        <option value="2">2</option>
                        <option value="3">3</option>
                        <option value="5">5</option>
                        <option value="8">8</option>
                        <option value="13">13</option>
                        <option value="21">21</option>
                    </select>
                    <button id="votes">投票</button>
                <#else>
                <div id="poker_${poker.id}" class="poker_vote"></div>
                </#if>
            </td>
        </tr>
    </#list>
    </tbody>
</table>
<div id="output"></div>
</body>
</html>