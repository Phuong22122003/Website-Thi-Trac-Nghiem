package com.laptrinhweb.thitracnghiem.Entity;

import java.util.Collection;

import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.CascadeType;
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
@Table(name = "CAUHOI")
public class CauHoi {
    @Id
    @Column(name = "IDCH")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idch;

    @Column(name = "HINHTHUC")
    private String hinhThuc;

    @Column(name = "NOIDUNG")
    private String noiDung;

    @Column(name = "DAPANDUNG")
    private Integer dapAnDung;

    @Column(name = "TRANGTHAIXOA")
    private boolean trangThaiXoa;

    @OneToMany(mappedBy = "cauHoi", fetch = FetchType.LAZY)
    @SQLRestriction("trangThaiXoa = 0")
    private Collection<LuaChon> luaChons;

    @OneToMany(mappedBy = "cauHoi", fetch = FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    // @SQLRestriction("trangThaiXoa = 0")
    private Collection<FileCauHoi> files;

    @OneToMany(mappedBy = "cauHoi", fetch = FetchType.LAZY)
    @SQLRestriction("trangThaiXoa = 0")
    private Collection<CTBaiThi> ctBaiThis;

    @ManyToOne
    @JoinColumn(name = "MAGV")
    private GiangVien giangVien;

    @ManyToOne
    // @SQLRestriction("trangThaiXoa = 0")
    @JoinColumn(name = "MAMH")
    private MonHoc monHoc;

    // ==============Constructor=============================//
    public CauHoi(Integer idch, String hinhThuc, String noiDung, Integer dapAnDung, boolean trangThaiXoa,
            GiangVien giangvien, MonHoc monhoc) {
        this.idch = idch;
        this.hinhThuc = hinhThuc;
        this.noiDung = noiDung;
        this.dapAnDung = dapAnDung;
        this.trangThaiXoa = trangThaiXoa;
        this.giangVien = giangvien;
        this.monHoc = monhoc;
    }

    public CauHoi() {
    }

    public Integer getIdch() {
        return idch;
    }

    public void setIdch(Integer idch) {
        this.idch = idch;
    }

    public String getHinhThuc() {
        return hinhThuc;
    }

    public void setHinhThuc(String hinhThuc) {
        this.hinhThuc = hinhThuc;
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public Integer getDapAnDung() {
        return dapAnDung;
    }

    public void setDapAnDung(Integer dapAnDung) {
        this.dapAnDung = dapAnDung;
    }

    public boolean isTrangThaiXoa() {
        return trangThaiXoa;
    }

    public void setTrangThaiXoa(boolean trangThaiXoa) {
        this.trangThaiXoa = trangThaiXoa;
    }

    public Collection<LuaChon> getLuaChons() {
        return luaChons;
    }

    public void setLuaChons(Collection<LuaChon> luaChons) {
        this.luaChons = luaChons;
    }

    public GiangVien getGiangVien() {
        return giangVien;
    }

    public void setGiangVien(GiangVien giangVien) {
        this.giangVien = giangVien;
    }

    public MonHoc getMonHoc() {
        return monHoc;
    }

    public void setMonHoc(MonHoc monHoc) {
        this.monHoc = monHoc;
    }

    public Collection<CTBaiThi> getCtBaiThis() {
        return ctBaiThis;
    }

    public void setCtBaiThis(Collection<CTBaiThi> ctBaiThis) {
        this.ctBaiThis = ctBaiThis;
    }

    public Collection<FileCauHoi> getFiles() {
        return files;
    }

    public void setFiles(Collection<FileCauHoi> files) {
        this.files = files;
    }
    
    // =================getter and setter===================//

}
