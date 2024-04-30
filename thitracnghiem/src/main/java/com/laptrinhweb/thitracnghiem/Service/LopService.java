package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Repository.Interface.DangKyThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.LopRepository;

@Service
public class LopService {
    @Autowired
    LopRepository lopRepository;

    public List<Lop> findAllLop() {
        return lopRepository.findAllLop();
    }

    public Lop findByMaLop(String maLop) {
        return lopRepository.findByMaLop(maLop);
    }
}
