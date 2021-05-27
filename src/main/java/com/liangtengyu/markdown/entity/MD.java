package com.liangtengyu.markdown.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Data
@Table(name = "MD")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer","handler"})
public class MD implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer ID;

    @Column(name = "TITLE")
    private String TITLE;

    @Column(name = "CONTEXT")
    private String CONTEXT;


    @Column(name = "PNAME")
    private String PNAME;



    @Column(name = "SAVE_PATH")
    private String savePath;

    @Column(name = "BLOG_URL")
    private String blogUrl;



    @Column(name = "CREATE_TIME")
    private Date createTime;

}
