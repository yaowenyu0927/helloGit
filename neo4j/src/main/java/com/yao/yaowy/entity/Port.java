package com.yao.yaowy.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "PORT")
@Data
public class Port implements Serializable {

    private static final long serialVersionUID = 4555635926444770103L;
    @Id
    @GeneratedValue
    private Long id;

    private long portId;
    private long deviceId;
    private String ifIndex;
    private String ifName;
    private long tenantId;

}
