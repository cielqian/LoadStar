package com.ciel.pocket.infrastructure.exceptions;

/**
 * @author cielqian
 * @email qianhong91@outlook.com
 * @date 2018/10/8 11:45
 */

public class ObjectNotExistingException extends RuntimeException {
    public ObjectNotExistingException(String message) {
        super(message);
    }
}
