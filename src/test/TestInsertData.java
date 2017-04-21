package test;

import com.graduate.core.InsertDate;
import com.graduate.tool.CommonUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anke on 2017/4/21.
 */
public class TestInsertData {

    @Test
    public void testInsert(){
        InsertDate insert = new InsertDate();
        Map<String,String> map = new HashMap<>();
        map.put("paramid", CommonUtil.getUUID());
        insert.insert(map);
    }
}
