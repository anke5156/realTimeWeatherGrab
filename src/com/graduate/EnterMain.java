package com.graduate;

import com.graduate.core.RealTimeWeatherGrab;
import com.graduate.tool.PropUtils;
import com.graduate.tool.WRConfFile;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by anke on 2017/4/20.
 */
public class EnterMain {
    static Logger logger = LogManager.getLogger(EnterMain.class);
    public static final String FILE_PATH = "data/";
    public static final String FILE_NAME = "code_city.csv";

    public static void main(String[] args) {

        //加载城市代码列表,用于后面的查询城市天气
        PropUtils.checkPropFileToCache(FILE_PATH + FILE_NAME, "code_city");

        logger.info("获取实时天气数据开始,定时执行...");

        long hourSpan = Long.parseLong(WRConfFile.RValue("config/URL.Path.properties", "hourSpan"));
        String beginTime = WRConfFile.RValue("config/URL.Path.properties", "beginTime");

        logger.info("开始时间: " + beginTime);
        logger.info("执行间隔:" + hourSpan);
        schedule(beginTime, hourSpan);
    }

    /***定时器
     *
     */
    public static void schedule(String beginTime, long hourSpan) {

        final RealTimeWeatherGrab rtwg = new RealTimeWeatherGrab();

        // 每小时的毫秒数
//        long hourSpan = 60 * 60 * 1000;
        //测试过程,将时间调短了
//        long hourSpan = 60 * 1000;
        // 规定的首次执行时间为 16:38:00
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd '" + beginTime + "'");
        // 首次运行时间
        Date startTime = null;
        try {
            startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 如果今天的已经过了 首次运行时间就改为明天
        if (System.currentTimeMillis() > startTime.getTime()) {
            startTime = new Date(startTime.getTime() + hourSpan * 24);
        }
        Timer t = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                // 要执行的代码
                rtwg.doinit();
            }
        };

        // 开始执行调度
        t.scheduleAtFixedRate(task, startTime, hourSpan);
    }
}
