import XuLyTable, { openModal } from "../XuLyTable.js";
XuLyTable();

const listMonHoc = document.querySelectorAll("tbody tr");
const modalContainer = document.querySelector(
    ".modal-container[data-name='m-monhoc']"
);
listMonHoc.forEach((row) => {
    const editBtn = row.querySelector(".edit");
    console.log(editBtn);
    editBtn.onclick = function () {
        const mamh = row.querySelector(".mamh").textContent;
        const tenmh = row.querySelector(".tenmh").textContent;
        const soTietLt = row.querySelector(".soTietLt").textContent;
        const soTietTh = row.querySelector(".soTietTh").textContent;

        openModal(modalContainer);

        const inputMaMh = modalContainer.querySelector("#mamh");
        const inputTenMh = modalContainer.querySelector("#tenmh");
        const inputSoTietLt = modalContainer.querySelector("#soTietLt");
        const inputSoTietTh = modalContainer.querySelector("#soTietTh");

        inputMaMh.value = mamh;
        inputTenMh.value = tenmh;
        inputSoTietLt.value = soTietLt;
        inputSoTietTh.value = soTietTh;
    };
});
