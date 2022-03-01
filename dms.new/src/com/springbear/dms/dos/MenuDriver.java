package com.springbear.dms.dos;

import com.springbear.dms.entity.LogRec;
import com.springbear.dms.entity.MatchedLogRec;
import com.springbear.dms.entity.MatchedTransport;
import com.springbear.dms.entity.Transport;
import com.springbear.dms.gather.LogRecAnalyse;
import com.springbear.dms.gather.TransportAnalyse;
import com.springbear.dms.service.LogRecService;
import com.springbear.dms.service.TransportService;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 控制台主程序
 *
 * @author Spring-_-Bear
 * @version 2021-10-26 19:31
 **/
public class MenuDriver {
    /**
     * 操作日志
     */
    public static final int LOG = 1;
    /**
     * 操作物流
     */
    public static final int TRANSPORT = 2;
    /**
     * 日志数据集合
     */
    static ArrayList<LogRec> logRecArrayList = new ArrayList<>();
    /**
     * 物流数据集合
     */
    static ArrayList<Transport> transportArrayList = new ArrayList<>();
    /**
     * 匹配日志数据集合
     */
    static ArrayList<MatchedLogRec> matchedLogRecArrayList = new ArrayList<>();
    /**
     * 匹配物流数据集合
     */
    static ArrayList<MatchedTransport> matchedTransportArrayList = new ArrayList<>();
    /**
     * 日志业务类
     */
    static LogRecService logRecService = new LogRecService();
    /**
     * 物流业务类
     */
    static TransportService transportService = new TransportService();
    /**
     * 键盘扫描器
     */
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            while (true) {
                // 打印界面菜单
                MenuDriver.printMenu();

                System.out.print("请输入您的选择(0~5):");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        MenuDriver.gatherData();
                        break;
                    case 2:
                        MenuDriver.matchData();
                        break;
                    case 3:
                        MenuDriver.recordData();
                        break;
                    case 4:
                        MenuDriver.displayData();
                        break;
                    case 5:
                        MenuDriver.sendData();
                        break;
                    case 0:
                        System.out.println("谢谢您使用本系统，再见！");
                        System.exit(0);
                    default:
                        System.out.println("您的输入有误，请输入正确的菜单项（0~5）!");
                        break;
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("您的输入不合法，谢谢使用，再见！");
        }
    }

    /**
     * 打印菜单
     */
    private static void printMenu() {
        System.out.println("************************");
        System.out.println("欢迎进入日志物流数据挖掘系统！");
        System.out.println("* [1]数据采集 [2]数据匹配 *");
        System.out.println("* [3]数据记录 [4]数据显示 *");
        System.out.println("* [5]数据发送 [0]退出程序 *");
        System.out.println("************************");
    }

    /**
     * 数据采集
     */
    private static void gatherData() {
        System.out.print("请输入采集数据类型: [1]日志 [2]物流 :");
        int gatherType = scanner.nextInt();
        if (gatherType == LOG) {
            System.out.println("正在采集日志数据······");
            LogRec logRec = logRecService.inputLog();
            logRecArrayList.add(logRec);
        } else if (gatherType == TRANSPORT) {
            System.out.println("正在采集物流数据······");
            Transport transport = transportService.inputTransport();
            transportArrayList.add(transport);
        }
    }

    /**
     * 数据匹配
     */
    private static void matchData() {
        System.out.print("请输入匹配数据类型: [1]日志 [2]物流 :");
        int matchType = scanner.nextInt();
        if (matchType == LOG) {
            if (logRecArrayList == null || logRecArrayList.size() == 0) {
                System.out.println("没有日志数据！请先进行日志数据的采集！");
            } else {
                System.out.println("日志数据过滤匹配中······");
                LogRecAnalyse logRecAnalyse = new LogRecAnalyse(logRecArrayList);
                logRecAnalyse.doFilter();
                matchedLogRecArrayList = logRecAnalyse.matchData();
                System.out.println("日志数据过滤匹配完成！");
                System.out.println("匹配到" + matchedLogRecArrayList.size() + "条日志数据！");
            }
        } else if (matchType == TRANSPORT) {
            if (transportArrayList == null || transportArrayList.size() == 0) {
                System.out.println("没有物流数据！请先进行物流数据的采集！");
            } else {
                System.out.println("物流数据过滤匹配中······");
                TransportAnalyse transportAnalyse = new TransportAnalyse(transportArrayList);
                transportAnalyse.doFilter();
                matchedTransportArrayList = transportAnalyse.matchData();
                System.out.println("物流数据过滤匹配完成！");
                System.out.println("匹配到" + matchedTransportArrayList.size() + "条物流数据！");
            }
        }
    }

    /**
     * 数据记录
     */
    private static void recordData() {
        System.out.print("请输入数据记录类型: [1]日志 [2]物流 :");
        int saveType = scanner.nextInt();
        if (saveType == LOG) {
            // 保存匹配日志信息到文件并清空集合，避免重复采集
            logRecService.saveAppendMatchedLogRec(matchedLogRecArrayList);
            logRecArrayList.clear();
            matchedLogRecArrayList.clear();
        } else if (saveType == TRANSPORT) {
            // 保存匹配物流信息到文件并清空集合，避免重复采集
            transportService.saveAppendMatchedTransport(matchedTransportArrayList);
            transportArrayList.clear();
            matchedTransportArrayList.clear();
        }
    }

    /**
     * 数据显示
     */
    private static void displayData() {
        System.out.println("从日志文件读取到的日志信息如下:");
        ArrayList<MatchedLogRec> matchedLogRecsFromFile = logRecService.readMatchedLogRec();
        logRecService.showMatchedLogRec(matchedLogRecsFromFile);
        System.out.println("从物流文件读取到的物流信息如下:");
        ArrayList<MatchedTransport> matchedTransportsFromFile = transportService.readMatchedTransport();
        transportService.showMatchedTransport(matchedTransportsFromFile);
    }

    /**
     * 数据发送
     */
    private static void sendData() {
        System.out.println("功能舍弃，集成到主界面！");
    }
}
