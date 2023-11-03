package com.lgy.course.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lgy.course.dao.UserDao;
import com.lgy.course.entity.User;
import com.lgy.course.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 
 * @since 2023-11-01 11:47:03
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}

