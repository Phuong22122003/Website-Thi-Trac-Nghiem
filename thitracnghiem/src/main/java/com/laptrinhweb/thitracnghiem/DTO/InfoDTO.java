package com.laptrinhweb.thitracnghiem.DTO;

import java.sql.Date;

public class InfoDTO {
    private Integer IDTHI;
    private String maMH;
    private Integer lanThi;
    private Integer soCau;
    private Integer thoiLuong;
    public InfoDTO(Integer lanThi, Integer soCau, Integer thoiLuong, String tenMH, Date ngayThi, Float diem) {
        this.lanThi = lanThi;
        this.soCau = soCau;
        this.thoiLuong = thoiLuong;
        this.tenMH = tenMH;
        this.ngayThi = ngayThi;
        this.diem = diem;
    }
    private Boolean trangThai;
    public InfoDTO(String tenMH, Integer lanThi,  Float diem,Integer iDTHI) {
        IDTHI = iDTHI;
        this.lanThi = lanThi;
        this.tenMH = tenMH;
        this.diem = diem;
    }
    private String tenMH;
    private Date ngayThi;
    private Float diem;
    public InfoDTO() {
    }
    public Integer getIDTHI() {
        return IDTHI;
    }
    public void setIDTHI(Integer iDTHI) {
        IDTHI = iDTHI;
    }
    public String getMaMH() {
        return maMH;
    }
    public void setMaMH(String maMH) {
        this.maMH = maMH;
    }
    public Integer getLanThi() {
        return lanThi;
    }
    public InfoDTO(Integer IDTHI, String maMH, Integer lanThi, Integer soCau, Integer thoiLuong,
            String tenMH, Date ngayThi, String trangThai) {
        this.IDTHI = IDTHI;
        this.maMH = maMH;
        this.lanThi = lanThi;
        this.soCau = soCau;
        this.thoiLuong = thoiLuong;
        this.trangThai = Boolean.valueOf(trangThai);
        this.tenMH = tenMH;
        this.ngayThi = ngayThi;
    }
    public InfoDTO(Integer iDTHI, Integer lanThi, Integer soCau, Integer thoiLuong, String tenMH, Date ngayThi) {
        IDTHI = iDTHI;
        this.lanThi = lanThi;
        this.soCau = soCau;
        this.thoiLuong = thoiLuong;
        this.tenMH = tenMH;
        this.ngayThi = ngayThi;
    }
    public void setLanThi(Integer lanThi) {
        this.lanThi = lanThi;
    }
    public Integer getSoCau() {
        return soCau;
    }
    public void setSoCau(Integer soCau) {
        this.soCau = soCau;
    }
    public Integer getThoiLuong() {
        return thoiLuong;
    }
    public void setThoiLuong(Integer thoiLuong) {
        this.thoiLuong = thoiLuong;
    }
    public Boolean getTrangThai() {
        return trangThai;
    }
    public void setTrangThai(Boolean trangThai) {
        this.trangThai = trangThai;
    }
    public String getTenMH() {
        return tenMH;
    }
    public void setTenMH(String tenMH) {
        this.tenMH = tenMH;
    }
    public Date getNgayThi() {
        return ngayThi;
    }
    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }
    public Float getDiem() {
        return diem;
    }
    public void setDiem(Float diem) {
        this.diem = diem;
    }
    
}
