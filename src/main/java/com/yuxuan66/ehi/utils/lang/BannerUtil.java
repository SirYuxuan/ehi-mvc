package com.yuxuan66.ehi.utils.lang;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 读取显示banner
 *
 * @author Sir丶雨轩
 * @date 2019/1/18 15:46
 */

public enum BannerUtil {
    ;
    public static void write() {
        InputStream is = BannerUtil.class.getResourceAsStream("/banner.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String s = null;
            while ((s = br.readLine()) != null) {
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                is.close();
            } catch (IOException e) {

            }
        }


    }

}
