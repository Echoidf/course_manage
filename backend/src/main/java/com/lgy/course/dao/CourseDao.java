package com.lgy.course.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.course.entity.Course;
import com.lgy.course.vo.CourseVO;
import com.lgy.course.vo.GradeDistribution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Course)表数据库访问层
 *
 * @since 2023-11-01 11:47:57
 */
@Mapper
public interface CourseDao extends BaseMapper<Course> {
     List<CourseVO> queryCoursesInfo();

     GradeDistribution getCourseScoreDistrubtion(@Param("id") int id);
}

