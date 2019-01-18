package com.yuxuan66.ehi.utils.lang.scanner;

import com.yuxuan66.ehi.utils.lang.scanner.callback.ScannerCallback;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * 扫描器接口
 *
 * @author Sir丶雨轩
 * @date 2019/1/18 15:01
 */
public interface ClassScanner {
    /**
     * 扫描多个包下的Class
     *
     * @param scanBasePackages
     * @return
     */
    List<Class> scan(String ...scanBasePackages);

    /**
     * 扫描多个包下带有注解的Class
     *
     * @param scanBasePackages
     * @param anno
     * @return
     */
    List<Class> scanByAnno(Class<? extends Annotation> anno, String... scanBasePackages);

    /**
     * 扫描多个包下的Class，并执行回调
     *
     * @param scanBasePackages
     * @param callback
     */
    void scanAndCallback(ScannerCallback callback, String... scanBasePackages);

    /**
     * 扫描多个包下特定注解的Class，并执行回调
     *
     * @param scanBasePackages
     * @param anno
     * @param callback
     */
    void scanAndCallbackByAnno(Class<? extends Annotation> anno, ScannerCallback callback, String... scanBasePackages);
}
