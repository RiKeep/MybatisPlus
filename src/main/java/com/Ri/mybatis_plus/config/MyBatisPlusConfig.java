package com.Ri.mybatis_plus.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// 扫描指定包下的 mapper 接口
@MapperScan("com.Ri.mybatis_plus.mapper")
public class MyBatisPlusConfig {
    @Bean
    // 配置 MybatisPlus 中的插件
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 添加内部插件, 并使用 DbType枚举类型 来设置数据库类型
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 添加乐观锁配置
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 返回即可
        return interceptor;
    }
}
