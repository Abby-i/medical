package com.gxuwz.medical.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.gxuwz.medical.database.DbUtil;

public abstract class GenericDao<T> {
	
	protected abstract T handle(ResultSet rs)throws SQLException;
	
	//分页查询方法
	public List<T> queryPages(String sql,Integer currentPage, Integer pageSize) {
		
		Connection conn =null;
		PreparedStatement ptmt=null;
		ResultSet rs=null;
		
		 List<T> result = new ArrayList<T>();
		 StringBuilder sb=new StringBuilder();
		 int startNo = (currentPage-1)*pageSize;
		 sb.append(sql);
		 try {
			 conn = DbUtil.getConnection();
			 ptmt = conn.prepareStatement(sb.toString());
			 ptmt.setInt(1, startNo);
			 ptmt.setInt(2, pageSize);
			 rs = ptmt.executeQuery();
			 
			 while(rs.next()) {
				 T t=handle(rs);
				 result.add(t);
			 }
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException();
		}finally {
			DbUtil.close(rs, ptmt, conn);
		}
		return result;
	}
	
	
	/**
	 * 集合查询方法
	 * @param sql
	 * @param params
	 * @return
	 * @throws SQLException
	 */
	public List<T> queryObject(String sql,Object[] params)throws SQLException{
		Connection conn = null;
		PreparedStatement ptmt = null;
		ResultSet rs = null;
		List<T> results = new ArrayList<T>();
		try{
			conn = DbUtil.getConnection();
			ptmt = conn.prepareStatement(sql);
			int index = 1;
			if(params!=null){
				for(Object param:params){
					 if(param instanceof String){
						  ptmt.setString(index++, (String)param);
					  }
					  if(param instanceof Integer){
						  ptmt.setInt(index++, (Integer)param);
					  }
					  if(param instanceof Float){
						  ptmt.setFloat(index++, (Float)param);
					  }
				}
			}
			rs=ptmt.executeQuery();
			while(rs.next()){
				T t=handle(rs);
				results.add(t);
			}
			return results;
		}catch (SQLException e) {
			e.printStackTrace();
			throw new SQLException("执行SQL["+sql+"]失败",e);
		}
	
	}
	
		
}
	
