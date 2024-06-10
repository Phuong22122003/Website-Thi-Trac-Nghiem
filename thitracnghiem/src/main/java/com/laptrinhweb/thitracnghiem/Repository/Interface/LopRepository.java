package com.laptrinhweb.thitracnghiem.Repository.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.laptrinhweb.thitracnghiem.Entity.Lop;
import java.util.List;

@Repository
public interface LopRepository extends JpaRepository<Lop, String> {
    @Query(value = "SELECT * FROM Lop WHERE trangThaiXoa = 'false'", nativeQuery = true)
    public List<Lop> getAllClass();

    @Query(value = "SELECT * FROM Lop WHERE maLop =:malop and trangThaiXoa = 'false'", nativeQuery = true)
    public Lop getClassById(@Param("malop") String malop);
    @Transactional
    @Modifying
    @Query(value = "UPDATE Lop SET trangThaiXoa = 1 WHERE maLop = :maLop", nativeQuery = true)
    public void deleteClass(@Param("maLop") String malop);

    @Query(value = "select * from lop where trangthaixoa = 0", nativeQuery = true)
    public List<Lop> findAllLop();

    public Lop findByMaLop(String maLop);
}
