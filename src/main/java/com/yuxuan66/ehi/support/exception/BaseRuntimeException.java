package com.yuxuan66.ehi.support.exception;

import cn.hutool.core.util.StrUtil;

/**
 * 运行时异常
 *
 * @author Sir丶雨轩
 * @date 2019/1/18 15:25
 */
public class BaseRuntimeException extends RuntimeException {

    public BaseRuntimeException(String msg) {
        super(msg);
    }

    public BaseRuntimeException(CharSequence msg, Object... format) {
        super(StrUtil.format(msg, format));
    }

    public BaseRuntimeException(ExceptionCode exceptionCode) {
        super("RuntimeException FailCode:" + exceptionCode.value());
    }

    public BaseRuntimeException(ExceptionCode exceptionCode, String msg) {
        super("RuntimeException FailCode:" + exceptionCode.value() + "," + msg);
    }

    public BaseRuntimeException(ExceptionCode exceptionCode, CharSequence msg, Object... format) {
        super("RuntimeException FailCode:" + exceptionCode.value() + "," + StrUtil.format(msg, format));
    }
}
