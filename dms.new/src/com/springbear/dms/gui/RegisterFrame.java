package com.springbear.dms.gui;

import com.springbear.dms.entity.User;
import com.springbear.dms.service.UserService;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * 注册界面
 *
 * @author Spring-_-Bear
 * @version 2021-11-17 11:34
 */
public class RegisterFrame extends JFrame {
    private JTextField textUser;
    private JPasswordField textPwd;
    private JPasswordField textPwd2;
    private JRadioButton btnMale;
    private JTextField textPhone;
    private JTextArea areaAddress;


    public RegisterFrame() {
        initFrame();
        initPanel();
    }

    /**
     * 初始化窗体
     */
    private void initFrame() {
        setSize(555, 555);
        setTitle("\u7528\u6237\u6CE8\u518C");
        setIconImage(Toolkit.getDefaultToolkit().getImage("dms.new\\resources\\images\\BeFree.jpg"));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * 初始化面板
     */
    private void initPanel() {
        // 主面板
        JPanel contentPane = new JPanel();
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // 用户名标签
        JLabel lblUser = new JLabel("\u7528\u6237\u540D");
        lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUser.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblUser.setBounds(90, 43, 80, 40);
        contentPane.add(lblUser);
        // 用户名文本框
        textUser = new JTextField();
        textUser.setFont(new Font("SimSun", Font.PLAIN, 20));
        textUser.setBounds(190, 45, 250, 40);
        contentPane.add(textUser);

        // 密码标签
        JLabel lblPwd = new JLabel("\u5BC6\u7801");
        lblPwd.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPwd.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblPwd.setBounds(90, 103, 80, 40);
        contentPane.add(lblPwd);
        // 密码文本框
        textPwd = new JPasswordField();
        textPwd.setFont(new Font("SimSun", Font.PLAIN, 20));
        textPwd.setBounds(190, 105, 250, 40);
        contentPane.add(textPwd);
        // 确认密码标签
        JLabel lblPwd2 = new JLabel("\u786E\u8BA4\u5BC6\u7801");
        lblPwd2.setHorizontalAlignment(SwingConstants.RIGHT);
        lblPwd2.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblPwd2.setBounds(90, 165, 80, 40);
        contentPane.add(lblPwd2);
        // 确认密码文本框
        textPwd2 = new JPasswordField();
        textPwd2.setFont(new Font("SimSun", Font.PLAIN, 20));
        textPwd2.setBounds(190, 165, 250, 40);
        contentPane.add(textPwd2);

        // 电话号码标签
        JLabel lalPhone = new JLabel("\u7535\u8BDD");
        lalPhone.setHorizontalAlignment(SwingConstants.RIGHT);
        lalPhone.setFont(new Font("SimSun", Font.PLAIN, 20));
        lalPhone.setBounds(90, 263, 80, 40);
        contentPane.add(lalPhone);
        // 电话号码文本框
        textPhone = new JTextField();
        textPhone.setFont(new Font("SimSun", Font.PLAIN, 20));
        textPhone.setBounds(190, 265, 250, 40);
        contentPane.add(textPhone);

        // 性别标签
        JLabel lblSex = new JLabel("\u6027\u522B");
        lblSex.setHorizontalAlignment(SwingConstants.RIGHT);
        lblSex.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblSex.setBounds(90, 215, 80, 40);
        contentPane.add(lblSex);
        // 性别按钮组
        ButtonGroup btnGroup = new ButtonGroup();
        // 性别男单选按钮
        btnMale = new JRadioButton("\u7537");
        btnMale.setSelected(true);
        btnMale.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnGroup.add(btnMale);
        btnMale.setBounds(225, 215, 50, 40);
        contentPane.add(btnMale);
        // 性别女单选单选按钮
        JRadioButton btnFemale = new JRadioButton("\u5973");
        btnFemale.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnGroup.add(btnFemale);
        btnFemale.setBounds(335, 215, 50, 40);
        contentPane.add(btnFemale);

        // 住址标签
        JLabel lblAddress = new JLabel("\u4F4F\u5740");
        lblAddress.setHorizontalAlignment(SwingConstants.RIGHT);
        lblAddress.setFont(new Font("SimSun", Font.PLAIN, 20));
        lblAddress.setBounds(90, 344, 80, 40);
        contentPane.add(lblAddress);
        // 住址文本域
        areaAddress = new JTextArea();
        areaAddress.setLineWrap(true);
        areaAddress.setFont(new Font("Monospaced", Font.PLAIN, 20));
        areaAddress.setBounds(190, 325, 250, 80);
        contentPane.add(areaAddress);

        // 注册按钮
        JButton btnRegister = new JButton("\u6CE8\u518C");
        btnRegister.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnRegister.setBounds(190, 436, 80, 40);
        contentPane.add(btnRegister);
        // 登录按钮
        JButton btnLogin = new JButton("\u767B\u5F55");
        btnLogin.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnLogin.setBounds(276, 436, 80, 40);
        contentPane.add(btnLogin);
        // 重置按钮
        JButton btnReset = new JButton("\u91CD\u7F6E");
        btnReset.setFont(new Font("SimSun", Font.PLAIN, 20));
        btnReset.setBounds(360, 436, 80, 40);
        contentPane.add(btnReset);

        // 注册按钮事件注册监听
        btnRegister.addActionListener(new ButtonRegisterListener());
        // 重置按钮事件注册监听
        btnReset.addActionListener(new ButtonResetListener());
        // 登录按钮事件注册监听
        btnLogin.addActionListener(new ButtonLoginListener());
    }

    /**
     * 注册按钮事件响应
     */
    private class ButtonRegisterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

            String username = textUser.getText();
            String pwd = new String(textPwd.getPassword());
            String pwd2 = new String(textPwd2.getPassword());
            String sex = btnMale.isSelected() ? "男" : "女";
            String phone = textPhone.getText();
            String address = areaAddress.getText();

            if (username.length() == 0) {
                JOptionPane.showMessageDialog(null, "用户名不能为空，请输入用户名！", "错误提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (pwd.length() == 0) {
                JOptionPane.showMessageDialog(null, "密码不能为空，请输入密码！", "错误提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (pwd2.length() == 0) {
                JOptionPane.showMessageDialog(null, "请再次输入密码！", "错误提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (phone.length() == 0) {
                JOptionPane.showMessageDialog(null, "电话号码不能为空，请输入电话号码！", "错误提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (new UserService().findUserByName(username) != null) {
                JOptionPane.showMessageDialog(null, "用户名已存在，请输入新用户名！", "错误提示", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pwd.equals(pwd2)) {
                JOptionPane.showMessageDialog(null, "两次输入的密码不一致，请检查后重新输入！", "错误提示", JOptionPane.ERROR_MESSAGE);
                return;
            }

            User registerUser = new User(username, pwd, sex, phone, address);
            if (new UserService().saveUserToDb(registerUser)) {
                JOptionPane.showMessageDialog(null, "注册成功！", "成功提示", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "注册失败！", "失败提示", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * 重置按钮事件响应
     */
    private class ButtonResetListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            textUser.setText("");
            textPwd.setText("");
            textPwd2.setText("");
            textPhone.setText("");
            areaAddress.setText("");
        }
    }

    /**
     * 登录按钮事件响应
     */
    private class ButtonLoginListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new LoginFrame().setVisible(true);
            RegisterFrame.this.setVisible(false);
        }
    }
}

