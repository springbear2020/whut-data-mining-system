package com.qst.dms.dos;

import com.qst.dms.entity.LogRec;
import com.qst.dms.service.LogRecService;

public class LogRecDemo {
	public static void main(String[] args) {
		// 创建一个日志业务类
		LogRecService logService = new LogRecService();
		// 创建一个日志对象数组，用于存放采集的三个日志信息
		LogRec[] logs = new LogRec[3];
		for (int i = 0; i < logs.length; i++) {
			System.out.println("第" + (i + 1) + "个日志数据采集：");
			logs[i] = logService.inputLog();
		}
		// 显示日志信息
		logService.showLog(logs);
	}

}
