package com.lgy.course.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseSnoVO {
    private String grade;

    private String snos;

    @TableField(exist = false)
    private int[] snoList;
}
