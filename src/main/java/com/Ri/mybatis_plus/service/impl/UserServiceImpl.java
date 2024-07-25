package com.Ri.mybatis_plus.service.impl;

import com.Ri.mybatis_plus.mapper.UserMapper;
import com.Ri.mybatis_plus.pojo.User;
import com.Ri.mybatis_plus.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

// ServiceImpl 泛型内的 M 参数是 我们要操作的接口, T 是操作的类型
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
