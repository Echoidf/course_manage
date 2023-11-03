package com.lgy.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lgy.course.dao.StudentDao;
import com.lgy.course.entity.CourseSelection;
import com.lgy.course.entity.Student;
import com.lgy.course.service.CourseSelectionService;
import com.lgy.course.util.JwtUtil;
import com.lgy.course.vo.CourseSelectionVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/courseSelection")
public class CourseSelectionController {

    Logger logger = LoggerFactory.getLogger(CourseSelectionController.class);

    @Resource
    private CourseSelectionService courseSelectionService;

    @Resource
    private StudentDao studentDao;

    // 查询学生选课信息，id为学生的id
    @GetMapping("/list/{id}")
    public ResponseEntity queryStudentSelections(@PathVariable int id, HttpServletRequest request) {
        if(id == 0){
            long userId = 0;
            try {
                String token = request.getHeader("Authorization").split(" ")[1];
                JwtUtil jwtUtil = new JwtUtil();
                userId = (long) jwtUtil.decode(token).get("userId");
            }catch (Exception e) {
                logger.error(e.getMessage());
                return ResponseEntity.badRequest().build();
            }

            // 查询对应的学生
            QueryWrapper<Student> wrapper = new QueryWrapper<>();
            wrapper.eq("user_id", userId);
            Student student = studentDao.selectOne(wrapper);

            if(null == student){
                return ResponseEntity.ok().build();
            }
            id = student.getId();
        }

        List<CourseSelectionVO> selectionVOS = courseSelectionService.queryByStuId(id);
        return ResponseEntity.ok(selectionVOS);
    }

    //选课/退选
    @PostMapping("/selectOrCancelCourse/{id}")
    public ResponseEntity add(@PathVariable int id, HttpServletRequest request){

        long userId = 0;
        try {
            String token = request.getHeader("Authorization").split(" ")[1];
            JwtUtil jwtUtil = new JwtUtil();
            userId = (long) jwtUtil.decode(token).get("userId");
        }catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }

        if(request.getParameter("action").equals("cancel")){
            int affected = courseSelectionService.deleteByUidAndCourseId(userId, id);
            return ResponseEntity.ok("affected: " + affected);
        }

        // 查询对应的学生
        QueryWrapper<Student> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        Student student = studentDao.selectOne(wrapper);

        if(null == student){
            return ResponseEntity.badRequest().build();
        }

        CourseSelection courseSelection = new CourseSelection();
        courseSelection.setCourseId(id);
        courseSelection.setStudentId(student.getId());
        courseSelectionService.save(courseSelection);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/update/{sno}")
    public ResponseEntity updateStuScore(@RequestBody CourseSelection courseSelection, @PathVariable String sno) {
        QueryWrapper<Student> studentQueryWrapper = new QueryWrapper<>();
        studentQueryWrapper.eq("sno", sno);
        Student student = studentDao.selectOne(studentQueryWrapper);

        QueryWrapper<CourseSelection> wrapper = new QueryWrapper<>();
        wrapper.eq("student_id", student.getId());
        wrapper.eq("course_id", courseSelection.getCourseId());
        CourseSelection selection = courseSelectionService.getBaseMapper().selectOne(wrapper);

        courseSelection.setId(selection.getId());
        courseSelection.setStudentId(student.getId());
        UpdateWrapper<CourseSelection> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", selection.getId());
        boolean saved = courseSelectionService.update(courseSelection, updateWrapper);
        return ResponseEntity.ok("success:"+ saved);
    }

}

