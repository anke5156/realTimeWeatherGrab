package com.graduate.core;

import com.google.gson.Gson;
import com.graduate.bean.WeatherBean;
import com.graduate.tool.CommonUtil;
import com.graduate.tool.FetchURL;
import com.graduate.tool.PropCache;
import com.graduate.tool.WRConfFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anke on 2017/4/20.
 */
public class RealTimeWeatherGrab {
    Logger logger = LogManager.getLogger(RealTimeWeatherGrab.class);
    Gson gson = new Gson();
    InsertDate insetData = new InsertDate();
    WeatherBean weathbean = new WeatherBean();

    /***获取实时天气数据接口
     *
     * @return
     */
    public void doinit() {
        Map cityMap = PropCache.getProp("code_city");
        String ctime = CommonUtil.getLocalSysTime1();
        for (Object key : cityMap.keySet()) {
            String urlPath = getParams(key.toString());
            JSONObject rsJson = FetchURL.httpGet(urlPath);
            try {
                rsJson.put("ctime", ctime);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            logger.info(rsJson);
            int ssc = insetData.insert(dealWithRust(rsJson));
        }
    }

    /***对实时天气接口参数进行封装
     *
     * @param cityCode
     * @return
     */
    public String getParams(String cityCode) {
        String url = WRConfFile.RValue("config/URL.Path.properties", "url");
        String appkey = WRConfFile.RValue("config/URL.Path.properties", "appkey");
        String sign = WRConfFile.RValue("config/URL.Path.properties", "sign");
        String format = WRConfFile.RValue("config/URL.Path.properties", "format");
        return url + "&weaid=" + cityCode + "&" + appkey + "&" + sign + "&" + format;
    }

    /***对返回数据进行处理
     *
     * @param rsJson
     * @return
     */
    public Map dealWithRust(JSONObject rsJson) {
        Map<String, String> rsMap = new HashMap<>();
        WeatherBean weaBean = gson.fromJson(rsJson.toString(), WeatherBean.class);
        rsMap.put("paramid", CommonUtil.getUUID());
        rsMap.put("success", weaBean.getSuccess());
        rsMap.put("temp_low", weaBean.getResult().getTemp_low());
        rsMap.put("cityno", weaBean.getResult().getCityno());
        rsMap.put("wind", weaBean.getResult().getWind());
        rsMap.put("citynm", weaBean.getResult().getCitynm());
        rsMap.put("weaid", weaBean.getResult().getWeaid());
        rsMap.put("winpid", weaBean.getResult().getWinpid());
        rsMap.put("windid", weaBean.getResult().getWind());
        rsMap.put("weatid1", weaBean.getResult().getWeatid());
        rsMap.put("humi_high", weaBean.getResult().getHumi_high());
        rsMap.put("cityid", weaBean.getResult().getCityid());
        rsMap.put("temperature", weaBean.getResult().getTemperature());
        rsMap.put("temperature_curr", weaBean.getResult().getTemperature_curr());
        rsMap.put("temp_high", weaBean.getResult().getTemp_high());
        rsMap.put("humidity", weaBean.getResult().getHumidity());
        rsMap.put("days", weaBean.getResult().getDays());
        rsMap.put("weather_icon", weaBean.getResult().getWeather_icon());
        rsMap.put("weatid", weaBean.getResult().getWeatid());
        rsMap.put("temp_curr", weaBean.getResult().getTemp_curr());
        rsMap.put("humi_low", weaBean.getResult().getHumi_low());
        rsMap.put("weather", weaBean.getResult().getWeather());
        rsMap.put("weather_curr", weaBean.getResult().getWeather_curr());
        rsMap.put("winp", weaBean.getResult().getWinp());
        rsMap.put("weather_icon1", weaBean.getResult().getWeather_icon1());
        rsMap.put("week", weaBean.getResult().getWeek());
        rsMap.put("ctime", weaBean.getCtime());
        return rsMap;
    }


}
