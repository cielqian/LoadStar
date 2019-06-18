package com.ciel.loadstar.user.infrastructure.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

/**
 * 首页模块
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/16 13:43
 */

public enum ThemeModuleEnum implements IEnum<Integer> {
    Recently(1, "recently"),//最近访问
    Top(2, "top"),//最常访问
    Private(3, "private");//隐藏

    int value;
    String name;

    ThemeModuleEnum(int value, String name){
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
