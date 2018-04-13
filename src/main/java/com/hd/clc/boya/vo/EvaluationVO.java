package com.hd.clc.boya.vo;

import com.hd.clc.boya.db.entity.TeacherEvaluation;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class EvaluationVO {
    private Integer teacherId;//教师id
    private Double teacherScore;//教师评分
    private Integer scoreTimes;//评价次数
    private List<TeacherEvaluation> lastestEvalueationList;//最新的20条评价
}
