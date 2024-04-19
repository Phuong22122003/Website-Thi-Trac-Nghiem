package com.laptrinhweb.thitracnghiem.DTO;

import java.util.List;

public class DanhSachCauHoi {
    private List<CauHoiThiDTO> listCauHoi;
    private Integer idThi;
    
    public DanhSachCauHoi() {
    }

    public DanhSachCauHoi(List<CauHoiThiDTO> listCauHoi) {
        this.listCauHoi = listCauHoi;
    }

    public List<CauHoiThiDTO> getListCauHoi() {
        return listCauHoi;
    }

    public void setListCauHoi(List<CauHoiThiDTO> listCauHoi) {
        this.listCauHoi = listCauHoi;
    }

    public Integer getIdThi() {
        return idThi;
    }

    public void setIdThi(Integer idThi) {
        this.idThi = idThi;
    }
    
}
