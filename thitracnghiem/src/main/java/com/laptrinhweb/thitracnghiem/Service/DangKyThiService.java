package com.laptrinhweb.thitracnghiem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.DangKyThiRepository;

@Service
public class DangKyThiService {
    @Autowired
    DangKyThiRepository dangKyThiRepository;
    @Autowired
    CauHoiRepository cauHoiRepository;

    public int registerExam(DangKyThi dangKyThi) {
        int lanThi = (dangKyThiRepository.findByLopAndMonHoc(dangKyThi.getLop(),
                dangKyThi.getMonHoc()).size()) + 1;
        dangKyThi.setLan(lanThi);
        System.out.println(dangKyThi.getLan());
        System.out.println(dangKyThi.getSoCau());
        System.out.println(dangKyThi.getThoiLuong());
        System.out.println(dangKyThi.getGiangVien());
        System.out.println(dangKyThi.getLop().getMaLop());
        System.out.println(dangKyThi.getMonHoc().getMamh());
        System.out.println(dangKyThi.getNgayThi());
        System.out.println(dangKyThi.getNhanVien().getManv());
        System.out.println(dangKyThi.isTrangThaiXoa());
        dangKyThiRepository.save(dangKyThi);
        return 0;
    }

    public int countCauHoiByMonHoc(MonHoc monHoc) {
        return cauHoiRepository.findByMonHocAndTrangThaiXoa(monHoc, false).size();
    }

}
