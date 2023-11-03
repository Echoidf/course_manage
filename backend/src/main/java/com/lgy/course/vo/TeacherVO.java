package com.lgy.course.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherVO {
    private Integer id ;

    private String tno ;

    private String college ;

    private String username;

    // 0-男性 1-女性
    private Integer sex;

    private Integer age;

    private String avatar;
}
