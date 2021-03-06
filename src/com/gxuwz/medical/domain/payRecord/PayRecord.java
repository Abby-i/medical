package com.gxuwz.medical.domain.payRecord;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;


/**
 * 参合缴费记录管理模块
 * @ClassName: PayRecord
 * @author SunYi
 * @Date 2019年5月10日上午10:14:44
 */
public class PayRecord {
	
	// 家庭编号
	private String homeid;
	// 户主
	private String household;
	// 个人户内编号
	private String cardid;
	// 参合证号
	private String joinNum;
	// 参合发票号
	private String invoiceNum;
	// 参合个人姓名
	private String name;
	// 缴费金额
	private double payAccount;
	// 参合缴费时间
	private Date payTime;
	// 操作员
	private String operator;
	
	/*  ***********************************************实体类***********************************************  */
	
	public PayRecord() {
		super();
	}
	public PayRecord(String cardid, Date payTime) throws SQLException {
		this.cardid = cardid;
		this.payTime = payTime;
		getPayRecord();
	}
	
	
	
	/*  ***********************************************方法体***********************************************  */
	
	public String getHomeid() {
		return homeid;
	}
	public void setHomeid(String homeid) {
		this.homeid = homeid;
	}
	
	public String getHousehold() {
		return household;
	}
	public void setHousehold(String household) {
		this.household = household;
	}
	public String getCardid() {
		return cardid;
	}
	public void setCardid(String cardid) {
		this.cardid = cardid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPayAccount() {
		return payAccount;
	}
	public void setPayAccount(double payAccount) {
		this.payAccount = payAccount;
	}

	
	
	
	public Date getPayTime() {
		return payTime;
	}
	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}
	public String getJoinNum() {
		return joinNum;
	}
	public void setJoinNum(String joinNum) {
		this.joinNum = joinNum;
	}
	public String getInvoiceNum() {
		return invoiceNum;
	}
	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	@Override
	public String toString() {
		return "PayRecord [homeid=" + homeid + ", household=" + household + ", cardid=" + cardid + ", joinNum="
				+ joinNum + ", invoiceNum=" + invoiceNum + ", name=" + name + ", payAccount=" + payAccount
				+ ", payTime=" + payTime + ", operator=" + operator + "]";
	}
	/**
	 * 获取指定个人编号和缴费年度的参合缴费记录
	 * @author 潘宸
	 * @Date 2019年5月10日上午9:54:38
	 * @return Person
	 */
	public String getPayRecord() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from t_pay_record where cardid=? and payTime like'"+ this.payTime +"%' ");
			int index = 1;
			pstmt.setString(index++, this.cardid);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()) {
				cardid = rs.getString("cardid");
			}
			return cardid;
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
	public void add(String homeid, String household, String cardid, String name, double account,String operator,String invoiceNum,String joinNum) throws Exception {
		Connection conn=null;
		PreparedStatement ptmt=null;
		try{
			Date date=new Date();
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			//保存到数据库
			 StringBuffer sqlBuff = new StringBuffer("insert into t_pay_record(homeid,household,cardid,name,payAccount,payTime,operator,invoiceNum,joinNum) values (?,?,?,?,?,?,?,?,?)");
			 ptmt = conn.prepareStatement(sqlBuff.toString());
			 ptmt.setString(1, homeid);
			 ptmt.setString(2, household);
			 ptmt.setString(3, cardid);
			 ptmt.setString(4, name);
			 ptmt.setDouble(5, account);
			 ptmt.setDate(6, new java.sql.Date(date.getTime()) );
			 ptmt.setString(7, operator);
			 ptmt.setString(8, invoiceNum);
			 ptmt.setString(9, joinNum);
			 ptmt.executeUpdate(); 
			//提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(ptmt, conn);
		}
	}
			
		
		
	
	
}
