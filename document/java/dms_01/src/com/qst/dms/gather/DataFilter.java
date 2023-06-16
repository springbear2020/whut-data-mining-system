package com.qst.dms.gather;

import java.util.ArrayList;

import com.qst.dms.entity.DataBase;

//数据过滤抽象类
public abstract class DataFilter {
	// 数据集合,使用泛型集合
	private ArrayList<? extends DataBase> datas;

	public ArrayList<? extends DataBase> getDatas() {
		return datas;
	}

	public void setDatas(ArrayList<? extends DataBase> datas) {
		this.datas = datas;
	}

	// 构造方法
	public DataFilter() {

	}

	public DataFilter(ArrayList<? extends DataBase> datas) {
		this.datas = datas;
	}

	// 数据过滤抽象方法
	public abstract void doFilter();
}
