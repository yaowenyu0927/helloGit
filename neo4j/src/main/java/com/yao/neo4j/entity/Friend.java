package com.yao.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.*;

import java.util.Date;

@RelationshipEntity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
    @Id
    @GeneratedValue
    private Long id;
    private Date createTime;
    private String remark;

    @StartNode
    private Person personFrom;

    @EndNode
    private Person personTo;

    public Friend(Person personFrom, Person personTo, String remark) {
        this.personFrom = personFrom;
        this.personTo = personTo;
        this.remark = remark;
        this.createTime = new Date();
    }
}

