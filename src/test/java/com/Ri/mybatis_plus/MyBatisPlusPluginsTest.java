package com.Ri.mybatis_plus;

import com.Ri.mybatis_plus.mapper.ProductMapper;
import com.Ri.mybatis_plus.mapper.UserMapper;
import com.Ri.mybatis_plus.pojo.Product;
import com.Ri.mybatis_plus.pojo.User;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MyBatisPlusPluginsTest {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProductMapper productMapper;

    @Test
    public void test_selectPage(){
        // 会查询两次, 一次是总数, 一次是分页返回结果
        /*
        *   SELECT COUNT(*) AS total
        *   FROM t_user
        *   WHERE is_deleted = 0
         */
        /*
        *   SELECT uid AS id,user_name AS name,age,email,is_deleted
        *   FROM t_user
        *   WHERE is_deleted=0
        *   LIMIT ?,?
         */
        Page<User> page = new Page<>(2, 2);
        userMapper.selectPage(page, null);
        System.out.println("数据 = " + page.getRecords());
        System.out.println("总页数 = " + page.getPages());
        System.out.println("总数 = " + page.getTotal());
        System.out.println("每页显示数 = " +  + page.getSize());
        System.out.println("当前页 = " + page.getCurrent());
        System.out.println("是否存在下一页 = " + page.hasNext());
        System.out.println("是否存在上一页 = " + page.hasPrevious());
    }

    @Test
    public void test_PageVo(){
        Page<User> page = new Page<>(1, 2);
        userMapper.selectPageVo(page, 20);
        System.out.println("数据 = " + page.getRecords());
        System.out.println("总页数 = " + page.getPages());
        System.out.println("总数 = " + page.getTotal());
        System.out.println("每页显示数 = " +  + page.getSize());
        System.out.println("当前页 = " + page.getCurrent());
        System.out.println("是否存在下一页 = " + page.hasNext());
        System.out.println("是否存在上一页 = " + page.hasPrevious());
    }

        @Test
        public void test_ProductDemo(){
            // 小李查询的商品价格
            Product productLi = productMapper.selectById(1L);
            System.out.println("小李查询的商品价格：" + productLi.getPrice());

            // 小王查询的商品价格
            Product productWang = productMapper.selectById(1L);
            System.out.println("小王查询的商品价格：" + productWang.getPrice());

            // 小李将商品价格 +50
            productLi.setPrice(productLi.getPrice() + 50);
            productMapper.updateById(productLi);

            // 小王将商品价格 -30
            productWang.setPrice(productWang.getPrice() - 30);
            int result = productMapper.updateById(productWang);

                // 如果 result == 0 的话代表执行失败, 重新查询执行即可
            if(result == 0){
                productWang = productMapper.selectById(1L);
                productWang.setPrice(productWang.getPrice() - 30);
                productMapper.updateById(productWang);
            }

            // 老板查询商品价格
            Product productLaoBan = productMapper.selectById(1);
            System.out.println("老板查询的商品价格：" + productLaoBan.getPrice());

        }

}
