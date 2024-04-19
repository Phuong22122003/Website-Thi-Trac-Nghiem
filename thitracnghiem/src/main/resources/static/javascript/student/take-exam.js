var currentIndex = 0
function showQuestion(index){
    currentIndex = index 
    const previous = document.getElementById("previous");
    const next = document.getElementById("next");
    const li = document.querySelectorAll(".exam-view .question-answer")
    console.log(currentIndex)
    if(currentIndex==li.length -1)
        next.disabled = true;
    else next.disabled = false
    if(currentIndex == 0)
        previous.disabled = true;
    else previous.disabled = false
    for(let i = 0;i<li.length;i++){
        li[i].style.display = "none";
    }
    li[index].style.display = "flex";
}



function countdownMinutes(minutes) {
    var seconds = minutes * 60;
    const showTime = document.getElementById("time")
    function updateDisplay() {  
        var displayMinutes = Math.floor(seconds / 60);
        var displaySeconds = seconds % 60;

        displayMinutes = displayMinutes < 10 ? "0" + displayMinutes : displayMinutes;
        displaySeconds = displaySeconds < 10 ? "0" + displaySeconds : displaySeconds;
        showTime.innerHTML = "<p>Thời gian:" +minutes+ ":00 phút</p> <p>Còn lại: "+displayMinutes + ":" + displaySeconds +" phút</p>"

        // console.log(displayMinutes + ":" + displaySeconds);
        if (seconds <= 0) {
            clearInterval(timer);
            console.log("Countdown finished!"); // Thêm hành động khi hết thời gian
            window.alert("Hết thời gian!")
            subMit()
        }
    }

    // Cập nhật hiển thị mỗi giây
    var timer = setInterval(function () {
        updateDisplay();
        seconds --;
    }, 1000);

    // Khởi tạo hiển thị ban đầu
    updateDisplay();
}
function subMit(){
    const form = document.getElementById("form");
    form.submit()
}

function moveQuestion(socau){
    const previous = document.getElementById("previous");
    const next = document.getElementById("next");
    previous.disabled = true;
    previous.onclick= ()=>{
        console.log(currentIndex)
        if(currentIndex == socau -1){
            next.disabled = false;
        }
        currentIndex--;
        showQuestion(currentIndex)
        if(currentIndex==0){
            previous.disabled = true;
        }
    }
    next.onclick= ()=>{
        console.log(currentIndex)
        if(currentIndex == 0){
            previous.disabled = false;
        }
        currentIndex++;
        showQuestion(currentIndex)
        if(currentIndex==socau -1){
            next.disabled = true;
        }
    }
}
