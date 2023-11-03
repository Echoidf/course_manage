package com.lgy.course.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.course.dao.CourseDao;
import com.lgy.course.dao.CourseSelectionDao;
import com.lgy.course.entity.Course;
import com.lgy.course.entity.CourseSelection;
import com.lgy.course.service.CourseService;
import com.lgy.course.vo.CourseSelectionVO;
import com.lgy.course.vo.CourseVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * (Course)表服务实现类
 *
 
 * @since 2023-11-01 11:44:06
 */
@Service("courseService")
public class CourseServiceImpl extends ServiceImpl<CourseDao, Course> implements CourseService {
    @Resource
    private CourseDao courseDao;

    @Resource
    private CourseSelectionDao courseSelectionDao;

    @Override
    public List<CourseVO> queryCourses(int stuId) {
        // 包括已选择的和未选择的课程
        List<CourseVO> courseVOS = courseDao.queryCoursesInfo();

        if(stuId != 0){
// 已选择的课程
            List<CourseSelectionVO> selectionVOS = courseSelectionDao.queryByStuId(stuId);

            List<String> list = new ArrayList<>();
            for (CourseSelectionVO selectionVO : selectionVOS) {
                list.add(selectionVO.getCno());
            }

            for (CourseVO courseVO : courseVOS) {
                if(list.contains(courseVO.getCno())){
                    courseVO.setSelected(true);
                }
            }
        }

        for (CourseVO courseVO : courseVOS) {
            QueryWrapper<CourseSelection> wrapper = new QueryWrapper<>();
            wrapper.eq("course_id", courseVO.getId());
            Long count = courseSelectionDao.selectCount(wrapper);
            courseVO.setSelectedCount(Math.toIntExact(count));
        }

        return courseVOS;
    }
}

