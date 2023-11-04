package com.lgy.course.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.course.entity.CourseSelection;
import com.lgy.course.vo.CourseSelectionVO;
import com.lgy.course.vo.CourseSnoVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (CourseSelection)表数据库访问层
 *
 * @since 2023-11-01 11:48:22
 */
@Mapper
public interface CourseSelectionDao extends BaseMapper<CourseSelection> {

    List<CourseSelectionVO> queryByStuId(@Param("id") int id);

    int deleteByUidAndCourseId(@Param("userId")long userId, @Param("courseId")int courseId);

    List<CourseSnoVO> querySnoInDif(@Param("cId") int cId);
}

