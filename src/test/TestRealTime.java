package test;

import com.graduate.core.RealTimeWeatherGrab;
import com.graduate.tool.PropUtils;
import org.junit.Test;

/**
 * Created by anke on 2017/4/22.
 */
public class TestRealTime {

    public static final String FILE_PATH = "data/";
    public static final String FILE_NAME = "code_city.csv";

    @Test
    public void testRealTime(){

        //加载城市代码列表,用于后面的查询城市天气
        PropUtils.checkPropFileToCache(FILE_PATH + FILE_NAME, "code_city");

        RealTimeWeatherGrab rtwg = new RealTimeWeatherGrab();
        rtwg.doinit();
    }
}
