package com.laptrinhweb.thitracnghiem.Repository.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.laptrinhweb.thitracnghiem.Entity.NhanVien;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, String> {
    @Query(value = "From NhanVien s where s.userName = :userName")
    public NhanVien getEmployeeByUserName(@Param("userName") String userName);

    @Query(value = "From NhanVien s where s.email = :email")
    public NhanVien findEmployeeByEmail(@Param("email") String email);

    public NhanVien findByManv(String manv);

    public NhanVien findByUserNameAndTrangThaiXoa(String userName, boolean trangThaiXoa);
    @Transactional
    @Modifying
    @Query(value = "UPDATE NhanVien n SET n.passWord = :newPassword  WHERE n.email = :email")
    public void updatePassword(@Param("newPassword") String newPassword,@Param("email") String email);
}
