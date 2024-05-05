function switchView(index) {
    const xem_lich_thi = document.getElementById("xem-lich-thi");
    const xem_diem = document.getElementById("xem-diem");
    const sua_thong_tin = document.getElementById("sua-thong-tin")
    const doi_mat_khau = document.getElementById("doi-mat-khau")
    const li = [xem_lich_thi, xem_diem, sua_thong_tin,doi_mat_khau]
    for (let i = 0; i <4 ; i++) {
        li[i].style.display = "none";
    }
    li[index].style.display = "block";
}

function iconClick(event) {
    const li = document.getElementById("profile-logout");
    let status = li.style.display;
    if (status === "none" || status === "") {
        li.style.display = "block";
    }
    else {
        li.style.display = "none";
    }
    event.stopPropagation();
}
function bodyClick() {
    const li = document.getElementById("profile-logout");
    li.style.display = "none";
}


// ====================Main==============================
// ==================biến======================
// ================gắn sự kiện==========================
main_container = document.querySelector("body")
main_container.addEventListener("click", bodyClick);