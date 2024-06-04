package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;
import com.laptrinhweb.thitracnghiem.Entity.SinhVien;

@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {
    // @Query(value = "EXEC findUserByUsername @username = :username, @type =
    // 'GIANGVIEN'", nativeQuery = true)
    // GiangVien findUserByUserName(@Param("username") String username);

    // @Query(value = "select * from GIANGVIEN where GIANGVIEN.trangthaixoa=0",
    // nativeQuery = true)
    // public List<GiangVien> findAllGvWithoutDelete();

    // @Query(value = "EXEC searchGiangVien @keyword = :keyword", nativeQuery =
    // true)
    // public List<GiangVien> searchGiangVien(@Param("keyword") String keyword);

    // public GiangVien findByUserName(String userName);
    @Query(value = "from GiangVien s where s.userName = :userName and s.trangThaiXoa = false")
    public GiangVien getTeacherByUserName(@Param("userName") String userName);

    @Query(value = "select * from giangvien where trangthaixoa=0", nativeQuery = true)
    public List<GiangVien> findAllGiangVien();

    public GiangVien findByMaGvAndTrangThaiXoa(String maGv, boolean trangThaiXoa);

    public GiangVien findByMaGv(String maGv);

    public GiangVien findByUserName(String userName);

    public GiangVien findByUserNameAndTrangThaiXoa(String userName, boolean trangThaiXoa);

    @Query(value = "exec searchGiangVien @keyword = :keyword", nativeQuery = true)
    public List<GiangVien> searchGiangVien(@Param("keyword") String keyword);
}
