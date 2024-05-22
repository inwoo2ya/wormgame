var turn = 0;

function Roomname(){
    var inputString = prompt('방 이름을 입력하세요!','방 제목');
    String(inputString);
    const title = document.getElementById("Roomname");
    title.innerHTML = inputString;
}

function startBtnToggle(bool) { //게임 스타트 버튼 disable
    const target = document.getElementById('GameStartbtn');
    target.disabled = !bool;
}
function exitBtnToggle(bool){ // 나가기 버튼 disable
    const target = document.getElementById('exit');
    target.disabled = !bool;
}

function turnnum(data){ // 클라이언트에게 표시할 턴 함수
    const turnnumber = document.getElementById('Gameturn');
    if (data == "EVENT_INITIALIZE"){
        turn = 0;
        turnnumber.textContent = turn;
    }
    else if(data == "EVENT_YOUR_TURN"){
        turn =turn+1;
        turnnumber.textContent = turn;
    }
}
