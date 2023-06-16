package com.qst.dms.ui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
//import javax.swing.JComboBox;
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

//主窗口
public class MainFrame_temp extends JFrame {
	// 声明界面组件
	private JMenuBar menuBar;
	private JMenu menuOperate, menuHelp, menuMatch;
	private JMenuItem miGather, miSave, miSend,
			miShow, miExit, miCheck, miAbout;
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
	private JButton btnNewButton;

	// 构造方法
	public MainFrame_temp() {
		// 调用父类的构造方法
		super("Q-DMS系统客户端");

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

		// 初始化菜单
		initMenu();
		// 初始化工具栏
		initToolBar();

		// 设置主面板为CardLayout卡片布局
		card = new CardLayout();
		p = new JPanel();
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
		getContentPane().add(p, BorderLayout.CENTER);

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

		miSave = new JMenuItem("保存数据");
		miSave.addActionListener(new SaveDataListener());
		menuOperate.add(miSave);

		miSend = new JMenuItem("发送数据");
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

		miAbout = new JMenuItem("关于系统");
		// 注册监听
		miAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 显示消息对话框
				JOptionPane.showMessageDialog(null,
						"开发人：", "自行补充",
						JOptionPane.WARNING_MESSAGE);
			}
		});
		menuHelp.add(miAbout);
	}

	// 初始化工具栏的方法
	private void initToolBar() {
		// 创建工具栏
		toolBar = new JToolBar();
		// 将工具栏添加到窗体北部（上面）
		getContentPane().add(toolBar, BorderLayout.NORTH);

		// 添加带有图标的工具栏按钮--->采集数据
		ImageIcon gatherIcon = new ImageIcon("images\\gatherData.png");
		btnGather = new JButton("采集数据", gatherIcon);
		// 注册监听
		btnGather.addActionListener(new GatherListener());
		toolBar.add(btnGather);
		
		// 添加带有图标的工具栏按钮--->匹配日志数据
		ImageIcon matchIcon = new ImageIcon("images\\matchData.png");
		btnMatchLog = new JButton("匹配日志数据", matchIcon);
		// 注册监听
		btnMatchLog.addActionListener(new MatchLogListener());
		toolBar.add(btnMatchLog);

		//需要补充添加带有图标的工具栏按钮--->匹配日志数据 ，并注册监听
		/*...
		 * 
		 */
		// 添加带有图标的工具栏按钮--->保存数据
		ImageIcon saveIcon = new ImageIcon("images\\saveData.png");
		btnSave = new JButton("保存数据", saveIcon);
		// 注册监听
		btnSave.addActionListener(new SaveDataListener());
		toolBar.add(btnSave);

		// 添加带有图标的工具栏按钮--->发送数据
		ImageIcon sendIcon = new ImageIcon("images\\sendData.png");
		btnSend = new JButton("发送数据", sendIcon);
		btnSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		toolBar.add(btnSend);
		
		// 添加带有图标的工具栏按钮--->显示数据
		ImageIcon showIcon = new ImageIcon("images\\showData.png");
		btnShow = new JButton("显示数据", showIcon);
		btnShow.addActionListener(new ShowDataListener());
		toolBar.add(btnShow);
	}

	// 初始化日志数据采集界面的方法
	private void initLogGatherGUI() {
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

			txtIP = new JTextField();
			txtIP.setPreferredSize(new Dimension(100, 20));
			pIP.add(txtIP);

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

			btnLogConfirm = new JButton("确认");
			// 添加确认按钮监听
			btnLogConfirm.addActionListener(new GatherLogListener());
			
			btnNewButton = new JButton("New button");
			pLogButton.add(btnNewButton);
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

		String[] tranStatus = new String[] { "发货中", "送货中", "已签收" };

		pTranButton = new JPanel();
		pTran.add(pTranButton);

		btnTranConfirm = new JButton("确认");
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
			JOptionPane.showMessageDialog(null, "日志采集成功！", "提示",
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
			JOptionPane.showMessageDialog(null, "物流采集成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// 重置按钮监听类
	private class ResetListener implements ActionListener {
		// 重置按钮的事件处理方法
		public void actionPerformed(ActionEvent e) {
			txtName.setText("");
			txtLocation.setText("");
			txtIP.setText("");
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
			JOptionPane.showMessageDialog(null, "日志数据过滤、分析匹配成功！", "提示",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// 需要补充匹配物流信息监听类
	private class MatchTransListener implements ActionListener {
		// 数据匹配的事件处理方法
		public void actionPerformed(ActionEvent e) {
			
			//需要补充物流数据匹配的事件处理方法
			/*...
			 * 
			 */
		}
	}

	//需要补充保存数据的事件处理方法
	private class SaveDataListener implements ActionListener {
		// 数据保存的事件处理方法
		public void actionPerformed(ActionEvent e) {
			
			
			// 保存匹配的物流信息
			//若保存成功，弹出提示框：匹配的日志数据以保存到文件和数据库中！",
			//若没有保存成功，则弹出相应的告警提示框
						
			// 保存匹配的物流信息
			//若保存成功，弹出提示框：匹配的物流数据以保存到文件和数据库中！",
			//若没有保存成功，则弹出相应的告警提示框
		}
	}


	
	//数据显示监听类
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

	//需要补充显示物流信息表格
	private void flushMatchedTransTable() {
		//显示物流数据表格
	}

	public static void main(String[] args) {
		new MainFrame_temp();

	}
}
