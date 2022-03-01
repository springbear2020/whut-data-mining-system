package com.springbear.dms.utils;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 德鲁伊连接池类
 *
 * @author Spring-_-Bear
 * @version 2021-11-11 08:19
 */
public class DruidUtil {
    static DataSource dataSource;
    static String path = "dms.new\\config\\druid.properties";

    static {
        Properties properties = new Properties();
        try {
            // 加载配置文件
            properties.load(new FileInputStream(path));
            // 使用 Druid 方法获得 DataSource
            dataSource = DruidDataSourceFactory.createDataSource(properties);
            System.out.println("正在初始化 Druid 数据库连接池···");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从 Druid 连接池中获得一个数据库连接
     *
     * @return 一个数据库连接对象 connection
     * @throws SQLException 将异常抛出，方便调用方处理
     */
    public static Connection getConnection() throws SQLException {
        System.out.println("正在从 Druid 连接池获得一个数据库连接···");
        return dataSource.getConnection();
    }

    /**
     * 关闭连接，释放资源
     *
     * @param resultSet  none
     * @param statement  none
     * @param connection none
     */
    public static void close(ResultSet resultSet, Statement statement, Connection connection) {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
            System.out.println("成功释放一个数据库连接到 Druid 连接池！");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
