package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoi;
import com.laptrinhweb.thitracnghiem.Repository.Implement.CauHoiRepositoryImplt;
import com.laptrinhweb.thitracnghiem.Repository.Implement.LuaChonRepositoryImplt;
@Service
public class CauHoiService {
    @Autowired private CauHoiRepositoryImplt cauHoiRepositoryImplt;
    @Autowired private LuaChonRepositoryImplt luaChonRepositoryImplt;

    public List<CauHoiThiDTO> getListOfExamQuestion(Integer idThi,List<Integer> dap_an){
        List<CauHoiThiDTO> li = cauHoiRepositoryImplt.getListOfExamQuestion(idThi,dap_an);
        for(CauHoiThiDTO ch: li){
            ch.setLuaChons(luaChonRepositoryImplt.findAllByIdch(ch.getIdch()));
        }
        return li;
    }
    public DanhSachCauHoi getPastExamQuestions(Integer idThi){
        return cauHoiRepositoryImplt.getPastExamQuestions(idThi);
    }
}
