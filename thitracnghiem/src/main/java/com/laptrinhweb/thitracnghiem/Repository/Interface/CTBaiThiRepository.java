package com.laptrinhweb.thitracnghiem.Repository.Interface;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.laptrinhweb.thitracnghiem.Entity.CTBaiThi;
import com.laptrinhweb.thitracnghiem.Entity.IDCTBaiThi;

@Repository
public interface CTBaiThiRepository extends JpaRepository<CTBaiThi, IDCTBaiThi> {
    // @Query(value = "Exec layThongTinKQ @idThi= :idThi", nativeQuery = true)
    // public Map<String, Object> layThongTinKQ(@Param("idThi") int idThi);

    // public long countByIdchAndTrangThaiXoa(int idch, boolean trangThaiXoa);
}
