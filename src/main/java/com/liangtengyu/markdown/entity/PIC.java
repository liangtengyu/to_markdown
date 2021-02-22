package com.liangtengyu.markdown.entity;

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
@Table(name = "PIC")
@Data
public class PIC implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer ID;

    @Column(name = "PNAME")
    private String PNAME;

    @Column(name = "PATH")
    private String PATH;

    @Column(name = "CREATE_TIME")
    private Date createTime;

}
