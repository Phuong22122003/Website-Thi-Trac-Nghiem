package com.laptrinhweb.thitracnghiem.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.laptrinhweb.thitracnghiem.Entity.DayHoc;
import com.laptrinhweb.thitracnghiem.Entity.GiangVien;
import java.util.List;

@Repository
public interface DayHocRepository extends JpaRepository<DayHoc, Integer> {
    @Query(value = "EXEC findUserByUsername @username = :username, @type = 'GIANGVIEN'", nativeQuery = true)
    GiangVien findUserByUserName(@Param("username") String username);

    @Query(value = "findMonHocByMagv @magv = :magv", nativeQuery = true)
    List<Object[]> findMonHocByMaGv(@Param("magv") String magv);

    @Query(value = "EXEC findIDDHByMaMhAndMaGv @MAMH = :mamh, @MAGV = :magv", nativeQuery = true)
    Integer findIDDHByMaMhAndMaGv(@Param("mamh") String mamh, @Param("magv") String magv);

    long countByMamhAndTrangThaiXoa(String mamh, boolean trangThaiXoa);

    long countByMagvAndTrangThaiXoa(String magv, boolean trangThaiXoa);

}
