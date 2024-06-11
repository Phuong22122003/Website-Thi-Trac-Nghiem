package com.laptrinhweb.thitracnghiem.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.Interface.LopRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.SinhVienRepository;

import jakarta.transaction.Transactional;



@Service
public class SinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired
    private LopRepository lopRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired 
    private SessionFactory sessionFactory;
    public String getMasvByUsername(String username) {
        SinhVien sv = sinhVienRepository.getStudentByUserName(username);
        if (sv != null)
            return sv.getMasv();
        return null;
    }

    public SinhVien getStudentInfo(String masv) {
        if (masv == null || masv == "")
            return null;
        return sinhVienRepository.findSinhVienByMasv(masv);
    }

    public List<SinhVien> getStudentsByClass(String maLop) {
        Lop lopHoc = lopRepository.getClassById(maLop);
        if (lopHoc == null)
            return null;
        lopHoc.getSinhviens().removeIf(sv -> sv.isTrangThaiXoa() == true);
        return (List<SinhVien>) lopHoc.getSinhviens();
    }

    public List<InfoDTO> showExamSchedules(String masv) {
        if (masv == null || masv == "")
            return null;
            List<InfoDTO> lichthi = null ;
            Session session = sessionFactory.openSession();
            String hql = "exec showExamSchedules :masv";
            Query<InfoDTO> query = session.createNativeQuery(hql,InfoDTO.class);
            query.setParameter("masv",masv);
            lichthi = query.getResultList();
            session.close();
            return lichthi;
    }

    public List<InfoDTO> showExamResults(String masv) {
        if (masv == null || masv == "")
            return null;
        Session session = sessionFactory.openSession();
        List<InfoDTO> diems = null;
        String spCall = "exec showExamResults :masv";
        Query<InfoDTO> query = session.createNativeQuery(spCall, InfoDTO.class);
        query.setParameter("masv", masv);
        diems =  query.getResultList();
        session.close();
        return diems;
    }

    // @Transactional
    public InfoDTO getResultByID(Integer idThi) {
        InfoDTO result = new InfoDTO();
        // Session session = sessionFactory.getCurrentSession();
        Session session = sessionFactory.openSession();
        session.getTransaction();    
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

    public int deleteStudent(String masv) {
        SinhVien sv = sinhVienRepository.findSinhVienByMasv(masv);
        if (sv.get_this().size() > 0) {
            return 0;
        }
        sinhVienRepository.deleteStudent(masv);
        return 1;
    }

    public boolean checkExistEmail(String masv,String email){
        SinhVien sv = sinhVienRepository.findStudentByEmail(email);
        if(sv!=null && !sv.getMasv().trim().toUpperCase().equals(masv.trim().toUpperCase()))return true;//Có trung email
        return false;
    }

    public boolean modifyInfo(SinhVien sinhVien) {
        try {
            sinhVienRepository.modifyInfo(sinhVien.getMasv(), sinhVien.getHo(), sinhVien.getTen(),
                    sinhVien.isGioiTinh(), sinhVien.getDiaChi(), sinhVien.getNgaySinh(), sinhVien.getEmail());
            return true;
        } catch (Exception ex) {

            throw ex;
        }
    }

    public boolean addNewStudent(SinhVien sv) throws Exception {
        SinhVien temp;
        if ((temp = sinhVienRepository.checkValidSinhVien(sv.getMasv(), sv.getUserName(),sv.getEmail())) != null) {
            if (temp.getMasv().trim().toUpperCase().equals(sv.getMasv().trim().toUpperCase()))
                throw new Exception("Mã sinh viên đã tồn tại");
            else if(temp.getUserName().trim().toUpperCase().equals(sv.getUserName().trim().toUpperCase()))
                throw new Exception("Username đã được sử dụng");
            else throw new Exception("Email đã được sử dụng");
        }
        try {
            sv.setPassWord(passwordEncoder.encode(sv.getPassWord()));
            sinhVienRepository.save(sv);
            return true;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public String getPasswordByID(String masv) {
        return sinhVienRepository.getPasswordByID(masv);
    }

    public void updatePassword(String masv, String newPassword) {
        sinhVienRepository.updatePassword(masv, newPassword);

    }

    public String randomPassword() {

        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(10);
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }

    public List<String> sendNewPasswordToStudent(List<String> emails) {
        String newPassword, status;
        List<String> listEmailError = new ArrayList<>();
        for (String email : emails) {
            newPassword = randomPassword();

            status = emailService.sendMessage(email, "New Password", "Password: " + newPassword, "");
            if (status == "Gửi mail thất bại, vui lòng thử lại!")
                listEmailError.add(email);
            else
                sinhVienRepository.updatePasswordByEmail(email, passwordEncoder.encode(newPassword));
        }
        return listEmailError;
    }
    public String resetStudentPassword(String email) {
        String newPassword, status;
        newPassword = randomPassword();
        status = emailService.sendMessage(email, "New Password", "Password: " + newPassword, "ResetPassword thành công");
        if (status == "Gửi mail thất bại, vui lòng thử lại!"){
            return status;
        }
        else
            sinhVienRepository.updatePasswordByEmail(email, passwordEncoder.encode(newPassword));
        return status;
    }
}
