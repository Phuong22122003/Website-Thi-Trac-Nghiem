package com.laptrinhweb.thitracnghiem.Controller;

import java.sql.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;
import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Entity.NhanVien;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.Interface.LopRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.SinhVienRepository;
import com.laptrinhweb.thitracnghiem.Service.DangKyThiService;
import com.laptrinhweb.thitracnghiem.Service.EmailService;
import com.laptrinhweb.thitracnghiem.Service.GiangVienService;
import com.laptrinhweb.thitracnghiem.Service.LopService;
import com.laptrinhweb.thitracnghiem.Service.MonHocService;
import com.laptrinhweb.thitracnghiem.Service.NhanVienService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    MonHocService monHocService;
    @Autowired
    GiangVienService giangVienService;
    @Autowired
    LopService lopService;
    @Autowired
    LopRepository lopRepository;
    @Autowired
    DangKyThiService dangKyThiService;
    @Autowired
    NhanVienService nhanVienService;
    @Autowired
    EmailService emailService;
    @Autowired
    SinhVienRepository sinhVienRepository;

    @ModelAttribute("nhanvien")
    public NhanVien nhanVienLogin(HttpSession session) {
        String username = (String) session.getAttribute("username");
        // NhanVien nv = nhanVienService.findByUsername(username);
        return nhanVienService.findByUsername(username);
    }

    @GetMapping("/subject")
    public String subject(ModelMap model) {
        List<MonHoc> monHocList = monHocService.getAllMonHoc();
        model.addAttribute("monHocList", monHocList);
        return "/admin/subject";
    }

    @GetMapping("/deleteMonHoc/{mamh}")
    public String deleteMonHoc(@PathVariable String mamh, ModelMap model, RedirectAttributes redirectAttributes) {
        int statusXoaMonHoc = monHocService.deleteByMamh(mamh);
        redirectAttributes.addFlashAttribute("statusXoaMonHoc", statusXoaMonHoc);
        return "redirect:/admin/subject";
    }

    @ModelAttribute("monHoc")
    public MonHoc createNewMonHoc() {
        return new MonHoc();
    }

    @PostMapping("/addMonHoc")
    public String addMonHoc(@Valid @ModelAttribute("monHoc") MonHoc monHoc,
            RedirectAttributes redirectAttributes, BindingResult errors) {
        if (monHoc.getMamh().trim().length() == 0) {
            errors.rejectValue("mamh", "monHoc", "Mã môn học không được bỏ trống");
        }
        if (monHoc.getTenmh().trim().length() == 0) {
            errors.rejectValue("tenmh", "monHoc", "Tên môn học không được bỏ trống");
        }
        if (monHoc.getSoTietLt() < 0) {
            errors.rejectValue("soTietLt", "monHoc", "Số tiết lý thuyết không thể < 0");
        }
        if (monHoc.getSoTietTh() < 0) {
            errors.rejectValue("soTietTh", "monHoc", "Số tiết thực hành không thể < 0");
        }
        if (monHoc.getSoTietLt() + monHoc.getSoTietTh() <= 0) {
            errors.rejectValue("soTietTh", "monHoc", "Số tiết LT và Số tiết TH không thể <= 0");
        }
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorAdd", true);
            // for (FieldError error : errors.getFieldErrors()) {
            // redirectAttributes.addFlashAttribute("errorAdd_" + error.getField(),
            // error.getDefaultMessage());
            // }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.monHoc", errors);
            redirectAttributes.addFlashAttribute("monHoc", monHoc);
            redirectAttributes.addFlashAttribute("monHocError", monHoc);
            return "redirect:/admin/subject";
        }

        monHoc.setTrangThaiXoa(false);
        int statusThemMonHoc = monHocService.addMonHoc(monHoc);
        if (statusThemMonHoc != 0) {
            redirectAttributes.addFlashAttribute("errorAdd", true);
            errors.rejectValue("mamh", "monHoc", "Mã môn học đã tồn tại!");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.monHoc", errors);
            redirectAttributes.addFlashAttribute("monHoc", monHoc);
            redirectAttributes.addFlashAttribute("monHocError", monHoc);
            // for (FieldError error : errors.getFieldErrors()) {
            // redirectAttributes.addFlashAttribute("errorAdd_" + error.getField(),
            // error.getDefaultMessage());
            // }
            // redirectAttributes.addFlashAttribute("monHocError", monHoc);
            return "redirect:/admin/subject";
        }
        redirectAttributes.addFlashAttribute("statusThemMonHoc", statusThemMonHoc);
        return "redirect:/admin/subject";
    }

    @PostMapping("/editMonHoc")
    public String editMonHoc(@ModelAttribute("monHoc") MonHoc monHoc, RedirectAttributes redirectAttributes,
            BindingResult errors) {
        if (monHoc.getMamh().trim().length() == 0) {
            errors.rejectValue("mamh", "monHoc", "Mã môn học không được bỏ trống");
        }
        if (monHoc.getTenmh().trim().length() == 0) {
            errors.rejectValue("tenmh", "monHoc", "Tên môn học không được bỏ trống");
        }
        if (monHoc.getSoTietLt() < 0) {
            errors.rejectValue("soTietLt", "monHoc", "Số tiết lý thuyết không thể < 0");
        }
        if (monHoc.getSoTietTh() < 0) {
            errors.rejectValue("soTietTh", "monHoc", "Số tiết thực hành không thể < 0");
        }
        if (monHoc.getSoTietTh() + monHoc.getSoTietLt() <= 0) {
            errors.rejectValue("soTietTh", "monHoc", "STLT và STTH không thể < 0");
        }
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorEdit", true);
            // for (FieldError error : errors.getFieldErrors()) {
            // redirectAttributes.addFlashAttribute("errorEdit_" + error.getField(),
            // error.getDefaultMessage());
            // }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.monHoc", errors);
            redirectAttributes.addFlashAttribute("monHoc", monHoc);
            redirectAttributes.addFlashAttribute("monHocError", monHoc);
            return "redirect:/admin/subject";
        }
        int statusEditMonHoc = monHocService.editMonHoc(monHoc.getMamh(), monHoc.getTenmh(), monHoc.getSoTietLt(),
                monHoc.getSoTietTh());
        if (statusEditMonHoc != 0) {
            redirectAttributes.addFlashAttribute("errorEdit", true);
            errors.rejectValue("mamh", "monHoc", "Mã môn học không tồn tại!");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.monHoc", errors);
            redirectAttributes.addFlashAttribute("monHoc", monHoc);
            // for (FieldError error : errors.getFieldErrors()) {
            // redirectAttributes.addFlashAttribute("errorEdit_" + error.getField(),
            // error.getDefaultMessage());
            // }
            redirectAttributes.addFlashAttribute("monHocError", monHoc);
            return "redirect:/admin/subject";
        }
        redirectAttributes.addFlashAttribute("statusEditMonHoc", statusEditMonHoc);
        return "redirect:/admin/subject";
    }

    @GetMapping("/searchMonHoc")
    public String searchMonHoc(@RequestParam(name = "keyword") String keyword, ModelMap model) {
        List<MonHoc> monHocList = monHocService.searchMonHoc(keyword);
        model.addAttribute("monHocList", monHocList);
        return "/admin/subject";
    }

    // =============================Giang Vien=============================
    @ModelAttribute("giangVien")
    public GiangVien createNewGiangVien() {
        return new GiangVien();
    }

    @GetMapping("/lecturer")
    public String lecturer(ModelMap model) {
        List<GiangVien> giangVienList = giangVienService.findAllGiangVien();
        model.addAttribute("giangVienList", giangVienList);

        List<MonHoc> monHocList = monHocService.getAllMonHoc();
        model.addAttribute("monHocList", monHocList);

        List<Lop> lopList = lopService.findAllLop();
        model.addAttribute("lopList", lopList);
        return "/admin/lecturer";
    }

    @GetMapping("/deleteGiangVien/{maGv}")
    public String getMethodName(@PathVariable("maGv") String maGv, RedirectAttributes redirectAttributes) {
        int statusXoaGiangVien = giangVienService.deleteByMaGv(maGv);
        redirectAttributes.addFlashAttribute("statusXoaGiangVien", statusXoaGiangVien);
        return "redirect:/admin/lecturer";
    }

    @PostMapping("/addGiangVien")
    public String addGiangVien(@Validated @ModelAttribute("giangVien") GiangVien giangVien, BindingResult errors,
            RedirectAttributes redirectAttributes) {
        if (giangVien.getHo().trim().length() == 0) {
            errors.rejectValue("ho", "giangVien", "Họ giảng viên không được bỏ trống");
        }
        if (giangVien.getMaGv().trim().length() == 0) {
            errors.rejectValue("maGv", "giangVien", "Mã giảng viên không được bỏ trống");
        }
        if (giangVien.getPassWord().trim().length() == 0) {
            errors.rejectValue("passWord", "giangVien", "Password không được bỏ trống");
        }
        if (giangVien.getTen().trim().length() == 0) {
            errors.rejectValue("ten", "giangVien", "Tên giảng viên không được bỏ trống");
        }
        if (giangVien.getUserName().trim().length() == 0) {
            errors.rejectValue("userName", "giangVien", "Username không được bỏ trống");
        }
        GiangVien gv = giangVienService.findByEmail(giangVien.getEmail());
        GiangVien checkUsernameGiangVien = giangVienService.findByUsername(giangVien.getUserName());
        SinhVien checkEmailSinhVien = sinhVienRepository.findStudentByEmail(giangVien.getEmail());
        SinhVien checkUsernameSinhVien = sinhVienRepository.getStudentByUserName(giangVien.getUserName());
        if (gv!= null || checkEmailSinhVien!=null)  {
            errors.rejectValue("email", "giangVien", "Email đã tồn tại");
        }
        if (checkUsernameSinhVien != null) {
            errors.rejectValue("userName", "giangVien", "Username đã tồn tại"); 
        }
        if (checkUsernameGiangVien != null) {
            errors.rejectValue("userName", "giangVien", "Username đã tồn tại"); 
        }
        // if (giangVien.getEmail().trim().length() == 0) {
        //     errors.rejectValue("email", "giangVien", "Email không được bỏ trống");
        // }
        
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorAdd", true);
            // for (FieldError error : errors.getFieldErrors()) {
            // redirectAttributes.addFlashAttribute("errorAdd_" + error.getField(),
            // error.getDefaultMessage());
            // }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.giangVien", errors);
            redirectAttributes.addFlashAttribute("giangVien", giangVien);

            redirectAttributes.addFlashAttribute("giangVienError", giangVien);
            return "redirect:/admin/lecturer";
        }

        // redirectAttributes.addFlashAttribute("giangVienError", giangVien);
        giangVien.setTrangThaiXoa(false);
        int statusThemGiangVien = giangVienService.addGiangVien(giangVien);
        if (statusThemGiangVien == 1) {
            errors.rejectValue("maGv", "giangVien", "Mã giáo viên đã tồn tại");
            redirectAttributes.addFlashAttribute("errorAdd", true);
            // for (FieldError error : errors.getFieldErrors()) {
            // redirectAttributes.addFlashAttribute("errorAdd_" + error.getField(),
            // error.getDefaultMessage());
            // }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.giangVien", errors);
            redirectAttributes.addFlashAttribute("giangVien", giangVien);
            redirectAttributes.addFlashAttribute("giangVienError", giangVien);
            return "redirect:/admin/lecturer";
        } else if (statusThemGiangVien == 2) {
            errors.rejectValue("userName", "giangVien", "Tên tài khoản đã tồn tại");
            redirectAttributes.addFlashAttribute("errorAdd", true);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.giangVien", errors);
            redirectAttributes.addFlashAttribute("giangVien", giangVien);
            redirectAttributes.addFlashAttribute("giangVienError", giangVien);
            return "redirect:/admin/lecturer";
        }

        System.out.println("================================================");
        System.out.println(statusThemGiangVien);
        redirectAttributes.addFlashAttribute("statusThemGiangVien", statusThemGiangVien);
        return "redirect:/admin/lecturer";
    }

    @PostMapping("/editGiangVien")
    public String editGiangVien(@Validated @ModelAttribute("giangVien") GiangVien giangVien, BindingResult errors, RedirectAttributes redirectAttributes
           ) {
        if (giangVien.getHo().trim().length() == 0) {
            errors.rejectValue("ho", "giangVien", "Họ giảng viên không được bỏ trống");
        }
        if (giangVien.getMaGv().trim().length() == 0) {
            errors.rejectValue("maGv", "giangVien", "Mã giảng viên không được bỏ trống");
        }
        if (giangVien.getTen().trim().length() == 0) {
            errors.rejectValue("ten", "giangVien", "Tên giảng viên không được bỏ trống");
        }
        // if (giangVien.getEmail().trim().length() == 0) {
        //     errors.rejectValue("email", "giangVien", "Email không được bỏ trống");
        // }
        GiangVien gv = giangVienService.findByEmail(giangVien.getEmail());
        SinhVien sv = sinhVienRepository.findStudentByEmail(giangVien.getEmail());
        if (gv!= null && !gv.getMaGv().equals(giangVien.getMaGv()))  {
            errors.rejectValue("email", "giangVien", "Email đã tồn tại");
            }
        if (sv != null) { 
            errors.rejectValue("email", "giangVien", "Email đã tồn tại");
        }
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("errorEdit", true);
            // for (FieldError error : errors.getFieldErrors()) {
            // redirectAttributes.addFlashAttribute("errorEdit_" + error.getField(),
            // error.getDefaultMessage());
            // }
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.giangVien", errors);
            redirectAttributes.addFlashAttribute("giangVien", giangVien);
            redirectAttributes.addFlashAttribute("giangVienError", giangVien);
            return "redirect:/admin/lecturer";
        }

        giangVien.setTrangThaiXoa(false);
        int statusEditGiangVien = giangVienService.editGiangVien(giangVien.getHo(), giangVien.getTen(),
                giangVien.getEmail(), giangVien.getHocVi(), giangVien.getHocHam(), giangVien.isGioiTinh(),
                giangVien.getMaGv());
        redirectAttributes.addFlashAttribute("statusEditGiangVien", statusEditGiangVien);
        return "redirect:/admin/lecturer";
    }

    @GetMapping("/searchGiangVien")
    public String searchGiangVien(@RequestParam(name = "keyword") String keyword, Model model) {
        List<GiangVien> giangVienList = giangVienService.searchGiangVien(keyword);
        model.addAttribute("giangVienList", giangVienList);
        return "/admin/lecturer";
    }

    // =============================Create Exam=============================
    @ModelAttribute("dangKyThi")
    public DangKyThi createNewDangKyThi() {
        return new DangKyThi();
    }

    @PostMapping("/register-exam")
    public String getMethodName(@RequestParam(name = "maGv") String maGv,
            @RequestParam(name = "manv") String manv,
            @RequestParam(name = "mamh", required = false) String mamh,
            @RequestParam(name = "maLop") String maLop,
            @RequestParam(name = "ngayThi") String ngayThi,
            @RequestParam(name = "thoiLuong") int thoiLuong, @RequestParam(name = "soCau") int soCau,
            RedirectAttributes redirectAttributes, Model model) {
        GiangVien gv = giangVienService.findByMaGv(maGv);
        MonHoc mh = monHocService.findByMamh(mamh);
        Lop lop = lopService.findByMaLop(maLop);
        NhanVien nv = nhanVienService.findByManv(manv);
        DangKyThi dangKyThi = new DangKyThi();
        if (gv == null) {
            System.out.println("===========voday============");
            redirectAttributes.addFlashAttribute("errorExam", "Mã giáo viên không tồn tại!");
            return "redirect:/admin/lecturer";
        }
        if (mh == null) {
            redirectAttributes.addFlashAttribute("errorExam", "Mã môn học không tồn tại!");
            return "redirect:/admin/lecturer";
        }
        if (lop == null) {
            redirectAttributes.addFlashAttribute("errorExam", "Mã lớp không tồn tại!");
            return "redirect:/admin/lecturer";
        }
        if (nv == null) {
            redirectAttributes.addFlashAttribute("errorExam", "Mã nhân viên không tồn tại!");
            return "redirect:/admin/lecturer";
        }
        int soCauTrongDb = dangKyThiService.countCauHoiByMonHoc(mh);
        if (soCauTrongDb < soCau) {
            redirectAttributes.addFlashAttribute("errorExam", String.format("Số câu thi phải <= %d", soCauTrongDb));

            return "redirect:/admin/lecturer";
        }
        try {
            Date ngayThiDate = Date.valueOf(ngayThi);
            dangKyThi.setNgayThi(ngayThiDate);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorExam", "Ngày thi không hợp lệ!");
            return "redirect:/admin/lecturer";
        }
        dangKyThi.setGiangVien(gv);
        dangKyThi.setLop(lop);
        dangKyThi.setMonHoc(mh);
        dangKyThi.setNhanVien(nv);
        dangKyThi.setSoCau(soCau);
        dangKyThi.setThoiLuong(thoiLuong);
        dangKyThi.setTrangThaiXoa(false);
        int statusDangKyThi = dangKyThiService.registerExam(dangKyThi);
        redirectAttributes.addFlashAttribute("statusDangKyThi", statusDangKyThi);

        return "redirect:/admin/lecturer";
    }

    // =======================ResetPassword==========================
    @GetMapping("/resetPasswordLecturer/{maGv}")
    public String resetPasswordLecturer(@PathVariable("maGv") String maGv, RedirectAttributes redirectAttributes) {
        String message = giangVienService.resetPasswordLecturer(maGv);
        redirectAttributes.addFlashAttribute("messageResetPassword", message);
        return "redirect:/admin/lecturer";
    }

}
