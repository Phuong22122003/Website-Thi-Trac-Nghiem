import { openModal } from "../XuLyTable.js";
var innerHTMLOfBtnEmail = ""
var currentIndexOfEmail = 2
function openFormCreateNewAccount() {
    const btnRegister = document.querySelectorAll(".btn-register")[0];
    const modalContainer =  document.querySelector(".modal-container[data-name='student']");
    const inputMaLop = modalContainer.querySelector("#class")
    const maLop = document.getElementById("classSelect")
    inputMaLop.value = maLop.value
    btnRegister.addEventListener("click", () => {
        clearErrorMessage()
        openModal(modalContainer);
    });
}
function sendEmail(listSinhVien){
    const btnCreateNewForm = document.querySelectorAll(".btn-register")[0];
    const btnChooseEmail = document.querySelectorAll(".btn-register")[1];
    const btnSend = document.querySelectorAll(".btn-register")[2];
    if(btnChooseEmail.innerHTML == "Hủy"){
        currentIndexOfEmail = 2
        btnSend.style.display = "none"
        btnCreateNewForm.style.display = "inline-flex"
        btnChooseEmail.innerHTML = innerHTMLOfBtnEmail
        btnChooseEmail.style.background = "white";
        btnChooseEmail.style.color = "var(--primary-color)"
        listSinhVien.forEach((row)=>{
            row.onclick = null
            if(row.style.color === "white"){
                row.style.color = "black";
                row.style.background = "white";
                currentIndexOfEmail--;
            }
        })
        return
    }
    btnCreateNewForm.style.display = "none"
    innerHTMLOfBtnEmail=btnChooseEmail.innerHTML
    btnChooseEmail.style.background = "var(--primary-color)";
    btnChooseEmail.style.color = "white"
    btnChooseEmail.innerHTML = "Hủy"
    btnSend.style.display = "inline-flex" 

    btnSend.onclick=function(){
        if(currentIndexOfEmail<=2){
            toast({
                type: "error",
                title: "Không thể gửi!",
                message: "Vui lòng chọn email",
                duration: 3000,
            });
            return;
        }
        const formEmail = document.querySelector("#sendemail")
        formEmail.submit();
    }
        
}
function setValueBeforSend(listSinhVien){
    const maLop = document.getElementById("classSelect")
    const listOfEmail = document.querySelector("#sendemail")
    const inputEmails = listOfEmail.querySelectorAll("input")
    inputEmails[1].value = maLop.value
    const btnSend = document.querySelectorAll(".btn-register")[2];

    listSinhVien.forEach((row)=>{
        row.onclick = function(){
            if(row.style.color === "white"){
                row.style.color = "black";
                row.style.background = "white";
                currentIndexOfEmail--;
                return;
            }
            inputEmails[currentIndexOfEmail].value = row.querySelectorAll("td")[6].textContent
            currentIndexOfEmail++;
            row.style.color = "white";
            row.style.background = "#1187ad";
        }
    })
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
        !(colums[1].textContent.trim()+' '+colums[2].textContent.trim()).toUpperCase().includes(search.trim())&&
        !colums[6].textContent.includes(search.trim())
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

// const btnSearch = document.getElementById("search-button")

const listSinhVien = document.querySelectorAll("tbody tr");
const search =document.getElementById("search")
const maLop = document.getElementById("classSelect")
const delaySearch = debounce(inputSearchTyping,1000)
search.addEventListener("keyup",()=>{
    delaySearch(listSinhVien)
})
const modalContainer = document.querySelector(".modal-container[data-name='m-student']");
const btnSendEmail = document.querySelectorAll(".btn-register")[1];
btnSendEmail.onclick = function(){
    setValueBeforSend(listSinhVien)
    sendEmail(listSinhVien)
}

openFormCreateNewAccount()

listSinhVien.forEach((row) => {
    const editBtn = row.querySelector(".edit");
    const colums = row.querySelectorAll("td")
    editBtn.onclick = function () {
        const masv = colums[0].textContent;
        const ho = colums[1].textContent;
        const ten = colums[2].textContent;
        const gioiTinh = colums[3].textContent;
        const diaChi = colums[4].textContent;
        const ngaySinh = colums[5].textContent;
        const email = colums[6].textContent;
        openModal(modalContainer);
        clearErrorMessage();
        const inputLastname = modalContainer.querySelector("#m-lastname");
        const inputFirstname = modalContainer.querySelector("#m-firstname");
        const inputMale =  modalContainer.querySelector("#m-male");
        const inputFemale =  modalContainer.querySelector("#m-female");
        const inputAddress =  modalContainer.querySelector("#m-address");
        const inputStudentID =  modalContainer.querySelector("#m-masv");
        const inputClassID =  modalContainer.querySelector("#m-class");
        const inputBirthday =  modalContainer.querySelector("#m-birthday");
        const inputEmail =  modalContainer.querySelector("#m-email");


        inputLastname.value = ho; 
        inputFirstname.value = ten; 
        if(gioiTinh==='Nam')inputMale.checked = true
        else inputFemale.checked = true
        inputAddress.value = diaChi
        inputStudentID.value = masv
        inputClassID.value = maLop.value
        inputBirthday.value = ngaySinh
        inputEmail.value = email
    };
});
