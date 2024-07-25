package com.Ri.mybatis_plus.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter // 枚举类型都是常量, 只给 get 方法即可
public enum SexEnum {
    MALE(1, "男"),
    FEMALE(2, "女");

    @EnumValue
    // 代表枚举类型, 插入的时候会直接将这个值作为参数传入, 不用扫描 type-enums-package 包即可使用
    // 官网并没有提示 type-enums-package 是什么时候过时的, 请注意甄别
    private Integer sex;
    private String sexName;

    SexEnum(Integer sex, String sexName){
        this.sex = sex;
        this.sexName = sexName;
    }
}
