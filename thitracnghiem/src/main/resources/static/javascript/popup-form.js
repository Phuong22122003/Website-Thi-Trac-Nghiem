function popup(
    { type = "error", title = "Thông báo", desc = "This is a desc" },
    queryForm
) {
    const color = {
        success: "#039855",
        remove: "#D92D20",
    };
    const icon = {
        success: "fa-solid fa-check check",
        remove: "fa-solid fa-trash",
    };
    const formEl = document.querySelector(queryForm);
    const popupWrapper = document.createElement("div");
    popupWrapper.classList.add("popup-wrapper");

    const popup = document.createElement("div");
    popup.classList.add("popup");
    popup.innerHTML = `
        <div class="top">
            <div class="icon-wrapper" style="background: ${color[type]}">
                <i class="${icon[type]}"></i>
            </div>
            <i class="fa-solid fa-xmark close"></i>
        </div>
        <span class="title">${title}</span>
        <div class="desc">${desc}</div>
        <div class="btn-group">
            <a href="#!" class="cancel-btn">Cancel</a>
            <a href="#!" class="confirm-btn" style="background-color: ${color[type]}">Confirm</a>
        </div>
    `;
    popup.style.animation = "modalFadeIn ease 0.3s";
    popupWrapper.appendChild(popup);

    const body = document.querySelector("body");
    body.appendChild(popupWrapper);

    const cancelBtn = popup.querySelector(".cancel-btn");
    const closeBtn = popup.querySelector(".close");
    const submitBtn = popup.querySelector(".confirm-btn");

    function closePop() {
        popupWrapper.remove();
    }

    popup.addEventListener("click", (event) => {
        event.stopPropagation();
    });
    popupWrapper.addEventListener("click", closePop);
    cancelBtn.addEventListener("click", closePop);
    closeBtn.addEventListener("click", closePop);

    submitBtn.addEventListener("click", () => {
        formEl.submit();
        closePop();
    });
}
