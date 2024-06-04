package com.laptrinhweb.thitracnghiem.DTO;

import java.util.List;

import com.laptrinhweb.thitracnghiem.Entity.FileCauHoi;
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;
public class CauHoiThiDTO {
    private Integer idch;
    private String noiDung;
    private Integer dapAnDung;
    private List<LuaChon> luaChons;
    private List<FileCauHoi> files;
    private Integer dapAnSV;
    private Integer thuTu;
    //=================Constructor========================//
    public CauHoiThiDTO(String noiDung, List<LuaChon> luaChons, Integer dapAnSV, Integer thuTu) {
        this.noiDung = noiDung;
        this.luaChons = luaChons;
        this.dapAnSV = dapAnSV;
        this.thuTu = thuTu;
    }
    public CauHoiThiDTO(Integer idch,String noiDung,Integer dapAnDung){
        this.idch = idch;
        this.noiDung = noiDung;
        this.dapAnDung =dapAnDung;
    }
    public CauHoiThiDTO(Integer idch,String noiDung,Integer dapAnDung,Integer dapAnSV,Integer thuTu){
        this.idch = idch;
        this.noiDung = noiDung;
        this.dapAnDung =dapAnDung;
        this.dapAnSV = dapAnSV;
        this.thuTu = thuTu;
    }
    public CauHoiThiDTO(){}
    // ====================getter ans setter===================//
    public String getNoiDung() {
        return noiDung;
    }
    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }
    public List<LuaChon> getLuaChons() {
        return luaChons;
    }
    public void setLuaChons(List<LuaChon> luaChons) {
        this.luaChons = luaChons;
    }
    public Integer getDapAnSV() {
        return dapAnSV;
    }
    public void setDapAnSV(Integer dapAnSV) {
        this.dapAnSV = dapAnSV;
    }
    public Integer getThuTu() {
        return thuTu;
    }
    public void setThuTu(Integer thuTu) {
        this.thuTu = thuTu;
    }
    public Integer getIdch() {
        return idch;
    }
    public void setIdch(Integer idch) {
        this.idch = idch;
    }
    public String getCauHoi() {
        return noiDung;
    }
    public void setCauHoi(String noiDung) {
        this.noiDung = noiDung;
    }
    public Integer getDapAnDung() {
        return dapAnDung;
    }
    public void setDapAnDung(Integer dapAnDung) {
        this.dapAnDung = dapAnDung;
    }
    @Override
    public String toString() {
        return "CauHoiThiDTO [idch=" + idch + ", noiDung=" + noiDung + ", dapAnDung=" + dapAnDung + ", luaChons="
                + luaChons + ", dapAnSV=" + dapAnSV + ", thuTu=" + thuTu + "]";
    }
    public List<FileCauHoi> getFiles() {
        return files;
    }
    public void setFiles(List<FileCauHoi> files) {
        this.files = files;
    }
    
}
