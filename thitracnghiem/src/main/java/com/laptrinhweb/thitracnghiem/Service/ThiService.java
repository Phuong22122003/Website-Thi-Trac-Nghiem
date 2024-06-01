package com.laptrinhweb.thitracnghiem.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.Thi;
import com.laptrinhweb.thitracnghiem.Repository.Interface.ThiRepository;

@Service
public class ThiService {
    @Autowired
    private ThiRepository thiRepository;

    public Boolean checkFinished(int idThi) {
        Thi thi = thiRepository.checkExsitsThi(idThi);
        if (thi != null) {
            return thi.isDathi();
        }
        return false;
    }
}
