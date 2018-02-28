package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import org.springframework.stereotype.Service;

@Service
public interface IGroupRoomService {
    Result scanCodeForGroup(Integer userId, String sceneStr);
}
