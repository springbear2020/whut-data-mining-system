package com.qst.dms.gather;

import java.util.ArrayList;

import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.exception.DataAnalyseException;

//日志分析类，继承DataFilter抽象类，实现数据分析接口
public class LogRecAnalyse extends DataFilter implements IDataAnalyse {
    // “登录”集合
    private ArrayList<LogRec> logIns = new ArrayList<>();
    // “登出”集合
    private ArrayList<LogRec> logOuts = new ArrayList<>();

    // 构造方法
    public LogRecAnalyse() {
    }

    public LogRecAnalyse(ArrayList<LogRec> logRecs) {
        super(logRecs);
    }

    // 实现DataFilter抽象类中的过滤抽象方法
    public void doFilter() {
        // 获取数据集合
        ArrayList<LogRec> logs = (ArrayList<LogRec>) this.getDatas();

        // 遍历，对日志数据进行过滤，根据日志登录状态分别放在不同的数组中
        for(LogRec rec : logs) {
            if(rec.getLogType() == LogRec.LOG_IN)
                // 添加到“登录”日志集合中
                logIns.add(rec);
            else if(rec.getLogType() == LogRec.LOG_OUT)
                // 添加到“登出”日志集合中
                logOuts.add(rec);
        }




    }

    // 实现IDataAnalyse接口中数据分析方法
    public ArrayList<MatchedLogRec> matchData() {
        // 创建日志匹配集合
        ArrayList<MatchedLogRec> matchLogs = new ArrayList<>();

        // 数据匹配分析
        for(LogRec in : logIns) {
            for(LogRec out : logOuts) {
                if((in.getUser().equals(out.getUser()))&&(in.getIp().equals(out.getIp()))) {
                    //修改in和out日志状态类型为“匹配”
                    in.setType(DataBase.MATHCH);
                    out.setType(DataBase.MATHCH);
                    //添加到匹配集合中
                    matchLogs.add(new MatchedLogRec(in,out));
                }
            }
        }

        try {
            if(matchLogs.size()  == 0) {
                // 没找到匹配的数据,抛出DataAnalyseException异常
                throw new DataAnalyseException("没有匹配的日志数据！");
            }
        } catch (DataAnalyseException e) {
            e.printStackTrace();
        }


        //catch (DataAnalyseException e) {
        //e.printStackTrace();
        return matchLogs;
    }

}