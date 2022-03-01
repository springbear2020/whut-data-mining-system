package com.springbear.dms.entity;

import java.io.Serializable;

/**
 * 匹配日志信息类
 *
 * @author Spring-_-Bear
 * @version 2021-10-25 20:47
 **/
public class MatchedLogRec implements Serializable {
    /**
     * 登录对象
     */
    private LogRec login;
    /**
     * 登出对象
     */
    private LogRec logout;

    public LogRec getLogin() {
        return login;
    }

    public LogRec getLogout() {
        return logout;
    }

    public MatchedLogRec() {
    }

    public MatchedLogRec(LogRec login, LogRec logout) {
        if (login.getLogType() != LogRec.LOG_IN) {
            throw new RuntimeException("不是登录记录!");
        }

        if (logout.getLogType() != LogRec.LOG_OUT) {
            throw new RuntimeException("不是登出记录!");
        }

        if (!login.getUser().equals(logout.getUser())) {
            throw new RuntimeException("登录登出必须是同一个用户!");
        }

        if (!login.getIp().equals(logout.getIp())) {
            throw new RuntimeException("登录登出必须是同一个IP地址!");
        }

        this.login = login;
        this.logout = logout;
    }

    @Override
    public String toString() {
        return login.toString() + " | " + logout.toString();
    }
}
