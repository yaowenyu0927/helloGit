package com.yao.neo4j.repository;


import com.yao.neo4j.entity.Person;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.List;

public interface PersonRepository extends Neo4jRepository<Person, Long> {
    /**
     * 返回某个人的所有朋友
     * @param name
     * @return
     */
    @Query("MATCH p =(n:Person)-[r:FRIEND]->(m:Person) WHERE m.name=$name RETURN n")
    List<Person> findFriendByPerson(String name);
    /**
     * 根据名字查找
     * @param name
     * @return
     */
    Person findByName(String name);
}

