package com.yuxuan66.ehi.support.core;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.caller.CallerUtil;
import cn.hutool.core.util.StrUtil;
import com.yuxuan66.ehi.support.core.annotaion.AppStartup;
import com.yuxuan66.ehi.support.core.server.IServer;
import com.yuxuan66.ehi.support.core.server.jetty.JettyServer;
import com.yuxuan66.ehi.support.exception.AppException;
import com.yuxuan66.ehi.support.exception.ExceptionCode;
import com.yuxuan66.ehi.utils.lang.BannerUtil;
import com.yuxuan66.ehi.utils.lang.scanner.ClassScanner;
import com.yuxuan66.ehi.utils.lang.scanner.impl.DefaultClassScanner;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 系统核心
 *
 * @author Sir丶雨轩
 * @date 2019/1/17 10:57
 */
@Slf4j
public final class App {

    private App() {
    }


    /**
     * 唯一单例App对象
     */
    private static App app = null;

    public static App getInstance() {
        //初始化App数据，默认使用Jetty做为服务器容器
        app = new App();
        return app;
    }

    public void run() {
        try {
            init();
        } catch (AppException e) {
            e.printStackTrace();
            System.exit(0);
        }
        IServer server = new JettyServer();
        server.run();
    }

    /**
     * 初始化系统各项数据
     */
    public void init() throws AppException {
        Class<?> clasz = CallerUtil.getCaller(4);
        Assert.notNull(clasz, "没有找到调用者");
        AppStartup appStartup = clasz.getAnnotation(AppStartup.class);
        if (appStartup == null) {
            throw new AppException(ExceptionCode.INITFAIL, "启动类必须标注AppStartup");
        }
        String basePath = appStartup.value();
        if (StrUtil.isBlank(basePath)) {
            basePath = clasz.getPackage().getName();
        }
        log.info("EhiMVC Startup...");
        //打印输入Banner
        BannerUtil.write();
        log.debug("BasePackagePath is " + basePath);
        //开始进行包扫描
        ClassScanner classScanner = new DefaultClassScanner();
        List<Class> classList = classScanner.scan(basePath);
        classList.forEach(item -> log.info("Class Name" + item.getSimpleName()));
        log.info("App Console Init Success.");
    }
}
