package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    @Column(name = "IDCH")
    private int idch;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @Column(name = "THUTU")
    private int thuTu;

    public LuaChon() {
    }

    public LuaChon(int idlc, String noiDung, int thuTu, int idch, boolean trangThaiXoa) {
        this.idlc = idlc;
        this.noiDung = noiDung;
        this.thuTu = thuTu;
        this.idch = idch;
        this.trangThaiXoa = trangThaiXoa;
    }

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

    public int getThuTu() {
        return thuTu;
    }

    public void setThuTu(int thuTu) {
        this.thuTu = thuTu;
    }

    public int getIdch() {
        return idch;
    }

    public void setIdch(int idch) {
        this.idch = idch;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }
}