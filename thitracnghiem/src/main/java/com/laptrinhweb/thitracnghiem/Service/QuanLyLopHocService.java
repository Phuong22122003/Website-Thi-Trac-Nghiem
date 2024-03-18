package com.laptrinhweb.thitracnghiem.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Repository.LopRepository;


@Service
public class QuanLyLopHocService {
    @Autowired
    private LopRepository lopRepository;
    public List<Lop> getAllClass(){
        return lopRepository.getAllClass();
    }
    public Lop getClassById(String malop){
        return lopRepository.getClassById(malop);
    }
    public List<Lop> getAllClassByKeyword(String keyword){
        return lopRepository.getAllClassByKeyword(keyword);
    }
    public ResponseEntity<String> updateInfo(Map<String,Object> lop){
        String maLop,tenLop;
        Date namNhapHoc;
        maLop =(String) lop.get("maLop");
        tenLop = (String) lop.get("tenLop");
        namNhapHoc = Date.valueOf(LocalDate.parse((String)lop.get("namNhapHoc"), DateTimeFormatter.ISO_DATE));
        System.err.println(maLop);
        lopRepository.updateInfo(maLop, tenLop, namNhapHoc);
        return ResponseEntity.ok().body("Cập nhật thành công");
    }
    public ResponseEntity<String> deleteClass(String malop){
        if(getClassById(malop) == null){
            return ResponseEntity.ok().body("Lớp học không tồn tại");
        }
        lopRepository.deleteClass(malop);
        return ResponseEntity.ok().body("Xóa lớp học thành công");
    }
    public ResponseEntity<String> addNewClass(Lop lophoc){
        if(getClassById(lophoc.getMaLop()) !=null){
            return ResponseEntity.ok().body("Mã lớp học đã tồn tại");
        }
        lopRepository.save(lophoc);
        return ResponseEntity.ok().body("Thêm lớp học thành công");
    }
}
