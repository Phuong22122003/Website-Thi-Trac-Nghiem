package com.laptrinhweb.thitracnghiem.DTO;

import java.sql.Date;

public class InfoDTO {
    private Integer IDTHI;
    private String maMH;
    private Integer lanThi;
    private Integer soCau;
    private Integer thoiLuong;
    private Long remainingTime;
    private String tenMH;
    private Date ngayThi;
    private Float diem;
    private Boolean trangThai;

    public InfoDTO(Integer lanThi, Integer soCau, Integer thoiLuong, String tenMH, Date ngayThi, Float diem) {
        this.lanThi = lanThi;
        this.soCau = soCau;
        this.thoiLuong = thoiLuong;
        this.tenMH = tenMH;
        this.ngayThi = ngayThi;
        this.diem = diem;
    }
    /*
     * Sp: showExamResults
     * Chức năng: Xem danh sách điểm môn học đã thi
     */
    public InfoDTO(String tenMH, Integer lanThi, Float diem, Integer iDTHI) {
        IDTHI = iDTHI;
        this.lanThi = lanThi;
        this.tenMH = tenMH;
        this.diem = diem;
    }

    /* 
    SP: showExamSchedule;
    Chức năng: dùng để lấy lịch thi
    */
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

    /*
    SP: getResultByID;
    Chức năng: sẽ lấy thông tin của bài thi khi sinh viên có nhu cầu xem chi tiết bài thi
    */
    public InfoDTO(String tenMH,Date ngayThi,Integer lanThi,Integer soCau,Integer thoiLuong,Float diem){
        this.tenMH = tenMH;
        this.ngayThi = ngayThi;
        this.lanThi = lanThi;
        this.soCau = soCau;
        this.thoiLuong = thoiLuong;
        this.diem = diem;
    }
    public InfoDTO() {
    }

    public Long getRemainingTime() {
        return remainingTime;
    }

    public void setRemainingTime(Long remainingTime) {
        this.remainingTime = remainingTime;
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
