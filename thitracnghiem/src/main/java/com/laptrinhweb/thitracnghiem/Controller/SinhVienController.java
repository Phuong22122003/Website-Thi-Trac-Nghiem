package com.laptrinhweb.thitracnghiem.Controller;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoi;
import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Service.CTBaiThiService;
import com.laptrinhweb.thitracnghiem.Service.CauHoiService;
import com.laptrinhweb.thitracnghiem.Service.SinhVienService;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/student")
public class SinhVienController {
    @Autowired
    private SinhVienService sinhVienService;
    @Autowired private CauHoiService cauHoiService;
    @Autowired private CTBaiThiService ctBaiThiService;
    @ModelAttribute("sinhvien")
    public SinhVien getSinhVien(HttpSession session) {

        String masv = (String) session.getAttribute("masv");
        masv = "N21DCCN067";//tạm
        return sinhVienService.getStudentInfo(masv);
    }
    
    @GetMapping("/home")
    public String Home(Model model, HttpSession session) {
        
        // if (session.getAttribute("masv") == null || (Integer) session.getAttribute("type") != 2)
        // return "redirect:/login";
        
        String masv = (String) session.getAttribute("masv");
        masv = "N21DCCN067";//tạm
        List<InfoDTO> lichthi = sinhVienService.showExamSchedules(masv);
        List<InfoDTO> diem = sinhVienService.showExamResults(masv);
        model.addAttribute("lichthi", lichthi);
        model.addAttribute("diemthi", diem);
        return "/student/home";
    }
    @PostMapping("/exam")
    public String thi(@RequestParam(name = "idthi")Integer IDTHI,
    @RequestParam(name = "mamh")String maMH,
    @RequestParam(name = "tenmh")String tenMh,
    @RequestParam(name = "ngaythi")Date ngayThi,
    @RequestParam(name = "lanthi") Integer lanThi,
    @RequestParam(name = "socau")Integer soCau,
    @RequestParam(name = "thoiluong") Integer thoiLuong,
    HttpSession session,
    Model model){

        // if ((String) session.getAttribute("masv") == null || (Integer) session.getAttribute("type") != 2)   
        // return "redirect:/login";

        List<Integer> dap_an = new ArrayList<>();
        List<CauHoiThiDTO> li = cauHoiService.getListOfExamQuestion(IDTHI,dap_an);
        DanhSachCauHoi danhsachch = new DanhSachCauHoi(li);
        danhsachch.setIdThi(IDTHI);
        InfoDTO thongTinThi = new InfoDTO(IDTHI, lanThi, soCau, thoiLuong, tenMh, ngayThi);

        model.addAttribute("thongTinThi", thongTinThi);
        model.addAttribute("danhsachch", danhsachch);
        session.setAttribute("dapan"+IDTHI, dap_an);
        return "/student/take-exam";
    }
    @PostMapping("/result")
    public String result(DanhSachCauHoi danhsach,HttpSession session){

        @SuppressWarnings("unchecked")
        List<Integer> dap_an =(List<Integer>) session.getAttribute("dapan"+danhsach.getIdThi());
        session.removeAttribute("dapan"+danhsach.getIdThi());
        try {
            ctBaiThiService.saveAllCTBaiThi(danhsach,dap_an);
            
        } catch (Exception e) {
            //Không thể nộp bài//tạm
        }
        return "/student/home";
    }
    @GetMapping("/resultview")
    public String showResult(@RequestParam("idThi") Integer idThi,HttpSession session, Model model){
        DanhSachCauHoi listQuestions = cauHoiService.getPastExamQuestions(idThi);
        InfoDTO resultInfo = sinhVienService.getResultByID(idThi);
        model.addAttribute("resultInfo", resultInfo);
        model.addAttribute("listQuestions", listQuestions);
        return "/student/exam-result.html";
    }
}
