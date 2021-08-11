package com.yao.yaowy.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "DEVICE")
@Data
public class Device {

    @Id
    @GeneratedValue
    private Long id;

    private long deviceId;

    private String deviceIp;

    private long tenantId;

    private String sysName;

}
