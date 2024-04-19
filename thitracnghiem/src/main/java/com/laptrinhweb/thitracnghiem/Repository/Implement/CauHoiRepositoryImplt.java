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
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;


@Repository
public class CauHoiRepositoryImplt {
    @Autowired
    private LuaChonRepositoryImplt luaChonRepositoryImplt;
    @Autowired 
    private SessionFactory sessionFactory;
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


        String spCall = "exec getPastExamQuestions  :idThi";
        Session session = sessionFactory.openSession();
        Query<Object[]> query = session.createNativeQuery(spCall, Object[].class);
        query.setParameter("idThi",idThi);
        rs = query.getResultList();
        for(Object[] r: rs){
            temp = new CauHoiThiDTO((Integer)r[0], r[1].toString(), (Integer)r[2],(Integer)r[3],(Integer)r[4]);
            luaChons = luaChonRepositoryImplt.findAllByIdch((Integer)r[0]);
            temp.setLuaChons(luaChons);
            li.add(temp);
        }
        ds.setListCauHoi(li);      
        session.close();
        return ds;
    }
}
