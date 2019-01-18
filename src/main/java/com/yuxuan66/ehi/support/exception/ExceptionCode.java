package com.yuxuan66.ehi.support.exception;

/**
 * @author Sir丶雨轩
 * @date 2019/1/18 15:28
 */
public enum  ExceptionCode {
    /**
     * 初始化失败
     */
    NOTAPPSTARTUP(10001),
    /**
     * 配置文件已经存在
     */
    CONFIGEXIST(10002),
    /**
     * mapping重复
     */
    MAPPINGEXIST(10003);
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
