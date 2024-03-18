package com.laptrinhweb.thitracnghiem.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "DAYHOC")
public class DayHoc {
    @Id
    @Column(name = "IDDH")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int iddh;
    @Column(name = "MAGV")
    private String magv;
    @Column(name = "MAMH")
    private String mamh;
    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;

    public DayHoc(int iddh, String magv, String mamh, boolean trangThaiXoa) {
        this.iddh = iddh;
        this.magv = magv;
        this.mamh = mamh;
        this.trangThaiXoa = trangThaiXoa;
    }

    public DayHoc() {

    }

    public int getIddh() {
        return iddh;
    }

    public void setIddh(int iddh) {
        this.iddh = iddh;
    }

    public String getMagv() {
        return magv;
    }

    public void setMagv(String magv) {
        this.magv = magv;
    }

    public String getMamh() {
        return mamh;
    }

    public void setMamh(String mamh) {
        this.mamh = mamh;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

}
