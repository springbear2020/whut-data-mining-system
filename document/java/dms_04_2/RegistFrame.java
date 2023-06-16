package com.qst.dms.ui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.qst.dms.entity.User;
import com.qst.dms.service.UserService;

//注册窗口
public class RegistFrame extends JFrame {
	// 主面板
	private JPanel p;
	// 标签
	private JLabel lblName, lblPwd, lblRePwd, lblSex, lblHobby, lblAdress,
			lblDegree;
	// 用户名，文本框
	private JTextField txtName;
	// 密码和确认密码，密码框
	private JPasswordField txtPwd, txtRePwd;
	// 性别，单选按钮
	private JRadioButton rbMale, rbFemale;
	// 爱好，多选框
	private JCheckBox ckbRead, ckbNet, ckbSwim, ckbTour;
	// 地址，文本域
	private JTextArea txtAdress;
	// 学历，组合框
	private JComboBox<String> cmbDegree;
	// 确认和取消，按钮
	private JButton btnOk, btnCancle;
	// 注册的用户
	private static User user;

	// 用户业务类
	private UserService userService;

	// 构造方法
	public RegistFrame() {
		super("用户注册");

		// 实例化用户业务类对象
		userService = new UserService();
		
		// 设置窗体的icon
		ImageIcon icon = new ImageIcon("images\\dms.png");
		this.setIconImage(icon.getImage());

		// 设置面板布局，网格布局
		p = new JPanel(new GridLayout(8, 1));
		// 实例化组件
		lblName = new JLabel("用  户  名：");
		lblPwd = new JLabel("密        码：");
		lblRePwd = new JLabel("确认密码：");
		lblSex = new JLabel("性       别：");
		lblHobby = new JLabel("爱        好：");
		lblAdress = new JLabel("地        址：");
		lblDegree = new JLabel("学        历：");
		txtName = new JTextField(16);
		txtPwd = new JPasswordField(16);
		txtRePwd = new JPasswordField(16);
		rbMale = new JRadioButton("男");
		rbFemale = new JRadioButton("女");

		// 性别的单选逻辑
		ButtonGroup bg = new ButtonGroup();
		bg.add(rbMale);
		bg.add(rbFemale);

		ckbRead = new JCheckBox("阅读");
		ckbNet = new JCheckBox("上网");
		ckbSwim = new JCheckBox("游泳");
		ckbTour = new JCheckBox("旅游");
		txtAdress = new JTextArea(3, 20);

		// 组合框显示的学历数组
		String str[] = { "小学", "初中", "高中", "本科", "硕士", "博士" };
		cmbDegree = new JComboBox<String>(str);
		// 设置组合框可编辑
		cmbDegree.setEditable(true);
		btnOk = new JButton("确定");
		// 注册监听器，监听确定按钮
		btnOk.addActionListener(new RegisterListener());
		btnCancle = new JButton("重置");
		// 注册监听器，监听重置按钮
		btnCancle.addActionListener(new ResetListener());
		// 将组件分组放入面板，然后将小面板放入主面板
		JPanel p1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p1.add(lblName);
		p1.add(txtName);
		p.add(p1);
		JPanel p2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p2.add(lblPwd);
		p2.add(txtPwd);
		p.add(p2);
		JPanel p3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p3.add(lblRePwd);
		p3.add(txtRePwd);
		p.add(p3);
		JPanel p4 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p4.add(lblSex);
		p4.add(rbMale);
		p4.add(rbFemale);
		p.add(p4);
		JPanel p5 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p5.add(lblHobby);
		p5.add(ckbRead);
		p5.add(ckbNet);
		p5.add(ckbSwim);
		p5.add(ckbTour);
		p.add(p5);
		JPanel p6 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p6.add(lblAdress);
		p6.add(txtAdress);
		p.add(p6);
		JPanel p7 = new JPanel(new FlowLayout(FlowLayout.LEFT));
		p7.add(lblDegree);
		p7.add(cmbDegree);
		p.add(p7);
		JPanel p8 = new JPanel(new FlowLayout(FlowLayout.CENTER));
		p8.add(btnOk);
		p8.add(btnCancle);
		p.add(p8);
		// 主面板放入窗体中
		this.add(p);
		// 设置窗体大小和位置
		this.setSize(310, 350);
		this.setLocation(300, 300);
		// 设置窗体不可改变大小
		this.setResizable(false);

		// 设置窗体初始可见
		this.setVisible(true);
	}

	// 监听类，负责处理确认按钮的业务逻辑
	private class RegisterListener implements ActionListener {
		// 重写actionPerFormed()方法，事件处理方法
		public void actionPerformed(ActionEvent e) {
			// 获取用户输入的数据
			String userName = txtName.getText().trim();
			String password = new String(txtPwd.getPassword());
			String rePassword = new String(txtRePwd.getPassword());
			// 将性别“男”“女”对应转化为“1”“0”
			int sex = Integer.parseInt(rbFemale.isSelected() ? "0" : "1");
			String hobby = (ckbRead.isSelected() ? "阅读" : "")
					+ (ckbNet.isSelected() ? "上网" : "")
					+ (ckbSwim.isSelected() ? "游泳" : "")
					+ (ckbTour.isSelected() ? "旅游" : "");
			String address = txtAdress.getText().trim();
			String degree = cmbDegree.getSelectedItem().toString().trim();
			// 判断两次输入密码是否一致
			if (password.equals(rePassword)) {
				// 将数据封装到对象中
				user = new User(userName, password, sex, hobby, address, degree);
				// 保存数据
				if (userService.saveUser(user)) {
					// 输出提示信息
					//System.out.println("注册成功！");
					JOptionPane.showMessageDialog(null,"注册成功！","成功提示",JOptionPane.PLAIN_MESSAGE);
				} else {
					// 输出提示信息
					//System.out.println("注册失败！");
					JOptionPane.showMessageDialog(null,"注册失败！","错误提示",JOptionPane.ERROR_MESSAGE);
				}
			} else {
				// 输出提示信息
				//System.out.println("两次输入的密码不一致！");
				JOptionPane.showMessageDialog(null,"两次输入的密码不一致！","错误提示",JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// 监听类，负责处理重置按钮
	public class ResetListener implements ActionListener {
		// 重写actionPerFormed()方法，重置组件内容事件处理方法
		public void actionPerformed(ActionEvent e) {
			// 清空姓名、密码、确认密码内容
			txtName.setText("");
			txtPwd.setText("");
			txtRePwd.setText("");
			// 重置单选按钮为未选择
			rbMale.setSelected(false);
			rbFemale.setSelected(false);
			// 重置所有的复选按钮为未选择
			ckbRead.setSelected(false);
			ckbNet.setSelected(false);
			ckbSwim.setSelected(false);
			ckbTour.setSelected(false);
			// 清空地址栏
			txtAdress.setText("");
			// 重置组合框为未选择状态
			cmbDegree.setSelectedIndex(0);
		}
	}

	public static void main(String[] args) {
		new RegistFrame();
	}
}
