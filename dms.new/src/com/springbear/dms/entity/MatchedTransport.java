package com.springbear.dms.entity;

import java.io.Serializable;

/**
 * 匹配物流信息类
 *
 * @author Spring-_-Bear
 * @version 2021-10-25 21:07
 **/
public class MatchedTransport implements Serializable {
    /**
     * 发货记录对象
     */
    private final Transport send;
    /**
     * 送货记录对象
     */
    private final Transport trans;
    /**
     * 签收记录对象
     */
    private final Transport receive;

    public Transport getSend() {
        return send;
    }

    public Transport getTrans() {
        return trans;
    }

    public Transport getReceive() {
        return receive;
    }

    public MatchedTransport(Transport send, Transport trans, Transport receive) {
        if (send.getTransportType() != Transport.SENDING) {
            throw new RuntimeException("不是发货记录!");
        }

        if (trans.getTransportType() != Transport.TRANSPORTING) {
            throw new RuntimeException("不是送货记录!");
        }

        if (receive.getTransportType() != Transport.RECEIVED) {
            throw new RuntimeException("不是签收记录!");
        }

        this.send = send;
        this.trans = trans;
        this.receive = receive;
    }

    @Override
    public String toString() {
        return send.toString() + " | " + trans.toString() + " | " + receive.toString();
    }

}
