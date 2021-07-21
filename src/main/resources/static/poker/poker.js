// https://ionicabizau.github.io/emoji.css/#
// https://picturepan2.github.io/spectre/utilities/colors.html
const ACTIONS = {
    FLOP: "FLOP",
    SHUFFLE: "SHUFFLE",
    VOTE: "VOTE",
    ONLINE: "ONLINE",
}

const flopEvent = (roomId, pokerId) => {
    console.log("翻牌");
    $.ajax({
        url: "/demo/pokers/cmd",
        datatype: "json",
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({action: ACTIONS.FLOP, roomId: roomId, pokerId: pokerId}),
        type: 'POST',
        success: function (result) {
            console.log("send flop cmd success", result);
        }
    });
}

const shuffleEvent = (roomId, pokerId) => {
    console.log("洗牌");
    $.ajax({
        url: "/demo/pokers/cmd",
        type: 'post',
        contentType: "application/json;charset=utf-8",
        data: JSON.stringify({action: ACTIONS.SHUFFLE, roomId: roomId, pokerId: pokerId}),
        function(result) {
            outPrint('output', "Master click shuffle Response: " + result);
        }
    });
}

const afterVoted = (pokerId, vote) => {
    const className = ".poker_" + pokerId;
    $(className).find(".card").addClass('bg-primary');
    $(className).find(".card-body").html(vote);
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

const voteEvent = (roomId, pokerId, vote) => {
    $.post(
        "/demo/pokers/" + pokerId + "/vote",
        {roomId: roomId, pokerId: pokerId, vote: vote},
        function (result) {
            console.log(pokerId, "vote ", vote, " response: ", result);
            //afterVoted(pokerId, vote);
        }
    )
}

const eventSourceOpen = (e) => {
    console.log("connected...", e);
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
    const className = ".poker_" + message.pokerId;
    $(className).find(".card-body").html('<span class="ec ec-100"></span>');
}

const onlineListener = (e) => {
    console.log("online listener...", e.data);
    const message = JSON.parse(e.data);
    console.log(e.data, " online !!!");
}

const eventSource = (url) => {
    if (typeof (EventSource) !== "undefined") {
        const evtSource = new EventSource(url, {withCredentials: true});

        evtSource.onopen = eventSourceOpen;

        evtSource.addEventListener(ACTIONS.FLOP, flopListener);
        evtSource.addEventListener(ACTIONS.SHUFFLE, shuffleListener);
        evtSource.addEventListener(ACTIONS.VOTE, voteListener);
        evtSource.addEventListener(ACTIONS.ONLINE, onlineListener);

    } else {
        console.error("Sorry! No server-sent events support.");
    }
}