package com.Ri.mybatis_plus;

import com.Ri.mybatis_plus.enums.SexEnum;
import com.Ri.mybatis_plus.mapper.UserMapper;
import com.Ri.mybatis_plus.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPLusEnumTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_enumDemo(){
        /*
        *   INSERT INTO t_user ( uid, user_name, sex )
        *   VALUES ( ?, ?, ? )
        *   Parameters: 7(Long), admin(String), 1(Integer)
         */
        User user = new User();
        user.setId(7L);
        user.setName("admin");
        user.setSex(SexEnum.MALE);
        userMapper.insert(user);
    }
}
