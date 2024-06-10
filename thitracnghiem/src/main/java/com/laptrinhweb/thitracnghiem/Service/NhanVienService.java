package com.laptrinhweb.thitracnghiem.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.NhanVien;
import com.laptrinhweb.thitracnghiem.Repository.Interface.NhanVienRepository;

@Service
public class NhanVienService {
    @Autowired
    NhanVienRepository nhanVienRepository;
    @Autowired private EmailService emailService;
    @Autowired private PasswordEncoder passwordEncoder;
    public NhanVien findByManv(String manv) {
        return nhanVienRepository.findByManv(manv);
    }

    public NhanVien findByUsername(String username) {
        return nhanVienRepository.findByUserNameAndTrangThaiXoa(username, false);
    }
    public String resetEmployeePassword(String email){
        String newPassword = emailService.randomPassword();
        String status =  emailService.sendMessage(email, "New passowrd", "Password: "+newPassword , "ResetPassword thành công");
        if(status.equals("ResetPassword thành công")){
            nhanVienRepository.updatePassword(passwordEncoder.encode(newPassword), email);   
        }
        return status;
    }
}
