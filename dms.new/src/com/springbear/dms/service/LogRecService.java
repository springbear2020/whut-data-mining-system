package com.springbear.dms.service;

import com.springbear.dms.utils.AppendObjectOutputStream;
import com.springbear.dms.entity.DataBase;
import com.springbear.dms.entity.LogRec;
import com.springbear.dms.entity.MatchedLogRec;
import com.springbear.dms.utils.DbUtil;

import java.io.*;
import java.net.InetAddress;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * 日志信息服务类
 *
 * @author Spring-_-Bear
 * @version 2021-10-26 07:54
 **/
public class LogRecService {
    DbUtil dbUtil = new DbUtil();

    /**
     * 日志数据采集
     *
     * @return 采集到的日志数据对象
     */
    public LogRec inputLog() {
        LogRec logRec = null;
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("请输入ID标识:");
            int id = scanner.nextInt();
            Date time = new Date();
            System.out.print("请输入地址:");
            String address = scanner.next();
            // 数据操作状态为采集
            int type = DataBase.GATHER;
            System.out.print("请输入登录用户名:");
            String user = scanner.next();
            // 自动获取ip地址
            InetAddress inetAddress = InetAddress.getLocalHost();
            String ip = inetAddress.getHostAddress();
            System.out.println("自动获取IP：" + ip);
            System.out.print("请输入登录状态: [1]登录 [2]登出 :");
            int logType = scanner.nextInt();

            // 创建日志对象并初始化
            logRec = new LogRec(id, time, address, type, user, ip, logType);
            System.out.println("一条日志信息采集完成！");
        } catch (Exception e) {
            System.out.println("采集的日志信息不合法!");
        }

        return logRec;
    }

    /**
     * 输出匹配得到的日志登录、登出信息对
     *
     * @param matchedLogRecs 匹配得到的日志登录、登出信息对
     */
    public void showMatchedLogRec(ArrayList<MatchedLogRec> matchedLogRecs) {
        if (matchedLogRecs == null) {
            return;
        }
        for (MatchedLogRec matchedLogRec : matchedLogRecs) {
            if (matchedLogRec != null) {
                System.out.println(matchedLogRec);
            }
        }
    }

    /**
     * 将匹配得到的日志集合中的对象序列化后追加保存到 data 目录下的 MatchedLogRecs.txt 中
     *
     * @param matchedLogRecs 日志匹配成功后得到的对象集合
     */
    public boolean saveAppendMatchedLogRec(ArrayList<MatchedLogRec> matchedLogRecs) {
        File file = new File("dms.new\\resources\\data\\MatchedLogRecs.txt");
        AppendObjectOutputStream appendObjectOutputStream = null;
        AppendObjectOutputStream.setFile(file);

        if (matchedLogRecs.size() == 0) {
            System.out.println("没有匹配的日志信息！请将数据匹配后再保存！");
            return false;
        }
        try {
            appendObjectOutputStream = new AppendObjectOutputStream(file);
            // 将集合中的每一个日志对象依次保存到文件中
            for (MatchedLogRec matchedLogRec : matchedLogRecs) {
                if (matchedLogRec != null) {
                    appendObjectOutputStream.writeObject(matchedLogRec);
                    appendObjectOutputStream.flush();
                }
            }
            System.out.println("日志信息成功保存到文件！");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (appendObjectOutputStream != null) {
                    appendObjectOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    /**
     * 从文件中反序列化读取日志对象并保存到集合中
     *
     * @return 从文件中读取得到的日志对象集合
     */
    public ArrayList<MatchedLogRec> readMatchedLogRec() {
        ObjectInputStream objectInputStream = null;
        ArrayList<MatchedLogRec> matchedLogRecs = new ArrayList<>();
        File file = new File("dms.new\\resources\\data\\MatchedLogRecs.txt");

        if (file.length() == 0) {
            return null;
        }

        try {

            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            // 循环从文件中读取对象，直到文件尾，使用捕获异常的方式结束循环
            // 原因：对象输入流读到文件尾判断条件既不是 null 也不是 -1
            while (true) {
                try {
                    MatchedLogRec matchedLogRec = (MatchedLogRec) objectInputStream.readObject();
                    matchedLogRecs.add(matchedLogRec);
                } catch (EOFException e) {
                    break;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return matchedLogRecs;
    }

    /**
     * 将匹配的日志信息分解为登录、登出两个对象保存到 gather_log 表
     * 将登录、登出日志信息 id 保存到 matched_log 表
     *
     * @param matchedLogRecs 匹配的日志对象 matchedLogRec 集合
     */
    public boolean saveMatchedLogsToDb(ArrayList<MatchedLogRec> matchedLogRecs) {
        int loginId;
        int logoutId;
        for (MatchedLogRec matchedLogRec : matchedLogRecs) {
            // 获取登录、登出日志
            LogRec login = matchedLogRec.getLogin();
            LogRec logout = matchedLogRec.getLogout();

            // 从一条登录日志中获取信息
            Object[] params = new Object[]{
                    new Timestamp(login.getTime().getTime()),
                    login.getAddress(), login.getType(), login.getUser(),
                    login.getIp(), login.getLogType()
            };
            // 将登录日志信息保存到数据库的 gather_log 表中
            String sql = "INSERT INTO `gather_log`(`time`,address,`type`,username,ip,log) VALUES (?,?,?,?,?,?);";
            if ((loginId = dbUtil.executeUpdate(sql, params)) > 0) {
                System.out.println("登录日志信息保存到数据库成功！");
            }

            // 从一条登出日志中获取信息
            params = new Object[]{
                    new Timestamp(logout.getTime().getTime()),
                    logout.getAddress(), logout.getType(), logout.getUser(),
                    logout.getIp(), logout.getLogType()
            };
            if ((logoutId = dbUtil.executeUpdate(sql, params)) > 0) {
                System.out.println("登出日志信息保存到数据库成功！");
            }

            // 将登录、登出日志 id 保存到数据库的 matched_log 表中
            sql = "INSERT INTO matched_log VALUES(?,?);";
            params = new Object[]{loginId, logoutId};
            dbUtil.executeUpdate(sql, params);
            System.out.println("日志信息成功保存到数据库！");
            return true;
        }
        return false;
    }
}
