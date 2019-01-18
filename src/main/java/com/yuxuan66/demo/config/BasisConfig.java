package com.yuxuan66.demo.config;


import com.yuxuan66.ehi.support.core.config.AppConfig;

/**
 * @author Sir丶雨轩
 * @date 2019/1/18 22:44
 */
public class BasisConfig implements AppConfig {
    @Override
    public int port() {
        return 8089;
    }
}
