package com.yao.yaowy.dao;


import com.yao.yaowy.entity.Device;
import com.yao.yaowy.entity.Port;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface DeviceRepository extends Neo4jRepository<Device,Long> {

    /**
     * 创建单个节点
     * @param device
     */
    @Query("CREATE (d:DEVICE{deviceId:$device.deviceId,deviceIp:$device.deviceIp,tenantId:$device.tenantId})")
    void addDevice(@Param("device") Device device);

    //新增顶点，属性，关系
    @Query("CREATE (d:DEVICE{deviceId:$device.deviceId,deviceIp:$device.deviceIp,tenantId:$device.tenantId})" +
            "- [:INTERFACE] -> " +
            "(p:PORT{portId:$port.portId,ifIndex:$port.ifIndex,ifName:$port.ifName,tenantId:$port.tenantId})")
    void addDeviceAndPort(@Param("device") Device device, @Param("port") Port port);

    /**
     * 根据deviceId，portId建立关系
     */
    @Query("MATCH (d:DEVICE),(p:PORT) " +
            "where d.deviceId=$deviceId and p.portId=$portId " +
            "merge (d)-[:INTERFACE]->(p)")
    void mergeRelationshipPort(@Param("deviceId") long deviceId,@Param("portId") long  portId);

    /**
     * 根据 deviceId 删除节点，属性，以及关系信息
     */
    @Query("MATCH (d:DEVICE) -[r:INTERFACE]->(p)" +
            "WHERE d.deviceId = $deviceId " +
            "DELETE d,r,p")
    void deleteByDeviceId(@Param("deviceId") long deviceId);

    /**
     * 更新
     */
    @Query("match (d:DEVICE) " +
            "WHERE d.deviceId=$device.deviceId " +
            "SET d.deviceIp=$device.deviceIp,d.tenantId=$device.tenantId")
    Device update(@Param("device") Device device);

    /**
     * merge 更新
     */
    @Query("merge (d:DEVICE{deviceId:$device.deviceId,deviceIp:$device.deviceIp,tenantId:$device.tenantId}) " +
            "ON CREATE SET d.deviceId=$device.deviceId,d.deviceIp=$device.deviceIp,d.tenantId=$device.tenantId,d.sysName=$device.sysName " +
            "ON MATCH SET d.deviceId=$device.deviceId,d.deviceIp=$device.deviceIp,d.tenantId=$device.tenantId,d.sysName=$device.sysName")
    Device mergeDevice(@Param("device") Device device);

    /**
     * 查询1
     */
//    @Query("match (d:DEVICE)-[r:INTERFACE]-(p:PORT) " +
//            "match(p)-[:LINK]->(h:HOST) " +
//            "RETURN d,h")

    @Query("match (d:DEVICE{deviceId:1})-[:INTERFACE]->(p1)-[:LINK*1 ..]->(h1:HOST)" +
            "match (d:DEVICE{deviceId:1})-[:INTERFACE]->(p2)<-[:INTERFACE]->(d2:DEVICE)" +
            "return d2,h1,p2 ")
    List<Map<String ,Object>> queryDevice();

    /**
     * 查询2
     */
    @Query("match (d:DEVICE)-[:INTERFACE]->(p:PORT)-[r:LINK* 2..]-> (m) return p,r,m")
    List<Map<String ,Object>> queryPort();


    /**
     * 查询3
     */
    @Query("unwind $devices as device " +
            "match (d:DEVICE)-[:INTERFACE]->(p:PORT)-[:LINK]->(p2:PORT)<-[:INTERFACE]-(d2:DEVICE) " +
            "where d.deviceId=device " +
            "return d,p,p2,d2")
    List<Map<String ,Object>> queryPort2(@Param("devices")List<Long> devices);


}
