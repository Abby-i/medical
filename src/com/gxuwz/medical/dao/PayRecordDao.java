package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.payRecord.PayRecord;
import com.gxuwz.medical.domain.util.AgeUtil;

/**
 * 参合缴费记录数据访问层
 * @ClassName: AreaDao
 * @author SunYi
 * @Date 2019年4月3日下午11:59:33
 */
public class PayRecordDao extends GenericDao<PayRecord> {
	AgeUtil ageUtil = new AgeUtil();
	
	/**
	 * 实现ResultSet结果集转换为PayRecord类型实例
	 * @author SunYi
	 * @Date 2019年5月10日上午10:44:54
	 */
	@Override
	protected PayRecord handle(ResultSet rs) throws SQLException {
		try{
			// 实例化参合缴费记录对象
			PayRecord model = new PayRecord();
			// 取出ResultSet的值,存进PayRecord对象
			model.setHomeid(rs.getString("homeid"));
			model.setHousehold(rs.getString("household"));
			model.setCardid(rs.getString("cardid"));
			model.setJoinNum(rs.getString("joinNum"));
			model.setInvoiceNum(rs.getString("invoiceNum"));
			model.setName(rs.getString("name"));
			model.setPayAccount(rs.getDouble("payAccount"));
			model.setPayTime(rs.getDate("payTime"));
			model.setOperator(rs.getString("operator"));
			// 返回参合缴费记录对象
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}
	
	/**
	 * 计算指定人数的缴费总金额
	 * @author SunYi
	 * @Date 2019年5月10日上午10:49:00
	 * @return double
	 */
	public double calAccount(int payNum, double payAccount) {
		double amount = 0.0;
		try {
			if(payNum !=0 && payAccount != 0) {
				amount = (payNum * payAccount);	
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}

	public List<String> queryHasPayPersonid(String homeid) {
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		List<String> result =new ArrayList<String>();
		StringBuilder sb=new StringBuilder();
		
		sb.append("SELECT cardid from t_pay_record where homeid=? and payTime like '"+ageUtil.getNowYear()+"%' order by cardid asc");
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setString(1, homeid);
			 rs = ptmt.executeQuery();
			 
			 while(rs.next()) {
				 String cardid=rs.getString("cardid");
				 result.add(cardid);
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		return result;
	}
	
	public int queryhasPay(String homeid){
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		
		String sql = "select count(*) from t_pay_record where homeid =" + homeid;
		StringBuffer sb=new StringBuffer();
		sb.append(sql);
		int rowCount = 0;
		
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 while(rs.next()){
				 rowCount = rs.getInt(1);
			 }
		 }catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		 return rowCount;
	}
	
	public List<PayRecord> search(String payTime, String household, String startTime, String endTime) {
		Connection conn = null;
	    PreparedStatement ptmt= null;
		ResultSet rs = null;
		List<PayRecord> result =new ArrayList<PayRecord>();
		StringBuilder sb=new StringBuilder("SELECT * from t_pay_record where 1=1 ");
		//年度
		if(payTime!=null && !payTime.trim().equals("")){
			sb.append(" and payTime like '"+payTime+"%'");
		}
		//户主姓名
		if(household!=null && !household.trim().equals("")){
			sb.append(" and household like '%"+household+"%'");
		}
		//起始时间
		if(startTime!=null && !startTime.trim().equals("")){
			sb.append(" and startTime like '%"+startTime+"%'");
		}
		//终止时间
		if(endTime!=null && !endTime.trim().equals("")){
			sb.append(" and endTime like '%"+endTime+"%'");
		}
		try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 rs = ptmt.executeQuery();
			 PayRecord model = new PayRecord();
			 while(rs.next()) {
				model.setHomeid(rs.getString("homeid"));
				model.setHousehold(rs.getString("household"));
				model.setCardid(rs.getString("cardid"));
				model.setJoinNum(rs.getString("joinNum"));
				model.setInvoiceNum(rs.getString("invoiceNum"));
				model.setName(rs.getString("name"));
				model.setPayAccount(rs.getDouble("payAccount"));
				model.setPayTime(rs.getDate("payTime"));
				model.setOperator(rs.getString("operator"));
				result.add(model); 
			 }
		 } catch (Exception e) {
				e.printStackTrace();
			}finally {
				DbUtil.close(rs, ptmt, conn);
			}
		return result;
		
	}
	
	
	
	public static void main(String[] args) {
		PayRecordDao dao = new PayRecordDao();
		System.out.println(dao.queryHasPayPersonid("4504210101010048"));
	}



}
