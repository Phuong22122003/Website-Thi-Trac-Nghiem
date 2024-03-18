package com.laptrinhweb.thitracnghiem.Service;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.SinhVienRepository;
import com.laptrinhweb.thitracnghiem.Repository.ThiRepository;


@Service
public class QuanLySinhVienService {
    @Autowired
    private SinhVienRepository sinhVienRepository;
    @Autowired 
    private ThiRepository thiRepository;
    @Autowired
    private DangKyService dangKyService;
    public List<SinhVien> getAllStudent(){
        return sinhVienRepository.getAllStudent();
    }
    public SinhVien getStudent(String masv){
        return sinhVienRepository.findSinhVienByMasv(masv);
    }
    public List<SinhVien> getStudentByClass(String malop){
        return sinhVienRepository.getStudentByClass(malop);
    }
    public List<SinhVien> searchStudetnsByKeyword(String keyword,String maLop){
        return sinhVienRepository.searchStudetnsByKeyword(keyword,maLop);
    }
    public void updateInfo(Map<String,Object> studentInfo){
        sinhVienRepository.updateInfo((String)studentInfo.get("masv"),
                                      (String)studentInfo.get("ho"),
                                      (String)studentInfo.get("ten"), 
                                      Boolean.parseBoolean((String)studentInfo.get("gioiTinh")),
                                      (String)studentInfo.get("diaChi"),
                                      Date.valueOf(String.valueOf(studentInfo.get("ngaySinh"))),
                                      (String)studentInfo.get("passWord"));
    }
    public void modifyInfo(Map<String,Object> studentInfo){
        System.out.println("hello");
        Date ngaySinh = Date.valueOf(LocalDate.parse((String)studentInfo.get("ngaySinh"), DateTimeFormatter.ISO_DATE));
        // LocalDate ngaySinh = LocalDate.parse((String)studentInfo.get("ngaySinh"), DateTimeFormatter.ISO_DATE);
        System.out.println("ngay sinh ==========="+ngaySinh.toString());
        sinhVienRepository.modifyInfo((String)studentInfo.get("masv"),
                                      (String)studentInfo.get("ho"),
                                      (String)studentInfo.get("ten"), 
                                      (Boolean)studentInfo.get("gioiTinh"),
                                      (String)studentInfo.get("diaChi"),
                                      ngaySinh);
    }
    public ResponseEntity<String> addStudent(SinhVien sv){
        if (sinhVienRepository.findSinhVienByMasv(sv.getMasv()) != null){
            return ResponseEntity.ok().body("Mã sinh viên đã tồn tại trong cơ sở dữ liệu");
        }
        if(dangKyService.checkExistUserName(sv.getUserName())==true){
            return ResponseEntity.ok().body("User name đã tồn tại trong cơ sở dữ liệu");
        }
        sinhVienRepository.save(sv);
        return ResponseEntity.ok().body("Lưu thành công");
    }
    public ResponseEntity<String> deleteStudent(String masv){
        if(thiRepository.checkExsitsThi(masv).size()>0){
            return ResponseEntity.badRequest().body("SINH VIÊN ĐÃ ĐĂNG KÝ THI KHÔNG THỂ XÓA");
        }
        sinhVienRepository.deleteStudent(masv);
        return ResponseEntity.ok().body("Xóa thành công");
    }
}
