package com.lgy.course.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lgy.course.dao.UserDao;
import com.lgy.course.entity.User;
import com.lgy.course.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @date: 2023/11/1
 **/

@RestController
@RequestMapping("/auth")
public class AuthController {

    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Resource
    private UserDao userDao;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> authorize(@RequestParam String username,
                                                         @RequestParam String password,
                                                         @RequestParam String role) {
        Map<String, String> map = new HashMap<>();

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        wrapper.eq("role", role);
        User user = userDao.selectOne(wrapper);
        if(user == null || !user.getPassword().equals(password)){
            map.put("msg", "用户名密码错误");
            map.put("code", "400");
            return ResponseEntity.ok(map);
        }

        // 校验通过生成token
        JwtUtil jwtUtil = new JwtUtil();
        Map<String, Object> chaim = new HashMap<>();
        chaim.put("username", username);
        chaim.put("userId", user.getId());
        String jwtToken = jwtUtil.encode(username, 3600 * 60 * 1000, chaim);
        map.put("msg", "授权成功");
        map.put("token", jwtToken);


        // 返回token码
        return ResponseEntity.ok(map);
    }
}
