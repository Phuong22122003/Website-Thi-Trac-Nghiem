package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Repository.Interface.GiangVienRepository;

@Service
public class GiangVienService {
    @Autowired
    GiangVienRepository giangVienRepository;

    public List<GiangVien> findAllGiangVien() {
        return giangVienRepository.findAllGiangVien();
    }

    public int deleteByMaGv(String maGv) {
        GiangVien gv = giangVienRepository.findByMaGv(maGv);
        if (gv == null && gv.getCauHois().size() > 0) {
            return 1;
        }
        gv.setTrangThaiXoa(true);
        giangVienRepository.save(gv);
        return 0;
    }

    public int addGiangVien(GiangVien giangVien) {
        GiangVien existingGiangVien = giangVienRepository.findByMaGv(giangVien.getMaGv());
        GiangVien existingUserName = giangVienRepository.findByUserName(giangVien.getUserName());
        if (existingUserName != null) {
            return 2;
        } else if (existingGiangVien == null) {
            GiangVien newGiangVien = new GiangVien();
            newGiangVien.setGioiTinh(giangVien.isGioiTinh());
            newGiangVien.setHo(giangVien.getHo());
            newGiangVien.setHocHam(giangVien.getHocHam());
            newGiangVien.setHocVi(giangVien.getHocVi());
            newGiangVien.setMaGv(giangVien.getMaGv());
            newGiangVien.setPassWord(giangVien.getPassWord());
            newGiangVien.setTen(giangVien.getTen());
            newGiangVien.setUserName(giangVien.getUserName());
            newGiangVien.setTrangThaiXoa(false);
            newGiangVien.setEmail(giangVien.getEmail());
            giangVienRepository.save(newGiangVien);
            return 0;
        } else if (existingGiangVien.isTrangThaiXoa()) {
            existingGiangVien.setEmail(giangVien.getEmail());
            existingGiangVien.setGioiTinh(giangVien.isGioiTinh());
            existingGiangVien.setHo(giangVien.getHo());
            existingGiangVien.setHocHam(giangVien.getHocHam());
            existingGiangVien.setHocVi(giangVien.getHocVi());
            existingGiangVien.setPassWord(giangVien.getPassWord());
            existingGiangVien.setTen(giangVien.getTen());
            existingGiangVien.setUserName(giangVien.getUserName());
            existingGiangVien.setTrangThaiXoa(false);
            giangVienRepository.save(existingGiangVien);
            return 0;
        }
        return 1;
    }

    public int editGiangVien(String ho, String ten, String email, String hocVi, String hocHam, boolean gioiTinh,
            String maGv) {
        GiangVien existingGiangVien = giangVienRepository.findByMaGv(maGv);
        if (existingGiangVien != null) {
            existingGiangVien.setHo(ho);
            existingGiangVien.setTen(ten);
            existingGiangVien.setEmail(email);
            existingGiangVien.setHocVi(hocVi);
            existingGiangVien.setHocHam(hocHam);
            existingGiangVien.setGioiTinh(gioiTinh);
            giangVienRepository.save(existingGiangVien);
            return 0;
        }
        return 1;
    }

    public List<GiangVien> searchGiangVien(String keyword) {
        List<GiangVien> list = giangVienRepository.searchGiangVien(keyword);
        return list;
    }
}
