package com.Ri.mybatis_plus;

import com.Ri.mybatis_plus.pojo.User;
import com.Ri.mybatis_plus.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class MybatisPlusServiceTest {
    @Autowired
    private UserService userService;


    @Test
    public void test_count() {
        // 查询总记录数
        // SELECT COUNT( * ) AS total FROM user
        long count = userService.count();
        System.out.println("总记录数：" + count);
    }

    @Test
    public void test_insertMore(){
        // 批量添加多条数据
        // Preparing: INSERT INTO user ( id, name, age ) VALUES ( ?, ?, ? )
        List<User> lists = new ArrayList<>();
        for (int i = 1; i <= 10; ++i){
            User user = new User();
            user.setName("Ri" + i);
            user.setAge(20 + i);
            lists.add(user);
        }
        boolean b = userService.saveBatch(lists);
        System.out.println("是否操作成功：" + b);
    }
}
