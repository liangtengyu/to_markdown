package com.liangtengyu.markdown.dao;

import com.liangtengyu.markdown.entity.PIC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PICDao extends JpaRepository<PIC, Integer>, JpaSpecificationExecutor<PIC> {

}