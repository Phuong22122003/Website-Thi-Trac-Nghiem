package com.laptrinhweb.thitracnghiem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.SinhVienRepository;


@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;

    public SinhVien getSinhVienInfo(String masv) {
        return sinhVienRepository.findSinhVienByMasv(masv);
    }

    public void updateInfo(String masv, String ten, String ho, String gioiTinh, String diaChi) {
        ten = ten.strip();
        ho = ho.strip();
        gioiTinh = gioiTinh.strip();
        diaChi = diaChi.strip();
        // sinhVienRepository.updateInfo(masv, ten, ho, gioiTinh, diaChi);
    }
}
