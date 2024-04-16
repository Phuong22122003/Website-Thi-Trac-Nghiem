package com.laptrinhweb.thitracnghiem.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "SINHVIEN")
public class SinhVien {
    @Id
    @Column(name = "MASV")
    private String masv;
    @Column(name = "HO")
    private String ho;
    @Column(name = "TEN")
    private String ten;
    @Column(name = "GIOITINH")
    private boolean gioiTinh;
    @Column(name = "DIACHI")
    private String diaChi;
    @Column(name = "NGAYSINH")
    private Date ngaySinh;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "PASSWORD")
    private String passWord;
    // @Column(name = "MALOP")
    // private String maLop;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @ManyToOne
    @JoinColumn(name = "MALOP")
    private Lop lop;
    @OneToMany(mappedBy = "sinhvien", fetch = FetchType.EAGER)
    private Collection<Thi> _this;

    public SinhVien() {
    }

    public SinhVien(String masv, String ho, String ten, boolean gioiTinh, String diaChi, Date ngaySinh, String userName,
            String passWord, String maLop) {
        this.masv = masv;
        this.ho = ho;
        this.ten = ten;
        this.gioiTinh = gioiTinh;
        this.diaChi = diaChi;
        this.ngaySinh = ngaySinh;
        this.userName = userName;
        this.passWord = passWord;
        // this.maLop = maLop;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
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

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
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

    public Collection<Thi> get_this() {
        return _this;
    }

    public void set_this(Collection<Thi> _this) {
        this._this = _this;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public Lop getLop() {
        return lop;
    }

    public void setLop(Lop lop) {
        this.lop = lop;
    }

    // public String getMaLop() {
    // return maLop;
    // }

    // public void setMaLop(String maLop) {
    // this.maLop = maLop;
    // }

}
