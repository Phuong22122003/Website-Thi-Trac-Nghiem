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
    private NhanVien nhanVien;
    
    @ManyToOne
    @JoinColumn(name = "MAMH")
    private MonHoc monHoc;
    // ==================Constructor=====================
    public DangKyThi(int iddk, int lan, int soCau, Date ngayThi, int thoiLuong, boolean trangThaiXoa,
            Collection<Thi> _this, Lop lop, GiangVien giangvien, NhanVien nhanvien, MonHoc monHoc) {
        this.iddk = iddk;
        this.lan = lan;
        this.soCau = soCau;
        this.ngayThi = ngayThi;
        this.thoiLuong = thoiLuong;
        this.trangThaiXoa = trangThaiXoa;
        this._this = _this;
        this.lop = lop;
        this.giangVien = giangvien;
        this.nhanVien = nhanvien;
        this.monHoc = monHoc;
    }
    // ====================GETTER AND SETTER===========================//
    public int getIddk() {
        return iddk;
    }
    public void setIddk(int iddk) {
        this.iddk = iddk;
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
    public Lop getLop() {
        return lop;
    }
    public void setLop(Lop lop) {
        this.lop = lop;
    }
    public GiangVien getGiangvien() {
        return giangVien;
    }
    public void setGiangvien(GiangVien giangvien) {
        this.giangVien = giangvien;
    }
    public NhanVien getNhanvien() {
        return nhanVien;
    }
    public void setNhanvien(NhanVien nhanvien) {
        this.nhanVien = nhanvien;
    }
    public MonHoc getMonHoc() {
        return monHoc;
    }
    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }
    
}