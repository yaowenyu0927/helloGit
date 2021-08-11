package com.yao.yaowy.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "HOST")
@Data
public class Host {

    @Id
    @GeneratedValue
    private Long id;

    private String hostIp;

    private String hostMac;

}
