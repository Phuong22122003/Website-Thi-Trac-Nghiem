package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.DTO.DangKyThiDTO;
import com.laptrinhweb.thitracnghiem.Entity.DangKyThi;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;

@Repository
public interface DangKyThiRepository extends JpaRepository<DangKyThi, Integer> {
    List<DangKyThi> findByLopAndMonHoc(Lop lop, MonHoc monHoc);
    @Query(value = "EXEC FINDDANGKYTHIBYMAGV :magv", nativeQuery = true)
    List<DangKyThiDTO> findDangKyThiByMagv(@Param("magv") String magv);

    @Query(value = "EXEC searchDangKyThi :keyword, :MAGV", nativeQuery = true)
    List<DangKyThiDTO> searchDangKyThi(@Param("keyword") String keyword, @Param("MAGV") String maGv);
}
