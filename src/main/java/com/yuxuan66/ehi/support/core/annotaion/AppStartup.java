package com.yuxuan66.ehi.support.core.annotaion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AppStartup {

    /**
     * 包扫描路径,默认为启动类所在路径
     * @return
     */
    String value() default "";
}
