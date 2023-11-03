package com.lgy.course.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@TableName("teacher")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Teacher implements Serializable{

    @TableId(type = IdType.AUTO)
    private Integer id ;
    
    
    private String tno ;
    
    
    private String college ;
    
    
    private Long userId ;
}