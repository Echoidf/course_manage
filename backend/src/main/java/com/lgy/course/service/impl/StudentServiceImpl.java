package com.lgy.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.course.dao.StudentDao;
import com.lgy.course.entity.Student;
import com.lgy.course.service.StudentService;
import com.lgy.course.vo.StudentVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("studentService")
public class StudentServiceImpl extends ServiceImpl<StudentDao, Student> implements StudentService {

    @Resource
    private StudentDao studentDao;


    public List<StudentVO> findAll() {
        return studentDao.list();
    }

    @Override
    public List<StudentVO> getTeacherStudents(int tId, String cname) {
        List<StudentVO> teacherStudents = studentDao.getTeacherStudents(tId, cname);

        for (StudentVO student : teacherStudents) {
            try{
                student.setUsualGrade(student.getSignScore() + student.getWorkScore() + student.getExpScore());
                student.setTotalGrade(student.getUsualGrade() + student.getExamGrade() * 0.7);
            }catch (NullPointerException e){
            }
        }
        return teacherStudents;
    }
}

