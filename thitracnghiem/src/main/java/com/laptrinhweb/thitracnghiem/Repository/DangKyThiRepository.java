package com.laptrinhweb.thitracnghiem.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;

@Repository
public interface DangKyThiRepository extends JpaRepository<DangKyThi, Integer> {
    List<DangKyThi> findByMagvAndMaLop(String magv, String maLop);

    @Query(value = "SELECT DISTINCT maLop from DangKyThi WHERE UPPER(magv) = UPPER(:maGv)", nativeQuery = true)
    List<String> findMaLopByMaGv(String maGv);

    List<DangKyThi> findByMaLopAndMamh(String maLop, String mamh);

    long countByMamhAndTrangThaiXoa(String mamh, boolean trangThaiXoa);

    long countByMagvAndTrangThaiXoa(String magv, boolean trangThaiXoa);

    long countByMamhAndMagvAndTrangThaiXoa(String mamh, String magv, boolean trangThaiXoa);
}
