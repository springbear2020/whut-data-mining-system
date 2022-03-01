package com.qst.dms.service;

import java.net.InetAddress;
import java.net.UnknownHostException;

//自定义的额外服务类
public class ExtraService {

    //showIpaddress方法用于获取本机IP地址并显示
    public String showIpAddress() {
        String address = null;
        try {

            System.out.println("主机IP地址自动获取中···");
            InetAddress me =  InetAddress.getLocalHost();
            address = me.getHostAddress();
            System.out.println(address);
        } catch (UnknownHostException e) { System.out.println("自动获取IP地址失败！！！");}

        return address;
    }

}
