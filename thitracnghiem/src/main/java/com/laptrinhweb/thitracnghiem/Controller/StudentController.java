package com.laptrinhweb.thitracnghiem.Controller;

import java.sql.Date;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoi;
import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
import com.laptrinhweb.thitracnghiem.DTO.PasswordDTO;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Service.CTBaiThiService;
import com.laptrinhweb.thitracnghiem.Service.CauHoiService;
import com.laptrinhweb.thitracnghiem.Service.SinhVienService;
import com.laptrinhweb.thitracnghiem.Service.ThiService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private SinhVienService sinhVienService;
    @Autowired
    private CauHoiService cauHoiService;
    @Autowired
    private CTBaiThiService ctBaiThiService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ThiService thiService;

    @ModelAttribute("sinhvien")
    public SinhVien getSinhVien(HttpSession session) {

        String username = (String) session.getAttribute("username");
        String masv = sinhVienService.getMasvByUsername(username);
        return sinhVienService.getStudentInfo(masv);
    }

    @ModelAttribute("Password")
    public PasswordDTO getPasswordDTO() {
        return new PasswordDTO();
    }

    @GetMapping("/home")
    public String Home(Model model, HttpSession session) {
        String username = (String) session.getAttribute("username");
        String masv = sinhVienService.getMasvByUsername(username);
        List<InfoDTO> lichthi = sinhVienService.showExamSchedules(masv);
        List<InfoDTO> diem = sinhVienService.showExamResults(masv);
        model.addAttribute("lichthi", lichthi);
        model.addAttribute("diemthi", diem);
        return "/student/home";
    }

    @PostMapping("/exam")
    public String thi(@RequestParam(name = "idthi") Integer IDTHI,
            @RequestParam(name = "mamh") String maMH,
            @RequestParam(name = "tenmh") String tenMh,
            @RequestParam(name = "ngaythi") Date ngayThi,
            @RequestParam(name = "lanthi") Integer lanThi,
            @RequestParam(name = "socau") Integer soCau,
            @RequestParam(name = "thoiluong") Integer thoiLuong,
            HttpSession session) {

        if (thiService.checkFinished(IDTHI)) {
            return "redirect:home";
        }
        List<Integer> dap_an = new ArrayList<>();
        InfoDTO thongTinThi = new InfoDTO(IDTHI, lanThi, soCau, thoiLuong, tenMh, ngayThi);
        List<CauHoiThiDTO> li = cauHoiService.getListOfExamQuestion(IDTHI, dap_an);
        DanhSachCauHoi danhsachch = new DanhSachCauHoi(li);
        danhsachch.setIdThi(IDTHI);
        try {
            ctBaiThiService.saveAllCTBaiThi(danhsachch, dap_an, false);

        } catch (Exception e) {
            return "redirect:home";
        }
        thongTinThi.setRemainingTime((long) thoiLuong * 60);
        session.setAttribute("thongTinThi" + IDTHI, thongTinThi);
        session.setAttribute("danhsachch" + IDTHI, danhsachch);
        session.setAttribute("dapan" + IDTHI, dap_an);
        return "redirect:getexam/" + IDTHI;
    }

    @GetMapping("getexam/{idThi}")
    public String getExam(@PathVariable("idThi") Integer idThi, Model model, HttpSession session) {

        InfoDTO thongTinThi = (InfoDTO) session.getAttribute("thongTinThi" + idThi);
        if (thongTinThi == null)
            return "redirect:/student/home";
        LocalDateTime startTime = (LocalDateTime) session.getAttribute("startTime" + idThi);
        if (startTime == null) {
            session.setAttribute("startTime" + idThi, LocalDateTime.now());
        } else {

            Duration duration = Duration.between(startTime, LocalDateTime.now());
            // Lấy số giây từ Duration
            long seconds = duration.getSeconds();
            thongTinThi.setRemainingTime((long) thongTinThi.getThoiLuong() * 60 - seconds);
        }
        model.addAttribute("thongTinThi", thongTinThi);
        model.addAttribute("danhsachch", session.getAttribute("danhsachch" + idThi));
        return "/student/take-exam";
    }

    @PostMapping("/result")
    public String result(DanhSachCauHoi danhsach, HttpSession session) {
        InfoDTO thongTinThi = (InfoDTO) session.getAttribute("thongTinThi" + danhsach.getIdThi());
        if (thongTinThi == null)
            return "redirect:home";
        LocalDateTime startTime = (LocalDateTime) session.getAttribute("startTime" + danhsach.getIdThi());
        session.removeAttribute("thongTinThi" + danhsach.getIdThi());
        session.removeAttribute("danhsachch" + danhsach.getIdThi());
        if (LocalDateTime.now().isAfter(startTime.plusSeconds(thongTinThi.getThoiLuong() * 60 + 10))) {
            session.removeAttribute("dapan" + danhsach.getIdThi());
            ctBaiThiService.setFinishedThi(danhsach.getIdThi());
            return "redirect:home";
        }
        @SuppressWarnings("unchecked")
        List<Integer> dap_an = (List<Integer>) session.getAttribute("dapan" + danhsach.getIdThi());
        session.removeAttribute("dapan" + danhsach.getIdThi());
        try {
            ctBaiThiService.saveAllCTBaiThi(danhsach, dap_an, true);

        } catch (Exception e) {
            System.out.println("Lỗi không thể lưu");
        }
        return "redirect:resultview?idThi=" + danhsach.getIdThi();
    }

    @GetMapping("/resultview")
    public String showResult(@RequestParam("idThi") Integer idThi, HttpSession session, Model model) {
        DanhSachCauHoi listQuestions = cauHoiService.getPastExamQuestions(idThi);
        InfoDTO resultInfo = sinhVienService.getResultByID(idThi);
        model.addAttribute("resultInfo", resultInfo);
        model.addAttribute("listQuestions", listQuestions);
        return "/student/exam-result.html";
    }

    @PostMapping("/changepass")
    public String changePass(@Valid PasswordDTO passwordDTO, BindingResult errors,
            RedirectAttributes redirectAttributes, HttpSession session) {
        boolean check = true;
        if (!passwordDTO.getNewPassword().equals(passwordDTO.getConfirmNewPassword())) {
            errors.rejectValue("confirmNewPassword", "error", "Mật khẩu không khớp");
            check = false;
        }
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message",
                    "Thay đổi mật khẩu thất bại! Vui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.Password", errors);
            redirectAttributes.addFlashAttribute("Password", passwordDTO);
            return "redirect:home";
        }

        String username = (String) session.getAttribute("username");
        String masv = sinhVienService.getMasvByUsername(username);
        if (!passwordEncoder.matches(passwordDTO.getOldPassword(), sinhVienService.getPasswordByID(masv))) {
            errors.rejectValue("oldPassword", "", "Mật khẩu không đúng");
            check = false;
        } else {
            try {
                sinhVienService.updatePassword(masv, passwordEncoder.encode(passwordDTO.getNewPassword()));
                check = true;
            } catch (Exception ex) {
                check = false;
            }
        }
        if (check) {
            redirectAttributes.addFlashAttribute("message", "Thay đổi mật khẩu thành công");
        } else {
            redirectAttributes.addFlashAttribute("message",
                    "Thay đổi mật khẩu thất bại! Vui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.Password", errors);
            redirectAttributes.addFlashAttribute("Password", passwordDTO);
        }
        return "redirect:home";
    }

    @PostMapping("/changeinfo")
    public String changeInfo(SinhVien sinhVien, BindingResult errors, RedirectAttributes redirectAttributes,
            HttpSession session) {
        if (sinhVien.getHo().equals(""))
            errors.rejectValue("ho", "", "Họ không được trống");
        if (sinhVien.getTen().equals(""))
            errors.rejectValue("ten", "", "Tên không được trống");
        if (sinhVien.getDiaChi().equals(""))
            errors.rejectValue("diaChi", "", "Địa chỉ không được trống");
        if (sinhVien.getNgaySinh() == null)
            errors.rejectValue("ngaySinh", "", "Ngày sinh không được trống");
        if (sinhVien.getEmail().equals(""))
            errors.rejectValue("email", "", "Email không được trống");
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message",
                    "Thay đổi thông tin thất bại! Vui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sinhvien", errors);
            redirectAttributes.addFlashAttribute("sinhvien", sinhVien);
        } else {
            try {
                sinhVienService.modifyInfo(sinhVien);
                redirectAttributes.addFlashAttribute("message", "Thay đổi thông tin thành công!");

            } catch (Exception ex) {
                redirectAttributes.addFlashAttribute("message", "Thay đổi thông tin thất bại!" + ex.getMessage());
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.sinhvien", errors);
                redirectAttributes.addFlashAttribute("sinhvien", sinhVien);
            }
        }
        return "redirect:home";
    }
}