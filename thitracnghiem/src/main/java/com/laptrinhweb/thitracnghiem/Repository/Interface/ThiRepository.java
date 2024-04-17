package com.laptrinhweb.thitracnghiem.Repository.Interface;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.laptrinhweb.thitracnghiem.DTO.ThiInfoDTO;
import com.laptrinhweb.thitracnghiem.Entity.Thi;


@Repository
public interface ThiRepository extends JpaRepository<Thi, Integer> {
    // @Query(value = "EXEC findThiFromIddk @iddk = :iddk", nativeQuery = true)
    // public List<ThiInfoDTO> findThiInfoFromIddk(@Param("iddk") int iddk);

    // @Query(value = "EXEC insertThiByIddk @iddk = :iddk", nativeQuery = true)
    // public void insertThiByIddk(@Param("iddk") int iddk);

    // @Query(value = "SELECT * FROM Thi t where t.masv = :masv and t.trangThaiXoa = 0", nativeQuery = true)
    // public List<Thi> checkExsitsThi(@Param("masv") String masv);

    // @Transactional
    // @Modifying
    // @Query("update Thi t set t.dathi = true,t.diem = :diem where t.idThi = :idthi")
    // public void update(@Param(value = "idthi") int idthi, @Param(value = "diem") float diem);
}