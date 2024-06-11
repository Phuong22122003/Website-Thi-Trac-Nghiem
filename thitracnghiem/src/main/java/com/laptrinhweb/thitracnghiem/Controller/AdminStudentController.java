package com.laptrinhweb.thitracnghiem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptrinhweb.thitracnghiem.DTO.ListEmailDTO;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Entity.NhanVien;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.Interface.SinhVienRepository;
import com.laptrinhweb.thitracnghiem.Service.GiangVienService;
import com.laptrinhweb.thitracnghiem.Service.LopHocService;
import com.laptrinhweb.thitracnghiem.Service.NhanVienService;
import com.laptrinhweb.thitracnghiem.Service.SinhVienService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/admin")
public class AdminStudentController {
    @Autowired
    SinhVienService sinhVienService;
    @Autowired
    LopHocService lopHocService;
    @Autowired
    NhanVienService nhanVienService;
    @Autowired SinhVienRepository sinhVienRepository;
    @Autowired private GiangVienService giangVienService;
    //Lấy danh sách lớp 
    @ModelAttribute("listOfClass")
    public List<Lop> getListOfClass() {
        return lopHocService.getAllClass();
    }
    //Hiện thông tin nhân viên 
    @ModelAttribute("nhanvien")
    public NhanVien nhanVienLogin(HttpSession session) {
        String username = (String) session.getAttribute("username");
        return nhanVienService.findByUsername(username);
    }
    //Đưa sinh viên lên cho việc nhập form thêm mới
    @ModelAttribute("student")
    public SinhVien newStudent() {
        SinhVien sv = new SinhVien();
        Lop lop = new Lop();
        sv.setLop(lop);
        return sv;
    }
    // Đưa sinh viên lên cho việc nhập form sửa chữa
    @ModelAttribute("editStudent")
    public SinhVien editStudent() {
        SinhVien sv = new SinhVien();
        Lop lop = new Lop();
        sv.setLop(lop);
        return sv;
    }
    //Đưa danh sách email trống cho giảng viên có nhu cầu gửi mail cho sinh viên
    @ModelAttribute("EmailDTO")
    public ListEmailDTO createListOfEmail() {
        List<String> emails = new ArrayList<>();
        for (int i = 0; i < 150; i++) {
            emails.add("");
        }
        return new ListEmailDTO(emails);
    }

    //Khi người dùng vào trang student thì sẽ tìm lớp và trả về danh sách sinh viên của lớp đầu
    @GetMapping("/student")
    public String student(Model model) {
        @SuppressWarnings("unchecked")
        List<Lop> classes = (List<Lop>) model.getAttribute("listOfClass");
        if (classes == null || classes.size() == 0) {
            model.addAttribute("listOfStudent", null);
            return "/admin/student";
        }
        return "redirect:student/" + classes.get(0).getMaLop();
    }

    @GetMapping("/student/{maLop}")
    public String getStudentsByClass(@PathVariable("maLop") String maLop,
            Model model) {
        List<SinhVien> list = sinhVienService.getStudentsByClass(maLop);
        model.addAttribute("listOfStudent", list);
        model.addAttribute("selectedMaLop", maLop);
        return "/admin/student";
    }

    @PostMapping("/deleteStudent")
    public String deleteStudent(@RequestParam(name = "masv") String masv,
            @RequestParam(name = "maLop") String maLop,
            RedirectAttributes redirectAttributes) {
        int status = sinhVienService.deleteStudent(masv);
        if (status == 0) {
            redirectAttributes.addFlashAttribute("message", "Sinh viên đã đăng ký thi không thể xóa");
        } else
            redirectAttributes.addFlashAttribute("message", "Xóa thành công");
        return "redirect:student/" + maLop.trim();
    }

    // chỉnh sửa thông tin sinh viên
    @PostMapping("/editStudent")
    public String editStudent(@ModelAttribute("editStudent") SinhVien student, RedirectAttributes redirectAttributes,
            BindingResult errors) {
        if (student.getHo().length() == 0)
            errors.rejectValue("ho", "editStudent", "Họ không được trống");
        if (student.getTen().length() == 0)
            errors.rejectValue("ten", "editStudent", "Tên không được trống");
        if (student.getDiaChi().length() == 0)
            errors.rejectValue("diaChi", "editStudent", "Địa chỉ không được trống");
        if (student.getNgaySinh() == null)
            errors.rejectValue("ngaySinh", "editStudent", "Ngày sinh không được trống");
        if(student.getEmail().length() == 0)
            errors.rejectValue("email", null, "Email không được trống");
        else
            if(sinhVienService.checkExistEmail(student.getMasv(),student.getEmail()))
              errors.rejectValue("email", null, "Email bị trùng với sinh viên khác");
        else 
            if(giangVienService.findByEmail(student.getEmail())!=null||
                nhanVienService.findByEmail(student.getEmail())!=null)
                errors.rejectValue("email", null, "Email đã được sử dụng");
        
        String classID = student.getLop().getMaLop().trim();
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Sửa thất bại\nVui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editStudent", errors);
            redirectAttributes.addFlashAttribute("editStudent", student);
            return "redirect:student/" + classID;
        }
        try {
            sinhVienService.modifyInfo(student);
            redirectAttributes.addFlashAttribute("message", "Sửa thông tin thành công");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "Sửa thất bại\n" + ex.getMessage());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.student", errors);
            redirectAttributes.addFlashAttribute("student", student);
        }
        return "redirect:student/" + classID;
    }

    @PostMapping("/addNewStudent") //
    public String addNewStudent(@Valid @ModelAttribute("student") SinhVien student,
            BindingResult errors,
            RedirectAttributes redirectAttributes) {
        String classID = student.getLop().getMaLop().trim();
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Thêm thất bại\nVui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.student", errors);
            redirectAttributes.addFlashAttribute("student", student);
            return "redirect:student/" + classID;
        }
        if(sinhVienRepository.findSinhVienByMasv(student.getMasv())!=null){
            errors.rejectValue("masv", "", "Mã sinh viên đã tồn tại");
        }
        if(sinhVienRepository.getStudentByUserName(student.getUserName())!=null||
                    giangVienService.findByUsername(student.getUserName())!=null||
                    nhanVienService.findByUsername(student.getUserName())!=null){

            errors.rejectValue("userName", "", "UserName đã tồn tại");
        }
        if(sinhVienRepository.findStudentByEmail(student.getEmail())!=null||
            giangVienService.findByEmail(student.getEmail())!=null||
            nhanVienService.findByEmail(student.getEmail())!=null){
            errors.rejectValue("email", "", "Email đã tồn tại");
        }
        if (errors.hasErrors()) {
            redirectAttributes.addFlashAttribute("message", "Thêm thất bại\nVui lòng nhập chính xác các trường sau");
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.student", errors);
            redirectAttributes.addFlashAttribute("student", student);
            return "redirect:student/" + classID;
        }
        try {
            sinhVienService.addNewStudent(student);
            redirectAttributes.addFlashAttribute("message", "Thêm thành công");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("message", "Thêm thất bại\n" + ex.getMessage());
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.student", errors);
            redirectAttributes.addFlashAttribute("student", student);
        }
        return "redirect:student/" + classID;
    }

    @PostMapping("/sendemail")
    public String sendMail(ListEmailDTO emails, RedirectAttributes redirectAttributes) {
        String maLop = emails.getListEmail().get(0).trim();
        emails.getListEmail().removeIf(email -> email.length() == 0);
        List<String> emailsError = sinhVienService
                .sendNewPasswordToStudent(emails.getListEmail().subList(1, emails.getListEmail().size()));
        if (emailsError.size() != 0) {
            redirectAttributes.addFlashAttribute("message", "Gửi email gặp lỗi");
            redirectAttributes.addFlashAttribute("emailsError", emailsError);
        } else
            redirectAttributes.addFlashAttribute("message", "Gửi email thành công");
        return "redirect:student/" + maLop;
    }
}
