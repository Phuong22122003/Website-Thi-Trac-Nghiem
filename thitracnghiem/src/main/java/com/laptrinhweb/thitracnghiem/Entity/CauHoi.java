package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAUHOI")
public class CauHoi {
    @Id
    @Column(name = "IDCH")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idch;

    @Column(name = "HINHTHUC")
    private String hinhThuc;

    @Column(name = "NOIDUNG")
    private String noiDung;

    @Column(name = "DAPANDUNG")
    private Integer dapAnDung;

    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private GiangVien giangVien;

    @ManyToOne
    @JoinColumn(name = "MAMH")
    private MonHoc monHoc;

    // ==============Constructor=============================//
    public CauHoi(Integer idch, String hinhThuc, String noiDung, Integer dapAnDung, boolean trangThaiXoa,
            GiangVien giangvien, MonHoc monhoc) {
        this.idch = idch;
        this.hinhThuc = hinhThuc;
        this.noiDung = noiDung;
        this.dapAnDung = dapAnDung;
        this.trangThaiXoa = trangThaiXoa;
        this.giangVien = giangvien;
        this.monHoc = monhoc;
    }

    public Integer getIdch() {
        return idch;
    }

    public void setIdch(Integer idch) {
        this.idch = idch;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Integer getDapAnDung() {
        return dapAnDung;
    }

    public void setDapAnDung(Integer dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public GiangVien getGiangvien() {
        return giangVien;
    }

    public void setGiangvien(GiangVien giangvien) {
        this.giangVien = giangvien;
    }

    public MonHoc getMonhoc() {
        return monHoc;
    }

    public void setMonhoc(MonHoc monhoc) {
        this.monHoc = monhoc;
    }

    
    // ======Getter and setter===============================//
    
}
