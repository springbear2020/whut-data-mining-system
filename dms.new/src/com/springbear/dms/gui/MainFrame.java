package com.springbear.dms.gui;

import com.springbear.dms.entity.*;
import com.springbear.dms.gather.LogRecAnalyse;
import com.springbear.dms.gather.TransportAnalyse;
import com.springbear.dms.service.LogRecService;
import com.springbear.dms.service.TransportService;
import com.springbear.dms.utils.DruidUtil;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

/**
 * 系统主界面
 *
 * @author Admin
 */
public class MainFrame extends JFrame {
    private JPanel contentPane;
    private JTabbedPane tabbedPane;
    private JTextField textLocation;
    private JTextField textUser;
    private JTextField textIp;
    private JRadioButton radioButtonLogin;
    private JTextField textAddress;
    private JTextField textHandler;
    private JTextField textReceiver;
    private JRadioButton radioButtonSend;
    private JRadioButton radioButtonTrans;
    private JRadioButton radioButtonReceive;

    /**
     * 本机IP地址
     */
    private static String ipAddress;
    /**
     * 显示所有用户管理员密码
     */
    private static String ADMIN_PASSWORD;
    /**
     * 登录系统用户名
     */
    private static String loginUser;

    private static Connection connection = null;
    private static ResultSet resultSet = null;
    private static PreparedStatement preparedStatement = null;

    LogRecService logRecService = new LogRecService();
    ArrayList<LogRec> logRecs = new ArrayList<>();
    ArrayList<MatchedLogRec> matchedLogRecs = new ArrayList<>();
    TransportService transportService = new TransportService();
    ArrayList<Transport> transports = new ArrayList<>();
    ArrayList<MatchedTransport> matchedTransports = new ArrayList<>();

    public static void setUser(String user) {
        MainFrame.loginUser = user;
    }

    static {
        try {
            ipAddress = InetAddress.getLocalHost().getHostAddress();
            Properties properties = new Properties();
            properties.load(new FileInputStream("dms.new\\config\\admin.properties"));
            ADMIN_PASSWORD = properties.getProperty("adminPassword");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MainFrame() {
        initFrame();
        initMenuBar();
    }

    /**
     * 初始化窗体
     */
    public void initFrame() {
        setSize(666, 666);
        setTitle("\u6B22\u8FCE\u4F7F\u7528\u6570\u636E\u6316\u6398\u7CFB\u7EDF\u5BA2\u6237\u7AEF\uFF01");
        setIconImage(Toolkit.getDefaultToolkit().getImage("dms.new\\resources\\images\\BeFree.jpg"));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 主面板
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // 选项卡
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        // 日志、物流采集面板
        initGatherLogPanel();
        initGatherTransPanel();
    }

    /**
     * 初始化菜单栏
     */
    private void initMenuBar() {
        // 菜单栏
        JMenuBar menuBar = new JMenuBar();
        contentPane.add(menuBar, BorderLayout.NORTH);

        // 采集数据菜单
        JMenu menuGather = new JMenu("\u91C7\u96C6\u6570\u636E");
        menuBar.add(menuGather);
        // 采集日志数据菜单项
        JMenuItem menuItemGatherLog = new JMenuItem("\u65E5\u5FD7\u6570\u636E");
        menuGather.add(menuItemGatherLog);
        // 采集物流数据菜单项
        JMenuItem menuItemGatherTrans = new JMenuItem("\u7269\u6D41\u6570\u636E");
        menuGather.add(menuItemGatherTrans);

        // 匹配数据菜单
        JMenu menuMatch = new JMenu("\u5339\u914D\u6570\u636E");
        menuBar.add(menuMatch);
        // 匹配日志数据菜单项
        JMenuItem menuItemMatchLog = new JMenuItem("\u65E5\u5FD7\u6570\u636E");
        menuMatch.add(menuItemMatchLog);
        // 匹配物流数据菜单项
        JMenuItem menuItemMatchTrans = new JMenuItem("\u7269\u6D41\u6570\u636E");
        menuMatch.add(menuItemMatchTrans);

        // 保存数据菜单
        JMenu saveMenu = new JMenu("\u4FDD\u5B58\u6570\u636E");
        menuBar.add(saveMenu);
        // 日志数据保存菜单项
        JMenuItem menuItemSaveLog = new JMenuItem("\u65E5\u5FD7\u6570\u636E");
        saveMenu.add(menuItemSaveLog);
        // 物流数据保存菜单项
        JMenuItem menuItemSaveTrans = new JMenuItem("\u7269\u6D41\u6570\u636E");
        saveMenu.add(menuItemSaveTrans);

        // 发送数据菜单
        JMenu menuSend = new JMenu("\u53D1\u9001\u6570\u636E");
        menuBar.add(menuSend);
        // 发送日志数据菜单项
        JMenuItem menuItemSendLog = new JMenuItem("\u65E5\u5FD7\u6570\u636E");
        menuSend.add(menuItemSendLog);
        // 发送物流数据菜单项
        JMenuItem menuItemSendTrans = new JMenuItem("\u7269\u6D41\u6570\u636E");
        menuSend.add(menuItemSendTrans);

        // 显示数据菜单
        JMenu menuShow = new JMenu("\u663E\u793A\u6570\u636E");
        menuBar.add(menuShow);
        // 日志数据显示菜单项
        JMenuItem menuItemShowLog = new JMenuItem("\u65E5\u5FD7\u6570\u636E");
        menuShow.add(menuItemShowLog);
        // 物流数据显示菜单项
        JMenuItem menuItemShowTrans = new JMenuItem("\u7269\u6D41\u6570\u636E");
        menuShow.add(menuItemShowTrans);

        // 系统介绍菜单
        JMenu menuHelp = new JMenu("\u7CFB\u7EDF\u5E2E\u52A9");
        menuBar.add(menuHelp);
        // 用户信息菜单项
        JMenuItem menuItemAllUsers = new JMenuItem("\u7528\u6237\u4FE1\u606F");
        menuHelp.add(menuItemAllUsers);
        // 关于系统
        JMenuItem menuItemAbout = new JMenuItem("\u5173\u4E8E\u7CFB\u7EDF");
        menuHelp.add(menuItemAbout);

        // 欢迎语文本框
        JTextField textWelcome = new JTextField();
        textWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        textWelcome.setFont(new Font("SimSun", Font.PLAIN, 15));
        textWelcome.setEditable(false);
        textWelcome.setText("欢迎 " + MainFrame.loginUser + " 登录!");
        menuBar.add(textWelcome);


        // 日志信息采集事件注册监听
        menuItemGatherLog.addActionListener(new GatherLogListener());
        // 物流信息采集事件注册监听
        menuItemGatherTrans.addActionListener(new GatherTransListener());
        // 日志信息匹配事件注册监听
        menuItemMatchLog.addActionListener(new MatchLogListener());
        // 物流信息匹配事件注册监听
        menuItemMatchTrans.addActionListener(new MatchTransListener());
        // 日志信息保存事件注册监听
        menuItemSaveLog.addActionListener(new SaveLogListener());
        // 物流信息保存事件注册监听
        menuItemSaveTrans.addActionListener(new SaveTransListener());
        // 日志信息发送事件注册监听
        menuItemSendLog.addActionListener(new SendLogListener());
        // 物流信息发送事件注册监听
        menuItemSendTrans.addActionListener(new SendTransListener());
        // 日志信息显示事件注册监听
        menuItemShowLog.addActionListener(new ShowLogListener());
        // 物流信息显示事件注册监听
        menuItemShowTrans.addActionListener(new ShowTransListener());
        // 显示用户信息事件注册监听
        menuItemAllUsers.addActionListener(new ShowAllUsersListener());
        // 关于系统事件注册监听
        menuItemAbout.addActionListener(new AboutListener());
    }

    /**
     * 初始化日志数据采集面板
     */
    private void initGatherLogPanel() {
        // 日志数据采集面板
        JPanel logGatherPanel = new JPanel();
        tabbedPane.addTab("日志采集", null, logGatherPanel, null);
        logGatherPanel.setLayout(null);

        // 用户名标签
        JLabel lblUser = new JLabel("\u767B\u5F55\u540D");
        lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUser.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblUser.setBounds(138, 50, 80, 40);
        logGatherPanel.add(lblUser);
        // 用户名文本框
        textUser = new JTextField();
        textUser.setFont(new Font("SimSun", Font.PLAIN, 20));
        textUser.setColumns(10);
        textUser.setBounds(232, 50, 250, 40);
        logGatherPanel.add(textUser);

        // IP 标签
        JLabel lblIp = new JLabel("\u767B\u5F55IP");
        lblIp.setHorizontalAlignment(SwingConstants.RIGHT);
        lblIp.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblIp.setBounds(138, 250, 80, 40);
        logGatherPanel.add(lblIp);
        // IP 文本框
        textIp = new JTextField();
        textIp.setHorizontalAlignment(SwingConstants.CENTER);
        textIp.setFont(new Font("SimSun", Font.PLAIN, 20));
        textIp.setBounds(232, 250, 250, 40);
        textIp.setText(ipAddress);
        textIp.setEditable(false);
        logGatherPanel.add(textIp);

        // 登录位置标签
        JLabel lblLocation = new JLabel("\u767B\u5F55\u5730\u70B9");
        lblLocation.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLocation.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblLocation.setBounds(138, 150, 80, 40);
        logGatherPanel.add(lblLocation);
        // 登录位置文本框
        textLocation = new JTextField();
        textLocation.setFont(new Font("SimSun", Font.PLAIN, 20));
        textLocation.setColumns(10);
        textLocation.setBounds(232, 150, 250, 40);
        logGatherPanel.add(textLocation);

        // 日志类型标签
        JLabel lblLogType = new JLabel("\u767B\u5F55\u72B6\u6001");
        lblLogType.setHorizontalAlignment(SwingConstants.RIGHT);
        lblLogType.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblLogType.setBounds(138, 350, 80, 40);
        logGatherPanel.add(lblLogType);
        // 日志类型单选按钮组
        ButtonGroup buttonGroupLogType = new ButtonGroup();
        // 登录单选按钮
        radioButtonLogin = new JRadioButton("\u767B\u5F55");
        buttonGroupLogType.add(radioButtonLogin);
        radioButtonLogin.setSelected(true);
        radioButtonLogin.setFont(new Font("SimSun", Font.PLAIN, 17));
        radioButtonLogin.setBounds(280, 351, 80, 40);
        logGatherPanel.add(radioButtonLogin);
        // 登出单选按钮
        JRadioButton radioButtonLogout = new JRadioButton("\u767B\u51FA");
        buttonGroupLogType.add(radioButtonLogout);
        radioButtonLogout.setFont(new Font("SimSun", Font.PLAIN, 17));
        radioButtonLogout.setBounds(380, 350, 80, 40);
        logGatherPanel.add(radioButtonLogout);

        // 日志采集按钮
        JButton btnGatherLog = new JButton("\u91C7\u96C6");
        btnGatherLog.setBounds(232, 436, 80, 40);
        logGatherPanel.add(btnGatherLog);
        // 重置按钮
        JButton btnReset = new JButton("\u91CD\u7F6E");
        btnReset.setBounds(402, 436, 80, 40);
        logGatherPanel.add(btnReset);

        // 日志采集按钮事件注册监听
        btnGatherLog.addActionListener(new LogGatherPanelListener());
        // 重置按钮事件注册监听
        btnReset.addActionListener(new LogResetPanelListener());
    }

    /**
     * 初始化物流采集面板
     */
    private void initGatherTransPanel() {
        // 物流数据采集面板
        JPanel transGatherPanel = new JPanel();
        tabbedPane.addTab("物流采集", null, transGatherPanel, null);
        transGatherPanel.setLayout(null);

        // 经手人标签
        JLabel lblHandler = new JLabel("\u7ECF\u624B\u4EBA");
        lblHandler.setHorizontalAlignment(SwingConstants.RIGHT);
        lblHandler.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblHandler.setBounds(138, 50, 80, 40);
        transGatherPanel.add(lblHandler);
        // 经手人文本框
        textHandler = new JTextField();
        textHandler.setFont(new Font("SimSun", Font.PLAIN, 20));
        textHandler.setBounds(230, 50, 250, 40);
        transGatherPanel.add(textHandler);
        textHandler.setColumns(10);

        // 收货人标签
        JLabel lblReceiver = new JLabel("\u6536\u8D27\u4EBA");
        lblReceiver.setHorizontalAlignment(SwingConstants.RIGHT);
        lblReceiver.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblReceiver.setBounds(138, 150, 80, 40);
        transGatherPanel.add(lblReceiver);
        // 收货人文本框
        textReceiver = new JTextField();
        textReceiver.setFont(new Font("SimSun", Font.PLAIN, 20));
        textReceiver.setColumns(10);
        textReceiver.setBounds(230, 150, 250, 40);
        transGatherPanel.add(textReceiver);

        // 收货地址标签
        JLabel lblAddress = new JLabel("\u6536\u8D27\u5730\u5740");
        lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAddress.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblAddress.setBounds(138, 250, 80, 40);
        transGatherPanel.add(lblAddress);
        // 收货人文本框
        textAddress = new JTextField();
        textAddress.setFont(new Font("SimSun", Font.PLAIN, 20));
        textAddress.setColumns(10);
        textAddress.setBounds(230, 250, 250, 40);
        transGatherPanel.add(textAddress);

        // 物流状态标签
        JLabel lblTransType = new JLabel("\u7269\u6D41\u72B6\u6001");
        lblTransType.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTransType.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblTransType.setBounds(138, 350, 80, 40);
        transGatherPanel.add(lblTransType);
        // 物流状态单选按钮组
        ButtonGroup buttonGroupType = new ButtonGroup();
        // 发货中按钮
        radioButtonSend = new JRadioButton("\u53D1\u8D27\u4E2D");
        radioButtonSend.setSelected(true);
        buttonGroupType.add(radioButtonSend);
        radioButtonSend.setFont(new Font("SimSun", Font.PLAIN, 17));
        radioButtonSend.setBounds(234, 351, 80, 40);
        transGatherPanel.add(radioButtonSend);
        // 运输中按钮
        radioButtonTrans = new JRadioButton("\u8FD0\u8F93\u4E2D");
        buttonGroupType.add(radioButtonTrans);
        radioButtonTrans.setFont(new Font("SimSun", Font.PLAIN, 17));
        radioButtonTrans.setBounds(320, 351, 80, 40);
        transGatherPanel.add(radioButtonTrans);
        // 已签收按钮
        radioButtonReceive = new JRadioButton("\u5DF2\u7B7E\u6536");
        buttonGroupType.add(radioButtonReceive);
        radioButtonReceive.setFont(new Font("SimSun", Font.PLAIN, 17));
        radioButtonReceive.setBounds(402, 351, 80, 40);
        transGatherPanel.add(radioButtonReceive);

        // 物流采集按钮
        JButton btnGather = new JButton("\u91C7\u96C6");
        btnGather.setBounds(230, 447, 80, 40);
        transGatherPanel.add(btnGather);
        // 重置按钮
        JButton btnReset = new JButton("\u91CD\u7F6E");
        btnReset.setBounds(400, 447, 80, 40);
        transGatherPanel.add(btnReset);

        // 采集按钮事件注册监听
        btnGather.addActionListener(new GatherTransPanelListener());
        // 重置按钮事件注册监听
        btnReset.addActionListener(new TransResetPanelListener());
    }

    /**
     * 初始化日志信息表格
     */
    private void initLogTable() {
        String sql = "SELECT * from gather_log;";
        try {
            connection = DruidUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            TableModel tableModel = new TableModel(resultSet, TableModel.LOG_SIGN);
            JTable logTable = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(logTable);
            tabbedPane.addTab("显示日志", null, scrollPane, null);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化物流信息表格
     */
    private void initTransTable() {
        String sql = "SELECT * from gather_transport;";
        try {
            connection = DruidUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery(sql);
            TableModel tableModel = new TableModel(resultSet, TableModel.TRANS_SIGN);
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            tabbedPane.addTab("显示物流", null, scrollPane, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化用户信息数据表格
     */
    private void initUsersTable() {
        String sql = "SELECT * from user_info;";
        try {
            connection = DruidUtil.getConnection();
            preparedStatement = connection.prepareStatement(sql);
            resultSet = preparedStatement.executeQuery();
            TableModel tableModel = new TableModel(resultSet, TableModel.USER_SIGN);
            JTable table = new JTable(tableModel);
            JScrollPane scrollPane = new JScrollPane(table);
            tabbedPane.addTab("用户信息", null, scrollPane, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 采集日志事件响应
     */
    private class GatherLogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabbedPane.removeAll();
            initGatherLogPanel();
        }
    }

    /**
     * 物流采集事件响应
     */
    private class GatherTransListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabbedPane.removeAll();
            initGatherTransPanel();
        }
    }

    /**
     * 物流匹配事件响应
     */
    private class MatchTransListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransportAnalyse transportAnalyse = new TransportAnalyse(transports);
            transportAnalyse.doFilter();
            matchedTransports = transportAnalyse.matchData();
            if (matchedTransports.size() == 0) {
                JOptionPane.showMessageDialog(null, "物流数据匹配失败，请重新采集后再匹配！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "物流数据过滤、匹配成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
            transports.clear();
        }
    }

    /**
     * 日志匹配事件响应
     */
    private class MatchLogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            LogRecAnalyse logRecAnalyse = new LogRecAnalyse(logRecs);
            logRecAnalyse.doFilter();
            matchedLogRecs = logRecAnalyse.matchData();
            if (matchedLogRecs.size() == 0) {
                JOptionPane.showMessageDialog(null, "日志数据匹配失败，请重新采集后再匹配！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "日志数据过滤、分析匹配成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
            logRecs.clear();
        }
    }

    /**
     * 物流信息保存事件响应
     */
    private class SaveTransListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!transportService.saveAppendMatchedTransport(matchedTransports)) {
                JOptionPane.showMessageDialog(null, "物流信息保存失败，请重新采集匹配后保存！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "物流信息保存到文件成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 日志信息保存事件响应
     */
    private class SaveLogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!logRecService.saveAppendMatchedLogRec(matchedLogRecs)) {
                JOptionPane.showMessageDialog(null, "日志信息保存失败，请重新采集匹配后保存！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "日志信息保存到文件成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 物流信息发送事件响应
     */
    private class SendTransListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!transportService.saveMatchedTransToDb(matchedTransports)) {
                JOptionPane.showMessageDialog(null, "物流信息发送失败，请重新采集匹配后发送!", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            matchedTransports.clear();
            JOptionPane.showMessageDialog(null, "物流信息保存到数据库成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 日志信息发送事件响应
     */
    private class SendLogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (!logRecService.saveMatchedLogsToDb(matchedLogRecs)) {
                JOptionPane.showMessageDialog(null, "日志信息发送失败，请重新采集匹配后发送!", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            matchedLogRecs.clear();
            JOptionPane.showMessageDialog(null, "日志信息保存到数据库成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 物流信息显示事件响应
     */
    private class ShowTransListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabbedPane.removeAll();
            initTransTable();
        }
    }

    /**
     * 日志信息显示事件响应
     */
    private class ShowLogListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            tabbedPane.removeAll();
            initLogTable();
        }
    }

    /**
     * 显示所有用户事件响应
     */
    private class ShowAllUsersListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String verifyPwd = JOptionPane.showInputDialog(null, "请输入管理员密码：", "管理员验证", JOptionPane.INFORMATION_MESSAGE);
            if (verifyPwd.equals(ADMIN_PASSWORD)) {
                tabbedPane.removeAll();
                initUsersTable();
            } else {
                JOptionPane.showMessageDialog(null, "对不起，您没有权限查看用户信息！", "失败提示", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 关于系统菜单项事件响应
     */
    private static class AboutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JOptionPane.showMessageDialog(null, "开发者：Spring-_-Bear   开发时间：2021年11月19日", "关于系统", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 日志数据采集面板采集按钮事件响应
     */
    private class LogGatherPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date date = new Date();
            String location = textLocation.getText();
            int type = LogRec.GATHER;
            String username = textUser.getText();
            String ip = textIp.getText();
            int logType = radioButtonLogin.isSelected() ? 1 : 2;

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "登录名不能为空，请重新输入！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (location.length() == 0) {
                JOptionPane.showMessageDialog(null, "登录地点不能为空，请重新输入！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            logRecs.add(new LogRec(date, location, type, username, ip, logType));
            JOptionPane.showMessageDialog(null, "日志信息采集成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * 日志数据采集面板重置按钮事件响应
     */
    private class LogResetPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textUser.setText("");
            textLocation.setText("");
        }
    }

    /**
     * 物流数据采集面板重置按钮事件响应
     */
    private class TransResetPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textHandler.setText("");
            textReceiver.setText("");
            textAddress.setText("");
        }
    }

    /**
     * 物流数据采集面板采集按钮事件响应
     */
    private class GatherTransPanelListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date date = new Date();
            String address = textAddress.getText();
            int type = Transport.GATHER;
            String handler = textHandler.getText();
            String receiver = textReceiver.getText();
            int transportType = 0;
            if (radioButtonSend.isSelected()) {
                transportType = 1;
            } else if (radioButtonTrans.isSelected()) {
                transportType = 2;
            } else if (radioButtonReceive.isSelected()) {
                transportType = 3;
            }

            if (handler.length() == 0) {
                JOptionPane.showMessageDialog(null, "经手人信息不能为空，请重新输入！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (receiver.length() == 0) {
                JOptionPane.showMessageDialog(null, "收货人信息不能为空，请重新输入！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (address.length() == 0) {
                JOptionPane.showMessageDialog(null, "收货地址信息不能为空，请重新输入！", "失败提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            transports.add(new Transport(date, address, type, handler, receiver, transportType));
            JOptionPane.showMessageDialog(null, "物流信息采集成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
