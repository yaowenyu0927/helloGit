package com.yao.yaowy.service;


import com.yao.yaowy.dao.DeviceRepository;
import com.yao.yaowy.entity.Device;
import com.yao.yaowy.entity.Port;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public void addDevice(Device device){
        deviceRepository.addDevice(device);
    }


    public void mergeRelationshipPort(long deviceId,long  portId){
        deviceRepository.mergeRelationshipPort(deviceId, portId);
    }

    public void addDeviceAndPort(Device device, Port port){
        deviceRepository.addDeviceAndPort(device,port);
    }

    public void deleteByDeviceId(long deviceId){
        deviceRepository.deleteByDeviceId(deviceId);
    }

    public Device update(@Param("device") Device device){
        return deviceRepository.update(device);
    }

    public Device mergeDevice(Device device){
        return deviceRepository.mergeDevice(device);
    }

    /**
     * 查询1
     * @return
     */
    public List<Map<String ,Object>> queryDevice(){
        return deviceRepository.queryDevice();
    }
    /**
     * 查询2
     */
    public List<Map<String ,Object>> queryPort(){
        return deviceRepository.queryPort();
    }

    /**
     * 查询3
     */
    public List<Map<String ,Object>> queryPort2(@Param("devices")List<Long> devices){
        return deviceRepository.queryPort2(devices);
    }

}
