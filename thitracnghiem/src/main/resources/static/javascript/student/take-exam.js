var currentIndex = 0;
var listIndexQuestions = document.querySelectorAll(".index-question")
var listAudios = document.querySelectorAll(".img-audio")
function showQuestion(index) {
    console.log(listIndexQuestions)
    currentIndex = index;
    const previous = document.getElementById("previous");
    const next = document.getElementById("next");
    const li = document.querySelectorAll(".exam-view .question-answer");
    console.log(currentIndex);
    if (currentIndex == li.length - 1) next.disabled = true;
    else next.disabled = false;
    if (currentIndex == 0) previous.disabled = true;
    else previous.disabled = false;
    for (let i = 0; i < li.length; i++) {
        li[i].style.display = "none";
        if(listIndexQuestions[i]!=null)listIndexQuestions[i].style.color = "white";
       if(listAudios[i].querySelector("audio")!=null) listAudios[i].querySelector("audio").pause();
    }
    console.log(listAudios);
    li[index].style.display = "flex";
    listIndexQuestions[index].style.color = "red";
}

function countdownMinutes(time, remainingTime) {
    window.onbeforeunload = function () {
        return "Dữ liệu có thể bị mất";
    };
    var seconds = remainingTime;
    const showTime = document.getElementById("time");
    function updateDisplay() {
        var displayMinutes = Math.floor(seconds / 60);
        var displaySeconds = seconds % 60;

        displayMinutes =
            displayMinutes < 10 ? "0" + displayMinutes : displayMinutes;
        displaySeconds =
            displaySeconds < 10 ? "0" + displaySeconds : displaySeconds;
        showTime.innerHTML =
            "<p>Thời gian:" +
            time +
            ":00 phút</p> <p>Còn lại: " +
            displayMinutes +
            ":" +
            displaySeconds +
            " phút</p>";

        // console.log(displayMinutes + ":" + displaySeconds);
        if (seconds <= 0) {
            clearInterval(timer);
            console.log("Countdown finished!"); // Thêm hành động khi hết thời gian
            window.alert("Hết thời gian!");
            subMit();
        }
    }

    // Cập nhật hiển thị mỗi giây
    var timer = setInterval(function () {
        updateDisplay();
        seconds--;
    }, 1000);

    // Khởi tạo hiển thị ban đầu
    updateDisplay();
}
function subMit() {
    const form = document.getElementById("form");
    window.onbeforeunload = null;
    form.submit();
}

function moveQuestion(socau) {
    const previous = document.getElementById("previous");
    const next = document.getElementById("next");
    previous.disabled = true;
    previous.onclick = () => {
        console.log(currentIndex);
        if (currentIndex == socau - 1) {
            next.disabled = false;
        }
        currentIndex--;
        showQuestion(currentIndex);
        if (currentIndex == 0) {
            previous.disabled = true;
        }
    };
    next.onclick = () => {
        console.log(currentIndex);
        if (currentIndex == 0) {
            previous.disabled = false;
        }
        currentIndex++;
        showQuestion(currentIndex);
        if (currentIndex == socau - 1) {
            next.disabled = true;
        }
    };
}
