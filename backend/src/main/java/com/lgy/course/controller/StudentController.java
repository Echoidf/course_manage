package com.lgy.course.controller;



import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lgy.course.dao.StudentDao;
import com.lgy.course.dto.StudentDTO;
import com.lgy.course.entity.Course;
import com.lgy.course.entity.Student;
import com.lgy.course.entity.User;
import com.lgy.course.service.StudentService;
import com.lgy.course.service.UserService;
import com.lgy.course.vo.StudentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    @Resource
    private UserService userService;

    @GetMapping("/list")
    public ResponseEntity list(@RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "20") Integer pageSize) {
        //查询前，需要调用 PageHelper.startPage()
        PageHelper.startPage(pageNum, pageSize);
        //在 PageHelper.startPage() 后调用 findAll 就是分页查询(物理分页有 limit)
        List<StudentVO> studentList = studentService.findAll();
        //分页查询完之后，可以使用 pageInfo 来包装查询后的结果，
        PageInfo pageInfo = new PageInfo(studentList, pageSize);
        return ResponseEntity.ok(pageInfo);
    }

    @PostMapping("/add")
    public ResponseEntity add(@RequestBody StudentDTO studentDTO) {
        User user = new User();
        BeanUtils.copyProperties(studentDTO, user);
        long id = IdUtil.getSnowflakeNextId();
        user.setId(id);
        user.setRole("student");
        userService.save(user);

        Student student = new Student();
        BeanUtils.copyProperties(studentDTO, student);
        student.setUserId(id);
        studentService.save(student);

        return ResponseEntity.ok(null);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id){
        Student student = studentService.query().getBaseMapper().selectById(id);
        if(null == student){
            return ResponseEntity.badRequest().build();
        }
        userService.removeById(student.getUserId());
        studentService.removeById(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Student student) {
        UpdateWrapper<Student> studentUpdateWrapper = new UpdateWrapper<>();
        studentUpdateWrapper.eq("id", student.getId());

        boolean update = studentService.update(student, studentUpdateWrapper);
        if(!update) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }


}

