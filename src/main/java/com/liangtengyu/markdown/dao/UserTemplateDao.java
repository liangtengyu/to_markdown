package com.liangtengyu.markdown.dao;

import com.liangtengyu.markdown.entity.UserTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface UserTemplateDao extends JpaRepository<UserTemplate, Integer>, JpaSpecificationExecutor<UserTemplate> {



}