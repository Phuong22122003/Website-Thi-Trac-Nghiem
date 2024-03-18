package com.laptrinhweb.thitracnghiem.Service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Repository.GiangVienRepository;


@Service
public class UpdateGiangVien {
    @Autowired
    private GiangVienRepository repository;

    public void updateGiangVien(String maGv, Map<String, Object> updateInfo) {
        GiangVien gv = repository.findById(maGv).orElse(null);
        if (gv != null) {
            gv.setHo((String) updateInfo.get("ho"));
            gv.setTen((String) updateInfo.get("ten"));
            gv.setHocHam((String) updateInfo.get("hocHam"));
            gv.setHocVi((String) updateInfo.get("hocVi"));
            gv.setGioiTinh((Boolean) updateInfo.get("gioiTinh"));
            repository.save(gv);
        }
    }
}
