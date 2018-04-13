package com.hd.clc.boya.service;

import com.hd.clc.boya.common.Result;
import org.springframework.stereotype.Service;

@Service
public interface ITeacherEvaluationService {
    Result evaluate(int userId, int mapId, String evalueationWord, int scoreOne, int scoreTwo, int scoreThree);
    Result queryByTeacherId(int teacherId);
}
