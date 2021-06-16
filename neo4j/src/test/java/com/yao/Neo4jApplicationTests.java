package com.yao;

import com.yao.neo4j.entity.Friend;
import com.yao.neo4j.entity.Movie;
import com.yao.neo4j.entity.Person;
import com.yao.neo4j.repository.FriendRepository;
import com.yao.neo4j.repository.MovieRepository;
import com.yao.neo4j.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
class Neo4jDemoApplicationTests {
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Test
    void contextLoads() {
    }




   @Test
    public void addMovie() {
        Movie m1 = new Movie("无问西东","2018");
        Movie m2 = new Movie("罗曼蒂克消亡史","2016");
        movieRepository.save(m1);
        movieRepository.save(m2);
    }




    /**
     * 添加节点
     */
    @Test
    public void addNodeTest(){
        Person person=new Person();
        person.setName("jamie");
        person.setAge(18);
        personRepository.save(person);
    }
    /**
     * 删除节点
     */
    @Test
    public void deleteNodeTest(){
        personRepository.deleteById(3L);
    }

    /**
     * 修改节点
     */
    @Test
    public void updateNodeTest(){
        Person person=new Person();
        person.setName("jamie");
        person.setAge(19);
        person.setId(3L);
        personRepository.save(person);
    }

    /**
     * 添加朋友关系
     */
    @Test
    public void addNodeRSTest(){
        Person person=new Person();
        person.setName("rose3");
        person.setAge(18);
        Person rose = personRepository.save(person);

        Person jamie = personRepository.findByName("jamie");

        Friend friend=new Friend(rose,jamie,"附近的人");

        friendRepository.save(friend);
    }

    /**
     * 查找某人所有朋友
     */
    @Test
    public void findFriendByPersonTest(){
        List<Person> friends = personRepository.findFriendByPerson("jamie");
        friends.forEach(System.out::println);
    }

}

