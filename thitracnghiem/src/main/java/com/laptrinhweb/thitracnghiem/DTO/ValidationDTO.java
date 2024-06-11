package com.laptrinhweb.thitracnghiem.DTO;

/*
 * Dùng kiểm tra mã khi người dùng nhập
 */

import jakarta.validation.constraints.NotBlank;


public class ValidationDTO {
    @NotBlank(message = "Vui lòng nhập mã xác nhận")
    private String code;

    public ValidationDTO() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public ValidationDTO(String code) {
        this.code = code;
    }
    
}
