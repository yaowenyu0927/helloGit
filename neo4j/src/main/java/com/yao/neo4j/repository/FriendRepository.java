package com.yao.neo4j.repository;


import com.yao.neo4j.entity.Friend;
import org.springframework.data.neo4j.repository.Neo4jRepository;

/**
 * @program: neo4j-demo
 * @description: 朋友关系
 * @create: 2020-05-27 11:28
 **/
public interface FriendRepository  extends Neo4jRepository<Friend, Long> {
}

