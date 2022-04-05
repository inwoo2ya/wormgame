function testalert(){
    alert("안녕!!");
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
