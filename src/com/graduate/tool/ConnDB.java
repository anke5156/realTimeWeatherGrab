package com.graduate.tool;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 数据库链接模块
 * 所有的链接数据库通过该类来实现
 * @author anke
 * 2016年9月19日20:11:00
 */
@SuppressWarnings("unchecked")
public class ConnDB {
    /**
     * 参数
     */
    private static final String driver;
    private static final String url;
    private static final String username;
    private static final String userpsd;

    private Connection con = null;

    public static void main(String[] args) throws SQLException {
        List<Map> list = selectList("select paramid,status from tb_lear_real_time_weath_grab t where 1=?", "1");
        for (Map map : list) System.out.println(map);
//
        System.out.println("------------------");
        System.out.println(selectOne("select * from tb_lear_real_time_weath_grab ", null));

//		System.out.println(insert("insert into tb_ml_security_resources(resources_id,system_id) values(?,?)","9911","aatest"));
    }

    static {
        // 驱动程序名
        driver = "com.mysql.jdbc.Driver";
//		driver = "oracle.jdbc.driver.OracleDriver";
        // URL指向要访问的数据库名
        url = "jdbc:mysql://localhost/learn_test";
//		url = "jdbc:oracle:thin:@192.168.1.4:1521:orcl";
        // 数据库配置时的用户名
        username = "root";
        // 数据库配置时的密码
        userpsd = "123456";
        // 遍历查询结果集

        try {
            // 加载驱动程序
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            // 数据库驱动类异常处理
            System.out.println("Sorry,can`t connect the Database!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取连接
     *
     * @return
     * @throws SQLException
     */
    // private static Connection getConnection() throws SQLException{
    private static Connection getConnection() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, userpsd);
            conn.setAutoCommit(true);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    /**
     * 选择列表
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static List<Map> selectList(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        List<Map> list = null;
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            handleParams(pstmt, params);
            ResultSet resultSet = pstmt.executeQuery();
            list = handleResultSet(resultSet);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) conn.close();
        }
        return list;
    }

    /**
     * 插入操作
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int insert(String sql, Object... params) throws SQLException {
        Connection conn = getConnection();
        try {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            handleParams(pstmt, params);
            return pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return 0;
    }

    /**
     * 更新操作
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static int update(String sql, Object... params) throws SQLException {
        return insert(sql, params);
    }

    /**
     * 处理结果集
     *
     * @param resultSet
     * @return
     */
    private static List<Map> handleResultSet(ResultSet resultSet) {
        if (resultSet == null)
            return null;

        List<Map> list = new ArrayList<Map>();
        try {
            ResultSetMetaData rsmd = resultSet.getMetaData();
            int size = rsmd.getColumnCount();
            while (resultSet.next()) {
                Map<String, Object> map = new HashMap<String, Object>();

                for (int i = 1; i <= size; i++) {
                    Object val = resultSet.getObject(i);
                    String key = rsmd.getColumnName(i);
                    map.put(key, val);
                }
                list.add(map);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通用选择一条结果，若无结果则返回null
     *
     * @param sql
     * @param params
     * @return
     * @throws SQLException
     */
    public static Map selectOne(String sql, Object... params) throws SQLException {
        List<Map> list = selectList(sql, params);
        if (list != null && !list.isEmpty()) return list.get(0);
        else return null;
    }


    public void closeAll() {
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 处理参数
     * TODO 可以根据参数具体类型实现更精细化的控制
     *
     * @param pstmt
     * @param params
     */
    private static void handleParams(PreparedStatement pstmt, Object... params) {
        if (pstmt != null && params != null && params.length > 0) {
            int len = params.length;
            for (int i = 0; i < len; i++) {
                try {
                    pstmt.setObject(i + 1, params[i]);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}