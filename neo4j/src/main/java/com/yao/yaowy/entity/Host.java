package com.yao.yaowy.entity;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.io.Serializable;

@NodeEntity(label = "HOST")
@Data
public class Host implements Serializable {

    private static final long serialVersionUID = -6845884997861474200L;
    @Id
    @GeneratedValue
    private Long id;

    private String hostIp;

    private String hostMac;

}
