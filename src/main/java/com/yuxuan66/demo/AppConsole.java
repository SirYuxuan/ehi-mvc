package com.yuxuan66.demo;

import com.yuxuan66.ehi.EhiMVC;
import com.yuxuan66.ehi.support.core.annotaion.AppStartup;

/**
 * @author Sir丶雨轩
 * @date 2019/1/17 10:55
 */
@AppStartup
public class AppConsole {

    public static void main(String[] args) {
        EhiMVC.start();
    }
}
