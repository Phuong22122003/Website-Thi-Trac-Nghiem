package com.laptrinhweb.thitracnghiem.DTO;

import java.util.List;

public class EmailDTO {
    private List<String> listEmail;

    public List<String> getListEmail() {
        return listEmail;
    }

    public void setListEmail(List<String> listEmail) {
        this.listEmail = listEmail;
    }

    public EmailDTO() {
    }

    public EmailDTO(List<String> listEmail) {
        this.listEmail = listEmail;
    }
    
}
