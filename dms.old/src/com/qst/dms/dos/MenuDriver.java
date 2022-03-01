package com.qst.dms.dos;

import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.gather.LogRecAnalyse;
import com.qst.dms.gather.TransportAnalyse;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author 李春雄
 */
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
                // 输出菜单界面
                System.out.println("******************************");
                System.out.println("欢迎进入日志物流信息数据系统!");
                System.out.println("*1、数据采集\t2、数据匹配*");
                System.out.println("*3、数据记录\t4、数据显示*");
                System.out.println("*5、数据发送\t0、退出应用*");
                System.out.println("******************************");

                // 提示用户输入要操作的菜单项
                System.out.println("请输入菜单项（0~5）：");
                int choice = scanner.nextInt();
                //用户操作选择菜单
                switch (choice) {
                    case 1: {
                        System.out.println("请输入采集数据类型:1.日志   2.物流");
                        int type = scanner.nextInt();
                        if (type == 1) {
                            System.out.println("正在采集日志数据，请输入正确信息，确保数据的正常采集！");
                            // 采集日志数据，调用LogService类中的inputLog方法
                            LogRec log = logService.inputLog();
                            // 将采集的日志数据添加到logRecList集合中
                            logRecList.add(log);
                        } else if (type == 2) {
                            System.out.println("正在采集物流数据，请输入正确信息，确保数据的正常采集！");
                            // 采集物流数据，调用tranService类中的inputTransport方法
                            Transport tran = tranService.inputTransport();
                            // 将采集的物流数据添加到transportList集合中
                            transportList.add(tran);
                        }
                        break;
                    }

                    case 2: {
                        System.out.println("请输入匹配数据类型：1.日志    2.物流");
                        int type = scanner.nextInt();
                        if (type == 1) {
                            System.out.println("正在进行日志数据过滤匹配...");
                            // 创建日志数据分析对象，用于日志数据筛选与匹配
                            LogRecAnalyse logAn = new LogRecAnalyse(logRecList);
                            //日志数据过滤
                            logAn.doFilter();
                            // 日志数据分析
                            matchedLogs = logAn.matchData();
                            System.out.println("日志数据过滤匹配完成！");
                        } else if (type == 2) {
                            System.out.println("正在物流数据过滤匹配...");
                            // 创建物流数据分析对象
                            TransportAnalyse transAn = new TransportAnalyse(transportList);
                            // 物流数据过滤
                            transAn.doFilter();
                            // 物流数据分析
                            matchedTrans = transAn.matchData();
                            System.out.println("物流数据过滤匹配完成！");
                        }
                        break;
                    }

                    case 3: {
                        System.out.println("请输入数据记录类型:1.日志   2.物流");
                        int type = scanner.nextInt();
                        if(type == 1){
                            //保存匹配的日志信息到文件
                            logService.saveAndAppendMatchLog(matchedLogs);
                            //保存匹配的文件到数据库中
                            logService.saveMatchLogToDB(matchedLogs);
                            //清空匹配日志集合，避免存入相同匹配日志信息
                            matchedLogs.clear();
                        } else if(type == 2){
                            //保存匹配的物流信息到文件中
                            tranService.saveAndAppendMatchedTransport(matchedTrans);
                            //保存匹配的物流信息到数据库中
                            tranService.saveMatchTransportToDB(matchedTrans);
                            //清空匹配物流集合，避免存入相同匹配物流信息
                            matchedTrans.clear();
                        }
                        break;
                    }

                    case 4: {
                        //从文件中读取匹配的日志信息并显示
                        System.out.println("从文件中读取匹配的日志数据如下:");
                        ArrayList<MatchedLogRec> logList = logService.readMatchLog();
                        logService.showMatchLog(logList);
                        //从文件中读取匹配的物流信息并显示
                        System.out.println("从文件中读取匹配的物流数据如下:");
                        ArrayList<MatchedTransport> TransportList = tranService.readMatchedTransport();
                        tranService.showMatchTransport(TransportList);

                        //从数据库中读取匹配的物流信息
                        System.out.println("从数据库中读取匹配的日志数据如下:");
                        ArrayList<MatchedLogRec> matchDBLogs = logService.readMatchedLogFromDB();
                        logService.showMatchLog(matchDBLogs);
                        matchDBLogs.clear();
                        //显示从数据库中读取到的已匹配的物流信息
                        System.out.println("从数据库中读取匹配的物流数据如下:");
                        ArrayList<MatchedTransport> matchDBTransport = tranService.readMatchedTransportFromDB();
                        tranService.showMatchTransport(matchDBTransport);
                        matchDBTransport.clear();
                        break;
                    }

                    case 5: {
                        System.out.println("数据发送中...");
                        break;
                    }

                    case 0: {
                        // 应用程序退出
                        System.exit(0);
                        break;
                    }

                    default:
                        System.out.println("请输入正确的菜单项（0~5）！");
                }

            }
        } catch (Exception e) {
            System.out.println("输入的数据不合法！");
        }
    }
}
