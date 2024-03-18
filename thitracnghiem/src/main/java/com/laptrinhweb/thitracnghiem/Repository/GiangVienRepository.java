package com.laptrinhweb.thitracnghiem.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.GiangVien;



@Repository
public interface GiangVienRepository extends JpaRepository<GiangVien, String> {
    @Query(value = "EXEC findUserByUsername @username = :username, @type = 'GIANGVIEN'", nativeQuery = true)
    GiangVien findUserByUserName(@Param("username") String username);

    @Query(value = "select * from GIANGVIEN where GIANGVIEN.trangthaixoa=0", nativeQuery = true)
    public List<GiangVien> findAllGvWithoutDelete();

    @Query(value = "EXEC searchGiangVien @keyword = :keyword", nativeQuery = true)
    public List<GiangVien> searchGiangVien(@Param("keyword") String keyword);

    public GiangVien findByUserName(String userName);
}
