package com.yuxuan66.ehi.utils.lang.scanner;

import cn.hutool.core.util.StrUtil;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.yuxuan66.ehi.utils.lang.scanner.consts.CommonConsts.PACKAGE_SEPARATOR;
import static com.yuxuan66.ehi.utils.lang.scanner.consts.CommonConsts.PATH_SEPARATOR;

/**
 * 类相关工具方法
 * @author Sir丶雨轩
 * @date 2019/1/18 15:06
 */
public class ClassUtil {
    ;

    /**
     * 匿名内部类匹配表达式
     */
    public static final Pattern ANONYMOUS_INNER_CLASS_PATTERN = Pattern.compile("^[\\s\\S]*\\${1}\\d+\\.class$");

    /**
     * 包名转换为路径名
     *
     * @param packageName
     * @return
     */
    public static String package2Path(String packageName) {
        return packageName.replace(PACKAGE_SEPARATOR, PATH_SEPARATOR);
    }

    /**
     * 路径名转换为包名
     *
     * @param pathName
     * @return
     */
    public static String path2Package(String pathName) {
        return pathName.replaceAll(PATH_SEPARATOR, PACKAGE_SEPARATOR);
    }

    /**
     * 根据文件后缀名判断是否Class文件
     *
     * @param fileName
     * @return
     */
    public static boolean isClass(String fileName) {
        if (StrUtil.isEmpty(fileName)) {
            return false;
        }
        return fileName.endsWith(".class");
    }

    /**
     * Class文件名转为类名（即去除后缀名）
     *
     * @param classFileName
     * @return
     */
    public static String classFile2SimpleClass(String classFileName) {
        return classFileName.replace(".class", "");
    }

    /**
     * 根据类名判断是否匿名内部类
     *
     * @param className
     * @return
     */
    public static boolean isAnonymousInnerClass(String className) {
        return ANONYMOUS_INNER_CLASS_PATTERN.matcher(className).matches();
    }

    /**
     * 从给定的类的集合中获取包含指定注解的集合
     * @param classList 类的集合
     * @param annotationClass 注解类型
     * @return 符合条件的类的集合
     */
    public static List<Class> getClassListByAnnotation(List<Class> classList, Class<? extends Annotation> annotationClass){
       return classList.stream().filter(clazz -> clazz.isAnnotationPresent(annotationClass)).collect(Collectors.toList());
    }
    /**
     * 从给定的类的集合中获取指定父类的集合
     * @param classList 类的集合
     * @param superClass 指定父类
     * @return 符合条件的类的集合
     */
    public static List<Class> getClassListBySuperClass(List<Class> classList, Class superClass){
        return classList.stream().filter(clazz -> superClass.isAssignableFrom(clazz)).collect(Collectors.toList());
    }
}
