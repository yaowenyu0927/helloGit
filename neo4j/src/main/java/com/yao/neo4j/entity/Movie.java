package com.yao.neo4j.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label = "movie")
@Data
@AllArgsConstructor
public class Movie {

    @Id
    @GeneratedValue
    private Long nodeId;

    private String title;

    private String released;


    public Movie(){
    }

    public Movie(String title, String released){
        this.title = title;
        this.released = released;
    }
}
