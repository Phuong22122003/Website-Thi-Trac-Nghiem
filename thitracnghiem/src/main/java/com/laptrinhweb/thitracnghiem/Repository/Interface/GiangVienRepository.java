package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.GiangVien;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {
    @Query(value = "from GiangVien s where s.userName = :userName and s.trangThaiXoa = false")
    public GiangVien getTeacherByUserName(@Param("userName") String userName);

    @Query(value = "select * from giangvien where trangthaixoa=0", nativeQuery = true)
    public List<GiangVien> findAllGiangVien();

    @Query(value = "From GiangVien gv where gv.email = :email")
    public GiangVien findByEmail(@Param("email") String email);

    public GiangVien findByMaGvAndTrangThaiXoa(String maGv, boolean trangThaiXoa);

    public GiangVien findByMaGv(String maGv);

    public GiangVien findByUserName(String userName);

    
    public GiangVien findByUserNameAndTrangThaiXoa(String userName, boolean trangThaiXoa);

    @Query(value = "EXEC searchGiangVien @keyword = :keyword", nativeQuery = true)
    public List<GiangVien> searchGiangVien(@Param("keyword") String keyword);
}
