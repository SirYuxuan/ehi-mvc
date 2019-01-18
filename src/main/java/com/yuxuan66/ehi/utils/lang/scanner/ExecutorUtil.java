package com.yuxuan66.ehi.utils.lang.scanner;


import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 * @author Sir丶雨轩
 * @date 2019/1/18 15:07
 */
public enum  ExecutorUtil {
    ;
    /**
     * 线程池
     */
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(8,
            16,
            3,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(10000),
            new NamedThreadFactory("ehi-mvc-class-scanner-thread-"));

    /**
     * 在线程池执行线程
     *
     * @param thread
     */
    public static void executeInPool(Thread thread) {
        executor.execute(thread);
    }
}
