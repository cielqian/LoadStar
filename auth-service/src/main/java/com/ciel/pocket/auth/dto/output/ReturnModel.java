package com.ciel.pocket.auth.dto.output;

import lombok.Data;

@Data
public class ReturnModel<T> {
    private Integer status;

    private String message;

    private T data;

    public ReturnModel(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public ReturnModel(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ReturnModel OK(){
        return OK("成功");
    }

    public static ReturnModel OK(String message){
        return new ReturnModel(200, message);
    }

    public static<T> ReturnModel OK(String message, T data){
        return new ReturnModel(200, message, data);
    }

    public static<T> ReturnModel OK(T data){
        return OK("成功", data);
    }

    public static ReturnModel Fail(String message){
        return new ReturnModel(400, message);
    }
}
