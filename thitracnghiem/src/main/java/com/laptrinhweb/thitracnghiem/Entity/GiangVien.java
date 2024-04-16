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
    @OneToMany(mappedBy = "giangvien", fetch = FetchType.LAZY)
    private Collection<DangKyThi> dkThis;
    @OneToMany(mappedBy = "giangvien", fetch = FetchType.EAGER)
    private Collection<CauHoi> cauHois;

    public String getMaGv() {
        return maGv;
    }

    public String getHo() {
        return ho;
    }

    public String getTen() {
        return ten;
    }

    public boolean getGioiTinh() {
        return gioiTinh;
    }

    public String getHocVi() {
        return hocVi;
    }

    public String getHocHam() {
        return hocHam;
    }

    public String getUserName() {
        return userName;
    }

    public void setMaGv(String maGv) {
        this.maGv = maGv;
    }

    // Hàm setter cho thuộc tính ho
    public void setHo(String ho) {
        this.ho = ho;
    }

    // Hàm setter cho thuộc tính ten
    public void setTen(String ten) {
        this.ten = ten;
    }

    // Hàm setter cho thuộc tính gioiTinh
    public void setGioiTinh(boolean gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    // Hàm setter cho thuộc tính hocVi
    public void setHocVi(String hocVi) {
        this.hocVi = hocVi;
    }

    // Hàm setter cho thuộc tính hocHam
    public void setHocHam(String hocHam) {
        this.hocHam = hocHam;
    }

    // Hàm setter cho thuộc tính userName
    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Hàm setter cho thuộc tính passWord
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    // Hàm setter cho thuộc tính trangThaiXoa
    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public String getPassWord() {
        return passWord;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
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

}
