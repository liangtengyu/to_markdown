package com.liangtengyu.markdown.dao;

import com.liangtengyu.markdown.entity.SETTING;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SETTINGDao extends JpaRepository<SETTING, Integer>, JpaSpecificationExecutor<SETTING> {


    @Query("select st from SETTING st where CONFIG_NAME = ?1")
    SETTING findbyname(String key);
}