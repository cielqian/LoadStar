package com.ciel.loadstar.infrastructure.events.link;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2019/6/21 17:02
 */

public class LinkEventType {
    public final static String CREATE = "NEW";
    public final static String DELETE = "DELETE";
    public final static String TRASH = "TRASH";
    public final static String UPDATE = "UPDATE";
    public final static String VIEW = "VIEW";

    public LinkEventType(String typeString){
        this.typeString = typeString;
    }

    private String typeString = "";

    @Override
    public String toString() {
        return this.typeString;
    }
}
