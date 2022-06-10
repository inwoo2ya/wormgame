let websocket;
let roomName = document.getElementById("Roomname").textContent;
let userName = sessionStorage.getItem("userName");
let sessionId = sessionStorage.getItem("sessionId");

let playerName = [];
let playerSessionId = [];

let playerCount;
let isGamePlaying = false;

const BOARD_SIZE = 10;
let board;

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
        if (msg.indexOf("EVENT_"))
            websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "CHAT", writer : sessionId, message : msg}));
        else {
            var chatarea = document.getElementById("messageArea");
            var errMsg = "SYSTEM : EVENT_로 시작하는 메시지는 보낼 수 없습니다."
            chatarea.innerHTML = chatarea.innerHTML + "<br/>" + errMsg;
        }
        document.getElementById("message").value = "";
    }
}

function onOpen() {
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "ENTER", writer : sessionId}));
}

function onMessage(evt) {
    var data = evt.data.slice(1, -1);
    console.log(data);
    if (data == "EVENT_INITIALIZE") {
        isGamePlaying = true;
        initializeBoard();
        makeViewBoard();
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
    else if (!str.indexOf("EVENT_PLAYER_COUNT")) {
        playerCount = str.substr(21);
        displayPlayerName(playerCount);
    }
    
    if (sessionId == playerSessionId[1] && !isGamePlaying && playerCount > 1)
        startBtnToggle(true);
    else
        startBtnToggle(false);
}

function displayPlayerName(userCount) {
    var i;
    for (i = 1 ; i <= userCount ; i++)
        document.getElementById("playerName" + i).textContent = playerName[i];
    for ( ; i <=4 ; i++)
        document.getElementById("playerName" + i).textContent = "";
}

function initializeBoard() {
    board = [];
    for (var i=0 ; i<BOARD_SIZE ; i++) {
        board[i] = [];
        for (var j=0 ; j<BOARD_SIZE ; j++)
            board[i][j] = 0;
    }
    board[0][0] = 'h';
    board[0][1] = board[0][2] = 't';
    board[1][0] = 'b';
    board[2][0] = 'd';
    board[3][0] = 'a';
}

function makeViewBoard() {
    let tableEle = "<table>";
    for (var i=0 ; i<BOARD_SIZE ; i++) {
        tableEle += "<tr>";
        for (var j=0 ; j<BOARD_SIZE ; j++) {
            // tableEle += "<td>" + board[i][j] + "</td>";
            classString = "";
            if (board[i][j] == 'h')
                classString = "wormHead";
            else if (board[i][j] == 't')
                classString = "wormTail";
            else if (board[i][j] == 'b')
                classString = "bomb";
            else if (board[i][j] == 'd')
                classString = "damaged";
            else if (board[i][j] == 'a')
                classString = "attacked";

            if (classString)
                tableEle += "<td class='" + classString + "'>";
            else
                tableEle += "<td>";
            tableEle += "</td>";
        }
        tableEle += "</tr>";
    }
    tableEle += "</table>";
    document.getElementById("GBoard").innerHTML = tableEle;
}

function makeViewBoardClickable() {
    let tableEle = "<table>";
    for (var i=0 ; i<BOARD_SIZE ; i++) {
        tableEle += "<tr>";
        for (var j=0 ; j<BOARD_SIZE ; j++)
            tableEle += "<td>" + board[i][j] + "</td>";
        tableEle += "</tr>";
    }
    tableEle += "</table>";
    document.getElementById("GBoard").innerHTML = tableEle;
}