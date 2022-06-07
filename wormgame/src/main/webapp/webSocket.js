let websocket;
let roomName = document.getElementById("Roomname").textContent;
let userName = sessionStorage.getItem("userName");
let sessionId = sessionStorage.getItem("sessionId");

let playerName = [];
let playerSessionId = [];

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
    if (msg.length > 0) {
        websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "CHAT", writer : sessionId, message : msg}));
        document.getElementById("message").value = "";
    }
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
    if(!str.indexOf("EVENT_PLAYER_NAME"))
        playerName[str[17]] = str.substr(21);
    else if(!str.indexOf("EVENT_PLAYER_SESSIONID"))
        playerSessionId[str[22]] = str.substr(26);
    else if (!str.indexOf("EVENT_PLAYER_COUNT"))
        playerCount = str.substr(21);
    
    if (sessionId == playerSessionId[1] && !isGamePlaying && playerCount > 1)
        startBtnToggle(true);
    else
        startBtnToggle(false);
}