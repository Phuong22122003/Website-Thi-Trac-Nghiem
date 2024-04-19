package com.laptrinhweb.thitracnghiem.Repository.Implement;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.LuaChon;



@Repository
public class LuaChonRepositoryImplt {
    @Autowired 
    private SessionFactory sessionFactory;
    public List<LuaChon> findAllByIdch(Integer idch){
        List<LuaChon> list =null;
        String spCall = "FROM LuaChon lc where lc.cauHoi.idch = :idch order by thuTu ASC";
        Session session = sessionFactory.openSession();
        Query<LuaChon> query = session.createQuery(spCall, LuaChon.class);
        query.setParameter("idch",idch);
        list=query.getResultList();
        // list.sort((e1,e2)->e1.getThuTu()-e2.getThuTu());
        session.close();
        return list;
    }
}
