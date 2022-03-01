package com.springbear.dms.gather;

import com.springbear.dms.entity.DataBase;
import com.springbear.dms.entity.LogRec;
import com.springbear.dms.entity.MatchedLogRec;
import com.springbear.dms.exception.DataAnalyseException;

import java.util.ArrayList;

/**
 * 日志分析过滤类
 * @author Spring-_-Bear
 * @version 2021-10-26 17:13
 **/
public class LogRecAnalyse extends DataFilter implements IDataAnalyse {
    /**
     * 登录对象集合
     */
    private final ArrayList<LogRec> loginArrayList = new ArrayList<>();
    /**
     * 登出对象集合
     */
    private final ArrayList<LogRec> logoutArrayList = new ArrayList<>();

    public LogRecAnalyse() {
    }

    public LogRecAnalyse(ArrayList<LogRec> logRecs) {
        super(logRecs);
    }

    /**
     * 根据日志登录状态的不同将登录、登出对象分别放进不同的集合中
     */
    @Override
    public void doFilter() {
        // 获取日志对象集合
        ArrayList<LogRec> logRecs = (ArrayList<LogRec>) this.getData();

        // 遍历日志对象，根据不同的登录状态放进不同的集合中
        for (LogRec logRec : logRecs) {
            if (logRec.getLogType() == LogRec.LOG_IN) {
                loginArrayList.add(logRec);
            } else if (logRec.getLogType() == LogRec.LOG_OUT) {
                logoutArrayList.add(logRec);
            }
        }
    }

    /**
     * 从过滤后的登录、登出对象集合中匹配出一对登录、登出日志匹配对
     * 匹配条件：登录、登出对象的用户名和主机IP必须一致
     *
     * @return 匹配到的日志登录、登出对集合
     */
    @Override
    public ArrayList<MatchedLogRec> matchData() {
        // 创建日志匹配登录、登出对对象集合
        ArrayList<MatchedLogRec> matchedLogRecs = new ArrayList<>();

        // 遍历过滤后的登录、登出集合，从中匹配出一对登录、登出对
        // 登录对象集合
        for (LogRec login : loginArrayList) {
            // 登出对象集合
            for (LogRec logout : logoutArrayList) {
                // 若登录、登出对象的用户名、主机IP一致，则认为是一对匹配的登录、登出对
                if (login.getUser().equals(logout.getUser()) && login.getIp().equals(logout.getIp())) {
                    // 修改数据操作状态为匹配
                    login.setType(DataBase.MATCH);
                    logout.setType(DataBase.MATCH);

                    // 将匹配到的"登录、登出对"对象添加到集合中
                    matchedLogRecs.add(new MatchedLogRec(login, logout));
                }
            }

            try {
                if (matchedLogRecs.size() == 0) {
                    // 没找到匹配的登录、登出匹配对
                    throw new DataAnalyseException("没有匹配的日志数据!");
                }
            } catch (DataAnalyseException e) {
                e.printStackTrace();
            }
        }
        return matchedLogRecs;
    }
}
