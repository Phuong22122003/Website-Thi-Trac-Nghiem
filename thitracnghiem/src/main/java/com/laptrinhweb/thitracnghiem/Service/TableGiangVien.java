package com.laptrinhweb.thitracnghiem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Repository.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.DayHocRepository;

import java.util.List;



@Service
public class TableGiangVien {
    @Autowired
    DayHocRepository dayHocRepository;
    @Autowired
    CauHoiRepository cauHoiRepository;

    public List<Object[]> getMonHoc(String magv) {
        return dayHocRepository.findMonHocByMaGv(magv);
    }

    public int getCountQuestionByMonHocAndLecturer(String magv, String mamh) {
        return cauHoiRepository.countQuestionByMonHocAndLecturer(magv, mamh);
    }

    public List<Object[]> getCauHoiByMaGvAndMaMh(String magv, String mamh, int pageNumber, int pageSize) {
        return cauHoiRepository.findCauHoiByMaGvAndMaMh(magv, mamh, pageNumber, pageSize);
    }

    public List<Object[]> getLuaChocByMaCauHoi(Integer maCauHoi) {
        return cauHoiRepository.findLuaChocByMaCauHoi(maCauHoi);
    }

    public List<Object[]> searchCauHoi(String magv, int pageNumber, int pageSize, String keyword) {
        List<Object[]> list = cauHoiRepository.searchCauHoi(magv, pageNumber, pageSize, keyword);
        System.out.println(list);
        return list;
    }

    public int getCountCauHoiByFinding(String magv, String keyword) {
        return cauHoiRepository.countQuestionByFinding(magv, keyword);
    }
}
