package com.lgy.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentDTO {
    // 姓名
    private String username;

    // 0-男性 1-女性
    private Integer sex;

    private Integer age;

    private String password;

    // 学号
    private String sno;

    // 班级
    private String cls;

    // 学院
    private String college;
}
