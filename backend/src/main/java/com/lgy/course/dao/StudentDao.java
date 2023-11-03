package com.lgy.course.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.course.entity.Student;
import com.lgy.course.vo.StudentVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StudentDao extends BaseMapper<Student> {
   List<StudentVO> list();

   List<StudentVO> getTeacherStudents(@Param("tId") int tId, @Param("courseName") String cname);
}

