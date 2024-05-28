package com.laptrinhweb.thitracnghiem.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.laptrinhweb.thitracnghiem.DTO.InfoDTO;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;
import com.laptrinhweb.thitracnghiem.Repository.Implement.SinhVienRepositoryImplt;
import com.laptrinhweb.thitracnghiem.Repository.Interface.LopRepository;
import com.laptrinhweb.thitracnghiem.Repository.Interface.SinhVienRepository;

@Service
public class SinhVienService {
    @Autowired private SinhVienRepositoryImplt sinhVienRepositoryImplt;
    @Autowired private SinhVienRepository sinhVienRepository;
    @Autowired private LopRepository lopRepository;
    public SinhVien getStudentInfo(String masv){
        if(masv == null || masv =="")return null;
        return sinhVienRepositoryImplt.getStudentInfo(masv);
    }
    public List<SinhVien> getStudentsByClass(String maLop){
        Lop lopHoc = lopRepository.getClassById(maLop);
        if(lopHoc == null)return null;
        lopHoc.getSinhviens().removeIf(sv->sv.isTrangThaiXoa() == true);
        return (List<SinhVien>) lopHoc.getSinhviens();
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
    public int deleteStudent(String masv){
        SinhVien sv = sinhVienRepository.findSinhVienByMasv(masv);
        if(sv.get_this().size()>0){
            return 0;
        }
        sinhVienRepository.deleteStudent(masv);
        return 1;
    }
    public boolean modifyInfo(SinhVien sinhVien){
        try{
            sinhVienRepository.modifyInfo(sinhVien.getMasv(),sinhVien.getHo() , sinhVien.getTen(), 
                                            sinhVien.isGioiTinh(), sinhVien.getDiaChi(), sinhVien.getNgaySinh(), sinhVien.getEmail());
            return true;
        }
        catch (Exception ex){

            throw ex;
        }
    }
    public boolean addNewStudent(SinhVien sv) throws Exception{
        SinhVien temp;
        if((temp = sinhVienRepository.checkValidSinhVien(sv.getMasv(),sv.getUserName())) != null){
            if(temp.getMasv() == sv.getMasv())
            throw new Exception("Mã sinh viên đã tồn tại");
            else throw new Exception("Username đã được sử dụng");
        }
        try{
            sinhVienRepository.save(sv);
            return true;
        }
        catch(Exception ex){
            throw ex;
        }
    }
    public String getPasswordByID(String masv){
        return sinhVienRepository.getPasswordByID(masv);
    }
    public void updatePassword(String masv,String newPassword){
            sinhVienRepository.updatePassword(masv, newPassword);

    }
}
