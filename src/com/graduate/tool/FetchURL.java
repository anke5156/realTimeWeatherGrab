package com.graduate.tool;

import com.google.gson.Gson;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLDecoder;

public class FetchURL {

    static Logger logger = LogManager.getLogger(FetchURL.class);
    private static Gson gson = new Gson();

    /***
     * post请求
     * @param url url地址
     * @param jsonParam 参数
     * @param noNeedResponse 不需要返回结果
     * @return
     */
    public JSONObject httpPost(String url, JSONObject jsonParam, boolean noNeedResponse) {
        // post请求返回结果
        DefaultHttpClient httpClient = new DefaultHttpClient();
        JSONObject jsonResult = null;
        HttpPost method = new HttpPost(url);
        try {
            if (null != jsonParam) {
                // 解决中文乱码问题
                StringEntity entity = new StringEntity(jsonParam.toString(),
                        "utf-8");
                entity.setContentEncoding("UTF-8");
                entity.setContentType("application/json");
                method.setEntity(entity);
            }
            HttpResponse result = httpClient.execute(method);
            url = URLDecoder.decode(url, "UTF-8");
            /** 请求发送成功，并得到响应 **/
            if (result.getStatusLine().getStatusCode() == 200) {
                try {
                    /** 读取服务器返回过来的json字符串数据 **/
                    EntityUtils.toString(result.getEntity());
                    if (noNeedResponse) {
                        return null;
                    }
                } catch (Exception e) {
                    logger.debug("post请求提交失败:" + url + "------" + e);
                }
            }
        } catch (IOException e) {
            logger.debug("post请求提交失败:" + url + "------" + e);
        }
        return jsonResult;
    }

    /***发送get请求,获取数据集
     *
     * @param url
     * @return
     */
    public static JSONObject httpGet(String url) {
        String rsStr = null;
        JSONObject rsJson = null;
        // get请求返回结果
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            // 发送get请求
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(request);

            //请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                //读取服务器返回过来的json字符串数据
                rsStr = EntityUtils.toString(response.getEntity(), "utf8");
            } else {
                logger.debug(response);
            }
        } catch (IOException e) {
            logger.error(e);
        }
        try {
            rsJson = new JSONObject(rsStr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rsJson;
    }

}
