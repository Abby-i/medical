package com.gxuwz.medical.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;
import com.gxuwz.medical.domain.illExpense.IllExpense;
import com.gxuwz.medical.domain.medical.S201;

public class S201Dao extends GenericDao<S201>{
	public List<S201> findListByType(String type)throws SQLException{
		String sql="select * from s201 where type=?";
		Object[]params={type};
		return super.queryObject(sql, params);
	}

	@Override
	protected S201 handle(ResultSet rs) throws SQLException {
		S201 entity=new S201();
		entity.setId(rs.getInt("id"));
		entity.setItemcode(rs.getString("itemcode"));
		entity.setItemname(rs.getString("itemname"));
		entity.setType(rs.getString("type"));
		return entity;
	}
	

	public S201 queryById(String itemcode) {
		Connection conn =null;
		PreparedStatement ptmt =null;
		ResultSet rs =null;
		StringBuffer sb=new StringBuffer("select * from s201 where itemcode = ? ");
		try {
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sb.toString());
			int index=1;
			ptmt.setString(index, itemcode);
			rs = ptmt.executeQuery();
			S201 entity = new S201();
			if (rs != null && rs.next()) {
				entity.setId(rs.getInt("id"));
				entity.setItemcode(rs.getString("itemcode"));
				entity.setItemname(rs.getString("itemname"));
				entity.setType(rs.getString("type"));
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
