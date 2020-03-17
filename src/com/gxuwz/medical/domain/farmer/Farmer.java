package com.gxuwz.medical.domain.farmer;

import java.sql.*;

import com.gxuwz.medical.database.DbUtil;

public class Farmer {
	private String farmerid;//农合机构编号
	private String farmername;//农合经办点
	private String areacode; //所属行政区域编号
	
	/**
	 * 添加农合机构信息
	 * @param farmerid
	 * @param farmername
	 * @param areacode
	 * @throws SQLException
	 */
	public void addFarmer(String farmerid, String farmername, String areacode) throws SQLException{
		Connection conn=null;
		PreparedStatement ptmt=null;
		try {
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sqlBuff=new StringBuffer("insert into t_farmer(farmerid,farmername,areacode) values(?,?,?)");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setString(1, farmerid); 
			ptmt.setString(2, farmername); 
			ptmt.setString(3, areacode); 
			ptmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			  DbUtil.close(ptmt, conn);
		  } 
	}
	
	
	
	
	public Farmer() {
		
	}




	public String getFarmerid() {
		return farmerid;
	}
	public void setFarmerid(String farmerid) {
		this.farmerid = farmerid;
	}
	public String getFarmername() {
		return farmername;
	}
	public void setFarmername(String farmername) {
		this.farmername = farmername;
	}
	public String getAreacode() {
		return areacode;
	}
	public void setAreacode(String areacode) {
		this.areacode = areacode;
	}




	public void delFarmer(String farmerid) throws SQLException {
		
		Connection conn=null;
		PreparedStatement ptmt=null;
		try {
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sqlBuff=new StringBuffer("delete from t_farmer where farmerid=?");
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setString(1, farmerid); 
			ptmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			  DbUtil.close(ptmt, conn);
		  } 
		
	}


	@Override
	public String toString() {
		return "Farmer [farmerid=" + farmerid + ", farmername=" + farmername + ", areacode=" + areacode + "]";
	}




	public void editFarmer(String farmerid, String farmername, String areacode) throws SQLException {
		Connection conn=null;
		PreparedStatement ptmt=null;
		try {
			conn =DbUtil.getConnection();
			conn.setAutoCommit(false);
			StringBuffer sqlBuff=new StringBuffer("update t_farmer t set t.farmerid=?,t.farmername=?,t.areacode=? where t.farmerid=? ");//update t_menu t set t.menuname=?,t.url=? where t.menuid=?
			ptmt=conn.prepareStatement(sqlBuff.toString());
			ptmt.setString(1, farmerid); 
			ptmt.setString(2, farmername); 
			ptmt.setString(3, areacode); 
			ptmt.setString(4, farmerid); 
			ptmt.executeUpdate();
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			e.printStackTrace();
		}finally{
			  DbUtil.close(ptmt, conn);
		  } 
	}
	
	
	
}
