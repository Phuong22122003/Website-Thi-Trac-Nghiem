package com.laptrinhweb.thitracnghiem.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.laptrinhweb.thitracnghiem.DTO.ForgotPasswordDTO;
import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Entity.NhanVien;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.Interface.GiangVienRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.NhanVienRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.SinhVienRepository;
import com.laptrinhweb.thitracnghiem.Service.EmailService;
import com.laptrinhweb.thitracnghiem.Service.GiangVienService;
import com.laptrinhweb.thitracnghiem.Service.NhanVienService;
import com.laptrinhweb.thitracnghiem.Service.SinhVienService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;



@Controller
public class LoginController {

    @Autowired private SinhVienRepository sinhVienRepository;
    @Autowired private GiangVienRepository giangVienRepository;
    @Autowired private NhanVienRepository nhanVienRepository;
    @Autowired private EmailService emailService;
    @Autowired private SinhVienService sinhVienService;
    @Autowired private GiangVienService giangVienService;
    @Autowired private NhanVienService nhanVienService;
    @GetMapping("/login")
    public String login(@RequestParam(name = "error",required =  false) Object error) {
        if(error!=null)System.out.println("Sai thông tin");
        return "/login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
          Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout"; // Chuyển hướng đến trang login sau khi logout
    }
    @GetMapping("/forgot-password")
    public String getPageForgot(Model model){
        model.addAttribute("email",new ForgotPasswordDTO());
        return "forgot";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @ModelAttribute("email")ForgotPasswordDTO emailDTO,BindingResult error, Model model){
        if(!error.hasErrors()){
            String email = emailDTO.getEmail();
            SinhVien sv = sinhVienRepository.findStudentByEmail(email);
            GiangVien gv = giangVienRepository.findByEmail(email);
            NhanVien nv = nhanVienRepository.findEmployeeByEmail(email);
            if(sv==null&&
                gv == null && 
                nv==null
            ){
                error.rejectValue("email", "email.invalid","Email không tồn tại");
                model.addAttribute("message", "Vui lòng nhập chính xác");
                return "forgot";
            }
            else{
                String status;
                if(sv!=null){
                    status = sinhVienService.resetStudentPassword(email);
                }
                else if(gv!=null){
                    status = giangVienService.resetPasswordLecturer(gv.getMaGv());
                }
                else{
                    status = nhanVienService.resetEmployeePassword(email);
                }
                if(status.contains("Gửi mail thất bại, vui lòng thử lại!")){
                    model.addAttribute("message",status);
                    return "forgot";
                }
            }
        }
        else
        {
            model.addAttribute("message", "Vui lòng nhập chính xác");
            return "forgot";
        }
        return "login";
    }
}
