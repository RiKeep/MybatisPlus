package com.Ri.mybatisplus.system.service.impl;

import com.Ri.mybatisplus.system.entity.User;
import com.Ri.mybatisplus.system.mapper.UserMapper;
import com.Ri.mybatisplus.system.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Ri
 * @since 2024-07-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
