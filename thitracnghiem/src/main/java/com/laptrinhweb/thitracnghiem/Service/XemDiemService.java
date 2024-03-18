package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.ThiInfoDTO;
import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;
import com.laptrinhweb.thitracnghiem.Repository.DangKyThiRepository;
import com.laptrinhweb.thitracnghiem.Repository.ThiRepository;



@Service
public class XemDiemService {
    @Autowired
    DangKyThiRepository dangKyThiRepository;
    @Autowired
    ThiRepository thiRepository;

    public List<DangKyThi> getListDangKyThi(String maGv, String malop) {
        System.out.println(maGv);
        return dangKyThiRepository.findByMagvAndMaLop(maGv, malop);
    }

    public List<String> getMaLop(String maGv) {
        return dangKyThiRepository.findMaLopByMaGv(maGv);
    }

    public List<ThiInfoDTO> getThiInfoFromIddk(int iddk) {
        return thiRepository.findThiInfoFromIddk(iddk);
    }
}
