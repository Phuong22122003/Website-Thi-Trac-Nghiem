package com.laptrinhweb.thitracnghiem.Repository.Implement;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.laptrinhweb.thitracnghiem.Entity.NhanVien;

@Repository
public class NhanVienRepositoryImplt {
    @Autowired
    private SessionFactory sessionFactory;

    public NhanVien getStudentByUserName(String userName) {
        NhanVien nv = null;
        Session session = sessionFactory.openSession();
        String hql = "From NhanVien where userName =:userName";
        Query<NhanVien> query = session.createQuery(hql, NhanVien.class);
        query.setParameter("userName", userName);
        nv = (NhanVien) query.uniqueResult();
        session.close();
        return nv;
    }

}
