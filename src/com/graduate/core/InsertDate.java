package com.graduate.core;

import com.graduate.tool.ConnDB;
import com.graduate.tool.WRConfFile;

import java.sql.SQLException;
import java.util.Map;

/**
 * 数据库插入数据模块 所有的数据库插入操作通过该类来实现
 *
 * @author anke 2016年9月19日20:11:00
 */
public class InsertDate {
    WRConfFile wrcf = new WRConfFile();
//    PreparedStatement pst = null;

    String intosql = wrcf.RValue("config/URL.Path.properties", "intosql");
    String selesql = wrcf.RValue("config/URL.Path.properties", "selesql");

    /***
     * 执行sql,存储数据
     */
    public int insert(Map<String, String> map) {
        if (map == null || map.isEmpty()) return 0;
        String paramid = map.get("paramid");
        String success = map.get("success");
        String temp_low = map.get("temp_low");
        String cityno = map.get("cityno");
        String wind = map.get("wind");
        String citynm = map.get("citynm");
        String weaid = map.get("weaid");
        String winpid = map.get("winpid");
        String windid = map.get("windid");
        String weatid1 = map.get("weatid1");
        String humi_high = map.get("humi_high");
        String cityid = map.get("cityid");
        String temperature = map.get("temperature");
        String temperature_curr = map.get("temperature_curr");
        String temp_high = map.get("temp_high");
        String humidity = map.get("humidity");
        String days = map.get("days");
        String weather_icon = map.get("weather_icon");
        String weatid = map.get("weatid");
        String temp_curr = map.get("temp_curr");
        String humi_low = map.get("humi_low");
        String weather = map.get("weather");
        String weather_curr = map.get("weather_curr");
        String winp = map.get("winp");
        String weather_icon1 = map.get("weather_icon1");
        String week = map.get("week");
        String ctime = map.get("ctime");
        try {
            return ConnDB.insert(intosql,
                    paramid,
                    success,
                    temp_low,
                    cityno,
                    wind,
                    citynm,
                    weaid,
                    winpid,
                    windid,
                    weatid1,
                    humi_high,
                    cityid,
                    temperature,
                    temperature_curr,
                    temp_high,
                    humidity,
                    days,
                    weather_icon,
                    weatid,
                    temp_curr,
                    humi_low,
                    weather,
                    weather_curr,
                    winp,
                    weather_icon1,
                    week,
                    ctime
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /***更新参数表
     *
     * @throws SQLException
     */
    public int upData() throws SQLException {
        try {
            return ConnDB.update("");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

}
