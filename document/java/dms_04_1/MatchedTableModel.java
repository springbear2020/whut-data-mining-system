package com.qst.dms.entity;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.swing.table.AbstractTableModel;

public class MatchedTableModel extends AbstractTableModel {

	// 使用ResultSet来创建TableModel
	private ResultSet rs;
	private ResultSetMetaData rsmd;
	// 标志位，区分日志和物流：1，日志；0，物流
	private int sign;

	public MatchedTableModel(ResultSet rs, int sign) {
		this.rs = rs;
		this.sign = sign;
		try {
			rsmd = rs.getMetaData();
		} catch (Exception e) {
			rsmd = null;
		}
	}

	// 获取表格行数
	public int getRowCount() {
		try {
			rs.last();
			// System.out.println(count);
			return rs.getRow();
		} catch (Exception e) {
			return 0;
		}
	}

	// 获取表格的列数
	public int getColumnCount() {
		try {
			// System.out.println(rsmd.getColumnCount());
			return rsmd.getColumnCount();
		} catch (Exception e) {
			return 0;
		}
	}

	// 获取指定位置的值
	public Object getValueAt(int rowIndex, int columnIndex) {
		try {
			rs.absolute(rowIndex + 1);
			return rs.getObject(columnIndex + 1);
		} catch (Exception e) {
			return null;
		}
	}

	// 获取表头信息
	public String getColumnName(int column) {
		String[] logArray = { "日志ID", "采集时间", "采集地点", "状态", "用户名", "IP", "日志类型" };
		String[] tranArray = { "物流ID", "采集时间", "目的地", "状态", "经手人", "收货人",
				"物流类型" };
		return sign == 1 ? logArray[column] : tranArray[column];
	}
}
