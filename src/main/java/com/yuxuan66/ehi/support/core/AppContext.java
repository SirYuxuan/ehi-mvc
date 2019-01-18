package com.yuxuan66.ehi.support.core;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Singleton;
import com.yuxuan66.ehi.support.core.config.Configuration;
import com.yuxuan66.ehi.support.mvc.MappingHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * App核心容器,存储bean、config等
 *
 * @author Sir丶雨轩
 * @date 2019/1/18 22:16
 */
public final class AppContext {

    /**
     * 系统核心配置
     */
    private Configuration configuration;
    /**
     * 所有请求映射
     */
    private Map<String, MappingHandler> mappingHandlerMap = new HashMap<>();

    /**
     * 创建唯一容器实例
     *
     * @return appContext 实例
     */
    public static AppContext getInstance() {
        return Singleton.get(AppContext.class);
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }


    public Configuration getConfiguration() {
        return this.configuration;
    }

    public void addMapping(String url, MappingHandler mappingHandler) {
        mappingHandlerMap.put(url, mappingHandler);
    }

    public Map<String, MappingHandler> getMappingHandlerMap(){
        return this.mappingHandlerMap;
    }

}
