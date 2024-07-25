package com.Ri.mybatis_plus.pojo;


import com.Ri.mybatis_plus.enums.SexEnum;
import com.baomidou.mybatisplus.annotation.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor

// 设置实体类所对应的 MySQL 表名
 @TableName("t_user")  // 如果全局都有 t_ 这个前缀的话可以在 yml 文件中配置全局前缀
public class User {
    // mybatis plus 中默认使用的是 雪花算法, 所以我们在使用对象类型的时候要使用封装类
    // @TableId 的 value 属性用于指定数据库字段名
    // 使用 type 生成策略的话一定要在数据库中给这个字段添加一个自增属性!!!
//    @TableId(value = "uid", type = IdType.AUTO) // 将这个属性的字段做为当前的主键
    @TableId("uid")
    private Long id;

//    private String userName;  数据库中使用下划线, 但是当前字段使用驼峰命名的话 mybatis-plus 会自动转换
    @TableField("user_name") // 把当前字段名设置成数据库的字段名
    private String name;

    private Integer age;

    private  String email;

    private SexEnum sex;

    // 使用了 TableLogic 注解, 那么删除会变成修改操作, 查询会默认查询未删除的数据
    @TableLogic // 逻辑删除(假删除)
    // UPDATE t_user SET is_deleted=1 WHERE uid IN ( ? , ? , ? ) AND is_deleted=0
    // SELECT uid AS id,user_name AS name,age,email,is_deleted FROM t_user WHERE is_deleted=0
    private Integer isDeleted;
}
