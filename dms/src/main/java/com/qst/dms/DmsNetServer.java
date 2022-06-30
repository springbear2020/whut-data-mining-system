package com.qst.dms;


import com.qst.dms.cmd.MenuDriver;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;
import com.qst.dms.ui.LoginFrame;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

//服务器端应用程序，接收客户端发送来的数据保存到数据库中
public class DmsNetServer {

    //构造方法
    public DmsNetServer() {

        //通过使用不同的端口区分接收的不同数据：6666端口是日志，6668端口是物流
        //开启监听6666端口的线程，接收日志数据
        new AcceptLogThread(6666).start();
        //开启监听6668端口的线程，接收物流数据
        new AcceptTranThread(6668).start();

        System.out.println("本地服务器已开启···");
    }

    //接收日志数据的线程类
    private class AcceptLogThread extends Thread {
        private ServerSocket serverSocket;
        private Socket socket;
        private LogRecService logRecService;
        private ObjectInputStream ois;

        //构造方法
        public AcceptLogThread(int port) {
            logRecService = new LogRecService();
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //重写run（）方法，将日志数据保存到数据库中
        @Override
        public void run() {
            while (this.isAlive()) {
                try {
                    //接收客户端发送过来的套接字
                    socket = serverSocket.accept();
                    if (socket != null) {
                        ois = new ObjectInputStream(socket.getInputStream());
                        //反序列化，得到匹配日志列表
                        ArrayList<MatchedLogRec> matchedLogs = (ArrayList<MatchedLogRec>) ois.readObject();
                        //将客户端发送过来的匹配日志信息保存到数据库中
                        logRecService.saveMatchLogToDB(matchedLogs);
                        System.out.println("匹配日志数据由本地服务器保存到数据库成功！");
                        matchedLogs.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //接收物流数据的线程类
    private class AcceptTranThread extends Thread {

        private ServerSocket serverSocket;
        private Socket socket;
        private TransportService transportService;
        private ObjectInputStream ois;

        public AcceptTranThread(int port) {
            transportService = new TransportService();
            try {
                serverSocket = new ServerSocket(port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //重写run（）方法，将物流数据保存到数据库中
        @Override
        public void run() {
            while (this.isAlive()) {
                try {
                    //接收客户端发送过来的套接字
                    socket = serverSocket.accept();
                    if (socket != null) {
                        ois = new ObjectInputStream(socket.getInputStream());
                        //反序列化，得到匹配物流列表
                        ArrayList<MatchedTransport> matchedTrans = (ArrayList<MatchedTransport>) ois.readObject();
                        //将客户端发过来的匹配物流信息保存到数据库
                        transportService.saveMatchTransportToDB(matchedTrans);
                        System.out.println("匹配物流数据由本地服务器保存到数据库成功！");
                        matchedTrans.clear();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            try {
                ois.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //主程序
    public static void main(String[] args) {
        // 打开客户端
        new LoginFrame().setVisible(true);
        // 启动服务器
        new DmsNetServer();
    }

}
