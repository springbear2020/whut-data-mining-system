package com.qst.dms.service;

import java.sql.ResultSet;

import com.mysql.jdbc.Statement;
import com.qst.dms.db.DBUtil;
import com.qst.dms.entity.User;

public class UserService {
	private static Statement statement;  //revised by yyc 20191012
	// 根据用户名查询用户
	public User findUserByName(String userName) {
		DBUtil db = new DBUtil();
		User user = null;
		try {
			// 获取数据库链接
			db.getConnection();
			// 使用PreparedStatement发送sql语句
			String sql = "SELECT * FROM userdetails WHERE username=?";
			// 设置参数
			Object[] param = new Object[] { userName };
			// 执行查询
			ResultSet rs = db.executeQuery(sql, param);
			if (rs.next()) {
				// 将结果集中的数据封装到对象中
				user = new User(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getInt(4), rs.getString(5), rs.getString(6),
						rs.getString(7));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库的连接
			db.closeAll();
		}
		// 返回用户对象
		return user;
	}

	// 保存用户信息
	public boolean saveUser(User user) {
		// 定义一个布尔返回值，初始值为false
		boolean r = false;
		DBUtil db = new DBUtil();
		try {
			// 获取数据库连接
			db.getConnection();
			//start revised by yyc 20191012
//			String sqltest = "SELECT * FROM userdetails WHERE username=user";
//			ResultSet rs = statement.executeQuery(sqltest);
			
			//start revised by yyc 20191126
			String sqltest = "SELECT * FROM userdetails WHERE username='"+user.getUsername()+"'";
			ResultSet rs = db.executeQuery(sqltest,null);
			if(rs.next())
				return r;
			//end
				 // 使用PreparedStatement发送sql语句
			String sql = "INSERT INTO userdetails(username,password,sex,hobby,address,degree) VALUES (?,?,?,?,?,?)";
			// 设置参数
			Object[] param = new Object[] { user.getUsername(),
					user.getPassword(), user.getSex(), user.getHobby(),
					user.getAddress(), user.getDegree() };
			// 判断数据是否保存成功
			if (db.executeUpdate(sql, param) > 0) {
				// 保存成功，设置返回值为true
				r = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭数据库的连接
			db.closeAll();
		}
		// 返回
		return r;
	}
}
