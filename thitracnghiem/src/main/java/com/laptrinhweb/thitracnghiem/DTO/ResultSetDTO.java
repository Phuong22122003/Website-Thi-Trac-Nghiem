package com.laptrinhweb.thitracnghiem.DTO;

public class ResultSetDTO {
    private Integer idch;
    private String hinhThuc;
    private String noiDung;
    private Integer iddh;
    private Integer dapAnDung;
    private Integer dapAnSv;
    private Integer thuTuChon;
    
    public ResultSetDTO(Integer idch, String hinhThuc, String noiDung, Integer iddh, Integer dapAnDung,
            Integer dapAnSv, Integer thuTuChon) {
        this.idch = idch;
        this.hinhThuc = hinhThuc;
        this.noiDung = noiDung;
        this.iddh = iddh;
        this.dapAnDung = dapAnDung;
        this.dapAnSv = dapAnSv;
        this.thuTuChon = thuTuChon;
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
    public Integer getIddh() {
        return iddh;
    }
    public void setIddh(Integer iddh) {
        this.iddh = iddh;
    }
    public Integer getDapAnDung() {
        return dapAnDung;
    }
    public void setDapAnDung(Integer dapAnDung) {
        this.dapAnDung = dapAnDung;
    }
    public Integer getDapAnSv() {
        return dapAnSv;
    }
    public void setDapAnSv(Integer dapAnSv) {
        this.dapAnSv = dapAnSv;
    }
    public Integer getThuTuChon() {
        return thuTuChon;
    }
    public void setThuTuChon(Integer thuTuChon) {
        this.thuTuChon = thuTuChon;
    }
    
}
