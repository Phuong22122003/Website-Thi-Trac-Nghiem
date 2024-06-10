package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.laptrinhweb.thitracnghiem.DTO.CauHoiDTO;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.MonHoc;


@Repository
public interface CauHoiRepository extends JpaRepository<CauHoi, Integer> {

        public List<CauHoi> findByMonHocAndTrangThaiXoa(MonHoc monHoc, boolean trangThaiXoa);

        @Query(value = "EXEC findCauHoiByMaGv :magv", nativeQuery = true)
        List<CauHoiDTO> findCauHoiByMaGv(@Param("magv") String maGv);

        @Query(value = "EXEC searchCauHoi :keyword, :MAGV", nativeQuery = true)
        List<CauHoiDTO> searchCauHoi(@Param("keyword") String keyword, @Param("MAGV") String maGv);

        CauHoi findByIdch(Integer idch);
}
