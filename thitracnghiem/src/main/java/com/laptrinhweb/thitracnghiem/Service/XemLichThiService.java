package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
import com.laptrinhweb.thitracnghiem.Repository.LichThiRepository;


@Service
public class XemLichThiService {
    @Autowired
    private LichThiRepository lichThiRepository;
    public List<InfoDTO> xemLichThi(String masv){
        List<InfoDTO> list = lichThiRepository.layLichThi(masv);
        return list;
    }
}
