package com.ciel.loadstar.infrastructure.events.account;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/8/2 10:38
 */

public class AccountEventType {
    public final static String CREATE = "NEW";
    public final static String DELETE = "DELETE";
    public final static String UPDATE = "UPDATE";
    public final static String LOGIN = "LOGIN";

    public AccountEventType(String typeString){
        this.typeString = typeString;
    }

    private String typeString = "";

    @Override
    public String toString() {
        return this.typeString;
    }
}
