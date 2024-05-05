package com.laptrinhweb.thitracnghiem.Entity;

import java.io.Serializable;

import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "GIANGVIEN")
public class GiangVien implements Serializable {
    @Id
    @Column(name = "MAGV")
    private String maGv;
    @Column(name = "HO")
    private String ho;
    @Column(name = "TEN")
    private String ten;
    @Column(name = "GIOITINH") // 0: nam, 1: nu
    private boolean gioiTinh;
    @Column(name = "HOCVI")
    private String hocVi;
    @Column(name = "HOCHAM")
    private String hocHam;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String passWord;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @Column(name = "EMAIL")
    private String email;
    @OneToMany(mappedBy = "giangVien", fetch = FetchType.LAZY)
    private Collection<DangKyThi> dkThis;
    @OneToMany(mappedBy = "giangVien", fetch = FetchType.EAGER)
    private Collection<CauHoi> cauHois;

    // ===============Constructor========================//
    public GiangVien() {
    }

    public GiangVien(String maGv, String ho, String ten, boolean gioiTinh, String hocVi, String hocHam, String userName,
            String passWord, String email, boolean trangThaiXoa, Collection<DangKyThi> dkThis,
            Collection<CauHoi> cauHois) {
        this.maGv = maGv;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.hocVi = hocVi;
        this.hocHam = hocHam;
        this.userName = userName;
        this.passWord = passWord;
        this.trangThaiXoa = trangThaiXoa;
        this.dkThis = dkThis;
        this.cauHois = cauHois;
        this.email = email;
    }

    // ===============getter and setter=========================//
    public String getMaGv() {
        return maGv;
    }

    public void setMaGv(String maGv) {
        this.maGv = maGv;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public boolean isGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public String getHocVi() {
        return hocVi;
    }

    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }

    public String getHocHam() {
        return hocHam;
    }

    public void setHocHam(String hocHam) {
        this.hocHam = hocHam;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public Collection<DangKyThi> getDkThis() {
        return dkThis;
    }

    public void setDkThis(Collection<DangKyThi> dkThis) {
        this.dkThis = dkThis;
    }

    public Collection<CauHoi> getCauHois() {
        return cauHois;
    }

    public void setCauHois(Collection<CauHoi> cauHois) {
        this.cauHois = cauHois;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // ===================GETTER AND SETTER========================//

}
