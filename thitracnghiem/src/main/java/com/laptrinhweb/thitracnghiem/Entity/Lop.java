package com.laptrinhweb.thitracnghiem.Entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

// import java.sql.Date;
import java.time.LocalDate;
import java.util.Collection;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "LOP")
public class Lop implements Serializable {
    @Id
    @Column(name = "MALOP")
    @NotBlank(message = "Mã lớp không được trống")
    @Length(max = 15, message = "Mã lớp tối đa 15 ký tự")
    private String maLop;
    @Column(name = "TENLOP")
    @NotBlank(message = "Tên lớp không được trống")
    @Length(max = 50, message = "Tên lớp tối đa 50 ký tự")
    private String tenLop;
    @Column(name = "NAMNHAPHOC")
    @NotNull(message = "Năm nhập học không được trống")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "Năm nhập học không hợp lệ!")
    private LocalDate nam_nhap_hoc;
    @Column(name = "TRANGTHAIXOA")
    private boolean trang_thai_xoa;
    @OneToMany(mappedBy = "lop", fetch = FetchType.LAZY)
    private Collection<SinhVien> sinhviens;
    @OneToMany(mappedBy = "lop", fetch = FetchType.LAZY)
    private Collection<DangKyThi> dkThis;

    public Lop() {

    }

    public Lop(String maLop, String tenLop, LocalDate nam_nhap_hoc, boolean trang_thai_xoa) {
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

    public LocalDate getNamNhapHoc() {
        return nam_nhap_hoc;
    }

    public void setNamNhapHoc(LocalDate nam_nhap_hoc) {
        this.nam_nhap_hoc = nam_nhap_hoc;
    }

    public Collection<SinhVien> getSinhviens() {
        return sinhviens;
    }

    public LocalDate getNam_nhap_hoc() {
        return nam_nhap_hoc;
    }

    public void setNam_nhap_hoc(LocalDate nam_nhap_hoc) {
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
