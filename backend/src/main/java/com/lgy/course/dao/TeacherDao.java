package com.lgy.course.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.course.entity.Teacher;
import com.lgy.course.vo.TeacherVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherDao extends BaseMapper<Teacher> {

    List<TeacherVO> list();
}

