package com.yao.yaowy.controller;

import com.yao.yaowy.dao.HostRepository;
import com.yao.yaowy.dao.PortRepository;
import com.yao.yaowy.entity.Device;
import com.yao.yaowy.entity.Host;
import com.yao.yaowy.entity.Lldp;
import com.yao.yaowy.entity.Port;
import com.yao.yaowy.service.DeviceService;
import org.omg.PortableInterceptor.ObjectReferenceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private PortRepository portRepository;

    @Autowired
    private HostRepository hostRepository;

    @RequestMapping("/device/add")
    public String add(){
//        for (long i = 1000; i <= 1010; i++) {
//            Device device = new Device();
//            device.setDeviceId(i);
//            device.setDeviceIp("192.168.2."+i);
//            device.setTenantId(i);
//            deviceService.addDevice(device);
//        }

//        deviceService.mergeRelationshipPort(1002,2004);
//        deviceService.mergeRelationshipPort(1002,2005);
        deviceService.mergeRelationshipPort(1003,2006);
        deviceService.mergeRelationshipPort(1003,2007);

/*        deviceService.mergeRelationshipPort(1,3);
        deviceService.mergeRelationshipPort(2,2);
        deviceService.mergeRelationshipPort(2,1);
        deviceService.mergeRelationshipPort(2,2);*/
//        deviceService.mergeRelationshipPort(10,11);

//        Device device = new Device();
//        device.setDeviceId(10);
//        device.setDeviceIp("1.1.10.10");
//        device.setSysName("bbbbb");
//        deviceService.update(device);

//        deviceService.mergeRelationshipPort(4,1);


        return "success";
    }

    @RequestMapping("/port/add")
    public String add2(){
//        for (long i = 2006; i <= 2007; i++) {
//            Port port = new Port();
//            port.setPortId(i);
//            port.setTenantId(i);
//            port.setDeviceId(1003);
//            port.setIfIndex("index"+i);
//            port.setIfName("name"+i);
//            portRepository.addPort(port);
//        }
        portRepository.mergePortRelationship(2001,2004);
        portRepository.mergePortRelationship(2001,2005);
        portRepository.mergePortRelationship(2002,2006);
        portRepository.mergePortRelationship(2007,2003);
        portRepository.mergePortRelationship(2000,2006);
        return "success";
    }

    @RequestMapping("/port/host")
    public String add3(){
        portRepository.mergeHostRelationship(1,"1.1.1.1");
        portRepository.mergeHostRelationship(2,"1.1.1.2");
        portRepository.mergeHostRelationship(3,"1.1.1.3");
        return "success";
    }

    @RequestMapping("/host/add")
    public String addHost(){
        for (long i = 1; i <= 5 ; i++) {
            Host host = new Host();
            host.setHostIp("1.1.1."+i);
            host.setHostMac("1.1.1."+i);
            hostRepository.addHost(host);
        }
        return "success";
    }


    @RequestMapping("/port/deleted")
    public String delete(){
        hostRepository.deleteByHostId(1l);
        return "success";
    }

    @RequestMapping("/device/update")
    public String update(){
        Device device = new Device();
        device.setDeviceId(10L);
        device.setDeviceIp("2.4.4.4");
        device.setTenantId(5L);
        device.setSysName("huihn");
        deviceService.mergeDevice(device);
        return "success";
    }

    @RequestMapping("/device/select")
    public String queryDevice(){
        List<Map<String, Object>> maps = deviceService.queryDevice();
        maps.forEach(System.out::println);
        return "success";
    }

    @RequestMapping("/port/select")
    public List<Map<String,Object>> queryPort(){
        List<Map<String, Object>> maps = deviceService.queryPort();
        maps.forEach(System.out::println);
        return maps;
    }


    @RequestMapping("/device/select2")
    public List<Map<String, Object>> queryPort2(){
        Device device1 = new Device();
        device1.setDeviceId(1);
        Device device2 = new Device();
        device1.setDeviceId(2);

        List<Long> list = new ArrayList<>();
        list.add(1000L);
        list.add(1001L);
        list.add(1002L);

        List<Map<String, Object>> maps = deviceService.queryPort2(list);

        List<Collection<Object>> collect = maps.stream().map(Map::values).limit(1).collect(Collectors.toList());
        maps.forEach(item -> {
            Port remPort =  (Port) item.get("对端端口");
            Lldp lldp = new Lldp();





        });

//        for (Map<String, Object> map : maps) {
//            for (Map.Entry<String, Object> entry : map.entrySet()) {
//                if (entry.getKey() .equals("对端端口")){
//                   Port port =  (Port) entry.getValue();
//                    System.out.println(port);
//                }
//                if (entry.getKey() .equals("对端设备")){
//                    Device device4 =  (Device) entry.getValue();
//                    System.out.println(device4);
//                }
//                if (entry.getKey() .equals("本段端口")){
//                    Port port2 =  (Port) entry.getValue();
//                    System.out.println(port2);
//                }
//                if (entry.getKey() .equals("本端设备")){
//                    Device device3 =  (Device) entry.getValue();
//                    System.out.println(device3);
//                }
//
//            }
//        }





        //maps.forEach(System.out::println);

        return maps;


    }


}
