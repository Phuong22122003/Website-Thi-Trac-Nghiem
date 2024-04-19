package com.laptrinhweb.thitracnghiem.Repository.Implement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.GiangVien;

@Repository
public class GiangVienRepositoryImplt {
    @Autowired private SessionFactory sessionFactory;
    public GiangVien getTeacherInfoByUserName(String userName){
        GiangVien gv =null;
        Session session = sessionFactory.openSession();
        String hql = "From GiangVien where userName =:userName";
        Query<GiangVien> query = session.createQuery(hql, GiangVien.class);
        query.setParameter("userName",userName);
        gv = (GiangVien) query.uniqueResult();
        session.close();
        return  gv;
    }
}
