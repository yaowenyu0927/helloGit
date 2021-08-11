//package com.yao.neo4j.service;
//
//import com.yao.neo4j.entity.UserNode;
//import com.yao.neo4j.repository.UserRelationRepository;
//import com.yao.neo4j.repository.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    @Autowired
//    private UserRepository userRepository;
//
////    @Autowired
////    private UserRelationRepository userRelationRepository;
//
//    public void addUserNode(UserNode userNode) {
//        userRepository.addUserNodeList(userNode.getUserId(),userNode.getName(), userNode.getAge());
//    }
//
////    public void addRelationship(String firstId,String secondId){
////        userRelationRepository.addUserRelation(firstId,secondId);
////    }
////
////    public void findUserRelationByEachId(String firstId,String secondId){
////        userRelationRepository.findUserRelationByEachId(firstId, secondId);
////    }
//}