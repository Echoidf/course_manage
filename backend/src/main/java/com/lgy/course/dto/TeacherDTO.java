package com.lgy.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherDTO {
    // 姓名
    private String username;

    // 0-男性 1-女性
    private Integer sex;

    private Integer age;

    private String password;

    // 工号
    private String tno;

    // 学院
    private String college;
}
