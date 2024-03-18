package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "THI")
public class Thi {
    @Id
    @Column(name = "IDTHi")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idThi;
    @Column(name = "MASV")
    private String masv;
    @Column(name = "IDDK")
    private int iddk;
    @Column(name = "DATHI")
    private boolean dathi;
    @Column(name = "DIEM")
    private float diem;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;

    public int getIdThi() {
        return idThi;
    }

    public void setIdThi(int idThi) {
        this.idThi = idThi;
    }

    public String getMasv() {
        return masv;
    }

    public void setMasv(String masv) {
        this.masv = masv;
    }

    public int getIddk() {
        return iddk;
    }

    public void setIddk(int iddk) {
        this.iddk = iddk;
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
}
