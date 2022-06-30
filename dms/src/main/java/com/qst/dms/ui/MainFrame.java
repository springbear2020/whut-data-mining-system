package com.qst.dms.ui;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.LogRec;
import com.qst.dms.entity.MatchedLogRec;
import com.qst.dms.entity.MatchedTableModel;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.gather.LogRecAnalyse;
import com.qst.dms.gather.TransportAnalyse;
import com.qst.dms.service.LogRecService;
import com.qst.dms.service.TransportService;
import com.qst.dms.util.Config;

//主窗口
public class MainFrame extends JFrame {

    // 声明界面组件
    private JMenuBar menuBar;
    private JMenu menuOperate, menuHelp, menuMatch;
    private JMenuItem miGather, miSave, miSend,
            miShow, miExit, miCheck, miAbout, miMatchLog, miMatchTrans;
    private JTabbedPane tpGather, showPane;
    private JPanel p, pLog, pTran, pLogId, pName, pLocation, pIP, pLogStatus,
            pLogButton, pTransId, pAdress, pHandler, pReceiver, pTranStatus,
            pTranButton;
    private JLabel lblLogId, lblName, lblLocation, lblIP, lblLogStatus,
            lblTransId, lblAdress, lblHandler, lblReceiver, lblTranStatus;
    private JTextField txtLogId, txtName, txtLocation, txtIP, txtTransId,
            txtAdress, txtHandler, txtReceiver;
    private JRadioButton rbLogin, rbLogout;
    private JButton btnLogConfirm, btnLogReset, btnTranConfirm, btnTranReset,
            btnGather, btnMatchLog, btnMatchTrans, btnSave, btnSend, btnShow;
    private JComboBox<String> cmbTanStatus;
    private JToolBar toolBar;
    private JScrollPane scrollPane;
    private CardLayout card;

    // 声明日志对象
    private LogRec log;
    // 声明物流对象
    private Transport trans;
    // 声明日志列表
    private ArrayList<LogRec> logList;
    // 声明物流列表
    private ArrayList<Transport> transList;
    // 声明匹配日志列表
    private ArrayList<MatchedLogRec> matchedLogs;
    // 声明匹配物流列表
    private ArrayList<MatchedTransport> matchedTrans;
    // 声明日志业务对象
    private LogRecService logRecService;
    // 声明物流业务对象
    private TransportService transportService;
    //服务器IP地址
    private String serverIP;

    // 构造方法
    public MainFrame() {

        // 调用父类的构造方法
        super("欢迎使用数据挖掘系统客户端！");

        // 设置窗体的icon
        ImageIcon icon = new ImageIcon("images\\dms.png");
        this.setIconImage(icon.getImage());

        // 列表、业务对象初始化
        logList = new ArrayList<LogRec>();
        transList = new ArrayList<Transport>();
        matchedLogs = new ArrayList<MatchedLogRec>();
        matchedTrans = new ArrayList<MatchedTransport>();
        logRecService = new LogRecService();
        transportService = new TransportService();

        // 初始化菜单方法
        initMenu();
        // 初始化工具栏方法
        initToolBar();

        // 设置主面板为CardLayout卡片布局
        card = new CardLayout();
        p = new JPanel();
        p.setBounds(0, 27, 582, 300);
        p.setLayout(card);
        // 数据采集的选项卡面板
        tpGather = new JTabbedPane(JTabbedPane.TOP);
        // 数据显示的选项卡面板
        showPane = new JTabbedPane(JTabbedPane.TOP);
        showPane.addTab("日志", new JScrollPane());
        showPane.addTab("物流", new JScrollPane());
        // 将两个选项卡面板添加到卡片面板中
        p.add(tpGather, "gather");
        p.add(showPane, "show");

        // 将主面板添加到窗体中
        getContentPane().add(p);

        // 初始化日志数据采集界面
        initLogGatherGUI();
        // 初始化物流数据采集界面
        initTransGatherGUI();

        // 设置窗体初始可见
        this.setVisible(true);
        // 设置窗体初始最大化
        this.setSize(600, 400);
        // 设置窗口初始化居中
        this.setLocationRelativeTo(null);
        // 设置默认的关闭按钮操作为退出程序
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //开启更新表格数据的线程
        new UpdateTableThread().start();

        //从配置文件中获取网络通信服务器的IP地址
        serverIP = Config.getValue("serverIP");
    }

    // 初始化菜单的方法
    private void initMenu() {
        // 初始化菜单组件
        menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);
        menuOperate = new JMenu("操作");
        menuBar.add(menuOperate);

        miGather = new JMenuItem("采集数据");
        // 注册监听
        miGather.addActionListener(new GatherListener());
        menuOperate.add(miGather);


        // 二级菜单
        menuMatch = new JMenu("匹配数据");
        menuOperate.add(menuMatch);
        miMatchLog = new JMenuItem("匹配日志数据");
        //注册监听
        miMatchLog.addActionListener(new MatchLogListener());
        menuMatch.add(miMatchLog);
        miMatchTrans = new JMenuItem("匹配物流数据");
        //注册监听
        miMatchTrans.addActionListener(new MatchTransListener());
        menuMatch.add(miMatchTrans);

        //二级菜单
        miSave = new JMenuItem("保存数据");
        //注册监听
        miSave.addActionListener(new SaveListener());
        menuOperate.add(miSave);

        miSend = new JMenuItem("发送数据");
        //注册监听
        miSend.addActionListener(new SendDataListener());
        menuOperate.add(miSend);

        miShow = new JMenuItem("显示数据");
        // 注册监听
        miShow.addActionListener(new ShowDataListener());
        menuOperate.add(miShow);

        // 添加分割线
        menuOperate.addSeparator();

        miExit = new JMenuItem("退出系统");
        // 注册监听
        miExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 显示确认对话框，当选择YES_OPTION时退出系统
                if (JOptionPane.showConfirmDialog(null, "您确定要退出系统吗？", "退出系统",
                        JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    // 退出系统
                    System.exit(0);
                }
            }
        });
        menuOperate.add(miExit);


        menuHelp = new JMenu("帮助");
        menuBar.add(menuHelp);
        miCheck = new JMenuItem("查看帮助");
        // 注册监听
        miCheck.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 显示消息对话框
                JOptionPane.showMessageDialog(null,
                        "本系统实现数据的采集、过滤分析匹配、保存、发送及显示功能", "帮助",
                        JOptionPane.QUESTION_MESSAGE);
            }
        });
        menuHelp.add(miCheck);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        miAbout = new JMenuItem("关于系统");
        // 注册监听
        miAbout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 显示消息对话框
                JOptionPane.showMessageDialog(null,
                        "开发人:李春雄   开发时间:" + formatter.format(date), "版本信息",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        menuHelp.add(miAbout);
    }

    // 初始化工具栏的方法
    private void initToolBar() {
        getContentPane().setLayout(null);
        // 创建工具栏
        toolBar = new JToolBar();
        toolBar.setBounds(0, 0, 582, 27);
        // 将工具栏添加到窗体北部（上面）
        getContentPane().add(toolBar);

        // 添加带有图标的工具栏按钮
        //ImageIcon gatherIcon = new ImageIcon("images\\gatherData.png");
        btnGather = new JButton("采集数据");
        // 注册监听
        btnGather.addActionListener(new GatherListener());
        toolBar.add(btnGather);

        //ImageIcon matchIcon = new ImageIcon("images\\matchData.png");
        btnMatchLog = new JButton("匹配日志数据");
        // 注册监听
        btnMatchLog.addActionListener(new MatchLogListener());
        toolBar.add(btnMatchLog);

        //补充匹配物流数据 组件和注册监听器
        btnMatchTrans = new JButton("匹配物流数据");
        //注册监听
        btnMatchTrans.addActionListener(new MatchTransListener());
        toolBar.add(btnMatchTrans);

        //ImageIcon saveIcon = new ImageIcon("images\\saveData.png");
        btnSave = new JButton("保存数据");
        //注册监听
        btnSave.addActionListener(new SaveListener());
        toolBar.add(btnSave);

        //ImageIcon sendIcon = new ImageIcon("images\\sendData.png");
        btnSend = new JButton("发送数据");
        //注册监听
        btnSend.addActionListener(new SendDataListener());
        toolBar.add(btnSend);

        //ImageIcon showIcon = new ImageIcon("images\\showData.png");
        btnShow = new JButton("显示数据");
        //注册监听
        btnShow.addActionListener(new ShowDataListener());
        toolBar.add(btnShow);
    }

    // 初始化日志数据采集界面的方法
    private void initLogGatherGUI() {

        //IP地址获取
        String IpAddress = null;
        try {
            InetAddress myAddress = InetAddress.getLocalHost();
            IpAddress = myAddress.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        pLog = new JPanel();
        tpGather.addTab("日志", pLog);
        pLog.setLayout(new BoxLayout(pLog, BoxLayout.Y_AXIS));

        pLogId = new JPanel();
        pLog.add(pLogId);
        pLogId.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        lblLogId = new JLabel("日志ID：");
        pLogId.add(lblLogId);

        txtLogId = new JTextField();
        txtLogId.setPreferredSize(new Dimension(100, 20));
        pLogId.add(txtLogId);

        pName = new JPanel();
        pLog.add(pName);
        pName.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        lblName = new JLabel("用户名：");
        pName.add(lblName);

        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(100, 20));
        pName.add(txtName);

        pLocation = new JPanel();
        pLog.add(pLocation);

        lblLocation = new JLabel("登录地点：");
        pLocation.add(lblLocation);

        txtLocation = new JTextField();
        txtLocation.setPreferredSize(new Dimension(100, 20));
        pLocation.add(txtLocation);

        pIP = new JPanel();
        pLog.add(pIP);

        lblIP = new JLabel("登录IP：");
        pIP.add(lblIP);

        txtIP = new JTextField(IpAddress);
        pIP.add(txtIP);
        txtIP.setEditable(false);


        pLogStatus = new JPanel();
        pLog.add(pLogStatus);

        lblLogStatus = new JLabel("登录状态：");
        pLogStatus.add(lblLogStatus);

        rbLogin = new JRadioButton("登录");
        pLogStatus.add(rbLogin);
        rbLogin.setSelected(true);

        rbLogout = new JRadioButton("登出");
        pLogStatus.add(rbLogout);

        ButtonGroup bg = new ButtonGroup();
        bg.add(rbLogin);
        bg.add(rbLogout);

        pLogButton = new JPanel();
        pLog.add(pLogButton);

        btnLogConfirm = new JButton("采集");
        // 添加确认按钮监听
        btnLogConfirm.addActionListener(new GatherLogListener());
        pLogButton.add(btnLogConfirm);

        btnLogReset = new JButton("重置");
        // 添加重置按钮监听
        btnLogReset.addActionListener(new ResetListener());
        pLogButton.add(btnLogReset);
    }

    // 初始化物流数据采集界面的方法
    private void initTransGatherGUI() {
        pTran = new JPanel();
        tpGather.addTab("物流", pTran);
        pTran.setLayout(new BoxLayout(pTran, BoxLayout.Y_AXIS));

        pTransId = new JPanel();
        pTran.add(pTransId);

        lblTransId = new JLabel("物流ID：");
        pTransId.add(lblTransId);

        txtTransId = new JTextField();
        txtTransId.setPreferredSize(new Dimension(100, 20));
        pTransId.add(txtTransId);

        pAdress = new JPanel();
        pTran.add(pAdress);

        lblAdress = new JLabel("目的地：");
        pAdress.add(lblAdress);

        txtAdress = new JTextField();
        txtAdress.setPreferredSize(new Dimension(100, 20));
        pAdress.add(txtAdress);

        pHandler = new JPanel();
        pTran.add(pHandler);

        lblHandler = new JLabel("经手人：");
        pHandler.add(lblHandler);

        txtHandler = new JTextField();
        txtHandler.setPreferredSize(new Dimension(100, 20));
        pHandler.add(txtHandler);

        pReceiver = new JPanel();
        pTran.add(pReceiver);

        lblReceiver = new JLabel("收货人：");
        pReceiver.add(lblReceiver);

        txtReceiver = new JTextField();
        txtReceiver.setPreferredSize(new Dimension(100, 20));
        pReceiver.add(txtReceiver);

        pTranStatus = new JPanel();
        pTran.add(pTranStatus);

        lblTranStatus = new JLabel("物流状态：");
        pTranStatus.add(lblTranStatus);

        String[] tranStatus = new String[]{"发货中", "送货中", "已签收"};
        cmbTanStatus = new JComboBox<String>(tranStatus);
        pTranStatus.add(cmbTanStatus);

        pTranButton = new JPanel();
        pTran.add(pTranButton);

        btnTranConfirm = new JButton("采集");
        btnTranConfirm.addActionListener(new GatherTransListener());
        pTranButton.add(btnTranConfirm);

        btnTranReset = new JButton("重置");
        btnTranReset.addActionListener(new ResetListener());
        pTranButton.add(btnTranReset);
    }

    // 数据采集监听类
    private class GatherListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // 切换主面板的卡片为采集面板
            card.show(p, "gather");
        }
    }

    // 日志数据采集监听类
    private class GatherLogListener implements ActionListener {
        // 数据采集的事件处理方法
        public void actionPerformed(ActionEvent e) {
            // 获取日志ID
            int id = Integer.parseInt(txtLogId.getText().trim());
            // 创建当前时间
            Date time = new Date();
            // 获取地址栏地址
            String adress = txtLocation.getText().trim();
            // 设置数据类型为：采集
            int type = DataBase.GATHER;
            // 获取用户姓名
            String user = txtName.getText().trim();
            // 获取ip地址
            String ip = txtIP.getText().trim();
            // 设置日志类型
            int logType = rbLogin.isSelected() ? LogRec.LOG_IN : LogRec.LOG_OUT;
            // 将数据封装到日志对象
            log = new LogRec(id, time, adress, type, user, ip, logType);
            // 将日志对象添加到日志列表
            logList.add(log);
            // 显示对话框
            JOptionPane.showMessageDialog(null, "日志数据采集成功！", "成功提示",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 物流数据采集监听类
    private class GatherTransListener implements ActionListener {
        // 数据采集的事件处理方法
        public void actionPerformed(ActionEvent e) {
            // 获取物流ID
            int id = Integer.parseInt(txtTransId.getText().trim());
            // 创建当前时间
            Date time = new Date();
            // 获取地址栏地址
            String adress = txtAdress.getText().trim();
            // 设置数据类型为：采集
            int type = DataBase.GATHER;
            // 获取经手人信息
            String handler = txtHandler.getText().trim();
            // 获取发送人信息
            String reciver = txtReceiver.getText().trim();
            // 设置物流类型
            int transportType = cmbTanStatus.getSelectedIndex() + 1;
            // 将数据包装成物流对象
            trans = new Transport(id, time, adress, type, handler, reciver,
                    transportType);
            // 将物流对象放入物流列表
            transList.add(trans);
            // 显示对话框
            JOptionPane.showMessageDialog(null, "物流数据采集成功！", "成功提示",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // 重置按钮监听类
    private class ResetListener implements ActionListener {
        // 重置按钮的事件处理方法
        public void actionPerformed(ActionEvent e) {
            txtLogId.setText("");
            txtName.setText("");
            txtLocation.setText("");
            txtTransId.setText("");
            txtAdress.setText("");
            txtHandler.setText("");
            txtReceiver.setText("");
        }
    }

    // 匹配日志信息监听类
    private class MatchLogListener implements ActionListener {
        // 数据匹配的事件处理方法
        public void actionPerformed(ActionEvent e) {
            LogRecAnalyse logAn = new LogRecAnalyse(logList);
            // 日志数据过滤
            logAn.doFilter();
            // 日志数据分析
            matchedLogs = logAn.matchData();
            // 显示对话框
            if (matchedLogs.size() == 0) {
                JOptionPane.showMessageDialog(null, "日志数据过滤、分析匹配失败！", "失败提示",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "日志数据过滤、分析匹配成功！", "成功提示",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    // 匹配物流信息监听类
    private class MatchTransListener implements ActionListener {
        // 数据匹配的事件处理方法
        public void actionPerformed(ActionEvent e) {
            TransportAnalyse transAn = new TransportAnalyse(transList);
            //物流数据过滤
            transAn.doFilter();
            //物流数据分析
            matchedTrans = transAn.matchData();
            //显示对话框
            if (matchedTrans.size() != 0) {
                JOptionPane.showMessageDialog(null, "物流数据过滤、分析匹配成功！", "成功提示",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "物流数据过滤、分析匹配失败！", "失败提示",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    //保存数据监听类
    private class SaveListener implements ActionListener {
        //数据保存的处理方法
        @Override
        public void actionPerformed(ActionEvent e) {
            //日志数据保存
            if (matchedLogs != null && matchedLogs.size() > 0) {
                logRecService.saveAndAppendMatchLog(matchedLogs);
                //logRecService.saveMatchLogToDB(matchedLogs);
                JOptionPane.showMessageDialog(null, "匹配的日志数据保存到文件成功！", "成功提示",
                        JOptionPane.PLAIN_MESSAGE);
                //matchedLogs.clear();
                logList.clear();
            } else {
                JOptionPane.showMessageDialog(null, "没有匹配的日志数据！", "失败提示",
                        JOptionPane.PLAIN_MESSAGE);
            }

            //物流数据保存
            if (matchedTrans.size() > 0 && matchedTrans != null) {
                transportService.saveAndAppendMatchedTransport(matchedTrans);
                //transportService.saveMatchTransportToDB(matchedTrans);
                JOptionPane.showMessageDialog(null, "匹配的物流数据保存到文件成功！", "成功提示",
                        JOptionPane.PLAIN_MESSAGE);
                //matchedTrans.clear();
                transList.clear();
            } else {
                JOptionPane.showMessageDialog(null, "没有匹配的物流数据！", "失败提示",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    //显示数据监听类
    private class ShowDataListener implements ActionListener {
        // 数据显示的事件处理方法
        public void actionPerformed(ActionEvent e) {
            // 切换主面板的卡片为显示数据的面板
            card.show(p, "show");
            // 移除显示数据面板中的所有的选项卡
            showPane.removeAll();
            // 刷新日志信息表
            flushMatchedLogTable();
            // 刷新物流信息表
            flushMatchedTransTable();
        }
    }

    //线程类，每隔30s刷新1次显示数据表格中的数据与数据库保持同步
    private class UpdateTableThread extends Thread {
        //重写run（）方法
        public void run() {
            while (true) {
                //移除所有的选项卡
                showPane.removeAll();
                //刷新日志信息
                flushMatchedLogTable();
                //刷新物流信息
                flushMatchedTransTable();
                try {
                    //线程挂起1分钟
                    Thread.sleep(1 * 30 * 1000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //发送数据监听类
    private class SendDataListener implements ActionListener {
        //发送数据的事件处理方法
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                //判断匹配的日志信息列表是否为空
                if (matchedLogs != null && matchedLogs.size() > 0) {
                    //创建Socket发送日志信息，日志信息发送到服务器的6666端口
                    Socket logSocket = new Socket(serverIP, 6666);
                    //创建用于序列化匹配日志信息对象的输出流
                    ObjectOutputStream logOutputStream = new ObjectOutputStream(
                            logSocket.getOutputStream());
                    //向流中写入匹配的日志信息
                    logOutputStream.writeObject(matchedLogs);
                    logOutputStream.flush();
                    logOutputStream.close();
                    //因匹配的日志信息已发送到服务器，因此清空日志列表
                    matchedLogs.clear();
                    logList.clear();
                    //显示对话框
                    JOptionPane.showMessageDialog(null, "匹配的日志数据已发送到服务器！",
                            "成功提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "没有匹配的日志数据！",
                            "失败提示", JOptionPane.INFORMATION_MESSAGE);
                }

                if (matchedTrans != null && matchedTrans.size() > 0) {
                    //创建Socket发送方物流信息，物流信息发送到服务器的6668端口
                    Socket transSocket = new Socket(serverIP, 6668);
                    //创建用于序列化匹配物流信息对象的输出流
                    ObjectOutputStream transOutputStream = new ObjectOutputStream(
                            transSocket.getOutputStream());
                    //向流中写入匹配的物流信息
                    transOutputStream.writeObject(matchedTrans);
                    transOutputStream.flush();
                    transOutputStream.close();
                    //物流信息已发送到服务器，因此清空物流列表
                    matchedTrans.clear();
                    transList.clear();
                    //显示对话框
                    JOptionPane.showMessageDialog(null, "匹配的物流数据已发送到服务器！",
                            "成功提示", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "没有匹配的物流数据！",
                            "失败提示", JOptionPane.INFORMATION_MESSAGE);
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    // 刷新日志选项卡，显示日志信息表格
    private void flushMatchedLogTable() {
        // 创建tableModel，通过标志为区分日志和物流：1，日志；0，物流
        MatchedTableModel logModel = new MatchedTableModel(
                logRecService.readLogResult(), 1);
        // 使用tableModel创建JTable
        JTable logTable = new JTable(logModel);
        // 通过JTable对象创建JScrollPane，显示数据
        scrollPane = new JScrollPane(logTable);
        // 添加日志选项卡
        showPane.addTab("日志", scrollPane);
    }

    // 刷新物流选项卡，显示物流信息表格
    private void flushMatchedTransTable() {
        //显示物流数据表格
        //创建tableModel,通过标志为区分日志物流：1.日志   0.物流
        MatchedTableModel TransModel = new MatchedTableModel(
                transportService.readTransResult(), 0);
        //使用tableModel创建JTble
        JTable TransTable = new JTable(TransModel);
        //通过JTable对象创建JScrollPane,显示数据
        scrollPane = new JScrollPane(TransTable);
        //添加物流选项卡
        showPane.addTab("物流", scrollPane);
    }
}
