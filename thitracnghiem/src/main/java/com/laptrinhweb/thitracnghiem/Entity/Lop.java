package com.laptrinhweb.thitracnghiem.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Date;

import java.util.Collection;

@Entity
@Table(name = "LOP")
public class Lop implements Serializable {
    @Id
    @Column(name = "MALOP")
    private String maLop;
    @Column(name = "TENLOP")
    private String tenLop;
    @Column(name = "NAMNHAPHOC")
    private Date nam_nhap_hoc;
    @Column(name = "TRANGTHAIXOA")
    private boolean trang_thai_xoa;
    @OneToMany(mappedBy = "lop", fetch = FetchType.LAZY)
    private Collection<SinhVien> sinhviens;
    @OneToMany(mappedBy = "lop", fetch = FetchType.LAZY)
    private Collection<DangKyThi> dkThis;

    public Lop() {

    }

    public Lop(String maLop, String tenLop, Date nam_nhap_hoc, boolean trang_thai_xoa) {
        this.maLop = maLop;
        this.tenLop = tenLop;
        this.nam_nhap_hoc = nam_nhap_hoc;
        this.trang_thai_xoa = trang_thai_xoa;
    }

    public String getMaLop() {
        return maLop;
    }

    public void setMaLop(String maLop) {
        this.maLop = maLop;
    }

    public String getTenLop() {
        return tenLop;
    }

    public void setTenLop(String tenLop) {
        this.tenLop = tenLop;
    }

    public Date getNamNhapHoc() {
        return (Date) nam_nhap_hoc.clone();
    }

    public void setNamNhapHoc(Date nam_nhap_hoc) {
        this.nam_nhap_hoc = nam_nhap_hoc;
    }

    public Collection<SinhVien> getSinhviens() {
        return sinhviens;
    }

    public Date getNam_nhap_hoc() {
        return nam_nhap_hoc;
    }

    public void setNam_nhap_hoc(Date nam_nhap_hoc) {
        this.nam_nhap_hoc = nam_nhap_hoc;
    }

    public boolean isTrang_thai_xoa() {
        return trang_thai_xoa;
    }

    public void setTrang_thai_xoa(boolean trang_thai_xoa) {
        this.trang_thai_xoa = trang_thai_xoa;
    }

    public void setSinhviens(Collection<SinhVien> sinhviens) {
        this.sinhviens = sinhviens;
    }

    public Collection<DangKyThi> getDkThis() {
        return dkThis;
    }

    public void setDkThis(Collection<DangKyThi> dkThis) {
        this.dkThis = dkThis;
    }

}
