package com.lgy.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

@TableName("course")
public class Course implements Serializable{
    
    
    @TableId(type = IdType.AUTO)
    private Integer id ;
    
    // 课号
    private String cno ;
    
    // 名称
    private String name ;
    
    // 开课学院
    private String college ;
    
    // 开放年级
    private String grade ;
    
    // 教师id
    private Integer teacherId ;

    
    public Integer getId(){
        return this.id;
    }
    
    public void setId(Integer id){
        this.id=id;
    }
    
    public String getCno(){
        return this.cno;
    }
    
    public void setCno(String cno){
        this.cno=cno;
    }
    
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getCollege(){
        return this.college;
    }
    
    public void setCollege(String college){
        this.college=college;
    }
    
    public String getGrade(){
        return this.grade;
    }
    
    public void setGrade(String grade){
        this.grade=grade;
    }
    
    public Integer getTeacherId(){
        return this.teacherId;
    }
    
    public void setTeacherId(Integer teacherId){
        this.teacherId=teacherId;
    }
}