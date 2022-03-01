package com.springbear.dms.entity;

import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * Gui 表格模型类
 *
 * @author Spring-_-Bear
 * @version 2021-11-18 09:13
 */
public class TableModel extends AbstractTableModel {
    /**
     * 日志表格标志
     */
    public static final int LOG_SIGN = 1;
    /**
     * 物流表格标志
     */
    public static final int TRANS_SIGN = 2;
    /**
     * 用户信息表格标志
     */
    public static final int USER_SIGN = 3;
    /**
     * 表格类型
     */
    private final int sign;

    private final ResultSet resultSet;
    private ResultSetMetaData resultSetMetaData;

    public TableModel(ResultSet rs, int sign) {
        this.resultSet = rs;
        this.sign = sign;
        try {
            resultSetMetaData = rs.getMetaData();
        } catch (Exception e) {
            resultSetMetaData = null;
        }
    }

    @Override
    public int getRowCount() {
        try {
            resultSet.last();
            return resultSet.getRow();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public int getColumnCount() {
        try {
            return resultSetMetaData.getColumnCount();
        } catch (Exception e) {
            return 0;
        }
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            resultSet.absolute(rowIndex + 1);
            return resultSet.getObject(columnIndex + 1);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        String[] logArray = {"日志ID", "采集时间", "采集地点", "状态", "用户名", "IP", "日志类型"};
        String[] transArray = {"物流ID", "采集时间", "目的地", "状态", "经手人", "收货人", "物流类型"};
        String[] userArray = {"用户ID", "用户名", "密码", "性别", "电话", "住址"};
        String string = null;
        switch (sign) {
            case (LOG_SIGN):
                string = logArray[column];
                break;
            case (TRANS_SIGN):
                string = transArray[column];
                break;
            case (USER_SIGN):
                string = userArray[column];
                break;
            default:
                break;
        }
        return string;
    }
}
