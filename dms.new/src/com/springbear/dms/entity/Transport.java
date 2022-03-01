package com.springbear.dms.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 物流信息类
 *
 * @author Spring-_-Bear
 * @version 2021-10-25 19:50
 **/
public class Transport extends DataBase implements Serializable {
    /**
     * 经手人
     */
    private String handler;
    /**
     * 收货人
     */
    private String receiver;
    /**
     * 物流状态：只能选择以下常量值之一
     */
    private int transportType;
    /**
     * 物流状态：发货中
     */
    public static final int SENDING = 1;
    /**
     * 物流状态：送货中
     */
    public static final int TRANSPORTING = 2;
    /**
     * 物流状态：已签收
     */
    public static final int RECEIVED = 3;

    public String getHandler() {
        return handler;
    }

    public String getReceiver() {
        return receiver;
    }

    public int getTransportType() {
        return transportType;
    }

    public Transport() {
    }

    public Transport(int id, Date time, String address, int type, String handler, String receiver, int transportType) {
        super(id, time, address, type);
        this.handler = handler;
        this.receiver = receiver;
        this.transportType = transportType;
    }

    public Transport(Date time, String address, int type, String handler, String receiver, int transportType) {
        super(time, address, type);
        this.handler = handler;
        this.receiver = receiver;
        this.transportType = transportType;
    }

    @Override
    public String toString() {
        return this.getId() + "," + this.getTime() + "," + this.getAddress() + "," + this.getType() + "," + this.getHandler() + "," + this.getTransportType();
    }
}
