package com.springbear.dms.gui;

import com.springbear.dms.entity.User;
import com.springbear.dms.service.UserService;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 登录界面
 *
 * @author Spring-_-Bear
 * @version 2021-11-17 12:29
 */
public class LoginFrame extends JFrame {
    private JTextField textUser;
    private JPasswordField textPwd;

    public LoginFrame() {
        initFrame();
        initPanel();
    }

    /**
     * 初始化窗体
     */
    public void initFrame() {
        setSize(444, 300);
        setTitle("\u7528\u6237\u767B\u5F55");
        setIconImage(Toolkit.getDefaultToolkit().getImage("dms.new\\resources\\images\\BeFree.jpg"));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 初始化面板
     */
    public void initPanel() {
        // 主面板
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 用户名标签
        JLabel lblUser = new JLabel("\u7528\u6237\u540D");
        lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUser.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblUser.setBounds(30, 43, 80, 40);
        contentPane.add(lblUser);
        // 用户名文本框
        textUser = new JTextField();
        textUser.setFont(new Font("SimSun", Font.PLAIN, 20));
        textUser.setBounds(124, 45, 235, 40);
        contentPane.add(textUser);
        textUser.setColumns(10);

        // 密码标签
        JLabel lblPwd = new JLabel("\u5BC6\u7801");
        lblPwd.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPwd.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblPwd.setBounds(30, 125, 80, 40);
        contentPane.add(lblPwd);
        // 密码文本框
        textPwd = new JPasswordField();
        textPwd.setFont(new Font("SimSun", Font.PLAIN, 20));
        textPwd.setColumns(10);
        textPwd.setBounds(124, 125, 235, 40);
        contentPane.add(textPwd);

        // 登录按钮
        JButton btnLogin = new JButton("\u767B\u5F55");
        btnLogin.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnLogin.setBounds(124, 208, 80, 40);
        contentPane.add(btnLogin);
        // 注册按钮
        JButton btnRegister = new JButton("\u6CE8\u518C");
        btnRegister.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnRegister.setBounds(202, 208, 80, 40);
        contentPane.add(btnRegister);
        // 重置按钮
        JButton btnReset = new JButton("\u91CD\u7F6E");
        btnReset.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnReset.setBounds(280, 208, 80, 40);
        contentPane.add(btnReset);

        // 登录按钮事件注册监听
        btnLogin.addActionListener(new ButtonLoginListener());
        // 注册按钮事件注册监听
        btnRegister.addActionListener(new ButtonRegisterListener());
        // 重置按钮事件响应
        btnReset.addActionListener(new ButtonResetListener());
    }

    /**
     * 重置按钮事件响应
     */
    private class ButtonResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textUser.setText("");
            textPwd.setText("");
        }
    }

    /**
     * 注册按钮事件响应
     */
    private class ButtonRegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new RegisterFrame().setVisible(true);
            LoginFrame.this.setVisible(false);
        }
    }

    /**
     * 登录按钮事件响应
     */
    private class ButtonLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            {
                String inputUser = textUser.getText();
                String inputPwd = new String(textPwd.getPassword());

                if (inputUser.length() == 0) {
                    JOptionPane.showMessageDialog(null, "用户名不能为空，请输入用户名！", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (inputPwd.length() == 0) {
                    JOptionPane.showMessageDialog(null, "密码不能为空，请输入密码！", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                UserService userService = new UserService();
                User user = userService.findUserByName(inputUser);
                if (user == null) {
                    JOptionPane.showMessageDialog(null, "用户名不存在，请先进行注册！", "错误提示", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!inputPwd.equals(user.getPassword())) {
                    JOptionPane.showMessageDialog(null, "密码错误，请重新输入密码！", "错误提示", JOptionPane.ERROR_MESSAGE);
                    textPwd.setText("");
                    return;
                }
                MainFrame.setUser(user.getUsername());
                new MainFrame().setVisible(true);
                LoginFrame.this.setVisible(false);
            }
        }
    }

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                LoginFrame frame = new LoginFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
