import XuLyTable, { openModal, closeModal } from "../XuLyTable.js";
XuLyTable();
const nextTypeQuestionBtn = document.querySelector(".type-wrapper .next-btn");
const modalContainerTypeQuestion = document.querySelector(
    ".modal-container[data-name='cauhoi']"
);
const modalContainerCreateQuestionYesNo = document.querySelector(
    ".modal-container[data-name='create-cauhoi-yes/no']"
);
const modalContainerCreateQuestionOthers = document.querySelector(
    ".modal-container[data-name='create-cauhoi-others']"
);
console.log(nextTypeQuestionBtn);
nextTypeQuestionBtn.onclick = clickOnNextTypeQuestionBtn;

function clickOnNextTypeQuestionBtn() {
    const type = document.querySelector(".type-wrapper .type-selection").value;
    closeModal(modalContainerTypeQuestion);
    if (type === "YES/NO") {
        openModal(modalContainerCreateQuestionYesNo);
    } else {
        openModal(modalContainerCreateQuestionOthers);
        const hinhThucInput =
            modalContainerCreateQuestionOthers.querySelector("#hinhThuc");
        hinhThucInput.value = type;
        const addBtn =
            modalContainerCreateQuestionOthers.querySelector(".add-btn");
        const closeBtn =
            modalContainerCreateQuestionOthers.querySelector(".closeBtn");
        let numOption = 2;
        let optionAddedList = [];
        addBtn.onclick = () => {
            const inputOption = createInput(numOption++);
            optionAddedList.push(inputOption);
        };
        closeBtn.onclick = () => {
            if (optionAddedList) {
                optionAddedList.forEach((option) => option.remove());
            }
        };
    }
}

function createInput(stt) {
    const addBtn = modalContainerCreateQuestionOthers.querySelector(".add-btn");
    const optionWrapper =
        modalContainerCreateQuestionOthers.querySelector(".option-wrapper");
    const template = `<div class="input-wrapper input-option" data-name=${stt}>
    <div class="wrapper-option">
        <label for="option${stt}">Lựa chọn
    </label>
        <input
            type="text"
            placeholder=""
            name="luachon"
            id="luachon"
            autocomplete="nope"
            rules="required"
            maxlength="100"
            required
            autofocus
        />
        <div class="bar"></div>
    </div>

    <div class="interact-option-wrapper">
        <input
            type="radio"
            name="dapAnDung"
            id="option${stt}"
            class="radio-luachon"
            hidden
            data-name="option${stt}"
            value="${stt}"
            
        />
        <label for="option${stt}" class="dap-an-dung"></label>
        <i
            class="fa-solid fa-trash trash delete-selection"
        ></i>
    </div>
    
    </div>`;
    optionWrapper.insertAdjacentHTML("beforeend", template);
    const inputOption = document.querySelector(
        `.input-option[data-name="${stt}"]`
    );
    inputOption.focus();
    const deleteInputOptionBtn = inputOption.querySelector(".delete-selection");
    deleteInputOptionBtn.onclick = function () {
        inputOption.remove();
    };
    return inputOption;
}
