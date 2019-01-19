package com.yuxuan66.ehi.support.core;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Singleton;
import com.yuxuan66.ehi.support.core.config.Configuration;
import com.yuxuan66.ehi.support.mvc.MappingHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
     * 保存当前请求的request
     */
    private ThreadLocal<HttpServletRequest> requestThreadLocal = new ThreadLocal<>();
    /**
     * 保存当前请求的response
     */
    private ThreadLocal<HttpServletResponse> responseThreadLocal = new ThreadLocal<>();
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

    public HttpServletRequest getRequest() {
        return requestThreadLocal.get();
    }

    public void setRequest(HttpServletRequest request) {
        requestThreadLocal.set(request);
    }

    public HttpServletResponse getResponse() {
        return responseThreadLocal.get();
    }

    public void setResponse(HttpServletResponse response) {
        responseThreadLocal.set(response);
    }

    public Map<String, MappingHandler> getMappingHandlerMap(){
        return this.mappingHandlerMap;
    }

}
