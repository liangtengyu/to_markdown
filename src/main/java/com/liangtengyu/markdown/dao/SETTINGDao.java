package com.liangtengyu.markdown.dao;

import com.liangtengyu.markdown.entity.SETTING;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SETTINGDao extends JpaRepository<SETTING, Integer>, JpaSpecificationExecutor<SETTING> {

    @Query("select st from SETTING st where st.configName = ?1")
    SETTING findbyname(String key);

    @Transactional
    @Modifying
    @Query("update  SETTING  st set st.configValue = ?2  where  st.configName = ?1")
    int updateByName(String key, String s);
}