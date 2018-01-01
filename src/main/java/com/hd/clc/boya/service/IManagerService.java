package com.hd.clc.boya.service;

import com.hd.clc.boya.common.ResultDetial;
import org.springframework.stereotype.Service;

@Service
public interface IManagerService {
    ResultDetial register(String account,String password)throws Exception;
    ResultDetial login(String account,String password);
}
