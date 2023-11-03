package com.lgy.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * @date : 2023-11-1
 */
@TableName("student")
public class Student implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    // 学号
    private String sno;

    // 班级
    private String cls;

    // 学院
    private String college;


    private Long userId;

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSno() {
        return this.sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCls() {
        return this.cls;
    }

    public void setCls(String cls) {
        this.cls = cls;
    }

    public String getCollege() {
        return this.college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}