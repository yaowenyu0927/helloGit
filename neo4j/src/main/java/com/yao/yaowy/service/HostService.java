package com.yao.yaowy.service;

import com.yao.yaowy.dao.HostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HostService {

    @Autowired
    private HostRepository hostRepository;


}
