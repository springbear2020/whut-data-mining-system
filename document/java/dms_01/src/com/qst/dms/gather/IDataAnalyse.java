package com.qst.dms.gather;

import java.util.ArrayList;

//数据分析接口
public interface IDataAnalyse {
	// 进行数据匹配,返回泛型ArrayList集合
	ArrayList<?> matchData();
}
