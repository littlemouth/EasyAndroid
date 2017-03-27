package com.dfst.core.exception;

/**
 * @author yanfei@supcon.com
 * 自定义Exception，Resource id 不存在时抛出此异常
 * @date 2016/3/16.
 */
public class ResourceNotFoundException extends Exception {
    public ResourceNotFoundException(String id) {
        super("Resoudce " + id +" is not found");
    }
}
