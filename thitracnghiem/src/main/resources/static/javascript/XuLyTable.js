function clearFormValidation() {
    const formGroups = document.querySelectorAll(".input-wrapper");
    formGroups.forEach((formGroup) => {
        const input = formGroup.querySelector("input");
        if (input) input.value = "";
    });
}

function clearModalContainers() {
    const modalContainers = document.querySelectorAll(".modal-container");
    modalContainers.forEach((item) => item.classList.remove("open"));
}

function openModal(modalContainer) {
    const modal = document.querySelector(".modal");
    modal.classList.add("open");
    modalContainer.classList.add("open");
    modalContainer.addEventListener("click", (event) =>
        event.stopPropagation()
    );
    modal.addEventListener("click", () => closeModal(modalContainer));
    const closeBtn = modalContainer.querySelector(`.closeBtn`);
    if (!closeBtn) return;
    closeBtn.addEventListener("click", () => closeModal(modalContainer));
}

function closeModal(modalContainer) {
    const modal = document.querySelector(".modal");
    modal.classList.remove("open");
    modalContainer.classList.remove("open");
    clearFormValidation();
}

function handlerBtnRegister(contentWrapper) {
    const btnRegister = contentWrapper.querySelector(".btn-register");
    if (!btnRegister) return;
    btnRegister.addEventListener("click", () => {
        clearModalContainers();
        const modal = document.querySelector(".modal");
        const modalContainer = document.querySelector(
            `.modal-container[data-name=${contentWrapper.getAttribute(
                "data-name"
            )}]`
        );
        openModal(modalContainer);
        Validator(
            `.modal-container[data-name=${contentWrapper.getAttribute(
                "data-name"
            )}] .register-form`
        );
        // Ngan su kien noi bot ke tu modal container
    });
}

function XuLyTable() {
    const btnRegister = document.querySelector(".btn-register");
    const modalContainer = document.querySelector(".modal-container");
    btnRegister.addEventListener("click", () => {
        openModal(modalContainer);
    });
}
export default XuLyTable;
export { openModal };
