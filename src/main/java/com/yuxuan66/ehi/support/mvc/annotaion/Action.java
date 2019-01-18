package com.yuxuan66.ehi.support.mvc.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Sir丶雨轩
 * @date 2019/1/17 11:46
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Action {

    enum ActionType{
        /**
         * 删除请求
         */
        DEL,
        /**
         * 查询请求
         */
        GET,
        /**
         * 数据提交请求
         */
        Post,
        /**
         * 数据修改请求
         */
        Put
    }

    /**
     * 请求路径
     * @return
     */
    String value() default "/";

    /**
     * 请求方式
     * @return
     */
    ActionType[] actionType() default {ActionType.DEL,ActionType.GET,ActionType.Post,ActionType.Put};
}
