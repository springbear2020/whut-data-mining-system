package com.qst.dms.dos;

import com.qst.dms.entity.Transport;
import com.qst.dms.service.TransportService;

public class TransportDemo {

	public static void main(String[] args) {
		// 创建一个物流业务类
		TransportService tranService = new TransportService();
		// 创建一个物流对象数组，用于存放采集的四个物流信息
		Transport[] transports = new Transport[4];
		for (int i = 0; i < transports.length; i++) {
			System.out.println("第" + (i + 1) + "个物流数据采集：");
			transports[i] = tranService.inputTransport();
		}
		// 显示物流信息
		tranService.showTransport(transports);

	}

}
