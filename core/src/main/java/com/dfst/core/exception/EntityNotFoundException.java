package com.dfst.core.exception;

/**
 * @author yanfei@supcon.com
 * 自定义Exception，entity不存在时抛出此异常
 * @date 2016/3/16.
 */
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String name) {
        super("Can not found entity named '" + name + "'");
    }
}
