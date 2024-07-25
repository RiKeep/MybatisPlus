package com.Ri.mybatis_plus;

import com.Ri.mybatis_plus.mapper.UserMapper;
import com.Ri.mybatis_plus.pojo.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
public class MyBatisPlusWrapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void test_selectList01(){
        // 需求：查询用户名包含 a, 年龄在 20 到 30 之间, 邮箱不为 null
        /*
        * 如果已经逻辑删除的数据查询等操作都不会加入进来
        *   SELECT uid AS id,user_name AS name,age,email,is_deleted
        *   FROM t_user
        *   WHERE is_deleted=0 AND (user_name LIKE ? AND age BETWEEN ? AND ? AND email IS NOT NULL)
        *  */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "a")
                    .between("age", 20, 30)
                    .isNotNull("email");

        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test_selectList02(){
        // 需求：查询用户信息, 按照年龄的降序排序, 若年龄相同, 则按照id升序排序
        /*
        *   SELECT uid AS id,user_name AS name,age,email,is_deleted
        *   FROM t_user
        *   WHERE is_deleted=0
        *   ORDER BY user_name DESC,uid ASC
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("user_name")
                    .orderByAsc("uid");
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

   @Test
   public void test_selectMaps(){
        // 需求：查询用户的用户名, 年龄, 邮箱信息
       /*
       *    SELECT user_name,age,email
       *    FROM t_user
       *    WHERE is_deleted=0
        */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("user_name", "age", "email");
        List<Map<String, Object>> users = userMapper.selectMaps(queryWrapper);
        users.forEach(System.out::println);
   }

   @Test
   public void test_selectChildren(){
        // 需求：使用子查询, 查询 id 小于等于 100 的用户信息
       /*
       *    SELECT uid AS id,user_name AS name,age,email,is_deleted
       *    FROM t_user
       *    WHERE is_deleted=0
       *        AND (uid IN (select uid from t_user where is_deleted=0 and uid <= 100))
        */
       QueryWrapper<User> queryWrapper = new QueryWrapper<>();
       /* inSql：参数一：子查询判断时要设置的字段 | 参数二：子查询的 SQL */
       queryWrapper.inSql("uid", "select uid from t_user where is_deleted=0 and uid <= 100");
       List<User> users = userMapper.selectList(queryWrapper);
       users.forEach(System.out::println);
   }

    @Test
    public void test_delete(){
        // 需求：删除邮箱地址为 null 的用户信息
        /*
        * 因为加了逻辑删除, 所以真正执行的 SQL 其实是一个 更新的 SQL
        *   UPDATE t_user SET is_deleted=1 WHERE is_deleted=0 AND (email IS NULL)
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.isNull("email");

        int result = userMapper.delete(queryWrapper);
        System.out.println("result = " + result);
    }

    @Test
    public void test_update01(){
        // 需求：更新（年龄大于 20 并且用户名中包含有 a）或邮箱为 null 的用户信息修改
        /*
        *   UPDATE t_user
        *   SET user_name=?, email=?
        *   WHERE is_deleted=0 AND (age > ? AND user_name LIKE ? OR email IS NULL)
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.gt("age", "20")
                     .like("user_name", "a")
                     .or()
                     .isNull("email");
        User user = new User();
        user.setName("张三");
        user.setEmail("ri@rr.com");

        /* update 参数一：设置要修改的内容 | 参数二：设置要修改的条件 */
        int result = userMapper.update(user, queryWrapper);
        System.out.println("result = " + result);
    }

    @Test
    public void test_update02(){
        // 需求：将用户名中包含有 a 并且（年龄大于 20 或邮箱为 null）的用户信息修改
        // 如果使用 and 或者 or 方法内的 Consumer 参数, 那么 Lambda 中的条件优先执行
        /*
        *   UPDATE t_user
        *   SET user_name=?, email=?
        *   WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
         */
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("user_name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"));

        User user = new User();
        user.setName("小明");
        user.setEmail("ri@rr.com");
        int result = userMapper.update(user, queryWrapper);
        System.out.println("result = " + result);
    }

    @Test
    public void test_update03(){
        // 需求：将用户名中包含有 a 并且（年龄大于 20 或邮箱为 null）的用户信息修改
        /*
        * UPDATE t_user
        * SET user_name=?,age=?
        * WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
         */
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.like("user_name", "a")
                .and(i -> i.gt("age", 20).or().isNull("email"))
                .set("user_name", "updateWrapper")
                .set("age", 18);
        // 如果是使用的 UpdateWrapper 的话, 那么 update() 的第一个参数可以设为 null
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result = " + result);
    }

    @Test
    public void test_demo01(){
        /*
        *   SELECT uid AS id,user_name AS name,age,email,is_deleted
        *   FROM t_user
        *   WHERE is_deleted=0 AND (age >= ? AND age <= ?)
         */
        // 模拟一个真实业务场景
        // 缺点：太过于繁琐
        String username = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(StringUtils.isNotBlank(username)){
            // 和 validation 一样, 可以直接使用 validation 做数据校验
            queryWrapper.like("user_name", username);
        }
        if(ageBegin != null){
            queryWrapper.ge("age", ageBegin);
        }
        if(ageEnd != null){
            queryWrapper.le("age", ageEnd);
        }
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test_demo02(){
        // 需求：优化 demo1 的写法
        /*
        *   SELECT uid AS id,user_name AS name,age,email,is_deleted
        *   FROM t_user
        *   WHERE is_deleted=0 AND (age >= ? AND age <= ?)
         */
        String username = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(username), "user_name", username)
                .ge(ageBegin != null, "age", ageBegin)
                .le(ageEnd != null, "age", ageEnd);
        List<User> users = userMapper.selectList(queryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test_lambdaQueryDemo(){
        /*
        * SELECT uid AS id,user_name AS name,age,email,is_deleted
        * FROM t_user
        * WHERE is_deleted=0 AND (age >= ? AND age <= ?)
         */
        // lambdaQueryWrapper 和 QueryWrapper 其实就是在第二个参数的时候使用了反射以防字段名写错
        String username = "";
        Integer ageBegin = 20;
        Integer ageEnd = 30;
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.isNotBlank(username), User::getName, username)
                .ge(ageBegin != null, User::getAge, ageBegin)
                .le(ageEnd != null, User::getAge, ageEnd);
        List<User> users = userMapper.selectList(lambdaQueryWrapper);
        users.forEach(System.out::println);
    }

    @Test
    public void test_lambdaUpdateDemo(){
        // 在使用 lambda 开头的类型, 内置的字段就不能使用字符串了, 要使用函数式接口来代替
        /*
        *   UPDATE t_user
        *   SET user_name=?,age=?
        *   WHERE is_deleted=0 AND (user_name LIKE ? AND (age > ? OR email IS NULL))
         */
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.like(User::getName, "a")
                .and(i -> i.gt(User::getAge, 20).or().isNull(User::getEmail))
                .set(User::getName, "updateWrapper")
                .set(User::getAge, 18);
        // 如果是使用的 UpdateWrapper 的话, 那么 update() 的第一个参数可以设为 null
        int result = userMapper.update(null, updateWrapper);
        System.out.println("result = " + result);
    }
}
