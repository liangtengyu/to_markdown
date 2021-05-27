package com.liangtengyu.markdown.dao;

import com.liangtengyu.markdown.entity.MD;
import com.liangtengyu.markdown.entity.SETTING;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MDDao extends JpaRepository<MD, Integer>, JpaSpecificationExecutor<MD> {

    @Query("select md from MD md where md.ID  Between ?1  and  ?1+10 ")
    List<MD> findbyid(Integer key);

    @Transactional
    @Modifying
    @Query(value = "update   MD  set CONTEXT = ?2 WHERE ID = ?1 ", nativeQuery = true)
    void update(Integer id, String context);
}