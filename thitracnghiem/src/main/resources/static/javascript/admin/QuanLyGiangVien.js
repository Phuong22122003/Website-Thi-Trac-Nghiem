import XuLyTable, { openModal } from "../XuLyTable.js";
XuLyTable();
function showNumOfTeachers(listGiangVien){
    const numOfTeachers = document.getElementById("numOfTeachers")
    let count = 0
    listGiangVien.forEach((row) => {
        count ++;
    })
    numOfTeachers.textContent +=  count
}
const listGiangVien = document.querySelectorAll("tbody tr");
showNumOfTeachers(listGiangVien)
const modalContainer = document.querySelector(
    ".modal-container[data-name='m-lecturer']"
);
listGiangVien.forEach((row) => {
    const editBtn = row.querySelector(".edit");
    const addExamBtn = row.querySelector(".add-exam-btn");
    editBtn.onclick = function () {
        const ho = row.querySelector(".ho").textContent;
        const ten = row.querySelector(".ten").textContent;
        const email = row.querySelector(".email").textContent;
        const gioiTinh =
            row.querySelector(".gioiTinh").textContent == "Nam" ? 1 : 0;
        const hocVi = row.querySelector(".hocVi").textContent;
        const hocHam = row.querySelector(".hocHam").textContent;
        const maGv = row.querySelector(".maGv").textContent;

        openModal(modalContainer);

        const inputHo = modalContainer.querySelector(".ho");
        const inputTen = modalContainer.querySelector(".ten");
        const inputEmail = modalContainer.querySelector(".email");
        const inputGioiTinh = modalContainer.querySelector(
            `.gioiTinh[value="${gioiTinh}"]`
        );
        const inputHocVi = modalContainer.querySelector(".hocVi");
        const inpuHocHam = modalContainer.querySelector(".hocHam");
        const inputMaGv = modalContainer.querySelector(".maGv");
        inputHo.value = ho;
        inputTen.value = ten;
        inputEmail.value = email;
        inputGioiTinh.checked = true;
        inputHocVi.value = hocVi.toUpperCase();
        inpuHocHam.value = hocHam.toUpperCase();
        inputMaGv.value = maGv;
    };
    addExamBtn.onclick = function () {
        const maGv = row.querySelector(".maGv").textContent;
        const modalContainerExam = document.querySelector(
            ".modal-container[data-name='exam']"
        );
        openModal(modalContainerExam);
        const inputMaGv = modalContainerExam.querySelector(".maGv");
        inputMaGv.value = maGv;
    };
});
