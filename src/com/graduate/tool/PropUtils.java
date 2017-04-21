package com.graduate.tool;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 加載字典文件的工具類
 * Created by anke on 2017/3/27.
 */
public class PropUtils {


    /**
     * 把文件中的键值对加载到缓存中
     *
     * @param filePath  文件相对路径
     * @param cacheName 缓存名
     */
    public static void checkPropFileToCache(String filePath, String cacheName) {
        checkPropFileToCache(filePath, cacheName, ",", "utf-8");
    }

    public static void checkPropFileToCache(String filePath, String cacheName, String regex) {
        checkPropFileToCache(filePath, cacheName, regex, "utf-8");
    }

    /**
     * 把文件中的键值对加载到缓存中
     *
     * @param filePath  文件相对路径
     * @param cacheName 缓存名
     * @param regex     分隔符
     */
    public static void checkPropFileToCache(String filePath, String cacheName, String regex, String encoding) {
        Map<String, String> props = new HashMap<>();
        try {
            LineIterator li = FileUtils.lineIterator(new File(filePath), encoding);
            while (li.hasNext()) {
                String line = li.next();
                String[] lineSub;
                if (StrKit.isBlank(line) || !line.contains(regex)) return;
                lineSub = line.split(regex);
                String key = lineSub[0].trim();
                String value = lineSub[1];
                props.put(key, value);
            }
            PropCache.addProp(cacheName, props);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
