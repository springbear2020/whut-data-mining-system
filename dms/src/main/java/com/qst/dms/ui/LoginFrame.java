package com.qst.dms.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.qst.dms.entity.User;
import com.qst.dms.service.UserService;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {

    private JPanel contentPane;//主面板
    private JTextField nameField;//用户名输入文本框
    private JPasswordField passwordField;//密码输入文本框

    //构造方法
    public LoginFrame() {

        super("用户登录");

        // 实例化用户业务类对象
        UserService userService = new UserService();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        //用户名标签
        JLabel lblName = new JLabel("\u7528\u6237\u540D\uFF1A");
        lblName.setBounds(54, 64, 72, 18);
        contentPane.add(lblName);

        //用户名输入文本框
        nameField = new JTextField();
        nameField.setBounds(125, 61, 250, 24);
        contentPane.add(nameField);
        nameField.setColumns(10);

        //密码标签
        JLabel lblPwd = new JLabel("\u5BC6 \u7801\uFF1A");
        lblPwd.setBounds(54, 132, 72, 18);
        contentPane.add(lblPwd);

        //密码输入文本框
        passwordField = new JPasswordField();
        passwordField.setBounds(125, 129, 250, 24);
        contentPane.add(passwordField);

        //登录按钮
        JButton btnLogin = new JButton("\u767B\u5F55");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //根据用户名查询
                User user = userService.findUserByName(nameField.getText().trim());
                //判断用户是否存在
                if (user != null) {
                    //判断输入的密码是否正确
                    if (user.getPassword().equals(new String(passwordField.getPassword()))) {
                        //登录成功，隐藏登录窗口
                        LoginFrame.this.setVisible(false);
                        //显示主窗口
                        new MainFrame();
                    } else {
                        //输出提示信息
                        JOptionPane.showMessageDialog(null, "密码错误！请重新输入！", "错误提示", JOptionPane.ERROR_MESSAGE);
                        //清空密码框
                        passwordField.setText("");
                    }
                } else {
                    //输出提示信息
                    JOptionPane.showMessageDialog(null, "该用户不存在，请先进行注册！", "错误提示", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        btnLogin.setBounds(125, 189, 72, 27);
        contentPane.add(btnLogin);

        //重置按钮
        JButton btnReset = new JButton("\u91CD\u7F6E");
        btnReset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                passwordField.setText("");
            }
        });
        btnReset.setBounds(217, 189, 72, 27);
        contentPane.add(btnReset);

        //注册按钮
        JButton btnRegist = new JButton("\u6CE8\u518C");
        btnRegist.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //选择注册，隐藏登录窗口
                LoginFrame.this.setVisible(false);
                new RegistFrame();
            }
        });
        btnRegist.setBounds(303, 189, 72, 27);
        contentPane.add(btnRegist);

    }
}

