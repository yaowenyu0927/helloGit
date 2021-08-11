package com.yao.yaowy.service;

import com.yao.yaowy.dao.PortRepository;
import com.yao.yaowy.entity.Host;
import com.yao.yaowy.entity.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

@Service
public class PortService {

    @Autowired
    private PortRepository portRepository;

    void addPortAndPort(Port port1,Port port2){
        portRepository.addPortAndPort(port1,port2);
    }


    void addPortAndPHost(Port port,Host host){
        portRepository.addPortAndPHost(port,host);
    }

}
