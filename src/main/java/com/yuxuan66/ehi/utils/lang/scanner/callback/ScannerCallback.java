package com.yuxuan66.ehi.utils.lang.scanner.callback;

import java.util.List;

/**
 * 扫描回调接口
 * @author Sir丶雨轩
 * @date 2019/1/18 15:01
 */
public interface ScannerCallback {
    /**
     * 回调方法
     *
     * @param clzs
     */
    void callback(List<Class> clzs);
}
