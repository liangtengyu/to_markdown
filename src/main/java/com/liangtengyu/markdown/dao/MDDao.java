package com.liangtengyu.markdown.dao;

import com.liangtengyu.markdown.entity.MD;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MDDao extends JpaRepository<MD, Integer>, JpaSpecificationExecutor<MD> {

}