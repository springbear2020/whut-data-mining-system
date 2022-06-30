package com.qst.dms.service;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.qst.dms.db.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

import com.qst.dms.entity.AppendAndObjectOutputStream;
import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;

//日志业务类
public class LogRecService {

    private Connection coon;

    // 日志数据采集
    public LogRec inputLog() {
        LogRec log = null;
        ExtraService gainIp = new ExtraService();   //创建额外服务类的对象
        // 建立一个从键盘接收数据的扫描器
        Scanner scan = new Scanner(System.in);
        try {

            // 提示用户输入ID标识
            System.out.println("请输入ID标识：");
            // 接收键盘输入的整数
            int id = scan.nextInt();

            // 获取当前系统时间
            Date nowDate = new Date();

            // 提示用户输入地址
            System.out.println("请输入地址：");
            // 接收键盘输入的字符串信息
            String address = scan.next();

            // 数据状态是“采集”
            int type = DataBase.GATHER;

            // 提示用户输入登录用户名
            System.out.println("请输入登录用户名:");
            // 接收键盘输入的字符串信息
            String user = scan.next();

            //自动获取IP地址
            // 接收键盘输入的字符串信息
            String ip = gainIp.showIpAddress();

            // 提示用户输入登录状态、登出状态
            System.out.println("请输入登录状态:1是登录，0是登出");
            int logType = scan.nextInt();

            // 创建日志对象
            log = new LogRec(id, nowDate, address, type, user, ip, logType);
            System.out.println("日志信息采集完成！");
        } catch (Exception e) {
            System.out.println("采集的日志信息不合法");
        }
        // 返回日志对象
        return log;
    }

    // 匹配日志信息输出,参数是集合
    public void showMatchLog(ArrayList<MatchedLogRec> matchLogs) {
        for (MatchedLogRec e : matchLogs) {
            if (e != null) {
                System.out.println(e.toString());
            }
        }
    }

    //以可追加方式将输入的日志信息序列化后保存到文件
    public void saveAndAppendMatchLog(ArrayList<MatchedLogRec> matchLogs) {
        AppendAndObjectOutputStream aoos = null;
        // TODO Fix the problem that the jar can not find the file
        File file = new File("dms.old/resources/data/MatchLogs.txt");
        try {
            AppendAndObjectOutputStream.file = file;
            aoos = new AppendAndObjectOutputStream(file);
            //循环保存对象数据
            for (MatchedLogRec e : matchLogs) {
                if (e != null) {
                    //把对象写入文件中
                    aoos.writeObject(e);
                    aoos.flush();
                    System.out.println("日志信息记录到文件完成！");
                }

            }

        } catch (Exception ex) {
        } finally {
            if (aoos != null) {
                try {
                    aoos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //从文件中读匹配日志信息
    public ArrayList<MatchedLogRec> readMatchLog() {
        ArrayList<MatchedLogRec> matchLogs = new ArrayList<>();
        //创建一个ObjectInputStream对象输入流看，并连接文件输入流，读入MatchLogs.txt文件中
        InputStream resourceAsStream = LogRecService.class.getResourceAsStream("data/MatchLogs.txt");
        try (ObjectInputStream ois = new ObjectInputStream(resourceAsStream)) {
            MatchedLogRec matchLog;

            //更新，解决EOF—exception问题
            while (true) {
                try {
                    //将对象添加到泛型集合中
                    matchLog = (MatchedLogRec) ois.readObject();
                    matchLogs.add(matchLog);
                } catch (EOFException ex) {
                    break;
                }
            }
        } catch (Exception ex) {
            //ex.printStackTrace();
        }
        return matchLogs;
    }

    //匹配日志信息保存到数据库，参数是集合
    public void saveMatchLogToDB(ArrayList<MatchedLogRec> matchLogs) {
        DBUtil db = new DBUtil();
        try {
            //获取数据库连接
            db.getConnection();
            for (MatchedLogRec matchedLogRec : matchLogs) {
                //获取匹配的登录日志
                LogRec login = matchedLogRec.getLogin();
                //获取匹配的登出日志
                LogRec logout = matchedLogRec.getLogout();

                //保存匹配记录中的登录日志
                String sql = "INSERT INTO gather_logrec(id,time,address,type,username,ip,logtype) VALUES(?,?,?,?,?,?,?)";
                Object[] param = new Object[]{login.getId(),
                        new Timestamp(login.getTime().getTime()),
                        login.getAddress(), login.getType(), login.getUser(),
                        login.getIp(), login.getLogType()};
                db.executeUpdate(sql, param);
                //保存匹配中的登出日志
                param = new Object[]{logout.getId(),
                        new Timestamp(logout.getTime().getTime()),
                        logout.getAddress(), logout.getType(), logout.getUser(),
                        logout.getIp(), logout.getLogType()};
                db.executeUpdate(sql, param);

//                //保存匹配记录中的登录日志
//    			String sql = "INSERT INTO gather_logrec(time,address,type,username,ip,logtype) VALUES(?,?,?,?,?,?)";
//    			Object[] param = new Object[] {
//    					new Timestamp(login.getTime().getTime()),
//    					login.getAddress(),login.getType(),login.getUser(),
//    					login.getIp(),login.getLogType()};
//    			int loginKey = db.executeSQLAndReturnPrimaryKey(sql, param);
//    			login.setId(loginKey);
//
//    			//保存匹配中的登出日志
//    			param = new Object[] {
//    					new Timestamp(logout.getTime().getTime()),
//    					logout.getAddress(),logout.getType(),logout.getUser(),
//    					logout.getIp(),logout.getLogType()};
//
//     			int logoutKey = db.executeSQLAndReturnPrimaryKey(sql, param);
//    			logout.setId(logoutKey);

                //保存匹配日志的ID
                sql = "INSERT INTO matched_logrec(loginid,logoutid) VALUES(?,?)";
                param = new Object[]{login.getId(), logout.getId()};
                db.executeUpdate(sql, param);
            }
            //关闭数据库连接，释放资源
            db.closeAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //从数据库读匹配日志信息，返回匹配日志信息集合
    public ArrayList<MatchedLogRec> readMatchedLogFromDB() {
        ArrayList<MatchedLogRec> matchedLogRecs = new ArrayList<MatchedLogRec>();
        DBUtil db = new DBUtil();
        try {
            //获取数据库连接
            db.getConnection();
            //查询匹配的日志
            String sql = "SELECT i.id,i.time,i.address,i.type,i.username,i.ip,i.logtype,"
                    + "o.id,o.time,o.address,o.type,o.username,o.ip,o.logtype "
                    + "FROM matched_logrec m,gather_logrec i,gather_logrec o "
                    + "WHERE m.loginid=i.id AND m.logoutid=o.id";
            ResultSet rs = db.executeQuery(sql, null);
            while (rs.next()) {
                //获取登录记录
                LogRec login = new LogRec(rs.getInt(1), (Date) rs.getDate(2), rs.getString(3), rs.getInt(4),
                        rs.getString(5), rs.getString(6), rs.getInt(7));
                //获取登出记录
                LogRec logout = new LogRec(rs.getInt(8), (Date) rs.getDate(9), rs.getString(10), rs.getInt(11),
                        rs.getString(12), rs.getString(13), rs.getInt(14));
                //添加匹配登录信息到匹配集合
                MatchedLogRec matchedLog = new MatchedLogRec(login, logout);
                matchedLogRecs.add(matchedLog);
            }
            //关闭数据库连接，释放资源
            db.closeAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回匹配的日志信息集合
        return matchedLogRecs;
    }

    //获取数据库中的所有匹配的日志信息，返回一个ResultSet
    public ResultSet readLogResult() {
        DBUtil db = new DBUtil();
        ResultSet rs = null;
        try {
            // 获取数据库链接
            Connection conn = db.getConnection();
            // 查询匹配日志，设置ResultSet可以使用除了next()之外的方法操作结果集
            Statement st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            String sql = "SELECT * from gather_logrec";
            rs = st.executeQuery(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rs;
    }


}
