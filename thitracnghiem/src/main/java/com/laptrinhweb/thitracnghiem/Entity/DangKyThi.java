package com.laptrinhweb.thitracnghiem.Entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;

@Entity
@Table(name = "DANGKYTHI")
public class DangKyThi {
    @Id
    @Column(name = "IDDk")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddk;
    @Column(name = "MAMH")
    private String mamh;
    @Column(name = "LAN")
    private int lan;
    @Column(name = "SOCAU")
    private int soCau;
    @Column(name = "NGAYTHI")
    private Date ngayThi;
    @Column(name = "THOILUONG")
    private int thoiLuong;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @OneToMany(mappedBy = "dkThi", fetch = FetchType.EAGER)
    private Collection<Thi> _this;
    @ManyToOne
    @JoinColumn(name = "MALOP")
    private Lop lop;
    @ManyToOne
    @JoinColumn(name = "MAGV")
    private GiangVien giangVien;
    @ManyToOne
    @JoinColumn(name = "MANV")
    private NhanVien nhanvien;

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

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public Collection<Thi> get_this() {
        return _this;
    }

    public void set_this(Collection<Thi> _this) {
        this._this = _this;
    }

}