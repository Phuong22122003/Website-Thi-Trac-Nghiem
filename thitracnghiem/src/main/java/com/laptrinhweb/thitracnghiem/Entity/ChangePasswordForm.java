package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.validation.constraints.NotBlank;

public class ChangePasswordForm {
    @NotBlank(message = "Vui lòng nhập mật khẩu cũ")
    private String oldPassword;
    @NotBlank(message = "Vui lòng nhập mật khẩu mới")
    private String newPassword;
    @NotBlank(message = "Vui lòng nhập xác nhận mật khẩu")
    private String confirmNewPassword;
    // private String errorOldPassword;
    // private String errorNewPassword;
    // private String errorConfirmNewPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    // public String getErrorOldPassword() {
    // return errorOldPassword;
    // }

    // public void setErrorOldPassword(String errorOldPassword) {
    // this.errorOldPassword = errorOldPassword;
    // }

    // public String getErrorNewPassword() {
    // return errorNewPassword;
    // }

    // public void setErrorNewPassword(String errorNewPassword) {
    // this.errorNewPassword = errorNewPassword;
    // }

    // public String getErrorConfirmNewPassword() {
    // return errorConfirmNewPassword;
    // }

    // public void setErrorConfirmNewPassword(String errorConfirmNewPassword) {
    // this.errorConfirmNewPassword = errorConfirmNewPassword;
    // }

}