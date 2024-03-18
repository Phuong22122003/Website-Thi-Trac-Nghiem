package com.laptrinhweb.thitracnghiem.Repository;

import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laptrinhweb.thitracnghiem.Entity.SinhVien;



@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
        public SinhVien findSinhVienByMasv(String masv);

        public SinhVien findSinhVienByUserName(String userName);


        @Query(value = "select * from SinhVien s where s.maLop = :maLop and s.trangThaiXoa = 'false'",nativeQuery = true)
        public List<SinhVien> getStudentByClass(@Param("maLop") String malop);
        @Query(value = "exec searchStudents :keyword, :maLop",nativeQuery = true)
        public List<SinhVien> searchStudetnsByKeyword(@Param("keyword") String keyword, @Param("maLop")String malop);

        @Transactional
        @Modifying
        @Query(value = "UPDATE SinhVien s SET s.ho = :ho,s.ten = :ten,s.gioiTinh =:gioiTinh, s.diaChi = :diaChi,s.ngaySinh=:ngaySinh,s.passWord = :passWord where s.masv=:masv")
        public void updateInfo(
                        @Param("masv") String masv,
                        @Param("ho") String ho,
                        @Param("ten") String ten,
                        @Param("gioiTinh") boolean gioiTinh,
                        @Param("diaChi") String diaChi,
                        @Param("ngaySinh") Date ngaySinh,
                        @Param("passWord") String passWord);

        @Transactional
        @Modifying
        @Query(value = "UPDATE SinhVien s SET s.ho = :ho,s.ten = :ten,s.gioiTinh =:gioiTinh, s.diaChi = :diaChi,s.ngaySinh=:ngaySinh where s.masv=:masv")
        public void modifyInfo(
                        @Param("masv") String masv,
                        @Param("ho") String ho,
                        @Param("ten") String ten,
                        @Param("gioiTinh") boolean gioiTinh,
                        @Param("diaChi") String diaChi,
                        @Param("ngaySinh") Date ngaySinh);

        @Transactional
        @Modifying
        @Query(value = "UPDATE SinhVien s SET s.trangThaiXoa = true  WHERE s.masv = :masv")
        public void deleteStudent(@Param("masv") String masv);

        @Query(value = "select * from sinhvien where trangThaiXoa = 0", nativeQuery = true)
        public List<SinhVien> getAllStudent();
}
