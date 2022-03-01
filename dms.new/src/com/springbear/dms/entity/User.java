package com.springbear.dms.entity;

/**
 * 用户信息类
 *
 * @author Spring-_-Bear
 * @version 2021-11-16 16:47
 */
public class User {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private String sex;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 地址
     */
    private String address;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getSex() {
        return sex;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public User() {
    }

    public User(String username, String password, String sex, String phone, String address) {
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.phone = phone;
        this.address = address;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
