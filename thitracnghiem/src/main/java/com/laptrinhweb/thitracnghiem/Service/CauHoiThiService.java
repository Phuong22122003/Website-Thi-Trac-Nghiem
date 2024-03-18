package com.laptrinhweb.thitracnghiem.Service;


import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.Entity.CTBaiThi;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;
import com.laptrinhweb.thitracnghiem.Repository.CTBaiThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.DanhSachCauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.LuaChonRepository;
import com.laptrinhweb.thitracnghiem.Repository.ThiRepository;


@Service
public class CauHoiThiService {
    @Autowired
    private DanhSachCauHoiRepository danhSachCauHoiRepository;
    @Autowired
    private LuaChonRepository myLuaChonRepository;
    @Autowired
    private CTBaiThiRepository ctBaiThiRepository;
    @Autowired
    private ThiRepository thiRepository;

    public DanhSachCauHoiThiDTO layCauHoiThi(String mamh, int socau, int idthi,List<Integer> dap_an) {
        DanhSachCauHoiThiDTO listCauHoiThi = new DanhSachCauHoiThiDTO();
        List<CauHoi> listCauHoi;
        List<LuaChon> listLuaChon;
        CauHoiThiDTO tmp;
        CTBaiThi tmp1;
        int thuTuChon = 1;
        listCauHoi = danhSachCauHoiRepository.layCauHoiThi(mamh, socau,dap_an);
        for (CauHoi ch : listCauHoi) {
            listLuaChon = myLuaChonRepository.findAllByIdch(ch.getIdch());
            listLuaChon.sort((lc1,lc2)->(lc1.getThuTu() - lc2.getThuTu()));
            tmp1 = new CTBaiThi(idthi);
            tmp1.setIdch(ch.getIdch());
            tmp1.setThuTuChon(thuTuChon++);
            tmp = new CauHoiThiDTO(ch, listLuaChon, tmp1);
            listCauHoiThi.addCauHoiThi(tmp);
        }
        return listCauHoiThi;
    }
    public float luuCauHoiThi(DanhSachCauHoiThiDTO list ){
        float diem = 0;
        int soCauDung = 0,tongSoCau = 0;
        List<CTBaiThi> ctBaiThi = new ArrayList<>();
        for(CauHoiThiDTO ch:list.getList()){
            ctBaiThi.add(ch.getCtBaiThi());
            if(ch.getCtBaiThi().getDapAnSv() == ch.getCauHoi().getDapAnDung())soCauDung ++;
            System.out.println(ch.getCtBaiThi().getDapAnSv()+ "   |   " +ch.getCauHoi().getDapAnDung());
            tongSoCau ++;
        }
        System.out.println(soCauDung);
        diem = (float)soCauDung/tongSoCau*10;
        diem = (float)Math.round(diem*100)/100;
        System.out.println(diem);
        thiRepository.update(ctBaiThi.get(0).getIdThi(),diem);
        ctBaiThiRepository.saveAll(ctBaiThi);
        return diem;
    }
    public void chenDapAn(DanhSachCauHoiThiDTO danhSachCauHoiThiDTO, List<Integer> dap_an){
        for (int i = 0; i<danhSachCauHoiThiDTO.getList().size();i++){
            danhSachCauHoiThiDTO.getList().get(i).getCauHoi().setDapAnDung(dap_an.get(i));
        }
    }
    
}
