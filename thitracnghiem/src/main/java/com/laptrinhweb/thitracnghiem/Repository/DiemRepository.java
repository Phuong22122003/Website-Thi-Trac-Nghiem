package com.laptrinhweb.thitracnghiem.Repository;



import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;


@Repository
public class DiemRepository {
    @Autowired
    private SessionFactory sessionFactory;
    public List<InfoDTO> layDanhSachDiem(String masv){
        Session session = sessionFactory.openSession();
        List<InfoDTO> diems = null;
        String spCall = "exec xemDiemThi " + masv;
        session.beginTransaction();
        Query<InfoDTO> query = session.createNativeQuery(spCall, InfoDTO.class);
        diems =  query.getResultList();
        session.getTransaction().commit();
        session.close();
        return diems;
    }
}
