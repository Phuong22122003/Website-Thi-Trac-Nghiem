package com.laptrinhweb.thitracnghiem.Repository.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.laptrinhweb.thitracnghiem.Entity.NhanVien;
import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, String> {
    // @Query(value = "EXEC findUserByUsername @username=:username,
    // @type='NHANVIENGV'", nativeQuery = true)
    // NhanVien findUserByUsername(@Param("username") String username);

    // List<NhanVien> findByUserName(String userName);
    @Query(value = "From NhanVien s where s.userName = :userName and s.trangThaiXoa = false")
    public NhanVien getEmployeeByUserName(@Param("userName") String userName);

    public NhanVien findByManv(String manv);

    public NhanVien findByUserNameAndTrangThaiXoa(String userName, boolean trangThaiXoa);
}
