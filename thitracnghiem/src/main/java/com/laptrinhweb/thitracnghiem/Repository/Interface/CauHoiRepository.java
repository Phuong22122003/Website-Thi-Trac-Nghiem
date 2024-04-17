package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;

@Repository
public interface CauHoiRepository extends JpaRepository<CauHoi, Integer> {

        // @Query(value = "EXEC findCauHoiByMaGvAndMaMh @magv = :magv, @mamh = :mamh, @pageNumber = :pageNumber, @pageSize = :pageSize", nativeQuery = true)
        // List<Object[]> findCauHoiByMaGvAndMaMh(@Param("magv") String magv, @Param("mamh") String mamh,
        //                 @Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize);

        // @Query(value = "EXEC countQuestionByMonHocAndLecturer @MaGv = :magv, @mamh = :mamh", nativeQuery = true)
        // int countQuestionByMonHocAndLecturer(@Param("magv") String magv, @Param("mamh") String mamh);

        // @Query(value = "EXEC findLuaChocByMaCauHoi @maCauHoi = :maCauHoi", nativeQuery = true)
        // List<Object[]> findLuaChocByMaCauHoi(@Param("maCauHoi") int maCauHoi);

        // @Query(value = "Exec searchCauHoi @magv = :magv, @pageNumber = :pageNumber, @pageSize = :pageSize, @keyword = :keyword", nativeQuery = true)
        // List<Object[]> searchCauHoi(@Param("magv") String magv,
        //                 @Param("pageNumber") int pageNumber, @Param("pageSize") int pageSize,
        //                 @Param("keyword") String keyword);

        // @Query(value = "EXEC countQuestionByFinding @MaGv = :magv, @keyword = :keyword", nativeQuery = true)
        // int countQuestionByFinding(@Param("magv") String magv, @Param("keyword") String keyword);

        // @Query(value = "EXEC countCauHoiByMonHoc @mamh = :mamh", nativeQuery = true)
        // int countCauHoiByMonHoc(@Param("mamh") String mamh);

        // long countByIddhAndTrangThaiXoa(int iddh, boolean trangThaiXoa);
}
