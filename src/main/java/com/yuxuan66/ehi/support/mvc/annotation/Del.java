package com.yuxuan66.ehi.support.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sir丶雨轩
 * @date 2019/1/17 11:46
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Action(actionType = Action.ActionType.DEL)
public @interface Del {
    /**
     * 请求路径
     * @return
     */
    String value() default "/";
}
