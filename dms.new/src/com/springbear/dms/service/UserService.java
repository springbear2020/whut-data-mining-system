package com.springbear.dms.service;

import com.springbear.dms.entity.User;
import com.springbear.dms.utils.DbUtil;
import com.springbear.dms.utils.DruidUtil;

import java.sql.*;

/**
 * 用户信息服务类
 *
 * @author Spring-_-Bear
 * @version 2021-11-16 16:59
 */
public class UserService {
    DbUtil dbUtil = new DbUtil();

    /**
     * 将用户信息保存到数据库
     *
     * @param user 用户信息对象
     * @return ture:保存成功；false：保存失败
     */
    public boolean saveUserToDb(User user) {
        String sql = "INSERT INTO user_info (username,`password`,sex,phone,address) VALUES(?,?,?,?,?);;";
        Object[] params = new Object[]{
                user.getUsername(), user.getPassword(),
                user.getSex(), user.getPhone(), user.getAddress()
        };
        // 插入数据成功
        return dbUtil.executeUpdate(sql, params) != -1;
    }

    /**
     * 查询数据库中是否存在此用户
     *
     * @param username 用户名
     * @return 用户名对应的用户信息
     */
    public User findUserByName(String username) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String sql = "SELECT `username`,`password` FROM `user_info` WHERE `username` = ?;";

        try {
            connection = DruidUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if (resultSet != null && resultSet.next()) {
                return new User(resultSet.getString(1), resultSet.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DruidUtil.close(resultSet, preparedStatement, connection);
        }
        return null;
    }
}
