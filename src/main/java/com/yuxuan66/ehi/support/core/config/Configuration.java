package com.yuxuan66.ehi.support.core.config;

import com.yuxuan66.ehi.support.core.server.ServerType;


/**
 * 系统核心配置
 * @author Sir丶雨轩
 * @date 2019/1/18 22:19
 */
public final class Configuration {

    /**
     * 服务端口
     */
    private int prot;
    /**
     * web容器类型
     */
    private ServerType serverType;
    /**
     * 静态资源目录
     */
    private String staticPath;

    public String getStaticPath() {
        return staticPath;
    }

    public void setStaticPath(String staticPath) {
        this.staticPath = staticPath;
    }

    public int getProt() {
        return prot;
    }

    public void setProt(int prot) {
        this.prot = prot;
    }

    public ServerType getServerType() {
        return serverType;
    }

    public void setServerType(ServerType serverType) {
        this.serverType = serverType;
    }
}
