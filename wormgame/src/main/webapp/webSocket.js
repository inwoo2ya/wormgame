let websocket;
let roomName = document.getElementById("Roomname").textContent;
let userName = sessionStorage.getItem("userName");
let sessionId = sessionStorage.getItem("sessionId");

let playerName = [];
let playerSessionId = [];
let playerCount;
let isGamePlaying = false; //게임 여부
let wormnum = []; //지렁이 수
let bombnum = []; // 폭탄 수

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
    websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "LEAVE", writer: sessionId}));
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
        else
            sendToMe("SYSTEM : EVENT_로 시작하는 메시지는 보낼 수 없습니다.");
        document.getElementById("message").value = "";
    }
}

function sendToMe(string) {
    var chatarea = document.getElementById("messageArea");
    chatarea.innerHTML = chatarea.innerHTML + "<br/>" + string;
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
        sendToMe("1번째 지렁이를 설정합니다.");
        makeViewBoardClickable();
    } else if (!data.indexOf("EVENT_PLAYER_NAME") || !data.indexOf("EVENT_PLAYER_SESSIONID") || !data.indexOf("EVENT_PLAYER_COUNT")||!data.indexOf("EVENT_USERS_WORM_AND_BOMB_COUNT")){
        currentRoomPlayer(data);
    } else if (!data.indexOf("EVENT_YOUR_TURN")) {
        sendToMe("당신의 차례입니다. 공격할 좌표를 선택하세요.")
        addOnClick();
    } else
        sendToMe(data);
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
    if(!str.indexOf("EVENT_PLAYER_NAME")){
        playerName[str[17]] = str.substr(21);
    }
    else if(!str.indexOf("EVENT_PLAYER_SESSIONID")){
        playerSessionId[str[22]] = str.substr(26);
    }
    else if (!str.indexOf("EVENT_PLAYER_COUNT")) {
        playerCount = str.substr(21);
        displayPlayername(playerCount);
    }
    else if (!str.indexOf("EVENT_USERS_WORM_AND_BOMB_COUNT")){
        displayWormandBomb(str,playerCount);
    }
    
    if (sessionId == playerSessionId[1] && !isGamePlaying && playerCount > 1)
        startBtnToggle(true);
    else
        startBtnToggle(false);
}

function displayPlayername(userCount) { //Player닉네임 보여주기
    var i;
    for (i = 1 ; i <= userCount ; i++)
        document.getElementById("playerName" + i).textContent = playerName[i];
        
    for ( ; i <=4 ; i++)
        document.getElementById("playerName" + i).textContent = "";
       
}
function displayWormandBomb(str,userCount){ //player 지렁이수 보여주기
    var i = 0;
    var m = 1;
    while(m < userCount+1){
        i+=1;
        a = 33+i
        console.log(a,i,m);
        if (a%2 == 0){
            wormnum[m] = str.substr(a,1);
            document.getElementById("wormnumber"+m).textContent = wormnum[m];
            console.log(wormnum[m],a);
        }
        else {
            bombnum[m] = str.substr(a,1);
            document.getElementById("bombnumber"+m).textContent = bombnum[m];
            console.log(bombnum[m],a);
            m+=1
            
        }
    

    }
    // for(i=1;i<=userCount;i++){
       
    //     var a = 33+i;
    //     var b = 36-i;
    //     wormnum[i] = str.substr(a,1);
    //     bombnum[i] = str.substr(b,1);
    //     console.log(wormnum[i],a);
    //     console.log(bombnum[i],b);
    //     document.getElementById("wormnumber"+i).textContent = wormnum[i];
    //     document.getElementById("bombnumber"+i).textContent = bombnum[i];
    // }
    
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

    if (clickCount == 2)
        sendToMe("2번째 지렁이를 설정합니다.");
    else if (clickCount == 4)
        sendToMe("3번째 지렁이를 설정합니다.");
    else if (clickCount == 6)
        sendToMe("폭탄 위치를 설정합니다.");

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

        websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "INITIALIZED", writer : sessionId, message : worm.join("")}));
        sendToMe("다른 유저가 지렁이와 폭탄을 설정하기를 기다리는 중 ...");
        
        


    } else {
        websocket.send(JSON.stringify({chatRoomName : roomName, messageType : "ATTACK", writer : sessionId, message : "" + x + y}));
        removeOnClick();
        sendToMe("공격을 서버로 전송하였습니다.");
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

function addOnClick() {
    for (var i=0 ; i<BOARD_SIZE ; i++)
        for (var j=0 ; j<BOARD_SIZE ; j++)
            if (board[i][j] == 0) {
                tdId = "" + i + j;
                td = document.getElementById(tdId);
                td.onclick = function() { onClick(Number(this.id[0]), Number(this.id[1])); }
            }
}

function removeOnClick() {
    for (var i=0 ; i<BOARD_SIZE ; i++)
        for (var j=0 ; j<BOARD_SIZE ; j++) {
            tdId = "" + i + j;
            td = document.getElementById(tdId);
            td.onclick = null;
        }
}