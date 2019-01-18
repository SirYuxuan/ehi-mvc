package com.yuxuan66.ehi.support.exception;

/**
 * @author Sir丶雨轩
 * @date 2019/1/18 15:26
 */
public class AppException extends BaseRuntimeException {
    public AppException(String msg) {
        super(msg);
    }

    public AppException(CharSequence msg, Object... format) {
        super(msg, format);
    }

    public AppException(ExceptionCode exceptionCode) {
        super(exceptionCode);
    }

    public AppException(ExceptionCode exceptionCode, String msg) {
        super(exceptionCode, msg);
    }

    public AppException(ExceptionCode exceptionCode, CharSequence msg, Object... format) {
        super(exceptionCode, msg, format);
    }
}
