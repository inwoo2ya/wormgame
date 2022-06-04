let websocket;
let roomName = document.getElementById("Roomname").textContent;
let userName = sessionStorage.getItem("userName");
let sessionId = sessionStorage.getItem("sessionId");

function connect() {
    websocket = new WebSocket("ws://" + window.location.hostname + ":8080/chatHandler");
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onclose = onClose;
}

function disconnect() {
    // msg = document.getElementById("nickname").value;
    // websocket.send(msg + "님이 퇴장하셨습니다");
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "LEAVE", writer: sessionId}))
    websocket.close();
}

function send() {
    msg = document.getElementById("message").value;
    // websocket.send(nickname + ":" + msg);
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "CHAT", writer : sessionId, message : msg}));
    document.getElementById("message").value = "";
}

function onOpen() {
    // two = document.getElementById("two");
    // two.style.display = 'block';
    // websocket.send(nickname + "님 입장하셨습니다.");
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "ENTER", writer : sessionId}));
}

function onMessage(evt) {
    var data = evt.data.slice(1, -1);
    var chatarea = document.getElementById("messageArea");
    chatarea.innerHTML = data + "<br/>" + chatarea.innerHTML
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