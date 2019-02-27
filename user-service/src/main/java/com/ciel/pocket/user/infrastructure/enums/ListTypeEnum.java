package com.ciel.pocket.user.infrastructure.enums;

import com.baomidou.mybatisplus.core.enums.IEnum;

public enum ListTypeEnum implements IEnum<Integer> {
    Card(1, "card"),
    List(2, "list"),
    List1(3, "list1");

    int value;
    String name;

    ListTypeEnum(int value, String name){
        this.value = value;
        this.name = name;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }
}
