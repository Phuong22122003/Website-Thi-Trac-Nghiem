package com.laptrinhweb.thitracnghiem.Repository.Implement;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.DTO.CauHoiThiDTO;
import com.laptrinhweb.thitracnghiem.DTO.DanhSachCauHoi;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.FileCauHoi;
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;
import com.laptrinhweb.thitracnghiem.Repository.Interface.CauHoiRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.FileRepository;


@Repository
public class CauHoiRepositoryImplt {
    @Autowired private SessionFactory sessionFactory;
    @Autowired private FileRepository fileRepository;
    @Autowired private CauHoiRepository cauHoiRepository;
    public List<CauHoiThiDTO> getListOfExamQuestion(Integer idThi,List<Integer> dap_an){
        List<CauHoiThiDTO> list =null;
        String spCall = "exec listOfExamQuestions :idThi";
        Session session = sessionFactory.openSession();
        Query<CauHoiThiDTO> query = session.createNativeQuery(spCall, CauHoiThiDTO.class);
        query.setParameter("idThi",idThi);
        list=query.getResultList();
        for(CauHoiThiDTO c:list ){
            dap_an.add(c.getDapAnDung());
            c.setDapAnDung(null);
        }
        session.close();
        return list;
    }
    public DanhSachCauHoi getPastExamQuestions(Integer idThi){
        DanhSachCauHoi ds = new DanhSachCauHoi();
        List<CauHoiThiDTO> li = new ArrayList<>();
        CauHoiThiDTO temp;
        List<Object[]> rs ;
        List<LuaChon> luaChons = null;
        List<FileCauHoi> files = null;
        CauHoi cauHoi = null;
        String spCall = "exec getPastExamQuestions  :idThi";
        Session session = sessionFactory.openSession();
        Query<Object[]> query = session.createNativeQuery(spCall, Object[].class);
        query.setParameter("idThi",idThi);
        rs = query.getResultList();
        for(Object[] r: rs){
            temp = new CauHoiThiDTO((Integer)r[0], r[1].toString(), (Integer)r[2],(Integer)r[3],(Integer)r[4]);
            cauHoi = cauHoiRepository.findByIdch((Integer)r[0]);
            // luaChons = luaChonRepositoryImplt.findAllByIdch((Integer)r[0]);
            // files = fileRepository.getFilesByIDCH((Integer)r[0]);
            if(cauHoi!=null){
                luaChons = (List<LuaChon>) cauHoi.getLuaChons();
                files = (List<FileCauHoi>) cauHoi.getFiles();
                temp.setLuaChons(luaChons);
                if(files!=null)temp.setFiles(files);
                li.add(temp);
            }
        }
        ds.setListCauHoi(li);      
        session.close();
        return ds;
    }
}
