package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.DangKyThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.ThiDTO;
import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Repository.Interface.DangKyThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.GiangVienRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.ThiRepository;

@Service
public class GiangVienService {
    @Autowired
    GiangVienRepository giangVienRepository;
    @Autowired
    EmailService emailService;
    @Autowired
    DangKyThiRepository dangKyThiRepository;
    @Autowired
    ThiRepository thiRepository;

    public List<GiangVien> findAllGiangVien() {
        return giangVienRepository.findAllGiangVien();
    }

    public int deleteByMaGv(String maGv) {
        GiangVien gv = giangVienRepository.findByMaGvAndTrangThaiXoa(maGv, false);
        if (gv == null || gv.getCauHois().size() > 0 || gv.getDkThis().size() > 0) {
            return 1;
        }
        gv.setTrangThaiXoa(true);
        giangVienRepository.save(gv);
        return 0;
    }

    public int addGiangVien(GiangVien giangVien) {
        GiangVien existingGiangVien = giangVienRepository.findByMaGv(giangVien.getMaGv());
        GiangVien existingUserName = giangVienRepository.findByUserName(giangVien.getUserName());
        if (existingGiangVien != null && existingGiangVien.isTrangThaiXoa() == false) {
            return 1;
        }
        if (existingUserName != null && existingUserName.isTrangThaiXoa() == false) {
            return 2;
        }
        if (existingUserName != null && existingUserName.isTrangThaiXoa() == true) {
            giangVienRepository.delete(existingUserName);
        }
        if (existingGiangVien != null && existingGiangVien.isTrangThaiXoa() == true) {
            giangVienRepository.delete(existingGiangVien);
        }
        GiangVien newGiangVien = new GiangVien();
        newGiangVien.setGioiTinh(giangVien.isGioiTinh());
        newGiangVien.setHo(giangVien.getHo());
        newGiangVien.setHocHam(giangVien.getHocHam());
        newGiangVien.setHocVi(giangVien.getHocVi());
        newGiangVien.setMaGv(giangVien.getMaGv().toUpperCase());
        newGiangVien.setPassWord(giangVien.getPassWord());
        newGiangVien.setTen(giangVien.getTen());
        newGiangVien.setUserName(giangVien.getUserName());
        newGiangVien.setTrangThaiXoa(false);
        newGiangVien.setEmail(giangVien.getEmail());
        giangVienRepository.save(newGiangVien);
        return 0;
        // else if (existingGiangVien == null && existingUserName) {
        // GiangVien newGiangVien = new GiangVien();
        // newGiangVien.setGioiTinh(giangVien.isGioiTinh());
        // newGiangVien.setHo(giangVien.getHo());
        // newGiangVien.setHocHam(giangVien.getHocHam());
        // newGiangVien.setHocVi(giangVien.getHocVi());
        // newGiangVien.setMaGv(giangVien.getMaGv().toUpperCase());
        // newGiangVien.setPassWord(giangVien.getPassWord());
        // newGiangVien.setTen(giangVien.getTen());
        // newGiangVien.setUserName(giangVien.getUserName());
        // newGiangVien.setTrangThaiXoa(false);
        // newGiangVien.setEmail(giangVien.getEmail());
        // giangVienRepository.save(newGiangVien);
        // return 0;
        // } else if (existingGiangVien.isTrangThaiXoa()) {
        // existingGiangVien.setEmail(giangVien.getEmail());
        // existingGiangVien.setGioiTinh(giangVien.isGioiTinh());
        // existingGiangVien.setHo(giangVien.getHo());
        // existingGiangVien.setHocHam(giangVien.getHocHam());
        // existingGiangVien.setHocVi(giangVien.getHocVi());
        // existingGiangVien.setPassWord(giangVien.getPassWord());
        // existingGiangVien.setTen(giangVien.getTen());
        // existingGiangVien.setUserName(giangVien.getUserName());
        // existingGiangVien.setTrangThaiXoa(false);
        // giangVienRepository.save(existingGiangVien);
        // return 0;
        // }
        // return 1;
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

    public GiangVien findByMaGv(String maGv) {
        return giangVienRepository.findByMaGv(maGv);
    }

    public String resetPasswordLecturer(String maGv) {
        GiangVien gv = giangVienRepository.findByMaGv(maGv);
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        gv.setPassWord(sb.toString());
        giangVienRepository.save(gv);
        String message = emailService.sendMessage(gv.getEmail(), "Reset Password",
                "Password của bạn được reset thành: " + sb.toString(), "Reset password thành công");
        return message;

    }

    public void changeProfile(GiangVien giangVien) {
        giangVienRepository.save(giangVien);
    }

    public List<DangKyThiDTO> findDangKyThiByGiangVien(String magv) {
        List<DangKyThiDTO> list = dangKyThiRepository.findDangKyThiByMagv(magv);
        return list;
    }

    public List<ThiDTO> findThiFromIddk(int iddk) {
        return thiRepository.findThiFromIddk(iddk);
    }

    public int changePassword(String newPassword, String maGv) {
        try {
            GiangVien gv = giangVienRepository.findByMaGv(maGv);
            gv.setPassWord(newPassword);
            giangVienRepository.save(gv);
            return 0;
        } catch (Exception e) {
            System.out.println("=================");
            System.out.println(e.toString());
            return 1;
        }
    }
}
