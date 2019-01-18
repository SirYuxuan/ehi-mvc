package com.yuxuan66.ehi.support.core.config;

import com.yuxuan66.ehi.support.core.server.ServerType;

/**
 * @author Sir丶雨轩
 * @date 2019/1/18 15:18
 */
public interface AppConfig {
    /**
     * 服务端口配置,默认 8379
     * @return 端口号
     */
    default int port(){
        return 8379;
    }

    /**
     * web容器选择,默认 内置Jetty
     * @return web容器类型
     */
    default ServerType serverType(){
        return ServerType.INNER_JETTY;
    }

    /**
     * 静态资源目录,默认 src/main/resources/static
     * @return 静态资源目录
     */
    default String staticPath(){
        return "src/main/resources/static";
    }
}
