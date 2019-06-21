package com.ciel.loadstar.infrastructure.events;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/21 17:02
 */

public class EventType {
    public final static String CREATE = "NEW";
    public final static String DELETE = "DELETE";
    public final static String UPDATE = "UPDATE";

    public EventType(String typeString){
        this.typeString = typeString;
    }

    private String typeString = "";

    @Override
    public String toString() {
        return this.typeString;
    }
}
