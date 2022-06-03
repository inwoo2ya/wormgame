let websocket;
let roomName = document.getElementById("Roomname").textContent;

function connect() {
    websocket = new WebSocket("ws://localhost:8080/chatHandler");
    websocket.onopen = onOpen;
    websocket.onmessage = onMessage;
    websocket.onclose = onClose;
}

function disconnect() {
    msg = document.getElementById("nickname").value;
    // websocket.send(msg + "님이 퇴장하셨습니다");
    websocket.send(JSON.stringify({chatRoomName : "임시 방제목 1", messageType : "LEAVE", writer: nickname}))
    websocket.close();
}

function send() {
    // nickname = document.getElementById("nickname").value;
    nickname = "temp";
    msg = document.getElementById("message").value;
    // websocket.send(nickname + ":" + msg);
    websocket.send(JSON.stringify({chatRoomName : "임시 방제목 1", messageType : "CHAT", writer : nickname, message : msg}));
    document.getElementById("message").value = "";
}

function onOpen() {
    // nickname = document.getElementById("nickname").value;
    // var nickname = "temp";
    var nickname = "temp";
    // two = document.getElementById("two");
    // two.style.display = 'block';
    // websocket.send(nickname + "님 입장하셨습니다.");
    websocket.send(JSON.stringify({chatRoomName : "임시 방제목 1", messageType : "ENTER", writer : nickname}));
}

function onMessage(evt) {
    var data = evt.data.slice(1, -1);
    var chatarea = document.getElementById("messageArea");
    chatarea.innerHTML = data + "<br/>" + chatarea.innerHTML
}

function onClose() {
}