package com.qst.dms.util;

import java.io.InputStream;
import java.util.Properties;

//配置类
public class Config {

    private static Properties p = null;

    static {
        try {
            p = new Properties();
            InputStream resourceAsStream = Config.class.getClassLoader().getResourceAsStream("mysql.properties");
            //加载配置类
            p.load(resourceAsStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取配置类的参数
    public static String getValue(String key) {
        return p.get(key).toString();
    }
}
