package com.laptrinhweb.thitracnghiem.Repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.MonHoc;



@Repository
public interface MonHocRepository extends JpaRepository<MonHoc, String> {
    @Query(value = "select * from monhoc where trangthaixoa=0", nativeQuery = true)
    public List<MonHoc> findAllMonHoc();

    @Query(value = "exec searchMonHoc @keyword = :keyword", nativeQuery = true)
    public List<MonHoc> searchMonHoc(@Param("keyword") String keyword);

}
