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
@Table(name = "SETTING")
@Data
public class SETTING implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)

    private Integer ID;

    @Column(name = "CONFIG_NAME")
    private String configName;

    @Column(name = "CONFIG_VALUE")
    private String configValue;

    @Column(name = "CREATE_TIME")
    private Date createTime;

}
