package com.graduate.core;

import com.graduate.tool.CommonUtil;
import com.graduate.tool.FetchURL;
import com.graduate.tool.PropCache;
import com.graduate.tool.WRConfFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by anke on 2017/4/20.
 */
public class RealTimeWeatherGrab {
    Logger logger = LogManager.getLogger(RealTimeWeatherGrab.class);

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
        }
    }

    public String getParams(String cityCode) {
        String url = WRConfFile.RValue("config/URL.Path.properties", "url");
        String appkey = WRConfFile.RValue("config/URL.Path.properties", "appkey");
        String sign = WRConfFile.RValue("config/URL.Path.properties", "sign");
        String format = WRConfFile.RValue("config/URL.Path.properties", "format");
        return url + "&weaid=" + cityCode + "&" + appkey + "&" + sign + "&" + format;
    }
}
