package com.lgy.course.controller;



import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgy.course.dao.CourseDao;
import com.lgy.course.dto.StudentDTO;
import com.lgy.course.dto.TeacherDTO;
import com.lgy.course.entity.Course;
import com.lgy.course.entity.Student;
import com.lgy.course.entity.Teacher;
import com.lgy.course.entity.User;
import com.lgy.course.service.StudentService;
import com.lgy.course.service.TeacherService;
import com.lgy.course.service.UserService;
import com.lgy.course.vo.StudentVO;
import com.lgy.course.vo.TeacherVO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;


@RestController
@RequestMapping("/teacher")
public class TeacherController  {
    /**
     * 服务对象
     */
    @Resource
    private TeacherService teacherService;

    @Resource
    private UserService userService;

    @Resource
    private StudentService studentService;

    @Resource
    private CourseDao courseDao;


    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "20") Integer pageSize) {
        //查询前，需要调用 PageHelper.startPage()
        PageHelper.startPage(pageNum, pageSize);
        //在 PageHelper.startPage() 后调用 findAll 就是分页查询(物理分页有 limit)
        List<TeacherVO> teacherVOList = teacherService.findAll();
        //分页查询完之后，可以使用 pageInfo 来包装查询后的结果，
        PageInfo pageInfo = new PageInfo(teacherVOList, pageSize);
        return ResponseEntity.ok(pageInfo);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody TeacherDTO teacherDTO) {
        User user = new User();
        BeanUtils.copyProperties(teacherDTO, user);
        long id = IdUtil.getSnowflakeNextId();
        user.setId(id);
        user.setRole("teacher");
        userService.save(user);

        Teacher teacher = new Teacher();
        BeanUtils.copyProperties(teacherDTO, teacher);
        teacher.setUserId(id);
        teacherService.save(teacher);

        return ResponseEntity.ok().build();
    }


    @PostMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Teacher teacher = teacherService.query().getBaseMapper().selectById(id);
        if(null == teacher){
            return ResponseEntity.badRequest().build();
        }
        userService.removeById(teacher.getUserId());
        teacherService.removeById(id);
        return ResponseEntity.ok().build();
    }

    // 获取教师的学生信息
    @GetMapping("/students")
    public ResponseEntity getTeacherStus(@RequestParam int uId,
                                         @RequestParam String cname) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("user_id",uId);
        Teacher teacher = teacherService.getBaseMapper().selectOne(teacherQueryWrapper);
        return ResponseEntity.ok(studentService.getTeacherStudents(teacher.getId(), cname));
    }

    // 查询教师授课的课程列表
    @GetMapping("/course/{uId}")
    public ResponseEntity getTeacherCourseList(@PathVariable int uId) {
        QueryWrapper<Teacher> teacherQueryWrapper = new QueryWrapper<>();
        teacherQueryWrapper.eq("user_id", uId);
        Teacher teacher = teacherService.getBaseMapper().selectOne(teacherQueryWrapper);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", teacher.getId());
        List<Course> courses = courseDao.selectList(wrapper);
        return ResponseEntity.ok(courses);
    }

}

