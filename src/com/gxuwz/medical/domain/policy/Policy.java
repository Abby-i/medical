package com.gxuwz.medical.domain.policy;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;

/**
 * 慢性病政策设置
 * @author 
 *
 */
public class Policy {
	private int id;
	private String annual;//年度
	private double ceiling;//封顶线
	private double ratio;//报销比例
	
	
	
	public Policy(String annual, double ceiling, double ratio) {
		super();
		this.annual = annual;
		this.ceiling = ceiling;
		this.ratio = ratio;
	}

	public Policy() {}
	
	public String getAnnual() {
		return annual;
	}
	public void setAnnual(String annual) {
		this.annual = annual;
	}
	public double getCeiling() {
		return ceiling;
	}
	public void setCeiling(double ceiling) {
		this.ceiling = ceiling;
	}
	public double getRatio() {
		return ratio;
	}
	public void setRatio(double ratio) {
		this.ratio = ratio;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void edit(int id, String annual, Double ceiling, Double ratio) throws Exception {
		  Connection conn=null;
		  PreparedStatement ptmt=null;
		  try {
			  conn = DbUtil.getConnection();
			  conn.setAutoCommit(false);
			  StringBuffer sqlBuff=new StringBuffer("update t_policy t set t.annual=?,t.ceiling=?,ratio=?  where t.id=?");
			  ptmt=conn.prepareStatement(sqlBuff.toString());
			  ptmt.setString(1, annual);
			  ptmt.setDouble(2, ceiling);
			  ptmt.setDouble(3, ratio);
			  ptmt.setInt(4, id);
			  ptmt.executeUpdate(); 
			  conn.commit();
		} catch (SQLException e) {
			if(conn!=null){
				conn.rollback();
			}
			throw new SQLException("Failed to update t_area  !", e);
		}finally{
			 DbUtil.close(ptmt, conn);
		}
	}

	public void add(String annual, Double ceiling, Double ratio)throws Exception {
		Connection conn=null;
		this.annual=annual;
		this.ceiling=ceiling;
		this.ratio=ratio;
		try{
			//加载父节点ID的对象
			//自动编号
			conn =DbUtil.getConnection();
			//开启手动提交事务
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

	private void saveToDB(Connection conn) throws SQLException {
		 PreparedStatement pstmt=null;
		  try{
			  StringBuffer sqlBuff=new StringBuffer("insert into t_policy(annual,ceiling,ratio)");
			  sqlBuff.append("values(?,?,?)");
			  pstmt=conn.prepareStatement(sqlBuff.toString());
			  pstmt.setString(1, this.annual);
			  pstmt.setDouble(2, this.ceiling);
			  pstmt.setDouble(3, this.ratio);
			  pstmt.executeUpdate(); 
		  }catch(SQLException e){
			  throw e;
		  }finally{
			  DbUtil.close(pstmt);
		  } 
		
	}

	public void del(int id) throws Exception {
		Connection conn=null;
		PreparedStatement ptmt=null;
		this.id = id;
		try{
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			
			StringBuffer sqlBuff=new StringBuffer("delete from t_policy where id=?");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setInt(1, this.id);
			ptmt.executeUpdate(); 
			
			 conn.commit();
		}catch(SQLException e){
			conn.rollback();
			throw new SQLException("Failed to delete record from table !", e);
		}finally {
			DbUtil.close(conn);
		}
	}
	
	
}
