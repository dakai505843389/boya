package com.hd.clc.boya.service;

import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.HotClass;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IHotClassService {
    ResultDetial getHotClass();
}
