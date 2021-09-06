package com.yao.yaowy.dao;

import com.yao.yaowy.entity.Host;
import com.yao.yaowy.entity.Port;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface PortRepository extends Neo4jRepository<Port,Long> {

    /**
     * 创建单个节点
     * @param port
     */
    @Query("CREATE (p:PORT{portId:$port.portId, deviceId:$port.deviceId, ifIndex:$port.ifIndex, ifName:$port.ifName, tenantId:$port.tenantId})")
    void addPort(@Param("port") Port port);


    @Query("MATCH (p1:PORT),(p2:PORT) " +
            "WHERE p1.portId=$portId1 and p2.portId=$portId2 " +
            "merge (p1)-[:LINK]->(p2) "
            )
    void mergePortRelationship(@Param("portId1") long portId1, @Param("portId2") long portId2);

    @Query("MATCH (p:PORT),(h:HOST) " +
            "WHERE p.portId=$portId and h.hostIp=$hostIp " +
            "merge (p)-[:LINK]->(h) "
    )
    void mergeHostRelationship(@Param("portId") long portId, @Param("hostIp") String hostIp);


    @Query("CREATE (p1:PORT{portId:$port1.portId,deviceId:$port1.deviceId,ifIndex:$port1.ifIndex,ifName:$port1.ifName,tenantId:$port1.tenantId})" +
            "- [:LINK] ->" +
            "(p2:PORT{portId:$port2.portId,deviceId:$port2.deviceId,ifIndex:$port2.ifIndex,ifName:$port2.ifName,tenantId:$port2.tenantId})")
    void addPortAndPort(@Param("port1") Port port1, @Param("port2") Port port2);

    @Query("CREATE (p:PORT{portId:$port.portId,deviceId:$port.deviceId,ifIndex:$port.ifIndex,ifName:$port.ifName,tenantId:$port.tenantId})" +
            "- [:LINK] ->" +
            "(h:HOST{hostIp:$host.hostIp,hostMac:$host.hostMac})")
    void addPortAndPHost(@Param("port") Port port,@Param("host") Host host);
}
