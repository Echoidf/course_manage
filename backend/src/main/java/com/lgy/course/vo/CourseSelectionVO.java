package com.lgy.course.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description: 选课信息
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSelectionVO {
    private int courseId;
    // 课号
    private String cno;

    // 名称
    private String name;

    // 开课学院
    private String college;

    // 开放年级
    private String grade;

    // 教师id
    private String tno;

    // 教师名称
    private String tname;

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
