package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import org.springframework.stereotype.Service;

@Service
public interface IClassRoomService {
    ResultDetial list();
}
