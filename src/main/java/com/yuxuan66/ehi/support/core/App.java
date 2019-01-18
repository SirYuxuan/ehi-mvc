package com.yuxuan66.ehi.support.core;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Singleton;
import cn.hutool.core.lang.caller.CallerUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.yuxuan66.ehi.support.core.annotaion.AppStartup;
import com.yuxuan66.ehi.support.core.config.AppConfig;
import com.yuxuan66.ehi.support.core.config.Configuration;
import com.yuxuan66.ehi.support.core.config.DefaultAppConfig;
import com.yuxuan66.ehi.support.core.server.ServerType;
import com.yuxuan66.ehi.support.core.server.jetty.JettyServer;
import com.yuxuan66.ehi.support.exception.AppException;
import com.yuxuan66.ehi.support.exception.ExceptionCode;
import com.yuxuan66.ehi.support.mvc.MappingHandler;
import com.yuxuan66.ehi.support.mvc.annotation.*;
import com.yuxuan66.ehi.utils.lang.BannerUtil;
import com.yuxuan66.ehi.utils.lang.scanner.ClassScanner;
import com.yuxuan66.ehi.utils.lang.scanner.ClassUtil;
import com.yuxuan66.ehi.utils.lang.scanner.impl.DefaultClassScanner;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统核心
 *
 * @author Sir丶雨轩
 * @date 2019/1/17 10:57
 */
@Slf4j
public final class App {

    private static App app;
    /**
     * 内置核心容器
     */
    private final AppContext appContext = AppContext.getInstance();

    /**
     * 指定包下所有Class
     */
    private List<Class> allClass = new ArrayList<>();


    public void run() {
        try {
            init();
        } catch (AppException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }


    /**
     * 初始化系统各项数据
     */
    public void init() throws AppException {
        Class<?> mainClasz = CallerUtil.getCaller(4);
        Assert.notNull(mainClasz, "没有找到调用者");
        AppStartup appStartup = mainClasz.getAnnotation(AppStartup.class);
        if (appStartup == null) {
            throw new AppException(ExceptionCode.NOTAPPSTARTUP, "启动类必须标注AppStartup");
        }
        String basePath = appStartup.value();
        if (StrUtil.isBlank(basePath)) {
            basePath = mainClasz.getPackage().getName();
        }
        log.info("EhiMVC Startup.");
        //打印输入Banner
        BannerUtil.write();
        log.debug("BasePackagePath is " + basePath);
        //开始进行包扫描
        ClassScanner classScanner = new DefaultClassScanner();
        allClass = classScanner.scan(basePath);
        log.debug("EhiMVC loading AppConfig.");
        appContext.setConfiguration(loadConfig());
        log.debug("EhiMVC loading AppConfig Success.");
        log.debug("EhiMVC loading Url Mapping.");
        loadUrlMapping();

        if (appContext.getConfiguration().getServerType() == ServerType.INNER_JETTY) {
            new JettyServer().run();
        }
        log.info("App Console Init Success.");
    }

    /**
     * 加载并保存所有url映射
     */
    private void loadUrlMapping() throws AppException {
        List<Class> actionClassList = ClassUtil.getClassListByAnnotation(allClass, Action.class);
        actionClassList.forEach(item -> {
            Action action = (Action) item.getAnnotation(Action.class);
            Path path = (Path) item.getAnnotation(Path.class);
            //如果parh未指定url，则使用action配置,Path注解 优先级最高
            String basePath = (path == null || "/".equals(path.value())) ? action.value() : path.value();
            for (Method method : item.getMethods()) {
                if (!isActionAnnotation(method)) {
                    continue;
                }
                String methodPath = getMetnodUrl(method);
                String separate = "";
                if (!basePath.endsWith("/") && !methodPath.startsWith("/") && !methodPath.endsWith("/") && StrUtil.isNotBlank(methodPath)) {
                    separate = "/";
                }
                if ("/".equals(methodPath)) {
                    methodPath = "";
                }
                if (basePath.endsWith("/") && methodPath.startsWith("/")) {
                    methodPath = methodPath.substring(1);
                }
                String url = basePath + separate + methodPath;
                if (url.endsWith("/")) {
                    url = url.substring(0, url.length() - 1);
                }
                Action.ActionType[] actionType = getMetnodActionType(method);
                String actionTypeStr = JSONUtil.parse(actionType).toString();
                String mapKey = url + ".#00#." + actionTypeStr;
                if (appContext.getMappingHandlerMap().containsKey(mapKey)) {
                    throw new AppException(ExceptionCode.MAPPINGEXIST, "Mapping 已存在 Url-> {} ActionType->{} 所在Controller->{} 所在方法->{}", url, Convert.toStr(actionType), item.getTypeName(), method.getName());
                }
                appContext.addMapping(mapKey, new MappingHandler(Singleton.get(item), method, actionType));
                log.debug(StrUtil.format("Mapping Url->{} ActionType-> {} ", url, Convert.toStr(actionType)));
            }
        });
    }


    /**
     * 判断方法是否存在action或请求注解
     *
     * @param method 方法
     * @return 是否存在
     */
    private boolean isActionAnnotation(Method method) {
        return method.isAnnotationPresent(Action.class) || method.isAnnotationPresent(Get.class) || method.isAnnotationPresent(Post.class) || method.isAnnotationPresent(Del.class) || method.isAnnotationPresent(Put.class);
    }

    /**
     * 获取方法上标注的url
     *
     * @param method 方法
     * @return url
     */
    private String getMetnodUrl(Method method) {
        Action action = method.getAnnotation(Action.class);
        if (action != null) {
            return action.value();
        }
        Get get = method.getAnnotation(Get.class);
        if (get != null) {
            return get.value();
        }
        Post post = method.getAnnotation(Post.class);
        if (post != null) {
            return post.value();
        }
        Put put = method.getAnnotation(Put.class);
        if (put != null) {
            return put.value();
        }
        Del del = method.getAnnotation(Del.class);
        if (del != null) {
            return del.value();
        }
        return "";
    }

    /**
     * 获取方法上标注的请求方式
     *
     * @param method 方法
     * @return url
     */
    private Action.ActionType[] getMetnodActionType(Method method) {
        Action action = method.getAnnotation(Action.class);
        if (action != null) {
            return action.actionType();
        }
        Get get = method.getAnnotation(Get.class);
        if (get != null) {
            return new Action.ActionType[]{Action.ActionType.GET};
        }
        Post post = method.getAnnotation(Post.class);
        if (post != null) {
            return new Action.ActionType[]{Action.ActionType.Post};
        }
        Put put = method.getAnnotation(Put.class);
        if (put != null) {
            return new Action.ActionType[]{Action.ActionType.Put};
        }
        Del del = method.getAnnotation(Del.class);
        if (del != null) {
            return new Action.ActionType[]{Action.ActionType.DEL};
        }
        return new Action.ActionType[]{Action.ActionType.All};
    }

    private Configuration loadConfig() {
        //校验并获取App核心配置文件
        AppConfig appConfig = checkAppConfig();
        Configuration configuration = new Configuration();
        configuration.setProt(appConfig.port());
        configuration.setServerType(appConfig.serverType());
        configuration.setStaticPath(appConfig.staticPath());
        return configuration;
    }

    /**
     * 校验配置文件合法性,并返回唯一配置
     *
     * @return 唯一配置
     */
    private AppConfig checkAppConfig() {
        List<Class> configClassList = ClassUtil.getClassListBySuperClass(allClass, AppConfig.class);
        if (configClassList.size() > 1) {
            final StringBuilder errorConfigStr = new StringBuilder();
            configClassList.forEach(item -> {
                errorConfigStr.append(item.getName());
                errorConfigStr.append(",");
            });
            errorConfigStr.deleteCharAt(errorConfigStr.length() - 1);
            throw new AppException(ExceptionCode.CONFIGEXIST, "系统核心配置文件最多只能1个 {}", errorConfigStr);
        }
        if (configClassList.isEmpty()) {
            return Singleton.get(DefaultAppConfig.class);
        } else {
            return (AppConfig) ReflectUtil.newInstance(configClassList.get(0));
        }
    }

    public static App getInstance() {
        if (app == null) {
            synchronized (App.class) {
                if (app == null) {
                    app = new App();
                }
            }
        }
        return app;
    }
}
