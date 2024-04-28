package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Repository.Interface.MonHocRepository;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Service
public class MonHocService {

    @Autowired
    MonHocRepository monHocRepository;

    public List<MonHoc> getAllMonHoc() {
        return monHocRepository.findAllMonHoc();
    }

    public int deleteByMamh(String mamh) {
        MonHoc mh = monHocRepository.findByMamh(mamh);
        if (mh.getCauHois().size() > 0) {
            return 1;
        }
        mh.setTrangThaiXoa(true);
        monHocRepository.save(mh);
        return 0;
    }

    public int addMonHoc(MonHoc monHoc) {
        MonHoc existingMonHoc = monHocRepository.findByMamh(monHoc.getMamh());
        if (existingMonHoc == null) {
            MonHoc newMonHoc = new MonHoc(monHoc.getMamh(), monHoc.getTenmh(), monHoc.getSoTietLt(),
                    monHoc.getSoTietTh(), false);
            monHocRepository.save(newMonHoc);
            return 0;
        } else if (existingMonHoc.isTrangThaiXoa()) {
            existingMonHoc.setSoTietLt(monHoc.getSoTietLt());
            existingMonHoc.setSoTietTh(monHoc.getSoTietTh());
            existingMonHoc.setTenmh(monHoc.getTenmh());
            existingMonHoc.setTrangThaiXoa(false);
            monHocRepository.save(existingMonHoc);
            return 0;
        }
        return 1;
    }

    public int editMonHoc(String mamh, String tenmh, int soTietLt, int soTietTh) {
        MonHoc existingMonHoc = monHocRepository.findByMamh(mamh);
        if (existingMonHoc != null) {
            existingMonHoc.setTenmh(tenmh);
            existingMonHoc.setSoTietLt(soTietLt);
            existingMonHoc.setSoTietTh(soTietTh);
            monHocRepository.save(existingMonHoc);
            return 0;
        }
        return 1;
    }

    public List<MonHoc> searchMonHoc(String keyword) {
        List<MonHoc> list = monHocRepository.searchMonHoc(keyword);
        return list;
    }
}
