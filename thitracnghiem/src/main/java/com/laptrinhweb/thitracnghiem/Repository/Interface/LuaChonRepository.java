package com.laptrinhweb.thitracnghiem.Repository.Interface;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.Entity.LuaChon;



@Repository
public interface LuaChonRepository extends JpaRepository<LuaChon,Integer>{
    // List<LuaChon> findAllByIdch(int IDCH);//Không dùng được nữa
}
