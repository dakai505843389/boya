package com.hd.clc.boya.service.impl;

import com.hd.clc.boya.common.Result;
import com.hd.clc.boya.common.ResultDetial;
import com.hd.clc.boya.db.entity.Teacher;
import com.hd.clc.boya.db.entity.TeacherEvaluation;
import com.hd.clc.boya.db.entity.User;
import com.hd.clc.boya.db.entity.UserSelectionClassMap;
import com.hd.clc.boya.db.impl.TeacherEvaluationMapper;
import com.hd.clc.boya.db.impl.TeacherMapper;
import com.hd.clc.boya.db.impl.UserMapper;
import com.hd.clc.boya.db.impl.UserSelectionClassMapMapper;
import com.hd.clc.boya.service.ITeacherEvaluationService;
import com.hd.clc.boya.vo.EvaluationVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TeacherEvaluationServiceImpl implements ITeacherEvaluationService {

    @Autowired
    private TeacherEvaluationMapper teacherEvaluationMapper;

    @Autowired
    private UserSelectionClassMapMapper userSelectionClassMapMapper;

    @Autowired
    private TeacherMapper teacherMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result evaluate(int userId, int mapId, String evalueationWord, int scoreOne, int scoreTwo, int scoreThree) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        UserSelectionClassMap userSelectionClassMap = userSelectionClassMapMapper.queryById(mapId);
        if (userId == userSelectionClassMap.getUserId()){
            if (userSelectionClassMap.getStatus() == 1){
                if (userSelectionClassMap.getIsEvalueted() == 0) {
                    User user = userMapper.queryById(userId);
                    TeacherEvaluation teacherEvaluation = new TeacherEvaluation();
                    teacherEvaluation.setUserName(user.getUserName());
                    teacherEvaluation.setMapId(userSelectionClassMap.getId());
                    teacherEvaluation.setBelongClassId(userSelectionClassMap.getClassId());
                    teacherEvaluation.setBelongTeacherId(userSelectionClassMap.getTeacherId());
                    if (evalueationWord != null) {
                        teacherEvaluation.setEvalueationWord(evalueationWord);
                    }
                    teacherEvaluation.setEvalueationScoreForFirst(scoreOne);
                    teacherEvaluation.setEvalueationScoreForSecond(scoreTwo);
                    teacherEvaluation.setEvalueationScoreForThird(scoreThree);
                    teacherEvaluation.setAddTime(new Date(System.currentTimeMillis()));
                    teacherEvaluationMapper.add(teacherEvaluation);
                    double avgScore = (scoreOne + scoreTwo + scoreThree) / 3;
                    Teacher teacher = teacherMapper.queryById(teacherEvaluation.getBelongTeacherId());
                    int times = teacher.getScoreTimes();
                    double oldScore = teacher.getScore();
                    avgScore = (times * oldScore + avgScore) / (times + 1);
                    teacher.setScore(avgScore);
                    teacher.setScoreTimes(times + 1);
                    teacherMapper.updateScore(teacher);
                    userSelectionClassMapMapper.evaluted(mapId);
                    msg = "评价成功！";
                    data.put("teacher", teacher);
                    data.put("teacherEvaluation", teacherEvaluation);
                }else{
                    msg = "该用户已经评价过！";
                }
            }else {
                msg = "该用户未上课或已退课！";
            }
        }else {
            msg = "该用户与此选课不对应！";
        }

        return new ResultDetial<>(msg, data);
    }

    @Override
    public Result queryByTeacherId(int teacherId) {
        Map<String, Object> data = new HashMap<>();
        String msg;
        Teacher teacher = teacherMapper.queryById(teacherId);
        if (teacher != null) {
            List<TeacherEvaluation> teacherEvaluationList = teacherEvaluationMapper.queryByTeacherId(teacherId);
            if (teacherEvaluationList != null && teacherEvaluationList.size() > 0){
                EvaluationVO evaluationVO = new EvaluationVO();
                evaluationVO.setTeacherId(teacherId);
                evaluationVO.setTeacherScore(teacher.getScore());
                evaluationVO.setScoreTimes(teacher.getScoreTimes());
                evaluationVO.setLastestEvalueationList(teacherEvaluationList);
                data.put("evaluation", evaluationVO);
                msg = "查询成功！";
            }else {
                msg = "该教师暂无人评价！";
            }
        }else {
            msg = "不存在该教师！";
        }
        return new ResultDetial<>(msg, data);
    }
}
