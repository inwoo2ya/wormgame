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
let worm;
let clickCount = 0;

let dx = [-2, -2, 0, 2, 2, 2, 0, -2], dy = [0, 2, 2, 2, 0, -2, -2, -2];

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
        startBtnToggle(false);
        exitBtnToggle(false);
        initializeBoard();

        worm = [];
        var chatarea = document.getElementById("messageArea");
        var msg = "1번째 지렁이를 설정합니다.";
        chatarea.innerHTML = chatarea.innerHTML + "<br/>" + msg;
        makeViewBoardClickable();
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
    // board[0][0] = 'h';
    // board[0][1] = board[0][2] = 't';
    // board[1][0] = 'b';
    // board[2][0] = 'd';
    // board[3][0] = 'a';
}

function makeViewBoardClickable() {
    let tableEle = "<table>";
    for (var i=0 ; i<BOARD_SIZE ; i++) {
        tableEle += "<tr>";
        for (var j=0 ; j<BOARD_SIZE ; j++) {
            classString = "";
            tdId = "" + i + j;

            tableEle += "<td id='" + tdId + "'></td>"
        }
        tableEle += "</tr>";
    }
    tableEle += "</table>";
    document.getElementById("GBoard").innerHTML = tableEle;

    for (var i=0 ; i<BOARD_SIZE ; i++)
        for (var j=0 ; j<BOARD_SIZE ; j++) {
            tdId = "" + i + j;
            td = document.getElementById(tdId);
            if (board[i][j] == 'h')
                td.classList.add("wormHead");
            else if (board[i][j] == 't')
                td.classList.add("wormTail");
            else if (board[i][j] == 'b')
                td.classList.add("bomb");
            else if (board[i][j] == 'd')
                td.classList.add("damaged");
            else if (board[i][j] == 'a')
                td.classList.add("attacked");
            else
                td.onclick = function() { onClick(Number(this.id[0]), Number(this.id[1])); }
        }
}

function onClick(x, y) {
    console.log(x, y);
    clickCount++;

    if (clickCount == 2) {
        var chatarea = document.getElementById("messageArea");
        var msg = "2번째 지렁이를 설정합니다.";
        chatarea.innerHTML = chatarea.innerHTML + "<br/>" + msg;
    } else if (clickCount == 4) {
        var chatarea = document.getElementById("messageArea");
        var msg = "3번째 지렁이를 설정합니다.";
        chatarea.innerHTML = chatarea.innerHTML + "<br/>" + msg;
    } else if (clickCount == 6) {
        var chatarea = document.getElementById("messageArea");
        var msg = "폭탄 위치를 설정합니다.";
        chatarea.innerHTML = chatarea.innerHTML + "<br/>" + msg;
    }

    if (clickCount <= 6) {
        if (clickCount % 2 == 1) {
            board[x][y] = 'h';
            tdId = "" + x + y;
            td = document.getElementById(tdId);
            td.classList.add("wormHead");
            //클릭할 수 있는 몸통 처리 로직
            for (var i=0 ; i<BOARD_SIZE ; i++)
                for (var j=0 ; j<BOARD_SIZE ; j++) {
                    tdId = "" + i + j;
                    td = document.getElementById(tdId);
                    td.onclick = null;
                }
            for (var i=0 ; i<8 ; i++) {
                var nx = x + dx[i];
                var ny = y + dy[i];
                if (nx < 0 || nx >= BOARD_SIZE || ny < 0 || ny >= BOARD_SIZE)
                    continue;
                if (board[nx][ny] || board[parseInt((x+nx)/2)][parseInt(y+ny)/2])
                    continue;
                tdId = "" + nx + ny;
                board[nx][ny] = 'c';
                td = document.getElementById(tdId);
                td.classList.add("clickable");
                td.onclick = function() { onClick(Number(this.id[0]), Number(this.id[1])); }
            }
            worm.push(x);
            worm.push(y);
        } else {
            board[x][y] = 't';
            tdId = "" + x + y;
            td = document.getElementById(tdId);
            td.classList.remove("clickable");
            td.onclick = null;

            bodyAdd(worm[(parseInt(clickCount/2)-1)*6], worm[(parseInt(clickCount/2)-1)*6+1], x, y);
            worm.push(x);
            worm.push(y);
            
            for (var i=0 ; i<BOARD_SIZE ; i++)
                for (var j=0 ; j<BOARD_SIZE ; j++) {
                    tdId = "" + i + j;
                    td = document.getElementById(tdId);
                    if (board[i][j] == 'c') {
                        td.classList.remove("clickable");
                        board[i][j] = 0;
                    } else if (board[i][j] == 'h')
                        td.classList.add("wormHead");
                    else if (board[i][j] == 't')
                        td.classList.add("wormTail");
                    else if (board[i][j] == 0)
                        td.onclick = function() { onClick(Number(this.id[0]), Number(this.id[1])); }
                }
        }
    } else if (clickCount == 7) {
        board[x][y] = 'b';
        tdId = "" + x + y;
        td = document.getElementById(tdId);
        td.classList.remove("clickable");
        td.onclick = null;
        td.classList.add("bomb");
        worm.push(x);
        worm.push(y);

        for (var i=0 ; i<BOARD_SIZE ; i++)
            for (var j=0 ; j<BOARD_SIZE ; j++) {
                tdId = "" + i + j;
                td = document.getElementById(tdId);
                td.onclick = null;
            }
    } else {

    }
    console.log("clickCount = " + clickCount + ", worm = " + worm);
}

function bodyAdd(headx, heady, tailx, taily) {
    var bodyx = parseInt((headx+tailx)/2);
    var bodyy = parseInt((heady+taily)/2);
    worm.push(bodyx);
    worm.push(bodyy);
    board[bodyx][bodyy] = 't';
}
