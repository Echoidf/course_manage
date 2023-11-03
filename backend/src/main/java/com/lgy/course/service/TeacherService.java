package com.lgy.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.course.entity.Teacher;
import com.lgy.course.vo.TeacherVO;

import java.util.List;

public interface TeacherService extends IService<Teacher> {

    List<TeacherVO> findAll();

}

