package com.laptrinhweb.thitracnghiem.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DANGKYTHI")
public class DangKyThi {
    @Id
    @Column(name = "IDDk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddk;
    @Column(name = "MAMH")
    private String mamh;
    @Column(name = "MALOP")
    private String maLop;
    @Column(name = "LAN")
    private int lan;
    @Column(name = "SOCAU")
    private int soCau;
    @Column(name = "NGAYTHI")
    private Date ngayThi;
    @Column(name = "THOILUONG")
    private int thoiLuong;
    @Column(name = "MANV")
    private String manv;
    @Column(name = "MAGV")
    private String magv;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;

    public int getIddk() {
        return iddk;
    }

    public void setIddk(int iddk) {
        this.iddk = iddk;
    }

    public String getMamh() {
        return mamh;
    }

    public void setMamh(String mamh) {
        this.mamh = mamh;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public int getLan() {
        return lan;
    }

    public void setLan(int lan) {
        this.lan = lan;
    }

    public int getSoCau() {
        return soCau;
    }

    public void setSoCau(int soCau) {
        this.soCau = soCau;
    }

    public Date getNgayThi() {
        return ngayThi;
    }

    public void setNgayThi(Date ngayThi) {
        this.ngayThi = ngayThi;
    }

    public int getThoiLuong() {
        return thoiLuong;
    }

    public void setThoiLuong(int thoiLuong) {
        this.thoiLuong = thoiLuong;
    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
    }

    public String getMagv() {
        return magv;
    }

    public void setMagv(String magv) {
        this.magv = magv;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

}