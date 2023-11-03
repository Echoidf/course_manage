package com.lgy.course.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lgy.course.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserDao extends BaseMapper<User> {

}

