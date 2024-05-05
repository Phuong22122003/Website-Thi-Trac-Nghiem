package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.DTO.DangKyThiDTO;
import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;

@Repository
public interface DangKyThiRepository extends JpaRepository<DangKyThi, Integer> {
    List<DangKyThi> findByLopAndMonHoc(Lop lop, MonHoc monHoc);

    // @Query(value = "SELECT DISTINCT maLop from DangKyThi WHERE UPPER(magv) =
    // UPPER(:maGv)", nativeQuery = true)
    // List<String> findMaLopByMaGv(String maGv);

    // List<DangKyThi> findByMaLopAndMamh(String maLop, String mamh);

    // long countByMamhAndTrangThaiXoa(String mamh, boolean trangThaiXoa);

    // long countByMagvAndTrangThaiXoa(String magv, boolean trangThaiXoa);

    // long countByMamhAndMagvAndTrangThaiXoa(String mamh, String magv, boolean
    // trangThaiXoa);
    // @Query(value = "EXEC FINDDANGKYTHIBYMAGV @MAGV = ?1", nativeQuery = true)
    // List<DangKyThiDTO> findByMaGv(String magv);
    @Query(value = "EXEC FINDDANGKYTHIBYMAGV :magv", nativeQuery = true)
    List<DangKyThiDTO> findDangKyThiByMagv(@Param("magv") String magv);

    @Query(value = "EXEC searchDangKyThi :keyword, :MAGV", nativeQuery = true)
    List<DangKyThiDTO> searchDangKyThi(@Param("keyword") String keyword, @Param("MAGV") String maGv);
}
