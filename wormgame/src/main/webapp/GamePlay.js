
function back(){
    window.location.href="Game.html";
}
function backindex(){
    window.location.href="index.html";
    
}
function tourl() { 
    window.location.href = "Game.html";
}

function Play(inputString){
    window.location.href = "GamePlay.html";
    const title = document.getElementById("roomname");
    title.innerHTML=inputString;
                        
    }
    function createGame(){
        var inputString = prompt('방 이름을 입력하세요!','방 제목');
        String(inputString);
        if (inputString){
            Play(inputString);
    
        }
    }
function roomname(){
    var inputString = prompt('방 이름을 입력하세요!','방 제목');
    String(inputString);
    const title = document.getElementById("roomname");
    title.innerHTML = inputString;
}