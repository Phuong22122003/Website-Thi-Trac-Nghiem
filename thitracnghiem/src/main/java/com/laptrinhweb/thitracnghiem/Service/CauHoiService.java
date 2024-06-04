package com.laptrinhweb.thitracnghiem.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.laptrinhweb.thitracnghiem.DTO.CauHoiDTO;
import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoi;
import com.laptrinhweb.thitracnghiem.DTO.LuaChonDTO;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;
import com.laptrinhweb.thitracnghiem.Repository.Implement.CauHoiRepositoryImplt;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CTBaiThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.FileRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.GiangVienRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.LuaChonRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.MonHocRepository;
import java.lang.Integer;


@Service
public class CauHoiService {
    @Autowired
    private CauHoiRepositoryImplt cauHoiRepositoryImplt;
    @Autowired
    private CauHoiRepository cauHoiRepository;
    @Autowired
    private GiangVienRepository giangVienRepository;
    @Autowired
    private MonHocRepository monHocRepository;
    @Autowired
    private LuaChonRepository luaChonRepository;
    @Autowired
    private MonHocService monHocService;
    @Autowired
    private FileRepository fileRepository;

    public List<CauHoiThiDTO> getListOfExamQuestion(Integer idThi, List<Integer> dap_an) {
        List<CauHoiThiDTO> li = cauHoiRepositoryImplt.getListOfExamQuestion(idThi,
                dap_an);
        for (CauHoiThiDTO ch : li) {
            ch.setLuaChons(luaChonRepository.findAllLuaChonByIdch(ch.getIdch()));
            ch.setFiles(fileRepository.findAllByIDCH(ch.getIdch()));
        }
        return li;
    }

    public DanhSachCauHoi getPastExamQuestions(Integer idThi) {
        return cauHoiRepositoryImplt.getPastExamQuestions(idThi);
    }

    public List<CauHoiDTO> findCauHoiByMaGv(String maGv) {
        return cauHoiRepository.findCauHoiByMaGv(maGv);
    }

    public List<CauHoiDTO> searchCauHoi(String keyword, String maGv) {
        return cauHoiRepository.searchCauHoi(keyword, maGv);
    }

    // @Transactional(rollbackOn = Exception.class)
    public int createYesNoQuestion(String noiDungCauhoi, String maMh, String maGv, int dapAnDung) {
        try {
            CauHoi cauHoi = new CauHoi();
            cauHoi.setDapAnDung(dapAnDung);
            cauHoi.setGiangVien(giangVienRepository.findByMaGvAndTrangThaiXoa(maGv, false));
            cauHoi.setMonHoc(monHocRepository.findByMamh(maMh));
            cauHoi.setNoiDung(noiDungCauhoi);
            cauHoi.setTrangThaiXoa(false);
            cauHoi.setHinhThuc("YES/NO");
            cauHoi = cauHoiRepository.save(cauHoi);
            LuaChon luaChonYes = new LuaChon();
            luaChonYes.setCauHoi(cauHoi);
            luaChonYes.setNoiDung("Yes");
            luaChonYes.setThuTu(1);
            luaChonYes.setTrangThaiXoa(false);
            luaChonRepository.save(luaChonYes);
            LuaChon luaChonNo = new LuaChon();
            luaChonNo.setCauHoi(cauHoi);
            luaChonNo.setNoiDung("No");
            luaChonNo.setThuTu(2);
            luaChonNo.setTrangThaiXoa(false);
            luaChonRepository.save(luaChonNo);
            return 0;
        } catch (Exception e) {
            System.out.println("=============================");
            System.out.println(e.toString());
            return 1;
            // TODO: handle exception
        }

    }

    // @Transactional(rollbackOn = Exception.class)
    public int createOthersQuestion(String noiDungCauhoi, String maMh, String maGv, int dapAnDung,
            List<String> luaChonList, String hinhThuc) {
        try {
            System.out.println("===========");
            System.out.println(maGv);
            CauHoi cauHoi = new CauHoi();
            cauHoi.setDapAnDung(dapAnDung);
            cauHoi.setGiangVien(giangVienRepository.findByMaGvAndTrangThaiXoa(maGv, false));
            cauHoi.setMonHoc(monHocRepository.findByMamh(maMh));
            cauHoi.setNoiDung(noiDungCauhoi);
            cauHoi.setTrangThaiXoa(false);
            cauHoi.setHinhThuc(hinhThuc);
            cauHoi = cauHoiRepository.save(cauHoi);
            int thutu = 1;
            for (String noiDungLuaChon : luaChonList) {
                LuaChon luaChon = new LuaChon();
                luaChon.setCauHoi(cauHoi);
                luaChon.setNoiDung(noiDungLuaChon);
                luaChon.setThuTu(thutu++);
                luaChon.setTrangThaiXoa(false);
                luaChonRepository.save(luaChon);
            }
            return 0;
        } catch (Exception e) {
            System.out.println("================================");
            System.out.println(e.toString());
            return 1;
        }
    }

    // @Transactional(rollbackOn = Exception.class)
    public int deleteQuestion(int idch) {
        try {
            CauHoi cauHoi = cauHoiRepository.findByIdch(idch);
            if (cauHoi.getCtBaiThis().size() > 0) {
                return 1;
            }
            List<LuaChon> luaChons = new ArrayList(cauHoi.getLuaChons());
            if (luaChons != null && luaChons.size() > 0) {
                for (LuaChon luaChon : luaChons) {
                    luaChon.setTrangThaiXoa(true);
                    luaChonRepository.save(luaChon);
                }
            }
            cauHoi.setTrangThaiXoa(true);
            cauHoiRepository.save(cauHoi);
            return 0;
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("=====================");
            System.out.println(e.toString());
            return 1;
        }
    }

    public List<LuaChonDTO> getInfoLuaChon(int idch) {
        List<LuaChonDTO> luaChonDTOList = luaChonRepository.findLuaChonByIdch(idch);
        return luaChonDTOList;
    }

    public int editYesNoQuestion(String mamh, String noiDungCauHoi, Integer dapAnDung, Integer idch) {

        try {
            if (noiDungCauHoi.length() == 0 || monHocService.findByMamh(mamh) == null || dapAnDung == null
                    || idch == null)
                return 1;

            CauHoi cauHoi = cauHoiRepository.findByIdch(idch);

            cauHoi.setMonHoc(monHocRepository.findByMamh(mamh));
            cauHoi.setNoiDung(noiDungCauHoi);
            cauHoi.setDapAnDung(dapAnDung);
            cauHoiRepository.save(cauHoi);
            return 0;
        } catch (Exception e) {
            System.out.println("====================");
            System.out.println(e.toString());
            return 1;
        }
    }

    public static boolean containsEmptyString(List<String> list) {
        for (String str : list) {
            if (str.isEmpty()) {
                return false; // Trả về false nếu có chuỗi rỗng
            }
        }
        return true; // Trả về true nếu không có chuỗi rỗng
    }

    public int editOthersQuestion(String mamh, String noiDungCauHoi, Integer dapAnDung, Integer idch,
            List<Integer> idlcEditedList,
            List<String> noiDungLuaChonEditedList, List<String> noiDungLuaChonAddedList) {
        try {
            if (noiDungCauHoi.length() == 0 || monHocService.findByMamh(mamh) == null || dapAnDung == null
                    || idch == null || idlcEditedList.size() == 0 || noiDungLuaChonEditedList.size() == 0)
                return 1;

            CauHoi cauHoi = cauHoiRepository.findByIdch(idch);

            cauHoi.setMonHoc(monHocRepository.findByMamh(mamh));
            cauHoi.setNoiDung(noiDungCauHoi);
            cauHoi.setDapAnDung(dapAnDung);
            cauHoiRepository.save(cauHoi);
            int thuTu = noiDungLuaChonEditedList.size() + 1;
            int i = 0;
            for (int idlc : idlcEditedList) {
                LuaChon luaChon = luaChonRepository.findByIdlc(idlc);
                luaChon.setNoiDung(noiDungLuaChonEditedList.get(i++));
                luaChonRepository.save(luaChon);
            }
            if (noiDungLuaChonAddedList.size() > 0) {
                for (String noiDungLuaChon : noiDungLuaChonAddedList) {
                    LuaChon luaChon = new LuaChon();
                    luaChon.setCauHoi(cauHoi);
                    luaChon.setNoiDung(noiDungLuaChon);
                    luaChon.setThuTu(thuTu++);
                    luaChonRepository.save(luaChon);
                }
            }
        } catch (Exception e) {
            System.out.println("====================");
            System.out.println(e.toString());
            return 1;
        }
        return 0;
    }

    public CauHoi findByIdch(int idch) {
        return cauHoiRepository.findByIdch(idch);
    }

    public CauHoi findByIdlc(int idlc) {
        LuaChon luaChon = luaChonRepository.findByIdlc(idlc);
        if (luaChon != null) {
            return luaChon.getCauHoi();
        }
        return null;
    }

    public int deleteLuaChon(int idlc) {
        try {
            LuaChon deletedLuaChon = luaChonRepository.findByIdlc(idlc);
            LuaChon luaChonDung = null;
            CauHoi cauHoi = deletedLuaChon.getCauHoi();
            if (cauHoi.getLuaChons().size() <= 1)
                return 2;
            for (LuaChon luaChon : cauHoi.getLuaChons()) {
                if (luaChon.getThuTu() == cauHoi.getDapAnDung()) {
                    luaChonDung = luaChon;
                }
            }

            int thutu = 1;

            for (LuaChon lc : cauHoi.getLuaChons()) {
                if (lc.getIdlc() != deletedLuaChon.getIdlc()) {
                    lc.setThuTu(thutu++);
                    luaChonRepository.save(lc);
                }
            }

            if (luaChonDung.getIdlc() == deletedLuaChon.getIdlc()) {
                cauHoi.setDapAnDung(1);
            } else {
                cauHoi.setDapAnDung(luaChonDung.getThuTu());
            }
            luaChonRepository.delete(deletedLuaChon);
            return 0;
        } catch (Exception e) {
            System.out.println("===========================");
            System.out.println(e.toString());
            return 1;
        }
    }
}
