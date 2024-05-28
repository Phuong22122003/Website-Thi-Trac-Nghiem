import { openModal } from "../XuLyTable.js";
function XuLyTable() {
    const btnRegister = document.querySelector(".btn-register");
    const modalContainer = document.querySelector(".modal-container");
    btnRegister.addEventListener("click", () => {
        clearErrorMessage()
        openModal(modalContainer);
    });
}
function clearErrorMessage(){
    document.querySelectorAll('.error-message').forEach(function(element) {
        element.remove();
    });
}
function inputSearchTyping(listSinhVien){
    const search =document.getElementById("search").value.toUpperCase()
    listSinhVien.forEach((row)=>{
        const colums = row.querySelectorAll("td")
        if(!colums[0].textContent.includes(search.trim())&&
            !(colums[1].textContent.trim()+' '+colums[2].textContent.trim()).toUpperCase().includes(search.trim())
        ){
            row.style.display = "none";

        }
        else row.style.display = "table-row";
    })
}

// Hàm debounce
function debounce(func, delay) {
    let timeoutId;
    return function() {
        const context = this;
        const args = arguments;
        clearTimeout(timeoutId);
        timeoutId = setTimeout(function() {
            func.apply(context, args);
        }, delay);
    };
}
// ------------Run---------------------
XuLyTable()
// const btnSearch = document.getElementById("search-button")

const listOfClass = document.querySelectorAll("tbody tr");
const search =document.getElementById("search")
const delaySearch = debounce(inputSearchTyping,1000)
search.addEventListener("keyup",()=>{
    delaySearch(listOfClass)
})

const modalContainer = document.querySelector(
    ".modal-container[data-name='m-class']"
);
listOfClass.forEach((row) => {
    const editBtn = row.querySelector(".edit");
    const colums = row.querySelectorAll("td")
    editBtn.onclick = function () {
        const classId = colums[0].textContent;
        const className = colums[1].textContent;
        const date = colums[2].textContent;
        openModal(modalContainer);
        clearErrorMessage();
        const inputClassID = modalContainer.querySelector("#m-classId");
        const inputClassName = modalContainer.querySelector("#m-className");
        const inputDate =  modalContainer.querySelector("#m-year-admission");

        inputClassID.value = classId; 
        inputClassName.value = className; 
        inputDate.value = date
    };
});
