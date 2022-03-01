package com.springbear.dms.gather;

import java.util.ArrayList;

/**
 * 数据分析（匹配）接口
 *
 * @author Spring-_-Bear
 * @version 2021-10-26 09:08
 **/
public interface IDataAnalyse {

    /**
     * 进行数据匹配分析，返回泛型集合
     *
     * @return 匹配的数据泛型集合
     */
    ArrayList<?> matchData();
}
