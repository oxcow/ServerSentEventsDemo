// https://ionicabizau.github.io/emoji.css/#
// https://picturepan2.github.io/spectre/utilities/colors.html
const ACTIONS = {
  FLOP: "FLOP",
  SHUFFLE: "SHUFFLE",
  VOTE: "VOTE",
  ONLINE: "ONLINE",
  OFFLINE: "OFFLINE",
  CANCEL: "CANCEL",
}

const sendCmd = (dataObject, callback) => {
  console.log("send command dataObject=", dataObject);
  $.ajax({
    url: "/demo/pokers/cmd",
    datatype: "json",
    contentType: "application/json;charset=utf-8",
    data: JSON.stringify(dataObject),
    type: 'POST',
    success: callback,
  });
}

const flopEvent = (roomNo, pokerId) => {
  const action = ACTIONS.FLOP;
  sendCmd({roomNo, pokerId, action}, (result) => {
    console.log("Send Flop cmd success", result);
  });
}

const shuffleEvent = (roomNo, pokerId) => {
  const action = ACTIONS.SHUFFLE;
  sendCmd({roomNo, pokerId, action}, (result) => {
    console.log("Send Shuffle cmd success", result);
  });
}

const afterFlop = (pokerId, vote) => {
  const className = ".poker_" + pokerId;
  $(className).find(".card").addClass('bg-success');
  $(className).find(".card-body").html(vote);
}
const afterShuffle = () => {
  $(".card").removeClass('bg-primary');
  $(".card").removeClass('bg-success');
  $(".card-body").html('<span class="ec ec-zzz"></span>');
}
const afterVoted = (pokerId) => {
  $(`.poker_${pokerId}`).find(".card-body").html('<span class="ec ec-100"></span>');
}
const afterCancelVoted = (pokerId) => {
  $(`.poker_${pokerId}`).find(".card-body").html('<span class="ec ec-zzz"></span>');
}

const voteEvent = (roomNo, pokerId, vote) => {
  const data = {roomNo, pokerId, vote};
  $.post("/demo/pokers/" + pokerId + "/vote",
    data,
    function (result) {
      console.log(pokerId, "vote ", vote, " response: ", result);
      afterVoted(pokerId);
    }
  )
}

const cancelEvent = (roomId, pokerId) => {
  const data = {roomNo, pokerId};
  $.post("/demo/pokers/" + pokerId + "/vote",
    data,
    function (result) {
      afterCancelVoted(pokerId);
    }
  )
}

const flopListener = (e) => {
  const messages = JSON.parse(e.data);
  messages.map((msg, idx) => {
    afterFlop(msg.pokerId, msg.votes);
  });
  console.log("flop listener...", messages);
}

const shuffleListener = (e) => {
  console.log("shuffle listener...", e.data);
  afterShuffle();
}

const voteListener = (e) => {
  console.log("vote listener...", e.data);
  const message = JSON.parse(e.data);
  afterVoted(message.pokerId);
}

const cancelListener = (e) => {
  console.log("cancel vote listener...", e.data);
  const message = JSON.parse(e.data);
  afterCancelVoted(message.pokerId);
}

const onlineListener = (e) => {
  const poker = JSON.parse(e.data);
  console.log(e.data, " online !!!");
  const pokerHtml = `
        <div class="column col-2 col-sm-6 col-md-4 col-lg-3 poker_${poker.id}">
            <div class="card m-1">
                <div class="card-header">
                    <div class="card-title">${poker.name}</div>
                </div>
                <div class="card-body text-center">
                    <span class="ec ec-zzz"></span>
                </div>
            </div>
        </div>`;
  $("#pokers").append($(pokerHtml));
}

const offlineListener = (e) => {
  console.log("off line", e.data);
  const message = JSON.parse(e.data);
  $(`.poker_${message.pokerId}`).remove();
}

let eveSource, _roomNo, _pokerId;

const eventSource = (roomNo, pokerId, url) => {

  if (typeof (EventSource) !== "undefined") {
    _roomNo = roomNo;
    _pokerId = pokerId;
    evtSource = new EventSource(url, {withCredentials: true});

    // 第一次服务器发送消息到客户端时触发。因此不能在该回调方法中发送上线信息。
    evtSource.addEventListener("open", function (e) {
      console.log("connected .."+ e.currentTarget.url);
      // sendCmd(_roomNo, _pokerId, ACTIONS.ONLINE, (result) => {
      //   console.log("Send Online cmd success");
      // });
    }, false);

    evtSource.addEventListener(ACTIONS.FLOP, flopListener);
    evtSource.addEventListener(ACTIONS.SHUFFLE, shuffleListener);
    evtSource.addEventListener(ACTIONS.VOTE, voteListener);
    evtSource.addEventListener(ACTIONS.ONLINE, onlineListener);
    evtSource.addEventListener(ACTIONS.OFFLINE, offlineListener);
    evtSource.addEventListener(ACTIONS.CANCEL, cancelListener);

    return evtSource;

  } else {
    console.error("Sorry! No server-sent events support.");
  }
}

const handleOfflineEvent = (roomNo, pokerId) => {
  const httpRequest = new XMLHttpRequest();
  httpRequest.open('PUT', `/demo/pokers/${pokerId}/room/${roomNo}`, true);
  httpRequest.send();
}

window.addEventListener('beforeunload', (event) => {
  evtSource.close();
  handleOfflineEvent(_roomNo, _pokerId);
});