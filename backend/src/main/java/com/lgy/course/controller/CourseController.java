package com.lgy.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lgy.course.dao.CourseDao;
import com.lgy.course.dao.StudentDao;
import com.lgy.course.entity.Course;
import com.lgy.course.entity.Student;
import com.lgy.course.service.CourseService;
import com.lgy.course.util.JwtUtil;
import com.lgy.course.vo.CourseVO;
import com.lgy.course.vo.GradeDistribution;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController  {
    private static Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Resource
    private CourseService courseService;

    @Resource
    private CourseDao courseDao;

    @Resource
    private StudentDao studentDao;

    // 查询教师授课的课程列表
    @GetMapping("/teacher/{id}")
    public ResponseEntity getTeacherCourseList(@PathVariable String id) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<Course> courses = courseDao.selectList(wrapper);
        return ResponseEntity.ok(courses);
    }

    //学生查询可选课程列表
    @GetMapping("/stu/list")
    public ResponseEntity queryCourses(HttpServletRequest request){
        long userId = 0;
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            JwtUtil jwtUtil = new JwtUtil();
            userId = (long) jwtUtil.decode(token).get("userId");
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.ok().build();
        }

        // 查询对应的学生
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Student student = studentDao.selectOne(wrapper);

        if(null == student){
            return ResponseEntity.ok().build();
        }

        // 查询课程信息
        List<CourseVO> courseVOS = courseService.queryCourses(student.getId());
        return ResponseEntity.ok(courseVOS);

    }

    // 管理员查看所有课程信息
    @GetMapping("/list")
    public ResponseEntity list() {
        List<CourseVO> courseVOS = courseService.queryCourses(0);
        return ResponseEntity.ok(courseVOS);
    }

    // 新增课程
    @PostMapping("/add")
    public ResponseEntity add(@RequestBody Course course) {
        boolean saved = courseService.save(course);
        if(!saved) {
            return ResponseEntity.ok("affected:0");
        }
        return ResponseEntity.ok().build();
    }

    //更新课程
    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Course course) {
        UpdateWrapper<Course> courseUpdateWrapper = new UpdateWrapper<>();
        courseUpdateWrapper.eq("id", course.getId());
        boolean update = courseService.update(course, courseUpdateWrapper);
        if(!update) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        int affected = courseService.getBaseMapper().deleteById(id);
        return ResponseEntity.ok("affected:" + affected);
    }


    //获得课程分数分布
    // id--课程id
    @GetMapping("/score/{id}")
    public ResponseEntity getCourseScore(@PathVariable int id) {
        GradeDistribution courseScoreDistrubtion = courseDao.getCourseScoreDistrubtion(id);
        return ResponseEntity.ok(courseScoreDistrubtion);
    }
}

