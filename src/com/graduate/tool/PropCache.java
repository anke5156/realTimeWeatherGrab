package com.graduate.tool;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * 装在字典文件的缓存对象
 * Created by anke on 2017/3/22.
 */
public class PropCache {
    private static Logger logger = LogManager.getLogger(PropCache.class);


    private static Map<String, Map<String, String>> props = new HashMap<>();

    /**
     * 获取所有缓存
     *
     * @return
     */
    public static Map<String, Map<String, String>> getProps() {
        return props;
    }

    /**
     * 根据名字获取缓存对象
     *
     * @param propName
     * @return
     */
    public static Map<?, ?> getProp(String propName) {
        logger.debug("获取缓存对象：" + propName);
        Map<String, String> map = props.get(propName);
        return map == null ? new HashMap<>() : map;
    }

    /**
     * 增加一个缓存对象
     *
     * @param cacheName
     * @param prop
     */
    public static void addProp(String cacheName, Map<String, String> prop) {
        if (StrKit.isBlank(cacheName)) return;
        if (props.containsKey(cacheName)) {
            try {
                throw new Exception("缓存名[" + cacheName + "] 已经存在！");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        props.put(cacheName, prop);
    }
}
