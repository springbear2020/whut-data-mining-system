package com.springbear.dms.gather;

import com.springbear.dms.entity.DataBase;
import com.springbear.dms.entity.MatchedTransport;
import com.springbear.dms.entity.Transport;
import com.springbear.dms.exception.DataAnalyseException;

import java.util.ArrayList;

/**
 * 物流数据分析类
 *
 * @author Spring-_-Bear
 * @version 2021-10-26 09:22
 **/
public class TransportAnalyse extends DataFilter implements IDataAnalyse {
    /**
     * 发货集合：存储发货物流对象
     */
    private final ArrayList<Transport> transSends = new ArrayList<>();
    /**
     * 送货集合：存储送货物流对象
     */
    private final ArrayList<Transport> transTransports = new ArrayList<>();
    /**
     * 签收集合：存储签收物流对象
     */
    private final ArrayList<Transport> transReceives = new ArrayList<>();

    public TransportAnalyse(ArrayList<Transport> transports) {
        super(transports);
    }

    /**
     * 对物流数据进行过滤，根据物流状态将不同的物流对象放进不同的集合中
     */
    @Override
    public void doFilter() {
        // 获取物流数据集合
        ArrayList<Transport> transports = (ArrayList<Transport>) this.getData();

        // 遍历transports集合，对物流数据进行过滤，根据物流状态将不同的物流对象放进不同的集合中
        for (Transport transport : transports) {
            if (transport.getTransportType() == Transport.SENDING) {
                // 发货记录
                transSends.add(transport);
            } else if (transport.getTransportType() == Transport.TRANSPORTING) {
                // 送货记录
                transTransports.add(transport);
            } else if (transport.getTransportType() == Transport.RECEIVED) {
                // 签收记录
                transReceives.add(transport);
            }
        }
    }

    /**
     * 匹配物流数据：如果发货记录对象、送货记录对象、签收记录对象中的收货人一致，则认为是一条匹配的物流信息
     *
     * @return 匹配的多对物流数据集合
     */
    @Override
    public ArrayList<MatchedTransport> matchData() {
        // 创建物流匹配数据集合
        ArrayList<MatchedTransport> matchedTransports = new ArrayList<>();

        // 数据分析匹配：分析发货、送货、签收三个对象是否是“一对”
        // 发货物流数据对象
        for (Transport send : transSends) {
            // 送货物流数据对象
            for (Transport transport : transTransports) {
                // 签收物流数据对象
                for (Transport receive : transReceives) {
                    // 如果发货对象、送货对象、收获对象中的收货人一致，则认为是一条匹配的物流信息
                    if ((send.getReceiver().equals(transport.getReceiver())) && send.getReceiver().equals(receive.getReceiver())) {
                        // 修改数据操作状态为“匹配”
                        send.setType(DataBase.MATCH);
                        transport.setType(DataBase.MATCH);
                        receive.setType(DataBase.MATCH);

                        // 将从发货记录、送货记录、收货记录对象中匹配到的一对物流信息对放进物流匹配信息对集合
                        matchedTransports.add(new MatchedTransport(send, transport, receive));
                    }
                }
            }
        }

        try {
            if (matchedTransports.size() == 0) {
                // 没找到匹配的数据，抛出DataAnalyseException异常
                throw new DataAnalyseException("没有匹配的物流数据!");
            }
        } catch (DataAnalyseException e) {
            e.printStackTrace();
        }
        return matchedTransports;
    }
}
