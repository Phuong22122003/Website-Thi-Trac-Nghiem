package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.NhanVien;
import com.laptrinhweb.thitracnghiem.Repository.Interface.NhanVienRepository;

@Service
public class NhanVienService {
    @Autowired
    NhanVienRepository nhanVienRepository;

    public NhanVien findByManv(String manv) {
        return nhanVienRepository.findByManv(manv);
    }
}
