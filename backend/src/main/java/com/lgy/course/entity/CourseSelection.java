package com.lgy.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@TableName("course_selection")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSelection implements Serializable{
   
    @TableId(type = IdType.AUTO)
    private Integer id ;
   
    
    private Integer studentId ;
   
    
    private Integer courseId ;
   
    // 平时成绩
    @TableField(exist = false)
    private Double usualGrade ;

    // 签到成绩
    private Double signScore ;

    // 作业成绩
    private Double workScore ;

    // 实验成绩
    private Double expScore ;

    // 考试成绩
    private Double examGrade ;
   
    // 总评成绩
    @TableField(exist = false)
    private Double totalGrade ;
}