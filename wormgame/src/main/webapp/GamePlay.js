
function Gametitle(){
    var title = document.getElementById('roomname');
    title.appendChild(inputString);
}
function back(){
    window.location.href="Game.html";
}
function chatting(){
    var form = document.getElementById('form');
    var input = document.getElementById('input');
    form.addEventListener('submit',function(e){
        e.preventDefault();
        var msg = input.value;

        if(msg){
            repo.textContent = msg;
            form.reset();
        } else{


        }
    })
}
function backindex(){
    window.location.href="index.html";
    
}
function tourl() { 
    window.location.href = "Game.html";
}
function createGame(){
    var inputString =prompt('방 이름을 입력하세요!','방 제목');
        if (inputString){
            Play();
        }
}
function Play(){
    window.location.href = "GamePlay.html";
                        
    }
