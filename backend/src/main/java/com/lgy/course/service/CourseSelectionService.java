package com.lgy.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.course.entity.CourseSelection;
import com.lgy.course.vo.CourseSelectionVO;

import java.util.List;

public interface CourseSelectionService extends IService<CourseSelection> {
    List<CourseSelectionVO> queryByStuId(int id);

    int deleteByUidAndCourseId(long userId, int courseId);

}

