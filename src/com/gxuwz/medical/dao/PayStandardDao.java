package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.payStandard.PayStantard;


public class PayStandardDao extends GenericDao<PayStantard>{

	@Override
	protected PayStantard handle(ResultSet rs) throws SQLException {
		try{
			PayStantard model = new PayStantard();
			model.setAnnual(rs.getString("annual"));
			model.setAccount(rs.getDouble("account"));
			model.setStartTime(rs.getDate("starttime"));
			model.setEndTime(rs.getDate("endtime"));
			return model;
		}catch(SQLException e){
			throw new SQLException("结果集转为实例失败!",e);
		}
	}

	public PayStantard findById(String annual) {
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuffer sb=new StringBuffer();
		 sb.append("select * from t_pay_standard where annual = ?");
		
		 try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			ptmt.setString(1, annual);
			rs = ptmt.executeQuery();
			PayStantard entity = null;
			if (rs != null && rs.next()) {
				entity = new PayStantard();
				entity.setAnnual(rs.getString("annual"));
				entity.setAccount(rs.getDouble("account"));
				entity.setStartTime(rs.getDate("startTime"));
				entity.setEndTime(rs.getDate("endTime"));
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
