package com.lgy.course.vo;

import lombok.Data;

/**
 * @description: 成绩分布
 **/

@Data
public class GradeDistribution {
    // 优秀人数
    private int excellent;

    // 良好人数
    private int good;

    // 中等人数
    private int medium;

    // 及格人数
    private int pass;

    // 不及格人数
    private int fail;
}
