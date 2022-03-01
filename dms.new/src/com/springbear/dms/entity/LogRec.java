package com.springbear.dms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 日志信息类
 *
 * @author Spring-_-Bear
 * @version 2021-10-25 20:14
 **/
public class LogRec extends DataBase implements Serializable {
    /**
     * 登录用户名
     */
    private String user;
    /**
     * 登录用户主机IP地址
     */
    private String ip;
    /**
     * 登录状态：只能选取 LOG_IN 或 LOG_OUT 之一
     */
    private int logType;
    /**
     * 登录状态
     */
    public static final int LOG_IN = 1;
    /**
     * 登出状态
     */
    public static final int LOG_OUT = 2;

    public String getUser() {
        return user;
    }

    public String getIp() {
        return ip;
    }

    public int getLogType() {
        return logType;
    }

    public LogRec() {
    }

    public LogRec(int id, Date time, String address, int type, String user, String ip, int logType) {
        super(id, time, address, type);
        this.user = user;
        this.ip = ip;
        this.logType = logType;
    }

    public LogRec(Date time, String address, int type, String user, String ip, int logType) {
        super(time, address, type);
        this.user = user;
        this.ip = ip;
        this.logType = logType;
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getTime() + "," + this.getAddress() + "," + this.getType() + "," + this.getUser() + "," + this.getIp() + "," + this.getLogType();
    }
}
