package com.hd.clc.boya.service;

import com.hd.clc.boya.common.ResultDetial;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;

@Service
public interface IClassTypeService {
    ResultDetial list();

}
