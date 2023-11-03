package com.lgy.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.course.dao.TeacherDao;
import com.lgy.course.entity.Teacher;
import com.lgy.course.service.TeacherService;
import com.lgy.course.vo.TeacherVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Teacher)表服务实现类
 *
 
 * @since 2023-11-01 11:46:18
 */
@Service("teacherService")
public class TeacherServiceImpl extends ServiceImpl<TeacherDao, Teacher> implements TeacherService {

    @Resource
    private TeacherDao teacherDao;

    @Override
    public List<TeacherVO> findAll() {
        return teacherDao.list();
    }
}

