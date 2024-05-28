package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Repository.Interface.LopRepository;

@Service
public class LopHocService {
    @Autowired private LopRepository lopRepository;
    public List<Lop> getAllClass(){
        return lopRepository.getAllClass();
    }
    public void deleteClass(String maLop) throws Exception{
        Lop lop = lopRepository.getClassById(maLop);
        if(lop == null){
            throw new Exception("Mã lớp không tồn tại");
        }
        if(lop.getSinhviens().size()>0){
            throw new Exception("Lớp đã có sinh viên không thể xóa");
        }
        lopRepository.deleteClass(maLop);
    }
    public boolean checkExistsMaLop(String maLop){
        if(lopRepository.existsById(maLop)){
            return true;
        }
        else return false;
    }
    public void addNewClass(Lop lop){
        lopRepository.save(lop);
    }
    public void editClass(Lop lop){
        lopRepository.save(lop);
    }
}
