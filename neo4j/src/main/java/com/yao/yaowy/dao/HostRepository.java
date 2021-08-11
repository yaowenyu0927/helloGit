package com.yao.yaowy.dao;

import com.yao.yaowy.entity.Host;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface HostRepository extends Neo4jRepository<Host,Long> {

    @Query("create (h:HOST{hostIp:$host.hostIp,hostMac:$host.hostMac})")
    void addHost(@Param("host") Host host);


    @Query("match (h:HOST) <-[r:LINK]- (p:PORT)" +
            "WHERE h.hostId = $hostId" +
            "delete h,r,p")
    void deleteByHostId(@Param("hostId") long hostId);

}
