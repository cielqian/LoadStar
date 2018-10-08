package com.ciel.pocket.infrastructure.exceptions;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/8 11:45
 */

public class ObjectExistingException extends RuntimeException {
    public ObjectExistingException(String message) {
        super(message);
    }
}
