package com.lgy.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.course.entity.Course;
import com.lgy.course.vo.CourseVO;

import java.util.List;

/**
 * (Course)表服务接口
 *
 * @since 2023-11-01 11:44:03
 */
public interface CourseService extends IService<Course> {
    List<CourseVO> queryCourses(int stuId);
}

