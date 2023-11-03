package com.lgy.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lgy.course.dao.StudentDao;
import com.lgy.course.entity.Student;
import com.lgy.course.vo.StudentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public interface StudentService extends IService<Student> {
    List<StudentVO> findAll();

    List<StudentVO> getTeacherStudents(int tId, String cname);
}

