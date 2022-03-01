package com.springbear.dms.utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * 数据库 dml 操作工具类
 * @author Spring-_-Bear
 * @version 2021-11-18 09:40
 */


public class DbUtil {

    Connection connection = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    /**
     * 执行 DML 语句并返回该条语句生成的主键值
     * @param sql SQL 语句
     * @param param SQL 参数
     * @return 数据库自动生成的主键值
     */
    public int executeUpdate(String sql, Object... param) {
        int generatedKey = -1;
        try {
            connection = DruidUtil.getConnection();

            preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            if (param != null) {
                for (int i = 0; i < param.length; i++) {
                    preparedStatement.setObject(i + 1, param[i]);
                }
            }
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                generatedKey = generatedKeys.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(resultSet, preparedStatement, connection);
        }
        return generatedKey;
    }
}

