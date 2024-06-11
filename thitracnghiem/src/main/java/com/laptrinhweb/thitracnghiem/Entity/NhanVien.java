package com.laptrinhweb.thitracnghiem.Entity;

import java.util.Collection;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "NHANVIENGV")
public class NhanVien {
    @Id
    @Column(name = "MANV")
    @Length(max = 15, message = "Mã NV dài tối đa 15 ký tự")
    private String manv;
    @Length(max = 20, message = "Họ dài tối đa 20 ký tự")
    @Column(name = "HO")
    private String ho;
    @Length(max = 7, message = "Tên dài tối đa 7 ký tự")
    @Column(name = "TEN")
    private String ten;
    @Column(name = "GIOITINH")
    private boolean gioiTinh;
    @Length(max = 50, message = "Username dài tối đa 50 ký tự")
    @Column(name = "USERNAME")
    private String userName;
    @Length(max = 30, message = "Password dài tối đa 30 ký tự")
    @Column(name = "PASSWORD")
    private String passWord;
    @Length(max = 50, message = "Email dài tối đa 50 ký tự")
    @Column(name = "EMAIL")
    private String email;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @OneToMany(mappedBy = "nhanVien", fetch = FetchType.EAGER)
    private Collection<DangKyThi> dkThis;

    public NhanVien(String manv, String ho, String ten, boolean gioiTinh, String userName, String passWord,
            boolean trangThaiXoa) {
        this.manv = manv;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.userName = userName;
        this.passWord = passWord;
        this.trangThaiXoa = trangThaiXoa;
    }

    public NhanVien() {

    }

    public String getManv() {
        return manv;
    }

    public void setManv(String manv) {
        this.manv = manv;
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
}
