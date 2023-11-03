package com.lgy.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.course.dao.CourseSelectionDao;
import com.lgy.course.entity.CourseSelection;
import com.lgy.course.service.CourseSelectionService;
import com.lgy.course.vo.CourseSelectionVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (CourseSelection)表服务实现类
 *
 
 * @since 2023-11-01 11:45:40
 */
@Service("courseSelectionService")
public class CourseSelectionServiceImpl extends ServiceImpl<CourseSelectionDao, CourseSelection> implements CourseSelectionService {

    @Resource
    private CourseSelectionDao courseSelectionDao;

    @Override
    public List<CourseSelectionVO> queryByStuId(int id) {
        List<CourseSelectionVO> selectionVOS = courseSelectionDao.queryByStuId(id);
        try {
            for (CourseSelectionVO selectionVO : selectionVOS) {
                selectionVO.setUsualGrade(selectionVO.getSignScore() + selectionVO.getWorkScore() + selectionVO.getExpScore());
                selectionVO.setTotalGrade(selectionVO.getUsualGrade() + selectionVO.getExamGrade() * 0.7);
            }
        }catch (NullPointerException e){
            return selectionVOS;
        }
        return selectionVOS;
    }

    @Override
    public int deleteByUidAndCourseId(long userId, int courseId) {
        return courseSelectionDao.deleteByUidAndCourseId(userId, courseId);
    }

}

