package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.Implement.SinhVienRepositoryImplt;

@Service
public class SinhVienService {
    @Autowired private SinhVienRepositoryImplt sinhVienRepositoryImplt;
    public SinhVien getStudentInfo(String masv){
        if(masv == null || masv =="")return null;
        return sinhVienRepositoryImplt.getStudentInfo(masv);
    }
    public List<InfoDTO> showExamSchedules(String masv){
        if(masv == null || masv =="")return null;
        return sinhVienRepositoryImplt.showExamSchedules(masv);
    }
    public List<InfoDTO> showExamResults(String masv){
        if(masv == null|| masv == "")return null;
        return sinhVienRepositoryImplt.showExamResults(masv);
    }
    public InfoDTO getResultByID(Integer idThi){
        return sinhVienRepositoryImplt.getResultByID(idThi);
    }
}
