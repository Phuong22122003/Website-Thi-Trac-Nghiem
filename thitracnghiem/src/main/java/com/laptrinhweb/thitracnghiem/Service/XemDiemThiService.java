package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
import com.laptrinhweb.thitracnghiem.Repository.DiemRepository;


@Service
public class XemDiemThiService {
    @Autowired
    private DiemRepository diemRepository;
    public List<InfoDTO>xemDiemThi(String masv){
        List<InfoDTO> list = diemRepository.layDanhSachDiem(masv);
        return list;
    }
}
