package com.laptrinhweb.thitracnghiem.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/*
 * Dùng để kiểm tra email người dùng nhập khi người dùng quên mật khẩu và muốn lấy mật khẩu mới
 */
public class EmailDTO {
    @NotBlank(message = "Vui lòng nhập email")
    @Email(message = "Email không hợp lệ")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public EmailDTO(String email) {
        this.email = email;
    }

    public EmailDTO() {
    }
    
}
