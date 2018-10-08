package com.ciel.pocket.infrastructure.utils;

import com.ciel.pocket.infrastructure.exceptions.FriendlyException;
import com.ciel.pocket.infrastructure.dto.web.ReturnModel;
import org.springframework.http.HttpStatus;

/**
 * @Author Ciel Qian
 * @CreateDate 2018/10/1
 * @Comment
 */
public class ReturnUtils{
    public static <T> ReturnModel<T> ok(String msg, T data){
        return general(HttpStatus.OK.value(), msg, data);
    }

    public static ReturnModel ok(String msg){
        return ok(msg, null);
    }

    public static ReturnModel ok(){
        return ok("成功");
    }

    public static ReturnModel fail(String msg){
        return general(HttpStatus.BAD_REQUEST.value(), msg, null);
    }

    public static ReturnModel error(String msg){
        return general(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg, null);
    }

    public static <T> ReturnModel<T> general(int status, String msg, T data){
        return new ReturnModel<>(status, msg, data);
    }

    public static void checkSuccess(ReturnModel returnModel){
        if (returnModel.getStatus() != HttpStatus.OK.value()){
            throw new FriendlyException(returnModel.getMessage());
        }
    }
}
