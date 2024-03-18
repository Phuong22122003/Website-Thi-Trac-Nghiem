package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Repository.DangKyThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.DayHocRepository;
import com.laptrinhweb.thitracnghiem.Repository.MonHocRepository;

@Service
public class QuanLyMonHocService {
    @Autowired
    MonHocRepository monHocRepository;

    @Autowired
    DayHocRepository dayHocRepository;

    @Autowired
    DangKyThiRepository dangKyThiRepository;

    public void deleteMonHoc(String mamh) {
        MonHoc monHoc = monHocRepository.findById(mamh).orElse(null);
        monHoc.setTrangThaiXoa(true);
        monHocRepository.save(monHoc);
    }

    public List<MonHoc> searchMonHoc(String keyword) {
        return monHocRepository.searchMonHoc(keyword);
    }

    public int addMonHoc(Map<String, Object> monHocInfo) {
        MonHoc checkId = monHocRepository.findById((String) monHocInfo.get("mamh")).orElse(null);
        if (checkId != null) {
            return 1;
        }
        MonHoc monHoc = new MonHoc();
        setMonHocValues(monHoc, monHocInfo);
        return 0;
    }

    public int updateMonHoc(Map<String, Object> monHocInfo) {
        MonHoc monHoc = monHocRepository.findById((String) monHocInfo.get("mamh")).orElse(null);
        setMonHocValues(monHoc, monHocInfo);
        return 0;
    }

    private void setMonHocValues(MonHoc monHoc, Map<String, Object> monHocInfo) {
        monHoc.setMamh((String) monHocInfo.get("mamh"));
        monHoc.setTenmh((String) monHocInfo.get("tenmh"));
        // Kiểm tra và thiết lập các giá trị khác
        Integer sotietlt = Integer.parseInt((String) monHocInfo.get("soTietLt"));
        if (sotietlt != null) {
            monHoc.setSoTietLt(sotietlt);
        }
        Integer sotietth = Integer.parseInt((String) monHocInfo.get("soTietTh"));
        if (sotietth != null) {
            monHoc.setSoTietTh(sotietth);
        }
        monHoc.setTrangThaiXoa(false);
        monHocRepository.save(monHoc);
    }

    // Kiem tra xem mon hoc co dang duoc day hay da duoc thi hay chua
    public boolean canDeleteMonHoc(String mamh) {
        long count = dayHocRepository.countByMamhAndTrangThaiXoa(mamh, false)
                + dangKyThiRepository.countByMamhAndTrangThaiXoa(mamh, false);
        return count == 0;
    }
}
