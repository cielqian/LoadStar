package com.ciel.loadstar.user.infrastructure.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;

public enum ListTypeEnum{
    Default(0, "list1"),
    Card(1, "card"),
    List(2, "list"),
    List1(3, "list1");

    @EnumValue
    int value;
    String name;

    ListTypeEnum(int value, String name){
        this.value = value;
        this.name = name;
    }
//
//    @Override
//    public Integer getValue() {
//        return this.value;
//    }
}
