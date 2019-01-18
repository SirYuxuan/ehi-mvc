package com.yuxuan66.ehi.support.exception;

/**
 * @author Sir丶雨轩
 * @date 2019/1/18 15:28
 */
public enum  ExceptionCode {
    /**
     * 初始化失败
     */
    INITFAIL(10001);
    /**
     * 错误编号
     */
    private Integer code;

    ExceptionCode(Integer code) {
        this.code = code;
    }


    public int value(){
        return this.code;
    }
}
