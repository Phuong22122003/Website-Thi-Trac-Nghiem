package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.DTO.LuaChonDTO;
import com.laptrinhweb.thitracnghiem.Entity.LuaChon;

@Repository
public interface LuaChonRepository extends JpaRepository<LuaChon, Integer> {
    // List<LuaChon> findAllByIdch(int IDCH);//Không dùng được nữa TAI SAO?
    @Query(value = "EXEC findLuaChonByIdch :idch", nativeQuery = true)
    List<LuaChonDTO> findLuaChonByIdch(@Param("idch") int idch);

    LuaChon findByIdlc(int idlc);
}
