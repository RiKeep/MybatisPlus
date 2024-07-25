package com.Ri.mybatis_plus;

import com.Ri.mybatis_plus.mapper.UserMapper;
import com.Ri.mybatis_plus.pojo.User;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
// 如果启动失败则可以看一下该包名是否和 上面 java 的包名一致!!!
public class MybatisPlusTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_selectList(){
        // 使用父类内的方法, 通过条件构造器查询一个 List 集合, 若没有条件, 则可以设置为 null 参数
        // SELECT id,name,age,email FROM user
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }


    @Test
    public void test_selectById(){
        // 根据 id 来查询用户信息
        // SELECT id,name,age,email FROM user WHERE id=?
        User user = userMapper.selectById(1L);
        System.out.println("user=" + user);
    }

    @Test
    public void test_selectBatchIds(){
        // 根据 id 批量查询用户信息
        // SELECT id,name,age,email FROM user WHERE id IN ( ? , ? , ? )
        List<Long> lists = Arrays.asList(1L, 2L, 3L);
        List<User> users = userMapper.selectBatchIds(lists);
        users.forEach(System.out::println);
    }

    @Test
    public void test_selectByMap(){
        // 根据给定 Map 集合的条件进行查询
        // SELECT id,name,age,email FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("name", "Jack");
        map.put("age", 20);

        List<User> users = userMapper.selectByMap(map);
        users.forEach(System.out::println);
    }

    @Test
    public void test_selectMapById(){
        // 使用自定义的方法来查询, mybatis-plus 只做增强, 不做改变！！
        // select id, name, age, email from user where id = ?;
        Map<String, Object> result = userMapper.selectMapById(1L);
        System.out.println("result：" + result);
    }

    @Test
    public void test_insert(){
        // 新增用户信息
        // INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
        User user = new User();
        user.setName("Ri");
        user.setAge(20);
        user.setEmail("rrrrrrri@qq.com");
        int result = userMapper.insert(user);
        System.out.println("result：" + result);
        System.out.println("id:" + user.getId());
    }

    @Test
    public void test_deleteById(){
        // 通过 id  删除用户信息
        // DELETE FROM user WHERE id=?
        int result = userMapper.deleteById(1814296898402951169L);
        System.out.println("result=" + result);
    }

    @Test
    public void test_deleteByMap(){
        // 根据 map 来进行条件删除, map 里面存放的是被删除的条件值
        // DELETE FROM user WHERE name = ? AND age = ?
        Map<String, Object> map = new HashMap<>();
        map.put("name", "张三");
        map.put("age", 19);

        int result = userMapper.deleteByMap(map);
        System.out.println("result=" + result);
    }

    @Test
    public void test_deleteBatchIds(){
        // 批量根据 id 删除数据
        // DELETE FROM user WHERE id IN ( ? , ? , ? )
        List<Long> list = Arrays.asList(1L, 2L, 3L);
        int result = userMapper.deleteBatchIds(list);
        System.out.println("result=" + result);
    }

    @Test
    public void test_updateById(){
        // 根据 id 来修改数据
        // UPDATE user SET name=?, age=?, email=? WHERE id=?
//        User user = new User(4L, "李四", 19,  "lisi@ri.com", 0);
//        int result = userMapper.updateById(user);
//        System.out.println("result=" + result);
    }

}
