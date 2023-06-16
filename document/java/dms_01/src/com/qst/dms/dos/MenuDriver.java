package com.qst.dms.dos;

import java.util.ArrayList;
import java.util.Scanner;

import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.gather.LogRecAnalyse;
import com.qst.dms.gather.TransportAnalyse;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

public class MenuDriver {
	public static void main(String[] args) {
		// 建立一个从键盘接收数据的扫描器
		Scanner scanner = new Scanner(System.in);

		// 创建一个泛型ArrayList集合存储日志数据
		ArrayList<LogRec> logRecList = new ArrayList<>();
		// 创建一个泛型ArrrayList集合存储物流数据
		ArrayList<Transport> transportList = new ArrayList<>();

		// 创建一个日志业务类
		LogRecService logService = new LogRecService();
		// 创建一个物流业务类
		TransportService tranService = new TransportService();

		// 日志数据匹配集合
		ArrayList<MatchedLogRec> matchedLogs = null;
		// 物流数据匹配集合
		ArrayList<MatchedTransport> matchedTrans = null;

		try {
			while (true) {
				// 输出菜单界面，需补充 
				//...

				// 提示用户输入要操作的菜单项
				System.out.println("请输入菜单项（0~5）：");

				// 接收键盘输入的选项
				int choice = scanner.nextInt();

				switch (choice) {
				case 1: {
					System.out.println("请输入采集数据类型：1.日志    2.物流");
					// 接收键盘输入的选项
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("正在采集日志数据，请输入正确信息，确保数据的正常采集！");
						// 采集日志数据，需补充LogService类中的inputLog方法
						LogRec log = logService.inputLog();
						// 将采集的日志数据添加到logRecList集合中
						logRecList.add(log);
					} else if (type == 2) {
						System.out.println("正在采集物流数据，请输入正确信息，确保数据的正常采集！");
						// 采集物流数据，需补充tranService类中的inputTransport方法
						Transport tran = tranService.inputTransport();
						// 将采集的物流数据添加到transportList集合中
						transportList.add(tran);
					}
				}
					break;
				case 2: {
					System.out.println("请输入匹配数据类型：1.日志    2.物流");
					// 接收键盘输入的选项
					int type = scanner.nextInt();
					if (type == 1) {
						System.out.println("正在日志数据过滤匹配...");
						// 创建日志数据分析对象，用于日志数据筛选与匹配
						LogRecAnalyse logAn = new LogRecAnalyse(logRecList);
						// 需实现doFilter抽象方法，对日志数据进行过滤，根据日志登录状态
						//分别放在登录和登出两个集合中
						logAn.doFilter();
						// 日志数据分析
						matchedLogs = logAn.matchData();
						System.out.println("日志数据过滤匹配完成！");
					} else if (type == 2) {
						System.out.println("正在物流数据过滤匹配...");
						// 创建物流数据分析对象
						TransportAnalyse transAn = new TransportAnalyse(
								transportList);
						// 物流数据过滤
						transAn.doFilter();
						// 物流数据分析
						matchedTrans = transAn.matchData();
						System.out.println("物流数据过滤匹配完成！");
					}
				}
					break;
				case 3:
					System.out.println("数据记录 中...");
					break;
				case 4: {
					System.out.println("显示匹配的数据：");
					if (matchedLogs == null || matchedLogs.size() == 0) {
						System.out.println("匹配的日志记录是0条！");
					} else {
						//输出匹配的日志信息
						logService.showMatchLog(matchedLogs);
					}
					if (matchedTrans == null || matchedTrans.size() == 0) {
						System.out.println("匹配的物流记录是0条！");
					} else {
						// 输出匹配的物流信息
						tranService.showMatchTransport(matchedTrans);
					}
				}
					break;
				case 5:
					System.out.println("数据发送 中...");
					break;
				case 0:
					// 应用程序退出
					System.exit(0);
				default:
					System.out.println("请输入正确的菜单项（0~5）！");
				}

			}
		} catch (Exception e) {
			System.out.println("输入的数据不合法！");
		}
	}
}
