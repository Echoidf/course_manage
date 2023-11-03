package com.lgy.course.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO {
    private Integer id ;
    // 课号
    private String cno ;

    // 名称
    private String name ;

    // 开课学院
    private String college ;

    // 开放年级
    private String grade ;

    // 教师
    private String tname ;

    // 已选人数
    private int selectedCount;

    // 是否已选择
    private boolean isSelected;
}
