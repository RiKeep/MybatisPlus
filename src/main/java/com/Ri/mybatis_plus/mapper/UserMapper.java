package com.Ri.mybatis_plus.mapper;

import com.Ri.mybatis_plus.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;


@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据 id 查询用户信息为 map 集合
     * @param id
     * @return
     */
    Map<String, Object> selectMapById(Long id);

    /**
     * 通过年龄查询用户信息并分页
     *  如果想要使用 MyBatis-Plus 自带的 Page 分页功能则必须位于第一个参数位置
     * @param page  MyBatis-Plus 所提供的分页对象，必须位于第一个参数位置
     * @param age
     * @return
     */
    Page<User> selectPageVo(@Param("page") Page<User> page,@Param("age") Integer age);
}