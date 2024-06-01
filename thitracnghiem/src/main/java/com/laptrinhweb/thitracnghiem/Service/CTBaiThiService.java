package com.laptrinhweb.thitracnghiem.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoi;
import com.laptrinhweb.thitracnghiem.Entity.CTBaiThi;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CTBaiThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.ThiRepository;

@Service
public class CTBaiThiService {
    @Autowired
    private CTBaiThiRepository ctBaiThiRepository;
    @Autowired
    private ThiRepository thiRepository;

    public boolean saveAllCTBaiThi(List<CTBaiThi> ctbts) {
        try {
            ctBaiThiRepository.saveAll(ctbts);
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public float saveAllCTBaiThi(DanhSachCauHoi danhsachch, List<Integer> dap_an, boolean finish) throws Exception {
        CTBaiThi temp;
        List<CTBaiThi> list = new ArrayList<>();
        int i = 0, soCauDung = 0;
        float diem = 0;
        for (CauHoiThiDTO ch : danhsachch.getListCauHoi()) {
            if (ch.getDapAnSV() == dap_an.get(i))
                soCauDung++;
            temp = new CTBaiThi(danhsachch.getIdThi(), ch.getIdch(), ch.getDapAnSV(), i + 1, false);
            i++;
            list.add(temp);
        }
        if (this.saveAllCTBaiThi(list) == false)
            throw new Exception("Lỗi không thể lưu chi tiết bài thi");
        if (finish == false)
            return 0;
        diem = (float) soCauDung / dap_an.size() * 10;
        diem = (float) Math.round(diem * 100) / 100;
        thiRepository.update(danhsachch.getIdThi(), diem);
        return 0;
    }

    public void setFinishedThi(int idThi) {
        thiRepository.update(idThi, 0);
    }
}
