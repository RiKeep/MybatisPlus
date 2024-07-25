package com.Ri.mybatis_plus.mapper;

import com.Ri.mybatis_plus.pojo.Product;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
