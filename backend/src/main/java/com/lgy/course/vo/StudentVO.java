package com.lgy.course.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentVO {
    // 学生id
    private int id;

    // 学号
    private String sno;

    // 班级
    private String cls;

    // 学院
    private String college;

    private String username;

    // 0-男性 1-女性
    private Integer sex;

    private Integer age;

    // 签到成绩
    private Double signScore;

    // 作业成绩
    private Double workScore;

    // 实验成绩
    private Double expScore;

    // 考试成绩
    private Double examGrade;

    // 平时成绩
    @TableField(exist = false)
    private Double usualGrade;

    // 总评成绩
    @TableField(exist = false)
    private Double totalGrade;
}
