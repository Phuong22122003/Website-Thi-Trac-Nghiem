function toast({
    type = "success", // Default type is "success"
    title = "Success!", // Default title is "Success!"
    message = "", // Default message is an empty string
    duration = 3000, // Default duration is 3000 milliseconds (3 seconds)
}) {
    // Select the body element from the DOM
    const body = document.querySelector("body");

    // Create a new div element for the toast
    const toast = document.createElement("div");
    toast.classList.add("toast");
    // Define icon and color based on the type of toast
    const icon = {
        success: "fa-solid fa-check",
        error: "fa-solid fa-xmark",
        info: "fa-solid fa-info",
    };
    const background = {
        success: "#48B16E",
        error: "#FB3836",
        info: "#DCA048",
    };

    // Convert duration to seconds with two decimal places
    const durationConvert = (duration / 1000).toFixed(2);
    toast.style.animation = `fadeIn ease 1s, fadeOut linear 1s 3s forwards`;
    // Set the HTML content of the toast
    toast.innerHTML = `
        <i class="${icon[type]}" style="background: ${background[type]}"></i>
        <div class="toast--content">
            <div class="toast--top">
                <span class="toast--title">${title}</span>
                <i class="fa-solid fa-xmark close"></i>
            </div>
            <div class="toast--desc">${message}</div>
        </div>
    `;
    function removeToast() {
        body.removeChild(toast);
    }
    // Append the toast to the body

    body.appendChild(toast);
    document.querySelector(".close").addEventListener("click", removeToast);
    setTimeout(removeToast, duration + 1000);
}
