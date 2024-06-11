package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.sql.Date;
import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laptrinhweb.thitracnghiem.Entity.SinhVien;

@Repository
public interface SinhVienRepository extends JpaRepository<SinhVien, String> {
        //Lấy thông tin sinh viên
        @Query(value = "from SinhVien s where s.masv = :masv")
        public SinhVien findSinhVienByMasv(@Param("masv") String masv);

        //Lấy thông tin sinh viên trong chức năng xác thcuwj và trạng thái xóa = false
        @Query(value = "from SinhVien s where s.userName = :userName")
        public SinhVien getStudentByUserName(@Param("userName") String userName);

        @Query(value = "from SinhVien s where s.email = :email")
        public SinhVien findStudentByEmail(@Param("email") String email);

        @Query(value = "from SinhVien s where s.masv=:masv or s.userName = :userName or s.email = :email")  
        public SinhVien checkValidSinhVien(@Param("masv") String masv, @Param("userName") String userName,@Param("email")String email);

        @Query(value = "select passWord from SinhVien s where s.masv=:masv and s.trangThaiXoa = false")
        public String getPasswordByID(@Param("masv") String masv);

        @Transactional
        @Modifying
        @Query(value = "UPDATE SinhVien s SET s.ho = :ho,s.ten = :ten,s.gioiTinh =:gioiTinh, s.diaChi = :diaChi,s.ngaySinh=:ngaySinh,s.email=:email where s.masv=:masv")
        public void modifyInfo(
                        @Param("masv") String masv,
                        @Param("ho") String ho,
                        @Param("ten") String ten,
                        @Param("gioiTinh") boolean gioiTinh,
                        @Param("diaChi") String diaChi,
                        @Param("ngaySinh") LocalDate ngaySinh,
                        @Param("email") String email);

        @Transactional
        @Modifying
        @Query(value = "UPDATE SinhVien s SET s.trangThaiXoa = true  WHERE s.masv = :masv")
        public void deleteStudent(@Param("masv") String masv);

        @Transactional
        @Modifying
        @Query(value = "UPDATE SinhVien s SET s.passWord = :newPassword  WHERE s.masv = :masv")
        public void updatePassword(@Param("masv") String masv, @Param("newPassword") String newPassword);

        @Transactional
        @Modifying
        @Query(value = "UPDATE SinhVien s SET s.passWord = :newPassword  WHERE s.email = :email")
        public void updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword);
}
