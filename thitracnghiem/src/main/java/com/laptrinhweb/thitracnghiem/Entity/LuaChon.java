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
@Table(name = "LUACHON")
public class LuaChon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDLC")
    private int idlc;
    @Column(name = "NOIDUNG")
    private String noiDung;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @Column(name = "THUTU")
    private int thuTu;
    @ManyToOne
    @JoinColumn(name = "IDCH")
    private CauHoi cauHoi;


    // ============Constructor===========================
    public LuaChon() {
    }

    public LuaChon(int idlc, String noiDung, boolean trangThaiXoa, int thuTu, CauHoi cauHoi) {
        this.idlc = idlc;
        this.noiDung = noiDung;
        this.trangThaiXoa = trangThaiXoa;
        this.thuTu = thuTu;
        this.cauHoi = cauHoi;
    }
    // ========================getter and setter==============================

    public int getIdlc() {
        return idlc;
    }

    public void setIdlc(int idlc) {
        this.idlc = idlc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public int getThuTu() {
        return thuTu;
    }

    public void setThuTu(int thuTu) {
        this.thuTu = thuTu;
    }

    public CauHoi getCauHoi() {
        return cauHoi;
    }

    public void setCauHoi(CauHoi cauHoi) {
        this.cauHoi = cauHoi;
    }
    
   
}