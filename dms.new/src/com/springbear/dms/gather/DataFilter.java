package com.springbear.dms.gather;

import com.springbear.dms.entity.DataBase;

import java.util.ArrayList;

/**
 * 数据过滤抽象类
 *
 * @author Spring-_-Bear
 * @version 2021-10-26 09:01
 **/
public abstract class DataFilter {
    /**
     * 数据集合，使用泛型集合，存储 DataBase 的子类对象
     */
    private ArrayList<? extends DataBase> data;

    public ArrayList<? extends DataBase> getData() {
        return data;
    }

    public DataFilter() {
    }

    public DataFilter(ArrayList<? extends DataBase> data) {
        this.data = data;
    }

    /**
     * 数据过滤抽象方法
     */
    public abstract void doFilter();
}
