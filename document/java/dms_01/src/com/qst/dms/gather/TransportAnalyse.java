package com.qst.dms.gather;

import java.util.ArrayList;

import com.qst.dms.entity.DataBase;
import com.qst.dms.entity.MatchedTransport;
import com.qst.dms.entity.Transport;
import com.qst.dms.exception.DataAnalyseException;

//物流分析类，继承DataFilter抽象类，实现数据分析接口
public class TransportAnalyse extends DataFilter implements IDataAnalyse {
	// 发货集合
	private ArrayList<Transport> transSends = new ArrayList<>();
	// 送货集合
	private ArrayList<Transport> transIngs = new ArrayList<>();
	// 已签收集合
	private ArrayList<Transport> transRecs = new ArrayList<>();

	// 构造方法
	public TransportAnalyse() {
	}

	public TransportAnalyse(ArrayList<Transport> trans) {
		super(trans);
	}

	// 实现DataFilter抽象类中的过滤抽象方法
	public void doFilter() {
		// 获取数据集合
		ArrayList<Transport> trans = (ArrayList<Transport>) this.getDatas();

		// 遍历，对物流数据进行过滤，根据物流状态分别放在不同的集合中
		for (Transport tran : trans) {
			if (tran.getTransportType() == Transport.SENDDING) {
				transSends.add(tran);
			} else if (tran.getTransportType() == Transport.TRANSPORTING) {
				transIngs.add(tran);
			} else if (tran.getTransportType() == Transport.RECIEVED) {
				transRecs.add(tran);
			}
		}

	}

	// 实现IDataAnalyse接口中数据分析方法
	public ArrayList<MatchedTransport> matchData() {
		// 创建物流匹配集合
		ArrayList<MatchedTransport> matchTrans = new ArrayList<>();
		// 数据匹配分析
		for (Transport send : transSends) {
			for (Transport tran : transIngs) {
				for (Transport rec : transRecs) {
					if ((send.getReciver().equals(tran.getReciver()))
							&& (send.getReciver().equals(rec.getReciver()))) {
						// 修改物流状态类型为“匹配”
						send.setType(DataBase.MATHCH);
						tran.setType(DataBase.MATHCH);
						rec.setType(DataBase.MATHCH);
						// 添加到匹配集合中
						matchTrans.add(new MatchedTransport(send, tran, rec));
					}
				}
			}
		}
		try {
			if (matchTrans.size() == 0) {
				// 没找到匹配的数据,抛出DataAnalyseException异常
				throw new DataAnalyseException("没有匹配的物流数据！");
			}
		} catch (DataAnalyseException e) {
			e.printStackTrace();
		}
		return matchTrans;
	}
}
