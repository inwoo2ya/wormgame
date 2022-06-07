let websocket;
let roomName = document.getElementById("Roomname").textContent;
let userName = sessionStorage.getItem("userName");
let sessionId = sessionStorage.getItem("sessionId");

let player1Name;
let player2Name;
let player3Name;
let player4Name;

let player1SessionId;
let player2SessionId;
let player3SessionId;
let player4SessionId;

let playerCount;
let isGamePlaying = false;

function connect() {
    websocket = new WebSocket("ws://" + window.location.hostname + ":8080/chatHandler");
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onclose = onClose;
}

function disconnect() {
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "LEAVE", writer: sessionId}))
    websocket.close();
}

function sendPressEnter(e) { //2022.06.06 채팅 엔터 구현
    if(e.keyCode === 13) {
        send();
    }
}

function send() {
    msg = document.getElementById("message").value;
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "CHAT", writer : sessionId, message : msg}));
    document.getElementById("message").value = "";
}

function onOpen() {
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "ENTER", writer : sessionId}));
}

function onMessage(evt) {
    var data = evt.data.slice(1, -1);
    if (data == "EVENT_INITIALIZE") {
        isGamePlaying = true;
    } else if (!data.indexOf("EVENT_PLAYER_NAME") || !data.indexOf("EVENT_PLAYER_SESSIONID") || !data.indexOf("EVENT_PLAYER_COUNT"))
        currentRoomPlayer(data);
    else {
        var chatarea = document.getElementById("messageArea");
        chatarea.innerHTML = chatarea.innerHTML + "<br/>" + data
    }
}

function onClose() {
    disconnect();
}

window.onbeforeunload = function() {
    disconnect();
}

function gameStartSend() {
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "GAMESTART", writer : sessionId}));
}

function currentRoomPlayer(str) {
    if (!str.indexOf("EVENT_PLAYER_NAME1"))
        player1Name = str.substr(21);
    else if (!str.indexOf("EVENT_PLAYER_NAME2"))
        player2Name = str.substr(21);
    else if (!str.indexOf("EVENT_PLAYER_NAME3"))
        player3Name = str.substr(21);
    else if (!str.indexOf("EVENT_PLAYER_NAME4"))
        player4Name = str.substr(21);
    else if (!str.indexOf("EVENT_PLAYER_SESSIONID1"))
        player1SessionId = str.substr(26);
    else if (!str.indexOf("EVENT_PLAYER_SESSIONID2"))
        player2SessionId = str.substr(26);
    else if (!str.indexOf("EVENT_PLAYER_SESSIONID3"))
        player3SessionId = str.substr(26);
    else if (!str.indexOf("EVENT_PLAYER_SESSIONID4"))
        player4SessionId = str.substr(26);
    else if (!str.indexOf("EVENT_PLAYER_COUNT"))
        playerCount = str.substr(21);
    
    if (sessionId == player1SessionId && !isGamePlaying && playerCount > 1)
        startBtnToggle(true);
    else
        startBtnToggle(false);
}