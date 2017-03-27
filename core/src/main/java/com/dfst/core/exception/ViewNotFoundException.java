package com.dfst.core.exception;

/**
 * @author yanfei@supcon.com
 * 自定义Exception，view不存在时抛出此异常
 * @date 2016/3/15.
 */
public class ViewNotFoundException extends Exception {
    public ViewNotFoundException(String name) {
        super(name + " can not be initialized. Please check view id is existed OR check layout is injected");
    }
}
