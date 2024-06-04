package com.laptrinhweb.thitracnghiem.Repository.Implement;



import java.sql.Date;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
@Repository
public class SinhVienRepositoryImplt{
    @Autowired private SessionFactory sessionFactory;
    public List<InfoDTO> showExamSchedules(String masv) {
        List<InfoDTO> lichthi = null ;
        Session session = sessionFactory.openSession();
        String hql = "exec showExamSchedules :masv";
        Query<InfoDTO> query = session.createNativeQuery(hql,InfoDTO.class);
        query.setParameter("masv",masv);
        lichthi = query.getResultList();
        session.close();
        return lichthi;
    }
    public List<InfoDTO> showExamResults(String masv){
        Session session = sessionFactory.openSession();
        List<InfoDTO> diems = null;
        String spCall = "exec showExamResults :masv";
        Query<InfoDTO> query = session.createNativeQuery(spCall, InfoDTO.class);
        query.setParameter("masv", masv);
        diems =  query.getResultList();
        session.close();
        return diems;
    }
    public InfoDTO getResultByID(Integer idThi){
        InfoDTO result = new InfoDTO();
        Session session = sessionFactory.openSession();
       
        String spCall = "exec getResultByID :idThi";
        Query<Object[]> query = session.createNativeQuery(spCall, Object[].class);
        query.setParameter("idThi", idThi);
        Object[] temp =  query.uniqueResult();//TENMH,NGAYTHI,LAN,SOCAU,THOILUONG,DIEM
        result.setTenMH(temp[0].toString());
        result.setNgayThi(Date.valueOf(temp[1].toString()));
        result.setLanThi((Integer)temp[2]);
        result.setSoCau((Integer)temp[3]);
        result.setThoiLuong((Integer)temp[4]);
        result.setDiem((Float)temp[5]);
        session.close();
        return result;
    }
}
