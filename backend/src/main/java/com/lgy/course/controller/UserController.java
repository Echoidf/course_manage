package com.lgy.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.course.dao.UserDao;
import com.lgy.course.entity.User;
import com.lgy.course.service.UserService;
import com.lgy.course.util.JwtUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestParam String username,
                                           @RequestParam String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        boolean saved = userService.save(user);
        if (saved) {
            return ResponseEntity.ok("success");
        }
        return ResponseEntity.ok("failed");
    }

    @GetMapping("/info")
    public ResponseEntity<Map<String, Object>> getUserInfo(HttpServletRequest request) {
        String token = request.getHeader("Authorization").split(" ")[1];
        JwtUtil jwtUtil = new JwtUtil();
        String username = String.valueOf(jwtUtil.decode(token).get("username"));
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = userDao.selectOne(wrapper);

        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("userId", user.getId().toString());
        map.put("avatar", user.getAvatar());
        map.put("role", user.getRole());
        return ResponseEntity.ok(map);
    }

}

