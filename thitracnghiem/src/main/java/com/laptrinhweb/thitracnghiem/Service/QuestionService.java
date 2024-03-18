package com.laptrinhweb.thitracnghiem.Service;

import java.util.Map;
import java.lang.reflect.Array;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;
import com.laptrinhweb.thitracnghiem.Repository.CTBaiThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.DayHocRepository;
import com.laptrinhweb.thitracnghiem.Repository.LuaChonRepository;



@Service
public class QuestionService {
    @Autowired
    private DayHocRepository dayHocRepository;

    @Autowired
    private CauHoiRepository cauHoiRepository;

    @Autowired
    private LuaChonRepository luaChonRepository;

    @Autowired
    private CTBaiThiRepository ctBaiThiRepository;

    public void addQuestion(String magv, Map<String, Object> question) {
        Integer iddh = dayHocRepository.findIDDHByMaMhAndMaGv((String) question.get("monHoc"), magv);
        CauHoi cauHoi = new CauHoi();
        cauHoi.setHinhThuc((String) (question.get("hinhThuc")));
        cauHoi.setIddh(iddh);
        cauHoi.setDapAnDung((int) (question.get("dapAnDung")));
        cauHoi.setTrangThaiXoa(false);
        cauHoi.setNoiDung((String) question.get("noiDungCauHoi"));
        CauHoi savedCauHoi = cauHoiRepository.save(cauHoi);
        List<Map<String, Object>> selections = (List) question.get("luaChon");
        // System.out.println(selections.get(0).get("noiDungLuaChon"));
        for (Map<String, Object> map : selections) {
            LuaChon luaChon = new LuaChon();
            luaChon.setIdch(savedCauHoi.getIdch());
            luaChon.setNoiDung((String) map.get("noiDungLuaChon"));
            luaChon.setThuTu((int) map.get("thuTuLuaChon"));
            luaChon.setTrangThaiXoa(false);
            luaChonRepository.save(luaChon);
        }
    }

    public void updateQuestion(int maCauHoi, Map<String, Object> question, String maGv) {
        System.out.println("------------------------- vo day-----------------------");
        int iddh = dayHocRepository.findIDDHByMaMhAndMaGv((String) question.get("monHoc"), maGv);
        CauHoi cauHoi = cauHoiRepository.findById(maCauHoi).orElse(null);
        cauHoi.setDapAnDung((int) (question.get("dapAnDung")));
        cauHoi.setNoiDung((String) question.get("noiDungCauHoi"));
        cauHoi.setIddh(iddh);
        cauHoiRepository.save(cauHoi);
        List<Map<String, Object>> selections = (List) question.get("luaChon");
        for (Map<String, Object> map : selections) {
            LuaChon luaChon = luaChonRepository.findById((int) map.get("maLuaChon")).orElse(null);
            luaChon.setNoiDung((String) map.get("noiDungLuaChon"));
            luaChonRepository.save(luaChon);
        }
    }

    public String deleteQuestion(int maCauHoi) {
        CauHoi cauHoi = cauHoiRepository.findById(maCauHoi).orElse(null);
        cauHoi.setTrangThaiXoa(true);
        cauHoiRepository.save(cauHoi);
        return "Xóa câu hỏi thành công";
    }

    public int deleteSelection(int maLuaChon, int maCauHoi) {
        LuaChon luaChon = luaChonRepository.findById(maLuaChon).orElse(null);
        luaChonRepository.delete(luaChon);

        CauHoi cauHoi = cauHoiRepository.findById(maCauHoi).orElse(null);
        List<Object[]> listLuaChon = cauHoiRepository.findLuaChocByMaCauHoi(maCauHoi);
        int thuTu = 1;
        for (Object[] item : listLuaChon) {
            // idlc
            Object idlcObject = item[2];
            if (idlcObject instanceof Number) {
                int temp = (int) idlcObject;
                LuaChon savedLuaChon = luaChonRepository.findById(temp).orElse(null);
                boolean check = false;
                if (savedLuaChon.getThuTu() == cauHoi.getDapAnDung()) {
                    check = true;
                }
                savedLuaChon.setThuTu(thuTu);
                if (check) {
                    cauHoi.setDapAnDung(thuTu);
                    cauHoiRepository.save(cauHoi);
                }
                thuTu++;
                luaChonRepository.save(savedLuaChon);
            }
        }
        return cauHoi.getDapAnDung();
    }

    public void addSelection(Integer maCauHoi, Map<String, Object> selection) {
        LuaChon luaChon = new LuaChon();
        luaChon.setIdch(maCauHoi);
        luaChon.setNoiDung((String) selection.get("noiDungLuaChon"));
        luaChon.setThuTu((int) selection.get("thuTuLuaChon"));
        luaChon.setTrangThaiXoa(false);
        luaChonRepository.save(luaChon);
    }

    public boolean canEditOrDelete(int idch) {
        long count = ctBaiThiRepository.countByIdchAndTrangThaiXoa(idch, false);
        System.out.println("count " + count);
        return count == 0;
    }
}
