package com.laptrinhweb.thitracnghiem.DTO;

/*
 * Danh sách email để giúp giảng viên gửi email khi tạo một sinh viên mới
 */

import java.util.List;

public class ListEmailDTO {
    private List<String> listEmail;

    public List<String> getListEmail() {
        return listEmail;
    }

    public void setListEmail(List<String> listEmail) {
        this.listEmail = listEmail;
    }

    public ListEmailDTO() {
    }

    public ListEmailDTO(List<String> listEmail) {
        this.listEmail = listEmail;
    }
    
}
