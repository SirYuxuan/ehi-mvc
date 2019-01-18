package com.yuxuan66.ehi.utils.lang.scanner.impl;

import com.yuxuan66.ehi.utils.lang.scanner.ClassScanner;
import com.yuxuan66.ehi.utils.lang.scanner.ExecutorUtil;
import com.yuxuan66.ehi.utils.lang.scanner.ScannerUtil;
import com.yuxuan66.ehi.utils.lang.scanner.callback.ScannerCallback;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.stream.Collectors;

/**
 * 默认类扫描器实现类
 *
 * @author Sir丶雨轩
 * @date 2019/1/18 15:02
 */
@Slf4j
public class DefaultClassScanner implements ClassScanner {

    @Override
    public List<Class> scan(String... scanBasePackages) {
        List<Class> classList = new LinkedList<>();

        //没有需要扫描的包，返回空列表
        if (scanBasePackages == null || scanBasePackages.length == 0) {
            return classList;
        }

        //创建异步线程
        List<FutureTask<List<Class>>> tasks = new LinkedList<>();
        Arrays.asList(scanBasePackages)
                .forEach(pkg -> {
                    ScannerCallable call = new ScannerCallable(pkg);
                    FutureTask<List<Class>> task = new FutureTask(call);
                    ExecutorUtil.executeInPool(new Thread(task));
                    tasks.add(task);
                });

        //等待返回结果
        tasks.parallelStream().forEach(task -> {
            try {
                classList.addAll(task.get());
            } catch (InterruptedException | ExecutionException e) {
                log.error(e.getMessage(), e);
            }
        });


        return classList;
    }

    /**
     * @param scanBasePackages
     * @param anno
     * @return
     */
    @Override
    public List<Class> scanByAnno(Class<? extends Annotation> anno, String... scanBasePackages) {
        List<Class> classList = this.scan(scanBasePackages);

        //根据Annotation过滤并返回
        return classList.parallelStream()
                .filter(clz -> {
                    try {
                        if (clz.getAnnotation(anno) == null) {
                            return false;
                        }
                    } catch (Throwable e) {
                        log.debug(e.getMessage());
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void scanAndCallback(ScannerCallback callback, String... scanBasePackages) {
        List<Class> classList = this.scan(scanBasePackages);
        callback.callback(classList);
    }

    @Override
    public void scanAndCallbackByAnno(Class<? extends Annotation> anno,
                                      ScannerCallback callback, String... scanBasePackages) {
        List<Class> classList = this.scanByAnno(anno, scanBasePackages);
        callback.callback(classList);
    }

    /**
     * 扫描器线程类
     */
    @AllArgsConstructor
    class ScannerCallable implements Callable<List<Class>> {

        /**
         * 扫描的包名称
         */
        private String pkg;

        @Override
        public List<Class> call() {
            return ScannerUtil.scan(pkg);
        }
    }
}
