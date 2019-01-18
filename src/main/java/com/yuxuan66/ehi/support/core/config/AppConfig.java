package com.yuxuan66.ehi.support.core.config;

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
}
