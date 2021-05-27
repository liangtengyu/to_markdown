package com.liangtengyu.markdown.dao;

import com.liangtengyu.markdown.entity.PIC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PICDao extends JpaRepository<PIC, Integer>, JpaSpecificationExecutor<PIC> {

    @Query("select pic.PATH from PIC pic where pic.PNAME = ?1 ")
    List<String> findbyPname(String pname);
    @Query("select pic from PIC pic where pic.PNAME = ?1 ")
    List<PIC> findPicbyPname(String pname);
}