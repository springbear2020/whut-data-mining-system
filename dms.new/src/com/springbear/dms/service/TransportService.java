package com.springbear.dms.service;

import com.springbear.dms.utils.AppendObjectOutputStream;
import com.springbear.dms.entity.DataBase;
import com.springbear.dms.entity.MatchedTransport;
import com.springbear.dms.entity.Transport;
import com.springbear.dms.utils.DbUtil;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/**
 * 物流信息服务类
 *
 * @author Spring-_-Bear
 * @version 2021-10-25 22:19
 **/
public class TransportService {
    DbUtil dbUtil = new DbUtil();

    /**
     * 物流数据采集：返回采集到的一个 Transport 物流数据类对象
     *
     * @return Transport类的对象的引用
     */
    public Transport inputTransport() {
        Transport transport = null;

        // 建立一个从键盘接收数据的扫描器
        Scanner scanner = new Scanner(System.in);

        // 采集物流数据信息
        try {
            System.out.print("请输入ID标识:");
            int id = scanner.nextInt();
            Date time = new Date();
            System.out.print("请输入地址:");
            String address = scanner.next();
            // 数据操作状态为采集
            int type = DataBase.GATHER;

            System.out.print("请输入货物经手人:");
            String handler = scanner.next();
            System.out.print("请输入收货人:");
            String receiver = scanner.next();
            System.out.print("请输入物流状态: [1]发货中 [2]派送中 [3]已签收 :");
            int transportType = scanner.nextInt();

            // 创建物流信息类的对象并初始化
            transport = new Transport(id, time, address, type, handler, receiver, transportType);
            System.out.println("一条物流信息采集完成！");
        } catch (Exception e) {
            System.out.println("采集的物流数据不合法!");
        }

        return transport;
    }

    /**
     * 输出匹配后的物流信息“对”集合
     *
     * @param matchedTransports 匹配后的物流信息“对”集合
     */
    public void showMatchedTransport(ArrayList<MatchedTransport> matchedTransports) {
        if (matchedTransports == null) {
            return;
        }
        for (MatchedTransport matchedTransport : matchedTransports) {
            if (matchedTransport != null) {
                System.out.println(matchedTransport);
            }
        }
    }

    /**
     * 将匹配得到的物流集合中的对象序列化后追加保存到 data 目录下的 MatchedTransports.txt 中
     *
     * @param matchedTransports 物流匹配成功后得到的对象集合
     */
    public boolean saveAppendMatchedTransport(ArrayList<MatchedTransport> matchedTransports) {
        File file = new File("dms.new\\resources\\data\\MatchedTransports.txt");
        AppendObjectOutputStream appendObjectOutputStream = null;
        AppendObjectOutputStream.setFile(file);

        if (matchedTransports.size() == 0) {
            System.out.println("没有匹配的物流信息！请将数据匹配后再保存！");
            return false;
        }

        try {
            appendObjectOutputStream = new AppendObjectOutputStream(file);
            // 将集合中的每一个物流对象依次追加保存到文件中
            for (MatchedTransport matchedTransport : matchedTransports) {
                if (matchedTransport != null) {
                    appendObjectOutputStream.writeObject(matchedTransport);
                    appendObjectOutputStream.flush();
                }
            }
            System.out.println("物流信息成功保存到文件！");
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
     * 从文件中反序列化读取物流对象并保存到集合中
     *
     * @return 从文件中读取得到的物流对象集合
     */
    public ArrayList<MatchedTransport> readMatchedTransport() {
        File file = new File("dms.new\\resources\\data\\MatchedTransports.txt");
        ObjectInputStream objectInputStream = null;
        ArrayList<MatchedTransport> matchedTransports = new ArrayList<>();

        if (file.length() == 0) {
            return null;
        }

        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(file));
            // 循环从文件中读取对象，直到文件尾，使用捕获异常的方式结束循环
            // 原因：对象输入流读到文件尾判断条件既不是 null 也不是 -1
            while (true) {
                try {
                    MatchedTransport matchedTransport = (MatchedTransport) objectInputStream.readObject();
                    matchedTransports.add(matchedTransport);
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
        return matchedTransports;
    }

    /**
     * 将匹配的物流信息分解为发货、送货、收货三条记录保存到数据库
     * 将发货、送货、收货物流信息的 id 保存到 matched_transport 表
     *
     * @param matchedTransports 匹配物流信息集合
     */
    public boolean saveMatchedTransToDb(ArrayList<MatchedTransport> matchedTransports) {
        int sendId;
        int transId;
        int receiveId;
        // 遍历物流匹配集合，从中分解出发货、送货、收货三个对象
        for (MatchedTransport matchedTransport : matchedTransports) {
            Transport send = matchedTransport.getSend();
            Transport trans = matchedTransport.getTrans();
            Transport receive = matchedTransport.getReceive();

            // 插入一条物流记录的 SQL 语句
            String sql = "INSERT INTO gather_transport (`time`,address,`type`,`handler`,receiver,transport)VALUES (?,?,?,?,?,?);";
            // 获取发货对象中的信息
            Object[] params = new Object[]{
                    new Timestamp(send.getTime().getTime()),
                    send.getAddress(), send.getType(), send.getHandler(),
                    send.getReceiver(), send.getTransportType()
            };
            if ((sendId = dbUtil.executeUpdate(sql, params)) > 0) {
                System.out.println("一条物流发货记录成功保存到数据库！");
            }


            // 获取送货对象中的信息
            params = new Object[]{
                    new Timestamp(send.getTime().getTime()),
                    trans.getAddress(), trans.getType(), trans.getHandler(),
                    trans.getReceiver(), trans.getTransportType()
            };
            if ((transId = dbUtil.executeUpdate(sql, params)) > 0) {
                System.out.println("一条物流送货记录成功保存到数据库！");
            }

            // 获取收货对象中的信息
            params = new Object[]{
                    new Timestamp(send.getTime().getTime()),
                    receive.getAddress(), receive.getType(), receive.getHandler(),
                    receive.getReceiver(), receive.getTransportType()
            };
            if ((receiveId = dbUtil.executeUpdate(sql, params)) > 0) {
                System.out.println("一条物流收货记录成功保存到数据库！");
            }


            // 将本条物流匹配记录的发货、送货、送货 id 保存到数据库 matched_transport 表中
            sql = "INSERT INTO matched_transport VALUES (?,?,?);";
            params = new Object[]{sendId, transId, receiveId};
            dbUtil.executeUpdate(sql, params);
            System.out.println("发货、送货、收货 id 成功保存到数据库！");
            return true;
        }
        return false;
    }
}
