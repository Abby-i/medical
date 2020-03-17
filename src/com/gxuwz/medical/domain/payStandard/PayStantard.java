package com.gxuwz.medical.domain.payStandard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import com.gxuwz.medical.database.DbUtil;

/**
 * 缴费标准
 * @author Administrator
 *
 */
public class PayStantard {
	private String annual;
	private double account;
	private Date startTime;
	private Date endTime;
	
	public PayStantard() {}
	

	public String getAnnual() {
		return annual;
	}
	public void setAnnual(String annual) {
		this.annual = annual;
	}
	public double getAccount() {
		return account;
	}
	public void setAccount(double account) {
		this.account = account;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public PayStantard(String annual, double account, Date startTime, Date endTime) {
		this.annual = annual;
		this.account = account;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public PayStantard(String annual) throws SQLException {
		this.annual = annual;
		getByAnnual();
	}


	public void add() throws Exception {
		Connection conn=null;
		try{
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			//保存到数据库
			saveToDB(conn);
			//提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}

	private void saveToDB(Connection conn) throws Exception {
		 PreparedStatement ptmt=null;
		 try{
			 StringBuffer sqlBuff = new StringBuffer("insert into t_pay_standard(annual,account,startTime,endTime) values (?,?,?,?)");
			 ptmt = conn.prepareStatement(sqlBuff.toString());
			 ptmt.setString(1, this.annual);
			 ptmt.setDouble(2, this.account);
			 ptmt.setDate(3, new java.sql.Date(this.startTime.getTime()));
			 ptmt.setDate(4, new java.sql.Date(this.endTime.getTime()));
			 ptmt.executeUpdate(); 
		 }catch(Exception e){
			 throw e;
		 }finally{
			 DbUtil.close(ptmt);
		 }
		
	}


	public void edit() throws Exception {
		Connection conn=null;
		try{
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			//保存到数据库
			editToDB(conn);
			//提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}


	private void editToDB(Connection conn) throws SQLException {
		 PreparedStatement pstmt=null;
		  try{
			  StringBuffer sqlBuff=new StringBuffer("update t_pay_standard set account=?,startTime=?,endTime=?  where annual=?");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  pstmt.setDouble(1, this.account);
			  pstmt.setDate(2,  new java.sql.Date(this.startTime.getTime()));
			  pstmt.setDate(3, new java.sql.Date(this.endTime.getTime()));
			  pstmt.setString(4, this.annual);
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
		
	}


	public void del(String annual) throws Exception {
		Connection conn=null;
		this.annual=annual;
		try{
			conn =DbUtil.getConnection();
			//开启手动提交事务
			conn.setAutoCommit(false);
			//保存到数据库
			delFromDB(conn);
			//提交事务
			conn.commit();
		}catch (Exception e) {
			if(conn!=null){
				conn.rollback();
			}
			throw e;
		}finally{
			DbUtil.close(conn);
		}
	}


	private void delFromDB(Connection conn) throws SQLException {
		 PreparedStatement pstmt=null;
		  try{
			  StringBuffer sqlBuff=new StringBuffer("delete from t_pay_standard where annual = ? ");
			  pstmt=conn.prepareStatement(sqlBuff.toString()); 
			  pstmt.setString(1, this.annual);
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
		
	}
	
	/**
	 * 获取指定年度的缴费标准
	 * @author panchen
	 * @Date 2019年5月10日上午10:27:33
	 * @return void
	 */
	private void getByAnnual() throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try{
			conn = DbUtil.getConnection();
			pstmt = conn.prepareStatement("select * from t_pay_standard where annual=? ");
			int index = 1;
			// 把页面传过来的treeID的值赋给占位符
			pstmt.setString(index, this.annual);
			// 执行查询操作
			rs = pstmt.executeQuery();
			if(rs.next()){
				this.annual = rs.getString("annual");
				this.account = rs.getDouble("account");
				this.startTime = rs.getDate("startTime");
				this.endTime = rs.getDate("endTime");
			}
		}catch(SQLException e){
			throw e;
		}finally{
			DbUtil.close(rs, pstmt, conn);
		}
	}
	
}	
