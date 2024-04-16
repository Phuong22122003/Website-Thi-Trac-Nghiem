package com.laptrinhweb.thitracnghiem.Entity;

import java.util.Collection;

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

@Entity
@Table(name = "THI")
public class Thi {
    @Id
    @Column(name = "IDTHi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idThi;
    @Column(name = "DATHI")
    private boolean dathi;
    @Column(name = "DIEM")
    private float diem;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;
    @ManyToOne
    @JoinColumn(name = "MASV")
    private SinhVien sinhvien;
    @ManyToOne
    @JoinColumn(name = "IDDK")
    private DangKyThi dkThi;
    @OneToMany(mappedBy = "thi", fetch = FetchType.EAGER)
    private Collection<CTBaiThi> dkThis;

    public int getIdThi() {
        return idThi;
    }

    public void setIdThi(int idThi) {
        this.idThi = idThi;
    }

    public float getDiem() {
        return diem;
    }

    public void setDiem(float diem) {
        this.diem = diem;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public boolean isDathi() {
        return dathi;
    }

    public void setDathi(boolean dathi) {
        this.dathi = dathi;
    }

    public SinhVien getSinhvien() {
        return sinhvien;
    }

    public void setSinhvien(SinhVien sinhvien) {
        this.sinhvien = sinhvien;
    }

    public DangKyThi getDkThi() {
        return dkThi;
    }

    public void setDkThi(DangKyThi dkThi) {
        this.dkThi = dkThi;
    }

    public Collection<CTBaiThi> getDkThis() {
        return dkThis;
    }

    public void setDkThis(Collection<CTBaiThi> dkThis) {
        this.dkThis = dkThis;
    }

}
