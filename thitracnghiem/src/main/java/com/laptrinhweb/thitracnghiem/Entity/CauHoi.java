package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "CAUHOI")
public class CauHoi {
    @Id
    @Column(name = "IDCH")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idch;

    @Column(name = "HINHTHUC")
    private String hinhThuc;

    @Column(name = "NOIDUNG")
    private String noiDung;

    @Column(name = "DAPANDUNG")
    private int dapAnDung;

    @Column(name = "IDDH")
    private int iddh;

    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private GiangVien giangVien;
    @ManyToOne
    @JoinColumn(name = "MAMH")
    private MonHoc monhoc;

    @Override
    public String toString() {
        return "CauHoi [idch=" + idch + ", hinhThuc=" + hinhThuc + ", noiDung=" + noiDung + ", dapAnDung=" + dapAnDung
                + ", iddh=" + iddh + ", trangThaiXoa=" + trangThaiXoa + "]";
    }

    public CauHoi() {
    }

    public CauHoi(int idch, String hinhThuc, String noiDung, int dapAnDung, int iddh, boolean trangThaiXoa) {
        this.idch = idch;
        this.hinhThuc = hinhThuc;
        this.noiDung = noiDung;
        this.dapAnDung = dapAnDung;
        this.iddh = iddh;
        this.trangThaiXoa = trangThaiXoa;
    }

    public int getIdch() {
        return idch;
    }

    public void setIdch(int idch) {
        this.idch = idch;
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

    public int getDapAnDung() {
        return dapAnDung;
    }

    public void setDapAnDung(int dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    public int getIddh() {
        return iddh;
    }

    public void setIddh(int iddh) {
        this.iddh = iddh;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }
}
