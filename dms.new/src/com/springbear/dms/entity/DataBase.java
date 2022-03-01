package com.springbear.dms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 数据(日志、物流)基类
 *
 * @author Spring-_-Bear
 * @version 2021-10-25 19:50
 **/
public class DataBase implements Serializable {
    /**
     * ID标识
     */
    private int id;
    /**
     * 时间
     */
    private Date time;
    /**
     * 地点
     */
    private String address;
    /**
     * 数据操作状态：只能选择以下常量值之一
     */
    private int type;
    /**
     * 采集
     */
    public static final int GATHER = 1;
    /**
     * 匹配
     */
    public static final int MATCH = 2;

    public int getId() {
        return id;
    }

    public Date getTime() {
        return time;
    }

    public String getAddress() {
        return address;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public DataBase() {
    }

    public DataBase(Date time, String address, int type) {
        this.time = time;
        this.address = address;
        this.type = type;
    }

    public DataBase(int id, Date time, String address, int type) {
        this.id = id;
        this.time = time;
        this.address = address;
        this.type = type;
    }

    @Override
    public String toString() {
        return this.id + "," + this.time + "," + this.address + "," + this.type;
    }
}

