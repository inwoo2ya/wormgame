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
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "LEAVE", writer: sessionId}))
    websocket.close();
}
function sendMessage(e){ //2022.06.06 채팅 엔터 구현
        if(e.keyCode === 13){
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

    } else {
        var chatarea = document.getElementById("messageArea");
        chatarea.innerHTML = data + "<br/>" + chatarea.innerHTML
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
