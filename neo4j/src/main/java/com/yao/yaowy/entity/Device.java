package com.yao.yaowy.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "DEVICE")
@Data
public class Device implements Serializable {

    private static final long serialVersionUID = 298316117797298250L;
    @Id
    @GeneratedValue
    private Long id;

    private long deviceId;

    private String deviceIp;

    private long tenantId;

    private String sysName;

}
