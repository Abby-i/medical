package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.farmer.Farmer;

public class FarmerDao extends GenericDao<Farmer>{

	@Override
	protected Farmer handle(ResultSet rs) throws SQLException {
		try{
			Farmer model=new Farmer();
			model.setFarmerid(rs.getString("farmerid"));
			model.setFarmername(rs.getString("farmername"));
			model.setAreacode(rs.getString("areacode"));
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

	public boolean isExist(String farmername) {
		Connection conn =null;
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			conn = DbUtil.getConnection();
			StringBuffer sb=new StringBuffer("select farmername from t_farmer where farmername = ?");
			ptmt=conn.prepareStatement(sb.toString());
			ptmt.setString(1, farmername);
			rs=ptmt.executeQuery();
			if(rs.next()){
				return false;
			}else{
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		 
	}

	/**
	 * 通过farmerid查询农合机构信息
	 * @param farmerid
	 * @return
	 */
	public Farmer queryById(String farmerid) {
		Connection conn =null;
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			conn = DbUtil.getConnection();
			StringBuffer sb=new StringBuffer("select * from t_farmer where farmerid = ?");
			ptmt=conn.prepareStatement(sb.toString());
			ptmt.setString(1, farmerid);
			rs=ptmt.executeQuery();
			
			Farmer f = null;
			while(rs.next()) {
				f = new Farmer();
				f.setFarmerid(rs.getString("farmerid"));
				f.setFarmername(rs.getString("farmername"));
				f.setAreacode(rs.getString("areacode"));
				return f;
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
		
	}

}
