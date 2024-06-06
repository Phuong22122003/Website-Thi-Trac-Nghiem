package com.laptrinhweb.thitracnghiem.Repository.Interface;

import org.springframework.stereotype.Repository;

import com.laptrinhweb.thitracnghiem.DTO.InfoFileDTO;
import com.laptrinhweb.thitracnghiem.Entity.CauHoi;
import com.laptrinhweb.thitracnghiem.Entity.FileCauHoi;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface FileRepository extends JpaRepository<FileCauHoi, Integer> {
    @Query(value = "From FileCauHoi f where f.cauHoi.idch = :idch")
    public List<FileCauHoi> findAllByIDCH(@Param("idch") Integer idch);

    public List<FileCauHoi> findByCauHoi(CauHoi cauHoi);

    @Query(value = "exec getInfoFile @idch = :idch", nativeQuery = true)
    public List<InfoFileDTO> findInfoFile(@Param("idch") Integer idch);
}
