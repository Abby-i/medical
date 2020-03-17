package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.chronicdis.Chronicdis;

public class ChronicdisDao extends GenericDao<Chronicdis>{
	Connection conn =null;
	PreparedStatement ptmt =null;
	ResultSet rs =null;

	@Override
	protected Chronicdis handle(ResultSet rs) throws SQLException {
		try{
			Chronicdis entity=new Chronicdis();
			
			entity.setIllcode(rs.getString("illcode"));//疾病编码
			entity.setIllname(rs.getString("illname"));//疾病名称
			entity.setPycode(rs.getString("pycode"));//拼音码
			entity.setWbcode(rs.getString("wbcode"));//五笔码
			
			return entity;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
		
	}
	
	public boolean isExist(String illcode) {
		Connection conn =null;
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		try {
			conn = DbUtil.getConnection();
			StringBuffer sb=new StringBuffer("select * from t_chronicdis where illcode = ?");
			ptmt=conn.prepareStatement(sb.toString());
			ptmt.setString(1, illcode);
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
	
	public void queryAll(){
		
	}
	
	public Chronicdis queryById(String illcode){
		StringBuffer sb=new StringBuffer("select * from t_chronicdis where illcode = '"+illcode+"'");
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			rs = ptmt.executeQuery();
			Chronicdis entity = new Chronicdis();
			if (rs != null && rs.next()) {
				entity.setIllcode(rs.getString("illcode"));//疾病编码
				entity.setIllname(rs.getString("illname"));//疾病名称
				entity.setPycode(rs.getString("pycode"));//拼音码
				entity.setWbcode(rs.getString("wbcode"));//五笔码
				return entity;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return null;
		
	}

}
