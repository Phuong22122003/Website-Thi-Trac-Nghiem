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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.laptrinhweb.thitracnghiem.DTO.EmailDTO;
import com.laptrinhweb.thitracnghiem.DTO.ValidationDTO;
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
import jakarta.servlet.http.HttpSession;
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

    @ModelAttribute("email")
    public EmailDTO getEmailDTO(){
        return new EmailDTO();
    }
    @GetMapping("/forgot-password")
    public String getPageForgot(){
        return "forgot";
    }
    @PostMapping("/forgot-password")
    public String forgotPassword(@Valid @ModelAttribute("email")EmailDTO emailDTO,BindingResult error, 
                                    Model model,HttpSession session){
        if(!error.hasErrors()){
            //Lấy email
            String email = emailDTO.getEmail();
            if(sinhVienRepository.findStudentByEmail(email) != null)
                session.setAttribute("role", "STUDENT");
            else if((giangVienRepository.findByEmail(email))!=null){
                session.setAttribute("role", "TEACHER");
            }
            else if ((nhanVienRepository.findEmployeeByEmail(email))!=null){       
                session.setAttribute("role", "EMPLOYEE");
            }
            //email không tồn tại
            else{
                error.rejectValue("email", "email.invalid","Email không tồn tại");
                model.addAttribute("message", "Vui lòng nhập chính xác");
                return "forgot";
            }
            
            String code = emailService.randomPassword();
            session.setAttribute("email", email);
            session.setAttribute("code", code);//dùng làm code
            emailService.sendMessage(email, "Mã xác nhận", "Code: " + code, "");            
        }
        else
        {
            model.addAttribute("message", "Vui lòng nhập chính xác");
            return "forgot";
        }
        return "redirect:validation";
    }

    @ModelAttribute("validationDTO")
    public ValidationDTO getValidationDTO(){
        return new ValidationDTO();
    }
    @GetMapping("/validation")
    public String getValidationView(){
        return "validation";
    }
    @PostMapping("/validation")
    public String validate(@Valid ValidationDTO validationDTO,BindingResult errors,
                            RedirectAttributes redirectAttributes,HttpSession session){
        String code = (String)session.getAttribute("code");
        String email = (String)session.getAttribute("email");
        String role = (String)session.getAttribute("role");
        if(code == null){
            redirectAttributes.addFlashAttribute("message","Vui lòng thao tác lại");
            return "redirect:login";
        }
        if(!errors.hasErrors()&&!code.trim().equals(validationDTO.getCode())){
            errors.rejectValue("code", "", "Mã xác nhận không chính xác");
        }
        if(errors.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.validationDTO", errors);
            redirectAttributes.addFlashAttribute("validationDTO", validationDTO);
            redirectAttributes.addFlashAttribute("message","Vui lòng nhập chính xác mã xác nhận");
            return "redirect:validation";
        }

        String status;
        if(role.equals("STUDENT")){
            status = sinhVienService.resetStudentPassword(email);
        }
        else if(role.equals("TEACHER")){
            
            status = giangVienService.resetPasswordLecturer(giangVienRepository.findByEmail(email).getMaGv());
        }
        else{
            status = nhanVienService.resetEmployeePassword(email);
        }
        if(status.contains("Gửi mail thất bại, vui lòng thử lại!")){
            redirectAttributes.addFlashAttribute("message",status);
            return "redirect:validation";
        }
        redirectAttributes.addFlashAttribute("message","Đổi mật khẩu thành công");
        session.removeAttribute("email");
        session.removeAttribute("code");
        session.removeAttribute("role");
        return "redirect:login";
    }
}
