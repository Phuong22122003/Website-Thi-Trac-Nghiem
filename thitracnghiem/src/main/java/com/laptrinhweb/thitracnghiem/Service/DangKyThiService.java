package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.DangKyThiDTO;
import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.DangKyThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.ThiRepository;

import jakarta.transaction.Transactional;

@Service
public class DangKyThiService {
    @Autowired
    DangKyThiRepository dangKyThiRepository;
    @Autowired
    CauHoiRepository cauHoiRepository;
    @Autowired
    ThiRepository thiRepository;

    @Transactional(rollbackOn = Exception.class)
    public int registerExam(DangKyThi dangKyThi) {
        try {
            int temp = dangKyThiRepository.findByLopAndMonHoc(dangKyThi.getLop(),
                    dangKyThi.getMonHoc()).size();
            int lanThi = temp + 1;
            dangKyThi.setLan(lanThi);
            DangKyThi dkt = dangKyThiRepository.save(dangKyThi);
            thiRepository.insertThiByIddk(dkt.getIddk());
            return 0; // Trả về giá trị thành công nếu không có lỗi
        } catch (Exception e) {
            System.out.println("=====================================================");
            System.out.println("Lỗi khi đăng ký thi");
            System.out.println(e.toString());
            return 1;
        }
    }

    public int countCauHoiByMonHoc(MonHoc monHoc) {
        return cauHoiRepository.findByMonHocAndTrangThaiXoa(monHoc, false).size();
    }

    public List<DangKyThiDTO> searchDangKyThi(String keyword, String magv) {
        return dangKyThiRepository.searchDangKyThi(keyword, magv);
    }
}
