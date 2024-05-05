package com.laptrinhweb.thitracnghiem.DTO;

import java.sql.Date;

import jakarta.persistence.Column;

public interface DangKyThiDTO {
    public int getIddk();

    public void setIddk();

    public String getTenMh();

    public void setTenMh(String tenMh);

    public String getTenLop();

    public void setTenLop(String tenLop);

    public int getLan();

    public void setLan(int lan);

    public int getSoCau();

    public void setSoCau(int soCau);

    public Date getNgayThi();

    public void setNgayThi(Date ngayThi);

    public int getThoiLuong();

    public void setThoiLuong(int thoiLuong);

}
